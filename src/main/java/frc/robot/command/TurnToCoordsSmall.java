package frc.robot.command;

import frc.robot.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

public class TurnToCoordsSmall extends Command {
    private double targetX, targetY;
    private NetworkTable lidarTable;
    private NetworkTableEntry lidarX, lidarY, lidarT;
    private NetworkTableInstance netInst;
    private DriveTrain driveTrain;
    private boolean isFinished;
    private double targetAngle;
    private int direction;

    /** Creates a new DriveToCoords. */
    public TurnToCoordsSmall(DriveTrain driveTrain, double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.driveTrain = driveTrain;
        // addRequirements(m_driveTrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        driveTrain.driveSpeed(0.0, 0.0);
        System.out.println("TURNING TO: " + targetX + ", " + targetY);
        netInst = NetworkTableInstance.getDefault();
        lidarTable = netInst.getTable("lidar");
        lidarX = lidarTable.getEntry("x");
        lidarY = lidarTable.getEntry("y");
        lidarT = lidarTable.getEntry("t");
        targetAngle = Math.toDegrees(
                Math.atan((targetY - (double) lidarY.getNumber(-1)) / (targetX - (double) lidarX.getNumber(-1))));
        if ((targetX - (double) lidarX.getNumber(-1) < 0.0)) {
            targetAngle += 180.0;
        }
        direction = -1;
        if (targetAngle < 0.0)
            direction *= -1;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("TARGET ANGLE: " + targetAngle);
        System.out.println("TURNING TO: " + targetX + ", " + targetY);
        driveTrain.driveSpeed(0.0, direction * 0.35);

        if (direction == -1 && (((double) lidarT.getNumber(-1)) - Robot.getInitT()) >= (targetAngle)) { // Math.toDegrees(Math.atan(c1y/c1x)))
            driveTrain.driveSpeed(0.0, 0.0);
            isFinished = true;
        } else if ((((double) lidarT.getNumber(-1)) - Robot.getInitT()) <= (targetAngle)) { // Math.toDegrees(Math.atan(c1y/c1x)))
            driveTrain.driveSpeed(0.0, 0.0);
            isFinished = true;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end() {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
