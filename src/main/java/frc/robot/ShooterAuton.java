package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterAuton {

    private HoloTable holo;
    private DriveTrain driveTrain;
    private boolean isRobotAimed;
    private String msg;

    public ShooterAuton() {
        holo = HoloTable.getInstance();
        driveTrain = new DriveTrain();
        isRobotAimed = false;
        msg = "";
    }

    public void runShooterAuton() {
        msg = "";
        if (holo.getIsDriveTrainAutonomous()) {
            if (!isRobotAimed) {
                msg = "aiming";
                centerRobot();
            }
            /*
            for shooting: 
            if (motor speed is not at target shooting speed) {
                pull code from pressing left bumper in Shooter.java
            } else {
                pull code from pressing A button in Shooter.java
                may have to "press RB" to get next ball in position in shooter - find where RB on the operator gamepad is pressed
            }
            */

            //TODO: add setting speed to 0 at the end of this if statement?
        } else { // shooter not running
            isRobotAimed = false; 
        }
        SmartDashboard.putString("ShooterAuton status", msg);
    }

    public void centerRobot() {
        System.out.println("centering robot");
        Pi.processTargets();
        if (Pi.getMoveLeft()) {
            driveTrain.driveSpeed(0, 0.4); //driveSpeed() is an arcade drive method
            System.out.println("turning left");
        } else if (Pi.getMoveRight()) {
            driveTrain.driveSpeed(0, -0.4);
            System.out.println("turning right");
        } else {
            driveTrain.driveSpeed(0, 0);
            System.out.println("not turning");
            isRobotAimed = true;
            // System.out.println("move1SecDone = false");
            //if (!isFindingPowerCells)
            // move1SecDone = false;
            //else //if (Pi.getHasFoundObjective())
            // autonStep++;
        }
        // motorNew = Pi.getScaledMotorVal();
        // if (motorNew != motorOld) {
        // System.out.println("motor: " + motorNew);
        // }
        // driveTrain.driveTank(motorNew * -1, motorNew);
        // if (Math.abs(Pi.getMotorVal()) < 0.05) {
        //     driveTrain.driveTank(0, 0);
        //     autonStep++;
        // }
        // motorOld = motorNew;
    }

}