package frc.robot.command;

import frc.robot.*;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.command.Command;

public class AutoRun extends Command {
    private double rpm;
    private double direction = 1.0;// 1 is forward(towards shooter), 2 is backwards
    private HoloTable holo = HoloTable.getInstance();

    public AutoRun(double rpmspeed, double direction) {
        this.rpm = rpmspeed;
        this.direction = direction;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if ((int) direction == 1) {
            holo.frontLeftPID.setReference(rpm, ControlType.kVelocity);
            holo.frontRightPID.setReference(-rpm, ControlType.kVelocity);
            holo.rearLeftPID.setReference(rpm, ControlType.kVelocity);
            holo.rearRightPID.setReference(-rpm, ControlType.kVelocity);
        }
        if ((int) direction == 2) {
            holo.frontLeftPID.setReference(-rpm, ControlType.kVelocity);
            holo.frontRightPID.setReference(rpm, ControlType.kVelocity);
            holo.rearLeftPID.setReference(-rpm, ControlType.kVelocity);
            holo.rearRightPID.setReference(rpm, ControlType.kVelocity);
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
