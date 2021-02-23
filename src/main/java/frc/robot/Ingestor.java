package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Ingestor {

    HoloTable holoTable = HoloTable.getInstance();
    WPI_TalonSRX intake = holoTable.getIntake();
    public XboxController gamepad1 = holoTable.getController();
    DoubleSolenoid dropIntake = holoTable.getDropIntake();
    Joystick joystick = holoTable.getJoystickRight();
    boolean intakeDown = false;

    public void runIngestor() {
        if (joystick.getPOV() == 180) {
            intake.set(0.9);
        } else if (joystick.getPOV() == 0) {
            intake.set(-0.9);
        } else {
            intake.set(0.0);
        }

        if (gamepad1.getYButtonPressed()) {
            dropIntake.set(Value.kForward);
        }
        if (gamepad1.getXButtonPressed()) {
            dropIntake.set(Value.kReverse);
        }
    }
}