package frc.robot.commandgroup;

import frc.robot.*;
import frc.robot.command.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import com.revrobotics.ControlType;

public class AutoNav3 extends CommandGroup {
    public double speed = 51.0; // inches per second for linear speed of wheels
    public double rpm = speed / 6 / Math.PI * 60 * 0.75;// rpm speed of motor // 0.75 is a multiplying factor found
                                                        // experimentally
    public double w = 26.521; // inches distance between left and right wheels
    public int direction = 1;
    public double r = 30 - w / 2;// radius This should stay fixed
    public double angleOffset;// 12 degree delay from gyro. This may change with the speed and/or radius
    public double angleOffset2;
    public double b = 20.0;

    public AutoNav3() {
        if ((int) speed == 51)
            angleOffset = 12.0;
        if ((int) speed == 75)
            angleOffset = 24.0;

        if ((int) speed == 51)
            angleOffset2 = 2.0;
        if ((int) speed == 75)
            angleOffset2 = 4.0;
        addSequential(new AutoRun(rpm, 1), (30 + 10) / speed);// midline of robot starts 30 inches behind the starting
                                                              // threshold

        double angle = 0.0;
        double gyroAngleDisplacement = 90.0;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45.0);

        bounce(b, 1);

        // addSequential(new AutoRun(rpm,1), b/speed);

        // resetSpeed();

        // addSequential(new AutoRun(rpm,2), b/speed);

        gyroAngleDisplacement = Math.atan(30.0 / 60) * 180 / Math.PI;// fixed constants
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(4, r, angle, angleOffset + angleOffset2, w, rpm), 45.0);

        double dist = Math.sqrt(30.0 * 30.0 + 60.0 * 60.0) + 10.0;// also fixed constants
        addSequential(new AutoRun(rpm, 2.0), dist / speed);

        gyroAngleDisplacement = 180 - Math.atan(30.0 / 60.0) * 180 / Math.PI;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(4, r, angle, angleOffset, w, rpm), 45.0);

        addSequential(new AutoRun(rpm, 2.0), 60.0 / speed);
        bounce(b, 2);
        addSequential(new AutoRun(rpm, 1.0), (60.0 + 10.0) / speed);

        // addSequential(new AutoRun(rpm,2), (60+b)/speed);

        // addSequential(new AutoRun(rpm,1), (60+b)/speed);

        gyroAngleDisplacement = 90.0;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45.0);

        addSequential(new AutoRun(rpm, 1), (30 + 5) / speed);

        gyroAngleDisplacement = 90.0;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45.0);

        addSequential(new AutoRun(rpm, 1), (60 + 10) / speed);
        bounce(b, 1);

        addSequential(new AutoRun(rpm, 2), 10 / speed);

        // addSequential(new AutoRun(rpm,1), (60 + b)/speed);

        // resetSpeed();

        // addSequential(new AutoRun(rpm,2), b/speed);

        gyroAngleDisplacement = 90.0;// fixed constants
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(4, r, angle, angleOffset, w, rpm), 45.0);

        addSequential(new AutoRun(rpm, 2), 30.0 / speed);// midline of robot starts 30 inches behind the starting
                                                         // threshold

    }

    private HoloTable holo = HoloTable.getInstance();

    public void resetSpeed() {
        holo.frontRightPID.setReference(-0.0, ControlType.kVelocity);
        holo.frontLeftPID.setReference(0.0, ControlType.kVelocity);
        holo.rearRightPID.setReference(-0.0, ControlType.kVelocity);
        holo.rearLeftPID.setReference(0.0, ControlType.kVelocity);
    }

    public void bounce(double d, int dir) {
        for (int i = 0; i <= d - 1; i++) {
            addSequential(new AutoRun(rpm * (1 - i / d), dir), 1 / (speed * (1 - i / d)));
        }

        addSequential(new AutoRun(0, dir), 0.05);
        addSequential(new AutoRun(rpm, dir % 2 + 1), d / (speed));

    }
}