package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterAuton {

    private HoloTable holo;
    private DriveTrain driveTrain;
    private String msg;
    private WPI_TalonSRX hopper;
    private WPI_TalonSRX ejector;
    private ArrayList<ShotChoice> choices;

    public ShooterAuton() {
        holo = HoloTable.getInstance();
        driveTrain = new DriveTrain();
        hopper = holo.getHopper();
        ejector = holo.getEjector();
        choices = new ArrayList<ShotChoice>();
        choices.add(new ShotChoice(150, 180, 1940, 2240));
    }

    public void runShooterAuton() {
        msg = "";
        if (holo.getIsDriveTrainAutonomous()) {
            Pi.processTargets();
            //TODO: Pick the best shot
            ShotChoice choice = choices.get(0);

            //run all checks
            if (!isRobotAimed()) {
                msg += "aiming ";
                centerRobot();
            } 
            //check for shooter wheel speeds
            if(!isShooterPrimed(choice)) {
                msg += "revup ";
            }

            //always command the shooter wheels, as we need to prep the shot anyways
            commandShooterWheels(choice);
            if(msg.length() == 0) {
                //all checks pass, shoot the balls
                ejector.set(0.7);
                hopper.set(-0.5);
            } else {
                //don't shoot yet
            }
        } else if (holo.getDriverController().getStartButtonReleased()) {
            //stop all commands when driver releases start button
            holo.topPID.setReference(-0, ControlType.kVelocity);
            holo.bottomPID.setReference(0, ControlType.kVelocity);
            ejector.set(0);
            hopper.set(0);
        } else { 
            // start button not touched
            msg = "Not Shooting";
        }
        SmartDashboard.putString("ShooterAuton status", msg);
    }

    public void centerRobot() {
        System.out.println("centering robot");
        driveTrain.driveSpeed(0, Pi.getMove()); //driveSpeed() is an arcade drive method
    }

    public boolean isRobotAimed() {
        if (Math.abs(Pi.getMove())>0.1) {
            return false;
        }
        return true;
    }

    public boolean isShooterPrimed(ShotChoice choice)
    {
        final double ERROR = 0.05; //TODO make this tighter error band?

        //get shooter velocities
        double topVel = Math.abs(holo.getTopShooter().getEncoder().getVelocity());
        double botVel = Math.abs(holo.getBottomShooter().getEncoder().getVelocity());

        //get the percent error
        double topErr = Math.abs((topVel - choice.getTopRpm())/topVel);
        double botErr = Math.abs((botVel - choice.getBottomRpm())/botVel);

        //chcek if either wheel is beyond the amount of allowed error
        if ((topErr > ERROR) || (botErr > ERROR)) {
            return false;
        } else {
            return true;
        }
    }

    public void commandShooterWheels(ShotChoice choice)
    {
        //command the shooter wheels based on our best choice
        holo.topPID.setReference(-choice.getTopCommand(), ControlType.kVelocity);
        holo.bottomPID.setReference(choice.getBottomCommand(), ControlType.kVelocity);
    }
}