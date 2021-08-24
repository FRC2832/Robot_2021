package frc.robot.command;

import frc.robot.*;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.command.Command;

public class RunShootWheels extends Command {

    private HoloTable holo;

    public RunShootWheels() {
        holo = HoloTable.getInstance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        holo.topPID.setReference(-180.0, ControlType.kVelocity);
        holo.bottomPID.setReference(180.0, ControlType.kVelocity);
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
