package frc.robot.command;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.DriveTrain;
import frc.robot.HoloTable;
import frc.robot.Robot;

public class AutoTurn extends Command {
    private PigeonIMU gyro;
    private HoloTable table;
    private DriveTrain driveTrain;
    private double commandAngle, startAngle, currentAngle;
    private boolean isFinished;

    public AutoTurn(double commandAngle) {
        table = HoloTable.getInstance();
        gyro = table.getGyro();
        driveTrain = Robot.getDriveTrain();
        this.commandAngle = commandAngle;
        isFinished = false;
    }

    @Override
    protected void initialize() {
        double[] yaw = new double[3];
        gyro.getYawPitchRoll(yaw);
        startAngle = -yaw[0];
    }

    @Override
    protected void execute() {
        double[] yaw = new double[3];
        gyro.getYawPitchRoll(yaw);
        currentAngle = -yaw[0];
        
        double remainingAngle = commandAngle - (currentAngle - startAngle);
        double turn;
        if(Math.abs(remainingAngle) < 1.0 ) {
            isFinished = true;
            turn = 0.0;
        } else {
            //we are just using the 36% turn command from shooting to turn the robot
            turn = -0.36 * Math.signum(remainingAngle);
        }
        driveTrain.driveSpeed(0, turn);
    }
    
    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    
}
