package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoNav3 extends CommandGroup{
    public double speed=51; //inches per second for linear speed of wheels
    public double rpm=speed/6/Math.PI*60*0.75;//rpm speed of motor // 0.75 is a multiplying factor found experimentally
    public double w=26.521; //inches distance between left and right wheels
    public int direction =1;
    public double r =30 - w/2 ;//radius This should stay fixed
    public double angleOffset = 12;//12 degree delay from gyro. This may change with the speed and/or radius
    public double bounce=20;
    
    public AutoNav3(){

        addSequential(new AutoRun(rpm,1), 30/speed);//midline of robot starts 30 inches behind the starting threshold

        double angle = 0;
        double gyroAngleDisplacement = 90;
        angle += gyroAngleDisplacement; 
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45);

        addSequential(new AutoRun(rpm,1), bounce/speed);
        
        addSequential(new AutoRun(rpm,2), bounce/speed);

        gyroAngleDisplacement = Math.atan(30/60)*180/Math.PI;//fixed constants
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(4, r, angle, angleOffset, w, rpm), 45);

        double dist = Math.sqrt(30*30 + 60*60);//also fixed constants
        addSequential(new AutoRun(rpm,2), dist/speed);

        gyroAngleDisplacement = 180 - Math.atan(30/60)*180/Math.PI;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(4, r, angle, angleOffset, w, rpm), 45);

        addSequential(new AutoRun(rpm,2), (60 + bounce)/speed);
        
        addSequential(new AutoRun(rpm,1), (60 + bounce)/speed);

        gyroAngleDisplacement = 90;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45);

        addSequential(new AutoRun(rpm,1), 30/speed);

        gyroAngleDisplacement = 90;
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1, r, angle, angleOffset, w, rpm), 45);

        addSequential(new AutoRun(rpm,1), (60 + bounce)/speed);

        addSequential(new AutoRun(rpm,2), bounce/speed);

        gyroAngleDisplacement = 90;//fixed constants
        angle += gyroAngleDisplacement;
        addSequential(new AutoCircle(4, r, angle, angleOffset, w, rpm), 45);

        addSequential(new AutoRun(rpm,2), 30/speed);//midline of robot starts 30 inches behind the starting threshold

    }
}
