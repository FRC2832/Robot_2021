package frc.robot.command;

import frc.robot.*;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.command.Command;

public class AutoShoot extends Command {

    private HoloTable holo;

    public AutoShoot() {
        holo = HoloTable.getInstance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        holo.topPID.setReference(-140.0, ControlType.kVelocity);
        holo.bottomPID.setReference(190.0, ControlType.kVelocity);
        holo.getEjector().set(0.7);
    }

    // Called once after isFinished returns true
    protected void end() {
        holo.topPID.setReference(0.0, ControlType.kVelocity);
        holo.bottomPID.setReference(0.0, ControlType.kVelocity);
        holo.getEjector().set(0.0);
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
