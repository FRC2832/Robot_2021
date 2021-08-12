package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Ingestor {

    private HoloTable holoTable;
    private WPI_TalonSRX intake;
    private XboxController operatorGamepad;
    private DoubleSolenoid dropIntake;
    //private boolean isIntakeDown;

    public Ingestor() {
        holoTable = HoloTable.getInstance();
        intake = holoTable.getIntake();
        operatorGamepad = holoTable.getOperatorController();
        dropIntake = holoTable.getDropIntake();
        //isIntakeDown = false;
    }

    public void runIngestor() {
        /*
         * if (controller.getTriggerAxis(Hand.kLeft) == 1) { intake.set(0.9); } else if
         * (controller.getTriggerAxis(Hand.kRight) == 1) { intake.set(-0.9); } else {
         * intake.set(0.0); }
         */

        if (holoTable.getIsDriveTrainAutonomous()) {

        } else {
            intake.set((operatorGamepad.getTriggerAxis(Hand.kLeft) - operatorGamepad.getTriggerAxis(Hand.kRight)) / 1.0 * 0.9);
            if (operatorGamepad.getYButtonPressed()) {
                dropIntake.set(Value.kForward);
            } else if (operatorGamepad.getXButtonPressed()) {
                dropIntake.set(Value.kReverse);
            }
        }
    }
}