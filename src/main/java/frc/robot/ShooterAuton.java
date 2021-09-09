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
        choices.add(new ShotChoice(150,160,1874,1931,10.78,32.80));
        choices.add(new ShotChoice(190,160,2440,1971,12.80,36.89));
        choices.add(new ShotChoice(150,180,1925,2245,12.31,36.72));
        choices.add(new ShotChoice(150,200,1920,2497,13.11,39.36));
        choices.add(new ShotChoice(170,200,2177,2497,13.94,39.22));
        choices.add(new ShotChoice(190,200,2434,2491,14.78,37.08));
        choices.add(new ShotChoice(210,200,2685,2485,15.05,37.86));
        choices.add(new ShotChoice(230,200,2954,2480,15.77,37.96));
        choices.add(new ShotChoice(250,200,3148,2434,15.14,37.82));
        choices.add(new ShotChoice(150,220,1862,2674,13.36,36.97));
        choices.add(new ShotChoice(170,220,2154,2725,14.91,38.05));
        choices.add(new ShotChoice(190,220,2417,2725,15.46,37.88));
        choices.add(new ShotChoice(210,220,2668,2714,16.60,38.12));
        choices.add(new ShotChoice(230,220,2931,2708,15.71,37.53));
        choices.add(new ShotChoice(250,220,3188,2702,15.90,37.43));
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

    public ArrayList<ShotChoice> getShotChoices() {
        return choices;
    }
}