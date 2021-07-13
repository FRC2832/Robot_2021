package frc.robot.commandgroup;

import frc.robot.command.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoNav1 extends CommandGroup{
    public double speed=51; //inches per second for linear speed of wheels
    public double rpm=speed/6/Math.PI*60*0.75;//rpm speed of motor // 0.75 is a multiplying factor found experimentally
    public double w=26.521; //inches distance between left and right wheels
    public int direction =1;
    public double r =30 - w/2;//radius
    private double v=60, h=90;
    public double angleOffset = 14;//12 degree delay from gyro. This may change with the speed and/or radius

    public AutoNav1(){

        addSequential(new AutoRun(rpm,1), 120/speed);//straight line distance to first cone is 106 inches
        //Robot starts above the marker in circle of radius radius and completes enough of arc until its align
        //with the internal tangent of the 2 circles around both markers
        double theta=Math.atan(h/v);
        double alpha=Math.acos((2*r + w)/Math.sqrt(v*v+h*h));//a is vertical distance between markers in inches
        double gyroangle = -(2*Math.PI+theta-alpha) * 180.0/Math.PI;
        addSequential(new AutoCircle(2,r, gyroangle, angleOffset, w, rpm), 45);

        //The robot travels on the internal tangent line between the 2 circles of radius (radius) around the markers
        double dist = Math.sqrt(h*h + v*v - (2*r + w) * (2*r + w));
        addSequential(new AutoRun(rpm,1), dist/speed);

        double gyroAngleDisplacement = (7.0/4*Math.PI-alpha+theta)*180/Math.PI; //robot turns left
        gyroangle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1,r, gyroangle, angleOffset -4, w, rpm), 45);

         dist = Math.sqrt(v*v + v*v );
        addSequential(new AutoRun(rpm,1), dist/speed);

        gyroAngleDisplacement = (5.0*Math.PI/4)*180/Math.PI;
        gyroangle += gyroAngleDisplacement;
        addSequential(new AutoCircle(1,r, gyroangle, angleOffset+1, w, rpm),45);
        
        addSequential(new AutoRun(rpm,1), 240/speed);//
        
    }
 
}
