package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraGuidedShooting extends Command {
    private boolean hasMoved;
    private boolean hasShot;

    private boolean isMoving;
    private boolean isShooting;
    private HoloTable holo = HoloTable.getInstance();
    private DriveTrain driveTrain;
    private Timer timer = new Timer();
    private WPI_TalonSRX ejector = holo.getEjector();
    public boolean shooterOff;
    private ShootingTable shTable = ShootingTable.getInstance();
    public XboxController gamepad1 = holo.getController();
    public Joystick joystick = holo.getJoystickRight();
    public CANSparkMax rightRear = holo.getDriveRightRear();
    public CANSparkMax rightFront = holo.getDriveRightFront();
    public WPI_TalonSRX hopper = holo.getHopper();

    // private double currentTime;

    public CameraGuidedShooting(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub
        isMoving = true;
        isShooting = false;
        timer.reset();
        timer.start();
        driveTrain.driveTank(.0, .0);
        System.out.println("Start");

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        while (RobotState.isAutonomous()) {
            if (isMoving) {

                Double timerVal = timer.get();
                System.out.println(timerVal);
                if (timerVal >= 2.0) {
                    driveTrain.driveTank(0.0, 0.0);
                    isMoving = false;
                    hasMoved = true;
                    isShooting = true;
                } else {
                    driveTrain.driveTank(.25, .25);
                }
            }
            if (isShooting) {
                if (Pi.getTurnLeft()) {
                    driveTrain.driveTank(-.25, .25);
                    System.out.println("Left");
                } else if (Pi.getTurnRight()) {
                    driveTrain.driveTank(.25, -.25);
                    System.out.println("Right");
                } else if (Pi.getTurnCentered()) {
                    timer.reset();
                    timer.start();
                    Double timerVal = timer.get();
                    System.out.println("Centered");
                    if (timerVal >= 15) {
                        Robot.setTop = 0;
                        Robot.setBottom = 0;
                        ejector.set(0);
                    } else {
                        driveTrain.driveTank(0.0, 0.0);
                        double mult = shTable.getMultiplier(holo.getDistance0());
                        double shootSpeed = 255.0 * mult;

                        holo.topPID.setReference(-(215.0), ControlType.kVelocity);
                        holo.bottomPID.setReference(180.0, ControlType.kVelocity);
                        ejector.set(1);
                    }
                } else {
                    driveTrain.driveTank(0.0, 0.0);
                    isShooting = false;
                    hasShot = true;
                }
            }
        }
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
}
