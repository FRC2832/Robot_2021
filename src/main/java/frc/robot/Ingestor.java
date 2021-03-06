package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Ingestor {

    HoloTable holoTable = HoloTable.getInstance();
    WPI_TalonSRX intake = holoTable.getIntake();
    public XboxController gamepad1 = holoTable.getController();
    DoubleSolenoid dropIntake = holoTable.getDropIntake();
    XboxController controller=holoTable.getController();
    boolean intakeDown = false;

    public void runIngestor() {
        /*if (controller.getTriggerAxis(Hand.kLeft) == 1) {
            intake.set(0.9);
        } else if (controller.getTriggerAxis(Hand.kRight) == 1) {
            intake.set(-0.9);
        } else {
            intake.set(0.0);
        }*/
        intake.set((controller.getTriggerAxis(Hand.kLeft) -controller.getTriggerAxis(Hand.kRight))/1.0 * 0.9) ;

        if (gamepad1.getYButtonPressed()) {
            dropIntake.set(Value.kForward);
        }
        if (gamepad1.getXButtonPressed()) {
            dropIntake.set(Value.kReverse);
        }
    }
}