package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoRunAndShoot extends CommandGroup {
    public  AutoRunAndShoot() {
        addSequential(new AutoRun(),120/AutoPath.speed); 
        addSequential(new WaitCommand(1));
        addSequential(new AutoShoot(),2);
        addSequential(new AutoShoot(),2);
        addSequential(new AutoShoot(),2); 
     }
}
