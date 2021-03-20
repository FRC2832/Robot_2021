package frc.robot;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class DriveTrain extends SubsystemBase {
  private HoloTable holo = HoloTable.getInstance();
  private CANSparkMax leftFront;
  private CANSparkMax rightFront;
  private CANSparkMax leftRear;
  private CANSparkMax rightRear;
  private DifferentialDrive differentialDrive;
  private XboxController controller;
  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;
  private double driveCoeff;
  

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    leftFront = holo.getDriveLeftFront();
    rightFront = holo.getDriveRightFront();
    leftRear = holo.getDriveLeftRear();
    rightRear = holo.getDriveRightRear();
    controller = holo.getController();
    leftMotors = new SpeedControllerGroup(leftFront, leftRear);
    leftMotors.setInverted(true);
    rightMotors = new SpeedControllerGroup(rightFront, rightRear);
    rightMotors.setInverted(true);
    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void driveTank() {
    differentialDrive.tankDrive(controller.getY(Hand.kLeft)*0.5, controller.getY(Hand.kRight)*0.5, false);
  }

  public void driveTankcbrt() {
    differentialDrive.tankDrive(Math.cbrt(controller.getY(Hand.kLeft)), Math.cbrt(controller.getY(Hand.kRight)), false);
  }

  public void driveTankcube() {
    differentialDrive.tankDrive(Math.pow(controller.getY(Hand.kLeft),3), Math.pow(controller.getY(Hand.kRight),3), false);
  }
  public void driveSpeed(double speed , double rotation){
    differentialDrive.arcadeDrive(speed, rotation);
  }
  public void driveArcade(){
    double xSpeed = 0.5*controller.getY(Hand.kLeft);
    //xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
    double zRotation = -0.25*controller.getX(Hand.kRight);
    //zRotation = Math.copySign(zRotation * zRotation, zRotation);
    differentialDrive.arcadeDrive(xSpeed,zRotation, false );
  }

  public void driveArcadecbrt(){
    differentialDrive.arcadeDrive(Math.cbrt(controller.getY(Hand.kLeft)),Math.cbrt(controller.getX(Hand.kRight)) );
  }

  public void driveArcadecube(){
    differentialDrive.arcadeDrive(Math.pow(controller.getY(Hand.kLeft),3),Math.pow(controller.getX(Hand.kRight),3) );
  }
}
