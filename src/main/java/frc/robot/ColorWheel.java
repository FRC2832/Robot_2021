package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class ColorWheel {
    private HoloTable holo;
    private XboxController operatorGamepad;
    private TalonSRX colorMotor;
    

    public ColorWheel() {
        holo = HoloTable.getInstance();
        operatorGamepad = holo.getOperatorController();
        colorMotor = new TalonSRX(10);
    }

    public void periodic() {
        //triggers always read 0-1
        double leftTrig = operatorGamepad.getTriggerAxis(Hand.kLeft);
        double rightTrig = operatorGamepad.getTriggerAxis(Hand.kRight);
        double speed;
        final double DEADBAND = 0.2;

        if (leftTrig > DEADBAND) {
            speed = -leftTrig;
        } else if (rightTrig > DEADBAND) {
            speed = rightTrig;
        } else {
            speed = 0;
        }

        //max out speed at 55% to make sure we don't go faster than 1 rev/sec
        colorMotor.set(TalonSRXControlMode.PercentOutput, speed * 0.55);
    }
}
