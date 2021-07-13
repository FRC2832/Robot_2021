// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.command;

import frc.robot.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class TurnTo180 extends Command {
  double targetX, targetY;
  NetworkTable lidarTable;
  NetworkTableEntry lidarX, lidarY, lidarT;
  NetworkTableInstance netInst;
  DriveTrain m_driveTrain;
  boolean finished = false;
  double targetAngle;
  int direction;

  /** Creates a new DriveToCoords. */
  public TurnTo180(DriveTrain driveTrain) {
    m_driveTrain = driveTrain;
    //addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveTrain.driveSpeed(0,0);
    netInst = NetworkTableInstance.getDefault();
    lidarTable = netInst.getTable("lidar");
    lidarX = lidarTable.getEntry("x");
    lidarY = lidarTable.getEntry("y");
    lidarT = lidarTable.getEntry("t");
    targetAngle = 180;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("TARGET ANGLE: " + targetAngle);
    m_driveTrain.driveSpeed(0,(-0.375));
    if((((double)lidarT.getNumber(-1)) - Robot.getInitT()) >= (targetAngle-15.0)){ //Math.toDegrees(Math.atan(c1y/c1x)))
      m_driveTrain.driveSpeed(0,0);
      finished = true;
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end() {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}