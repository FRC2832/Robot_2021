package frc.robot.commandgroup;

import frc.robot.command.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoNav2 extends CommandGroup{
    public double speed=51; //inches per second for linear speed of wheels
    public double rpm=speed/6/Math.PI*60*0.75;//rpm speed of motor // 0.75 is a multiplying factor found experimentally
    public double w=26.521; //inches distance between left and right wheels
    public int direction =1;
    public double r =30 - w/2 ;//radius
    public double angleOffset = 12;//12 degree delay from gyro. This may change with the speed and/or radius

    public AutoNav2(){

        addSequential(new AutoRun(rpm,1), 30/speed);//midline of robot starts 30 inches behind the starting threshold

        double angle = 0;
        double gyroAngleDisplacement = 90;
        angle += gyroAngleDisplacement; 
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45);//45 is meaningless, the robot stops when it completes the angle

        gyroAngleDisplacement = -90;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(2, r, angle, angleOffset, w, rpm), 45);

        addSequential(new AutoRun(rpm,1), 120/speed);

        gyroAngleDisplacement = -90;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(2, r, angle, angleOffset, w, rpm), 45);

        gyroAngleDisplacement = 360;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45);

        gyroAngleDisplacement = -90;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(2, r, angle, angleOffset, w, rpm), 45);

        addSequential(new AutoRun(rpm,1), 120/speed);

        gyroAngleDisplacement = -90;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(2, r, angle, angleOffset, w, rpm), 45);

        gyroAngleDisplacement = 90;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45);
        addSequential(new AutoRun(rpm,1), 40/speed);        
    }
}
