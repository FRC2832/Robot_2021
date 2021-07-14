package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    public boolean shooterOff;
    HoloTable holo = HoloTable.getInstance();
    private ShootingTable shTable = ShootingTable.getInstance();
    public XboxController gamepad1 = holo.getController();
    public WPI_TalonSRX ejector = holo.getEjector();
    public CANSparkMax rightRear = holo.getDriveRightRear();
    public CANSparkMax rightFront = holo.getDriveRightFront();
    public WPI_TalonSRX hopper = holo.getHopper();
    
    private double mult = 1.0;
    private double shootSpeed = 1.0;
   
    public void runShooter() throws InterruptedException{
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0.0);
        double i = SmartDashboard.getNumber("I Gain", 0.0);
        double d = SmartDashboard.getNumber("D Gain", 0.0);
        double iz = SmartDashboard.getNumber("I Zone", 0.0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0.0);
        double max = SmartDashboard.getNumber("Max Output", 0.0);
        double min = SmartDashboard.getNumber("Min Output", 0.0);

        // if PID coefficients on SmartDashboard have changed, write new values to
        // controller
        if ((p != Robot.kP)) {
            holo.topPID.setP(p);
            Robot.kP = p;
        }
        if ((i != Robot.kI)) {
            holo.topPID.setI(i);
            Robot.kI = i;
        }
        if ((d != Robot.kD)) {
            holo.topPID.setD(d);
            Robot.kD = d;
        }
        if ((iz != Robot.kIz)) {
            holo.topPID.setIZone(iz);
            Robot.kIz = iz;
        }
        if ((ff != Robot.kFF)) {
            holo.topPID.setFF(ff);
            Robot.kFF = ff;
        }
        if ((max != Robot.kMaxOutput) || (min != Robot.kMinOutput)) {
            holo.topPID.setOutputRange(min, max);
            Robot.kMinOutput = min;
            Robot.kMaxOutput = max;
        }
       
        if (HoloTable.getInstance().getController().getBumperPressed(Hand.kLeft)) {
            mult = shTable.getMultiplier(holo.getDistance0());
            shootSpeed = 255.0 * mult;


            holo.topPID.setReference(-(215.0), ControlType.kVelocity);
            holo.bottomPID.setReference(180.0, ControlType.kVelocity);

            shooterOff = false;
            //ejector.set(0.25);
           
        }
        if (HoloTable.getInstance().getController().getBumperReleased(Hand.kLeft)) {
            Robot.setTop = 0;
            Robot.setBottom = 0;

            ejector.set(0);

            holo.topPID.setReference(0 , ControlType.kVelocity);
            holo.bottomPID.setReference(0, ControlType.kVelocity);

        }
        if (HoloTable.getInstance().getController().getAButtonPressed()) {
            Robot.setTop = Robot.emptyTopRPM;
            Robot.setBottom = Robot.emptyBottomRPM;
            ejector.set(0.5);
        }
        if (HoloTable.getInstance().getController().getAButtonReleased()) {
            Robot.setTop = 0;
            Robot.setBottom = 0;
            ejector.set(0.0);
        }
        

    }
}