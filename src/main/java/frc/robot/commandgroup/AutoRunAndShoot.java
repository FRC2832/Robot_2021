package frc.robot.commandgroup;

import frc.robot.command.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoRunAndShoot extends CommandGroup {
    public double speed = 51.0; // inches per second for linear speed of wheels
    public double rpm = speed / 6 / Math.PI * 60 * 0.75;// rpm speed of motor // 0.75 is a multiplying factor found
                                                        // experimentally

    public AutoRunAndShoot() {
        // addSequential(new AutoRun(51.0),120/AutoPath.speed);
        addSequential(new AutoRun(rpm, 2.0), 12.0 / speed);
        addSequential(new RunShootWheels(), 1.0);
        addSequential(new AutoShoot(), 4.0);
        //after shooting, turn back to center and go
        addSequential(new AutoTurn(40),3);
        addSequential(new AutoRun(rpm, 2.0), 1.2);
    }
}
