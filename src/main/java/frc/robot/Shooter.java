package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    // private boolean isShooterOff; // TODO: This is never set to true, is it?
    private HoloTable holo;
    // private ShootingTable shTable;
    private XboxController operatorGamepad;
    private WPI_TalonSRX ejector;
    private NetworkTable table;
    // private CANSparkMax rightRear;
    // private CANSparkMax rightFront;
    // private WPI_TalonSRX hopper = holo.getHopper();

    // private double mult = 1.0;
    // private double shootSpeed = 1.0;

    public Shooter() {
        holo = HoloTable.getInstance();
        // shTable = ShootingTable.getInstance();
        operatorGamepad = holo.getOperatorController();
        ejector = holo.getEjector();
        // rightRear = holo.getDriveRightRear();
        // rightFront = holo.getDriveRightFront();
        table = NetworkTableInstance.getDefault().getTable("Shooter");
        table.getEntry("Top Target").setDouble(215);
        table.getEntry("Bottom Target").setDouble(180);
    }

    public void runShooter() throws InterruptedException {
        // read PID coefficients from SmartDashboard
        double dashboardP = SmartDashboard.getNumber("P Gain", 0.0);
        double dashboardI = SmartDashboard.getNumber("I Gain", 0.0);
        double dashboardD = SmartDashboard.getNumber("D Gain", 0.0);
        double dashboardIZone = SmartDashboard.getNumber("I Zone", 0.0);
        double dashboardFeedForward = SmartDashboard.getNumber("Feed Forward", 0.0);
        double dashboardMaxOutput = SmartDashboard.getNumber("Max Output", 0.0);
        double dashboardMinOutput = SmartDashboard.getNumber("Min Output", 0.0);

        // if PID coefficients on SmartDashboard have changed, write new values to
        // controller
        if (dashboardP != Robot.kP) {
            holo.topPID.setP(dashboardP);
            Robot.kP = dashboardP;
        }
        if (dashboardI != Robot.kI) {
            holo.topPID.setI(dashboardI);
            Robot.kI = dashboardI;
        }
        if (dashboardD != Robot.kD) {
            holo.topPID.setD(dashboardD);
            Robot.kD = dashboardD;
        }
        if (dashboardIZone != Robot.kIz) {
            holo.topPID.setIZone(dashboardIZone);
            Robot.kIz = dashboardIZone;
        }
        if (dashboardFeedForward != Robot.kFF) {
            holo.topPID.setFF(dashboardFeedForward);
            Robot.kFF = dashboardFeedForward;
        }
        if ((dashboardMaxOutput != Robot.kMaxOutput) || (dashboardMinOutput != Robot.kMinOutput)) {
            holo.topPID.setOutputRange(dashboardMinOutput, dashboardMaxOutput);
            Robot.kMinOutput = dashboardMinOutput;
            Robot.kMaxOutput = dashboardMaxOutput;
        }

        if (holo.getIsDriveTrainAutonomous()) {
            // since we're in auton mode, no commands happen
            // code for shooting is in ShooterAuton
        } else {
            if (operatorGamepad.getBumperPressed(Hand.kLeft)) {
                // mult = shTable.getMultiplier(holo.getDistance0());
                // shootSpeed = 255.0 * mult;
                
                double tSpeed = table.getEntry("Top Target").getDouble(215);
                double bSpeed = table.getEntry("Bottom Target").getDouble(180);
                holo.topPID.setReference(-tSpeed, ControlType.kVelocity);
                holo.bottomPID.setReference(bSpeed, ControlType.kVelocity);

                // isShooterOff = false;
                // ejector.set(0.25);

            }
            if (operatorGamepad.getBumperReleased(Hand.kLeft)) {
                Robot.setTop = 0.0;
                Robot.setBottom = 0.0;

                ejector.set(0.0);

                holo.topPID.setReference(0.0, ControlType.kVelocity);
                holo.bottomPID.setReference(0.0, ControlType.kVelocity);

            }
            if (operatorGamepad.getAButtonPressed()) {
                Robot.setTop = Robot.emptyTopRPM;
                Robot.setBottom = Robot.emptyBottomRPM;
                ejector.set(0.7);
            }
            if (operatorGamepad.getAButtonReleased()) {
                Robot.setTop = 0.0;
                Robot.setBottom = 0.0;
                ejector.set(0.0);
            }
        }

        CANSparkMax mTop = holo.getTopShooter();
        CANSparkMax mBot = holo.getBottomShooter();
        table.getEntry("Top Velocity").setDouble(mTop.getEncoder().getVelocity());
        table.getEntry("Top Output%").setDouble(mTop.getAppliedOutput());
        table.getEntry("Top Current").setDouble(mTop.getOutputCurrent());
        table.getEntry("Bottom Velocity").setDouble(mBot.getEncoder().getVelocity());
        table.getEntry("Bottom Output%").setDouble(mBot.getAppliedOutput());
        table.getEntry("Bottom Current").setDouble(mBot.getOutputCurrent());
    }
}