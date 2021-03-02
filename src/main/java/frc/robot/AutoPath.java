package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class AutoPath extends CommandGroup{
    public static double speed=51; //inches per second for linear speed of wheels
    public static double rpm=speed/6/Math.PI*60*0.75;//rpm speed of motor // 0.75 is a multiplying factor found experimentally
    public static double w=26.521; //inches distance between left and right wheels
    public static int direction =1;
    public static double radius1 =46.5;//true radius of circle
    public static double radius2 =43.5;//true radius of circle
    public static double radius3 =46;//true radius of circle
    public static double r =4;//radius used in AutoCircle to get the true radius
    private double verticalDistance=60, horizontalDistance=90;

    public AutoPath(){

        addSequential(new AutoRun(rpm), 106/speed);//straight line distance to first cone is 106 inches
        //Robot starts above the marker in circle of radius radius and completes enough of arc until its align
        //with the internal tangent of the 2 circles around both markers
        double theta=Math.atan(horizontalDistance/verticalDistance);
        double alpha=Math.acos(2*radius1/Math.sqrt(verticalDistance*verticalDistance+horizontalDistance*horizontalDistance));//a is vertical distance between markers in inches
        double time =(radius1+w)*(2*Math.PI+theta-alpha)/speed;
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7 "+time);
        addSequential(new AutoCircle(2,r), 493/speed);//493.0
        //The robot travels on the internal tangent line between the 2 circles of radius (radius) around the markers
        addSequential(new AutoRun(rpm), 65/speed);//68
        System.out.println("88888888888888 "+ calculateTimeBasedOnArcAngle(15.0/8*Math.PI-alpha+theta,radius2)*speed);
        addSequential(new AutoCircle(1,r), 444.2/speed);
        System.out.println("111111111111111 "+ (Math.sqrt(2)*horizontalDistance-24));
        addSequential(new AutoRun(rpm), 100/speed);
        System.out.println("2222222222222 "+ calculateTimeBasedOnArcAngle(15*Math.PI/8,radius3)*speed);
        addSequential(new AutoCircle(1,r),305.0/speed);//426.36
        //addSequential(new AutoRun(rpm), 300/speed);//
        addSequential(new TurnTo180(Robot.driveTrain));//
        addSequential(new DriveToOrigin(Robot.driveTrain));//
    }
    private double calculateTimeBasedOnArcAngle(double theta,double rad){
        double time =(rad+w)*(theta)/speed;
        return time;
    }
}
