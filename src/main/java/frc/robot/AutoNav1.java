package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoNav1 extends CommandGroup {
    public  double speed=51; //inches per second for linear speed of wheels
    public  double rpm=speed/6/Math.PI*60*0.75;//rpm speed of motor // 0.75 is a multiplying factor found experimentally
    public  double w=26.521; //inches distance between left and right wheels
    public  int direction =1;
    public  double radius1 =46;//true radius of circle
    public  double radius2 =43.5;//true radius of circle
    public  double radius3 =46;//true radius of circle
    public  double r =4;//radius used in AutoCircle to get the true radius
    private double verticalDistance=60, horizontalDistance=90;

    public void execute(){
        addSequential(new AutoRun(), 106/speed);//straight line distance to first cone is 106 inches
        //Robot starts above the marker in circle of radius radius and completes enough of arc until its align
        //with the internal tangent of the 2 circles around both markers
        double theta=Math.atan(horizontalDistance/verticalDistance);
        double alpha=Math.acos(2*radius1/Math.sqrt(verticalDistance*verticalDistance+horizontalDistance*horizontalDistance));//a is vertical distance between markers in inches
        double time =(radius1+w)*(2*Math.PI+theta-alpha)/speed;
        addSequential(new AutoCircle(2,r), time);
        //The robot travels on the internal tangent line between the 2 circles of radius (radius) around the markers
        addSequential(new AutoRun(), Math.sqrt(horizontalDistance*horizontalDistance+verticalDistance*verticalDistance-4 *(radius1*radius1))/speed);
        addSequential(new AutoCircle(1,r), calculateTimeBasedOnArcAngle(15.0/8*Math.PI-alpha+theta,radius2));
        addSequential(new AutoRun(), (Math.sqrt(2)*horizontalDistance-24)/speed);
        addSequential(new AutoCircle(1,r), calculateTimeBasedOnArcAngle(15*Math.PI/8,radius3));
        addSequential(new AutoRun(), 300/speed);//
    }
    private double calculateTimeBasedOnArcAngle(double theta,double rad){
        double time =(rad+w)*(theta)/speed;
        return time;
    }

}
