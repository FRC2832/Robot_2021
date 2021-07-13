package frc.robot.command;

import frc.robot.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//THIS CLASS IS OBSOLETE, TurnToCoords NOW DOES TURNS IN EITHER DIRECTION
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnToCoordsLeft extends CommandBase {
  double targetX, targetY;
  NetworkTable lidarTable;
  NetworkTableEntry lidarX, lidarY, lidarT;
  NetworkTableInstance netInst;
  DriveTrain m_driveTrain;
  boolean finished = false;
  double targetAngle;

  /** Creates a new DriveToCoords. */
  public TurnToCoordsLeft(DriveTrain driveTrain, double targetX, double targetY) {
    this.targetX = targetX;
    this.targetY = targetY;
    m_driveTrain = driveTrain;
    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    m_driveTrain.driveSpeed(0,0);
    System.out.println("TURNING TO: " + targetX + ", " + targetY);
    netInst = NetworkTableInstance.getDefault();
    lidarTable = netInst.getTable("lidar");
    lidarX = lidarTable.getEntry("x");
    lidarY = lidarTable.getEntry("y");
    lidarT = lidarTable.getEntry("t");
    targetAngle = Math.toDegrees(Math.atan( (targetY - (double)lidarY.getNumber(-1))/(targetX - (double)lidarX.getNumber(-1))));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("TURNING TO: " + targetX + ", " + targetY);
    System.out.println("TARGET ANGLE: " + targetAngle);
    m_driveTrain.driveSpeed(0,0.35);
      if((((double)lidarT.getNumber(-1)) - Robot.getInitT()) <= (targetAngle)){ //Math.toDegrees(Math.atan(c1y/c1x)))
        m_driveTrain.driveSpeed(0,0);
        finished = true;
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
