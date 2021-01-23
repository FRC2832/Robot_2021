# --------------------------------------------------------------------------
# This script sets up the RPLidar sensor and gets the distance data
# The distance information from the sensor is in mm.  This data is converted
# to inches when it is loaded into the scan_data array.  The scan_data array
# contains elements 0 - 359, corresponding to the azimuth angle that the 
# distance information was obtained.  For example, scan_data[0] is the 
# distance straight in front of the robot.  (need to confirm:  sensor rotates
# clockwise, 90 degrees is to the right, 180 behind, 270 to the left)
#
# Per the protocol spec, there needs to be a process of checking the health
# of the sensor and a method to reset the sensor if necessary, before using
# the information.  This is just to be as robust as possible and help 
# recover from faulted conditions.  
#
# Intended for Python3.  Sensor demonstrated working on Raspberry Pi 3B+
#
# Info on library at:  https://github.com/dastels/adafruit_rplidar
# --------------------------------------------------------------------------

import os
from math import cos, sin, pi, floor, sqrt, atan
from adafruit_rplidar import RPLidar
import threading
import time
from networktables import NetworkTables
 
scan_data = [0]*360

Not_Connected = True

#------------------------------------------------------------------------
# Connect to the RPLidar Sensor.  If the device isn't ready or plugged
# in it will throw an exception.  This loop will handle the exception
# and keep the code from crashing.  Once the RPLidar sensor is connected
# code execution will continue
#------------------------------------------------------------------------
while Not_Connected:
    try:
        PORT_NAME = '/dev/ttyUSB0'
        lidar = RPLidar(None, PORT_NAME)
        Not_Connected = False
        print('LIDAR Connection Established')
        time.sleep(5)

    except KeyboardInterrupt:
        print('Stopping.')
    
    except:
        print('Waiting for LIDAR Connection')
        time.sleep(1)


#----------------------------------------------------------------------------
# Initialize the connection to the RoboRIO network table server
#----------------------------------------------------------------------------
cond = threading.Condition()
notified = [False]

def connectionListener(connected, info):
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()

NetworkTables.initialize(server='10.28.32.2')
NetworkTables.addConnectionListener(connectionListener, immediateNotify=True)

with cond:
    print("Waiting for Network Table Server")
    if not notified[0]:
        cond.wait()

#-----------------------------------------------------------------------------
# System is connected now.  Start reading LIDAR data and sending on network
#-----------------------------------------------------------------------------
print("Connected to Network Table Server")
table = NetworkTables.getTable("datatable")
Run = True

while Run:
    
# --------------------------------------------------------------------------
# Get the scan data from the LIDAR using the iter_scans() library function
# --------------------------------------------------------------------------
    try:
        print(lidar.info)
        print(lidar.health)
        for scan in lidar.iter_scans():
                for (_, angle, distance) in scan:
                    scan_data[min([359, floor(angle)])] = distance / 25.4
        

# --------------------------------------------------------------------------
# Calculate the angle of the wall in front of robot
# Angles sent to the trig functions must be in radians, display to driver
# should be in degrees.  Use inches for distances (LIDAR distance is mm)
# FOV (field of view) is the angular distance to use when evaluating the wall
# angle, the angle is centered about azimuth 0 deg (straight ahead). 
# ONLY USE EVEN NUMBERS FOR FOV
# d2 is to the right of center, d3 is to the left of center
# --------------------------------------------------------------------------
                FOV_deg = 20
                FOV_rad = FOV_deg*pi/180
                index_adj = FOV_deg/2
                d1 = scan_data[0]
                d2 = scan_data[int(index_adj)]
                d3 = scan_data[int(359-index_adj+1)]

                if (d1>0 and d2>0 and d3>0):

                    x = d1 * cos(FOV_rad/2)
                    z = sqrt((d1*d1) - (x*x))
                    beta1 = 90 - (FOV_deg/2)

# ---------------------------------------------------------------------------
# Right side angle (use d2)
# ---------------------------------------------------------------------------
                    if d2 >= x:
                        y = d2 - x
                    else:
                        y = x - d2

                    beta2 = atan(y/z)*180/pi

                    if d2 >= x:
                        wall_angle_right = beta1+beta2
                    else:
                        wall_angle_right = 180-(beta1-beta2)

# ---------------------------------------------------------------------------
# Left side angle (use d3)
# ---------------------------------------------------------------------------
                    if d3 >= x:
                        y = d3 - x
                    else:
                        y = x - d3

                    beta2 = atan(y/z)*180/pi

                    if d3 >= x:
                        wall_angle_left = beta1+beta2
                    else:
                        wall_angle_left = 180-(beta1-beta2)


# ---------------------------------------------------------------------------
# Print out info to screen
# Distance ahead is scan_data[0], right is [90], behind is [180], left is [270]
# Wall angle is wall_angle_left and wall_angle_right
# ---------------------------------------------------------------------------
#                    print("Wall Angle Right: ", int(wall_angle_right), "  Wall Angle Left: ", int(wall_angle_left))
#                    print("Distance To Target: ", int(scan_data[0]))
                    table.putNumber("distance0", scan_data[0])
                    table.putNumber("distance45", scan_data[45])
                    table.putNumber("distance90", scan_data[90])
                    table.putNumber("distance135", scan_data[135])
                    table.putNumber("distance180", scan_data[180])
                    table.putNumber("distance225", scan_data[225])
                    table.putNumber("distance270", scan_data[270])
                    table.putNumber("distance315", scan_data[315])
                    table.putNumber("L_angle", wall_angle_left)
                    table.putNumber("R_angle", wall_angle_right)


    except KeyboardInterrupt:
        print('Stopping.')
        lidar.stop()
        lidar.disconnect()
        Run = False
        
    except:
        print('Connection to LIDAR Sensor Lost')
            