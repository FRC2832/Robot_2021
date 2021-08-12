package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public final class BallCount {

    private HoloTable table;

    private DigitalInput infraredHopper1;
    private DigitalInput infraredHopper2;
    // private DigitalInput infraredHopper3;
    // private DigitalInput infraredHopper4;
    // private DigitalInput infraredHopper5;
    // private DigitalInput infraredIntake;

    private WPI_TalonSRX intake;
    // private WPI_TalonSRX hopper;
    // private WPI_TalonSRX color;

    public BallCount() {
        table = HoloTable.getInstance();
        infraredHopper1 = table.getInfraredHopper1();
        infraredHopper2 = table.getInfraredHopper2();
        // infraredIntake = table.getInfraredIntake();

        // hopper = table.getHopper();
        intake = table.getIntake();
    }

    public String ballCount(DigitalInput dI) {
        if (dI.get()) {
            return "On";
        }
        return "Off";

    }

    public void safetyCheck() {
        if (!infraredHopper1.get() && !infraredHopper2.get()) {
            intake.set(0);
        }
    }
}