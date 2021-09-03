package frc.robot;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class DriveTrain extends SubsystemBase {
    private HoloTable holo;
    private CANSparkMax leftFront;
    private CANSparkMax rightFront;
    private CANSparkMax leftRear;
    private CANSparkMax rightRear;
    private DifferentialDrive differentialDrive;
    private XboxController driverController;
    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    /**
     * Creates a new DriveTrain.
     */
    public DriveTrain() {
        holo = HoloTable.getInstance();
        leftFront = holo.getDriveLeftFront();
        rightFront = holo.getDriveRightFront();
        leftRear = holo.getDriveLeftRear();
        rightRear = holo.getDriveRightRear();
        driverController = holo.getDriverController();
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
        differentialDrive.tankDrive(driverController.getY(Hand.kLeft) * 0.5, driverController.getY(Hand.kRight) * 0.5, false);
    }

    public void driveTankcbrt() {
        differentialDrive.tankDrive(Math.cbrt(driverController.getY(Hand.kLeft)), Math.cbrt(driverController.getY(Hand.kRight)),
                false);
    }

    public void driveTankcube() {
        differentialDrive.tankDrive(Math.pow(driverController.getY(Hand.kLeft), 3.0), Math.pow(driverController.getY(Hand.kRight), 3.0),
                false);
    }

    public void driveSpeed(double speed, double rotation) {
        differentialDrive.arcadeDrive(speed, rotation);
    }

    public void driveArcade() {
        double xSpeed = 0.5 * driverController.getY(Hand.kLeft);
        // xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
        double zRotation = -0.25 * driverController.getX(Hand.kRight);
        // zRotation = Math.copySign(zRotation * zRotation, zRotation);
        differentialDrive.arcadeDrive(xSpeed, zRotation, false);
    }

    public void driveArcadecbrt() {
        differentialDrive.arcadeDrive(Math.cbrt(driverController.getY(Hand.kLeft)), Math.cbrt(driverController.getX(Hand.kRight)));
    }

    public void driveArcadecube() {
        differentialDrive.arcadeDrive(Math.pow(driverController.getY(Hand.kLeft), 3.0),
                Math.pow(driverController.getX(Hand.kRight), 3.0));
    }

    public void runDriveTrain() {
        if (driverController.getStartButton()) {
            // Do auto stuff
            // Tell other subsystems we're doing auto stuff.
            holo.setIsDriveTrainAutonomous(true);
            
            
        } else {
            holo.setIsDriveTrainAutonomous(false);
            driveTank();
        }
    }

    public void setBrakeMode(boolean brake) {
        IdleMode mode;

        if (brake == true) {
            mode = IdleMode.kBrake;
        } else {
            mode = IdleMode.kCoast;
        }

        leftFront.setIdleMode(mode);
        rightFront.setIdleMode(mode);
        leftRear.setIdleMode(mode);
        rightRear.setIdleMode(mode);
    }
}
