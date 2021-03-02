// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveToCoords extends CommandBase {
  double targetX, targetY;
  NetworkTable lidarTable;
  NetworkTableEntry lidarX, lidarY, lidarT;
  NetworkTableInstance netInst;
  private final DriveTrain m_driveTrain;
  boolean finished = false;
  int direction;

  public DriveToCoords(DriveTrain driveTrain, double targetX, double targetY) {
    m_driveTrain = driveTrain;
    this.targetX = targetX;
    this.targetY = targetY;
    addRequirements(m_driveTrain);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    
    System.out.println("DRIVING TO: " + targetX + ", " + targetY);
    netInst = NetworkTableInstance.getDefault();
    lidarTable = netInst.getTable("lidar");
    lidarX = lidarTable.getEntry("x");
    lidarY = lidarTable.getEntry("y");
    lidarT = lidarTable.getEntry("t");

    if((targetX - ((double)lidarX.getNumber(-1))) < 0){
      direction = -1;
    }
    else {
      direction = 1;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("DRIVING TO: " + targetX + ", " + targetY);
    m_driveTrain.driveSpeed(-0.5,0);
    if(direction == 1){
      if(((((double)lidarX.getNumber(-1)) - Robot.getInitX()) >= targetX)){ // || ((((double)lidarY.getNumber(-1)) - Robot.getInitY()) >= targetY))
        m_driveTrain.driveSpeed(0,0);
          finished = true;
      }
    }
    else{
      if(((((double)lidarX.getNumber(-1)) - Robot.getInitX()) <= targetX )){ //|| ((((double)lidarY.getNumber(-1)) - Robot.getInitY()) = targetY))
        m_driveTrain.driveSpeed(0,0);
          finished = true;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
