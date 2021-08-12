package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Ingestor {

    private HoloTable holoTable;
    private WPI_TalonSRX intake;
    private XboxController driverGamepad;
    private DoubleSolenoid dropIntake;
    //private boolean isIntakeDown;

    public Ingestor() {
        holoTable = HoloTable.getInstance();
        intake = holoTable.getIntake();
        driverGamepad = holoTable.getDriverController();
        dropIntake = holoTable.getDropIntake();
        //isIntakeDown = false;
    }

    public void runIngestor() {
        /*
         * if (controller.getTriggerAxis(Hand.kLeft) == 1) { intake.set(0.9); } else if
         * (controller.getTriggerAxis(Hand.kRight) == 1) { intake.set(-0.9); } else {
         * intake.set(0.0); }
         */
        intake.set((driverGamepad.getTriggerAxis(Hand.kLeft) - driverGamepad.getTriggerAxis(Hand.kRight)) / 1.0 * 0.9);
        if (driverGamepad.getYButtonPressed()) {
            dropIntake.set(Value.kForward);
        } else if (driverGamepad.getXButtonPressed()) {
            dropIntake.set(Value.kReverse);
        }
    }
}