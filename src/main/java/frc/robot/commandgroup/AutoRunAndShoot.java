package frc.robot.commandgroup;

import frc.robot.command.*;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoRunAndShoot extends CommandGroup {
    public double speed=51; //inches per second for linear speed of wheels
    public double rpm=speed/6/Math.PI*60*0.75;//rpm speed of motor // 0.75 is a multiplying factor found experimentally
    public  AutoRunAndShoot() {
        //addSequential(new AutoRun(51.0),120/AutoPath.speed); 
        addSequential(new AutoRun(rpm, 2),12/speed); 
        addSequential(new WaitCommand(1));
        addSequential(new AutoShoot(),3);
     }
}
