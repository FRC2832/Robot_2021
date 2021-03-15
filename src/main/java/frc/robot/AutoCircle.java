package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import com.revrobotics.ControlType;

public class AutoCircle extends Command {
    private int direction=1;//1 = left,2=right, 3 = backwards left, 4 = backwards right
    private double radius=1.0;
    private double gyroangle=1.0;
    private double offset=1.0;
    private double width=1.0;
    private double rpm=1.0;

    public AutoCircle(int direction, double radius, double angle, double offset, double width, double rpm) {
        this.direction=direction;
        this.radius=radius;
        this.gyroangle = angle;
        this.offset = offset;
        this.width = width;
        this.rpm = rpm;




	}

	private HoloTable holo = HoloTable.getInstance();
 
     // Called repeatedly when this Command is scheduled to run
     protected void execute() {
         if(direction==1){
            holo.frontRightPID.setReference(-(rpm), ControlType.kVelocity);
            holo.frontLeftPID.setReference( (rpm/(1 + width/radius) ), ControlType.kVelocity);
            holo.rearRightPID.setReference(-(rpm), ControlType.kVelocity);
            holo.rearLeftPID.setReference( (rpm/(1 + width/radius) ), ControlType.kVelocity);  
         }
         if(direction ==2){
            holo.frontRightPID.setReference(-(rpm/(1+width/radius)), ControlType.kVelocity);
            holo.frontLeftPID.setReference((rpm), ControlType.kVelocity);
            holo.rearRightPID.setReference(-(rpm/(1+width/radius)), ControlType.kVelocity);
            holo.rearLeftPID.setReference((rpm), ControlType.kVelocity);
         }
         if(direction==3){
            holo.frontRightPID.setReference((rpm), ControlType.kVelocity);
            holo.frontLeftPID.setReference( -(rpm/(1 + width/radius) ), ControlType.kVelocity);
            holo.rearRightPID.setReference((rpm), ControlType.kVelocity);
            holo.rearLeftPID.setReference( -(rpm/(1 + width/radius) ), ControlType.kVelocity);  
         }
         if(direction ==4){
            holo.frontRightPID.setReference((rpm/(1+width/radius)), ControlType.kVelocity);
            holo.frontLeftPID.setReference(-(rpm), ControlType.kVelocity);
            holo.rearRightPID.setReference((rpm/(1+width/radius)), ControlType.kVelocity);
            holo.rearLeftPID.setReference(-(rpm), ControlType.kVelocity);
         }
     }
 
    
     // Called once after isFinished returns true
     protected void end() {
        double[] yaw = new double[3];
        HoloTable.getGyro().getYawPitchRoll(yaw);
        System.out.println("|||||||||||||||||||||||||||||||||||||      " +yaw[0]);
     }
 
     // Called when another command which requires one or more of the same
     // subsystems is scheduled to run
     protected void interrupted() {
        end();
     }
 

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        double[] yaw = new double[3];
        HoloTable.getGyro().getYawPitchRoll(yaw);

        if(direction == 1 || direction == 4)
            return yaw[0] >= gyroangle - offset;
        if(direction == 2 || direction == 3)
            return yaw[0] <= gyroangle + offset;
      return false;
    }
    
}
