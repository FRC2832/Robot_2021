package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import com.revrobotics.ControlType;

public class AutoCircle extends Command {
    private int direction=1;//1 = left,2=right
    private double radius=1.0;

    public AutoCircle(int direction, double radius) {
        this.direction=direction;
        this.radius=radius;

	}

	private HoloTable holo = HoloTable.getInstance();
 
     // Called repeatedly when this Command is scheduled to run
     protected void execute() {
         if(direction==1){
            holo.frontRightPID.setReference(-(AutoPath.rpm), ControlType.kVelocity);
            holo.frontLeftPID.setReference( (AutoPath.rpm/(1 + AutoPath.w/radius) ), ControlType.kVelocity);
            holo.rearRightPID.setReference(-(AutoPath.rpm), ControlType.kVelocity);
            holo.rearLeftPID.setReference( (AutoPath.rpm/(1 + AutoPath.w/radius) ), ControlType.kVelocity);  
         }else if(direction ==2){
            holo.frontRightPID.setReference(-(AutoPath.rpm/(1+AutoPath.w/radius)), ControlType.kVelocity);
            holo.frontLeftPID.setReference((AutoPath.rpm), ControlType.kVelocity);
            holo.rearRightPID.setReference(-(AutoPath.rpm/(1+AutoPath.w/radius)), ControlType.kVelocity);
            holo.rearLeftPID.setReference((AutoPath.rpm), ControlType.kVelocity);
         }
     }
 
    
     // Called once after isFinished returns true
     protected void end() {
     }
 
     // Called when another command which requires one or more of the same
     // subsystems is scheduled to run
     protected void interrupted() {
        end();
     }
 

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }
    
}
