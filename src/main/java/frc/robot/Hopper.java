package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import java.util.logging.Logger;

public final class Hopper {
    private HoloTable table;
    private WPI_TalonSRX hopper;
    private XboxController operatorGamepad;
    // private WPI_TalonSRX ejector;
    private DigitalInput infraredHopper1;
    private DigitalInput infraredHopper2;
    private DigitalInput infraredIntake;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Hopper() {
        table = HoloTable.getInstance();
        hopper = table.getHopper();
        operatorGamepad = table.getOperatorController();
        infraredHopper1 = table.getInfraredHopper1();
        infraredHopper2 = table.getInfraredHopper2();
        infraredIntake = table.getInfraredIntake();
        // ejector = table.getEjector();
    }

    public void runMotors() {
        double hopperSpeed;
        if (table.getIsDriveTrainAutonomous()) {
            hopperSpeed = 0.0; // TODO: This is just a placeholder.
        } else if (operatorGamepad.getAButton()) {
            // if a is pressed, this is shoot mode, so run the hopper
            hopperSpeed = -0.5;
        } else if (operatorGamepad.getBButton()) {
            // if b is pressed, run the hopper backwards to stop jams
            hopperSpeed = 0.4;
        } else if (operatorGamepad.getBumper(Hand.kRight)) {
            // if right bumper pressed, intake unless hopper sensor think full
            if (!infraredIntake.get()) {
                if (!infraredHopper1.get()) {
                    if (!infraredHopper2.get()) {
                        hopperSpeed = 0.0;
                    } else {
                        hopperSpeed = -0.5;
                    }
                } else {
                    hopperSpeed = -0.5;
                }
            } else {
                hopperSpeed = 0.0;
            }
        } else {
            // no controller pressed, do nothing
            hopperSpeed = 0.0;
        }
        hopper.set(hopperSpeed);
    }
}