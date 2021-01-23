package frc.robot;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
  private HoloTable holo = HoloTable.getInstance();
  private WPI_TalonSRX driveTurn;
  private CANSparkMax leftFront;
  private CANSparkMax rightFront;
  private CANSparkMax leftRear;
  private CANSparkMax rightRear;
  private DifferentialDrive differentialDrive;
  private PigeonIMU gyro;
  private XboxController controller;
  //private DoubleSolenoid turnSolenoid;
  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;
  private Joystick joystickLeft;
  private Joystick joystickRight;
  private int targetPixel = 640;
  private double driveCoeff;
  

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    driveTurn = holo.getDriveTurn();
    leftFront = holo.getDriveLeftFront();
    rightFront = holo.getDriveRightFront();
    leftRear = holo.getDriveLeftRear();
    rightRear = holo.getDriveRightRear();
    gyro = holo.getGyro();
    controller = holo.getController();
    joystickLeft = holo.getJoystickLeft();
    joystickRight = holo.getJoystickRight();
    //turnSolenoid = holo.getTurnSolenoid();
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
    if (joystickRight.getRawButton(2)){
      driveCoeff = .3;
    } else {
      driveCoeff = 1;
    }
    differentialDrive.tankDrive(driveCoeff * Math.pow(joystickLeft.getY(), 1), driveCoeff * Math.pow(joystickRight.getY(), 1) , false);
  }

  public void rotate(double rotateSpeed) {
    //differentialDrive.arcadeDrive(0, rotateSpeed);
  }
  /*
   * public void driveArcade() {
   * differentialDrive.arcadeDrive(controller.getRawAxis(0),
   * controller.getRawAxis(4), true); Not Being Used }
   */


  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

  /*public void autoAlign(int visionCenter) {
    if (joystickRight.getRawButton(3)) {
      if (visionCenter - targetPixel >= 10) {
        while (visionCenter - targetPixel >= 10) {
          rotate(0.25);
        }
      }
      if (visionCenter - targetPixel <= -10) {
        while (visionCenter - targetPixel <= -10) {
          rotate(-0.25);
        }
      }

      if(Math.abs(visionCenter - targetPixel) <= 10){
        rotate(0);
      }

    }
  }*/
}
