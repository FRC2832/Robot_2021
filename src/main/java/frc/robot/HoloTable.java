package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;

public final class HoloTable {

    private static HoloTable instance = null;
    private static WPI_TalonSRX driveTurn;
    private static CANSparkMax driveRightFront;
    private static CANSparkMax driveLeftFront;
    private static CANSparkMax driveRightRear;
    private static CANSparkMax driveLeftRear;
    private static PigeonIMU gyro;
    private static DoubleSolenoid dropIntake;
    private static WPI_TalonSRX intake;

    private static WPI_TalonSRX color;

    private static WPI_TalonSRX Hopper;
    private static DigitalInput infraredHopper1;
    private static DigitalInput infraredHopper2;
    private static DigitalInput infraredHopper3;
    private static DigitalInput infraredHopper4;
    private static DigitalInput infraredHopper5;
    private static DigitalInput infraredIntake;

    public CANPIDController topPID;
    public CANPIDController bottomPID;

    public CANPIDController frontLeftPID;
    public CANPIDController frontRightPID;
    public CANPIDController rearLeftPID;
    public CANPIDController rearRightPID;

    private static XboxController driverController;
    private static XboxController operatorController;

    private static CANSparkMax climberLeft;
    private static CANSparkMax climberRight;

    // private static Insert Camera Here;
    // private static Insert Color Sensor Here;

    private static CANSparkMax shooterTop;
    private static CANSparkMax shooterBottom;
    private static WPI_TalonSRX ejector;

    private NetworkTable table;

    public HoloTable() {
        // driveTurn = new WPI_TalonSRX(0);
        gyro = new PigeonIMU(new WPI_TalonSRX(11));
        // dropIntake = new DoubleSolenoid(0, 1);

        intake = new WPI_TalonSRX(4);

        // color = new WPI_TalonSRX(1);

        Hopper = new WPI_TalonSRX(6);
        infraredHopper1 = new DigitalInput(0);
        infraredHopper2 = new DigitalInput(2);
        infraredIntake = new DigitalInput(1);

        // shooter side is "front", left is CAN2, right is CAN3
        climberLeft = new CANSparkMax(2, MotorType.kBrushless);
        climberRight = new CANSparkMax(3, MotorType.kBrushless);
        climberLeft.setInverted(false);
        climberRight.setInverted(true);

        shooterTop = new CANSparkMax(13, MotorType.kBrushless);
        shooterBottom = new CANSparkMax(12, MotorType.kBrushless);
        topPID = shooterTop.getPIDController();
        bottomPID = shooterBottom.getPIDController();

        topPID = shooterTop.getPIDController();
        bottomPID = shooterBottom.getPIDController();
        dropIntake = new DoubleSolenoid(0, 1);
        ejector = new WPI_TalonSRX(5);
        driveRightFront = new CANSparkMax(1, MotorType.kBrushless);
        driveLeftFront = new CANSparkMax(15, MotorType.kBrushless);// on comp, 14. On practice 15
        driveRightRear = new CANSparkMax(39, MotorType.kBrushless);
        driveLeftRear = new CANSparkMax(14, MotorType.kBrushless);// on comp, 15. On practice, 14

        frontLeftPID = driveLeftFront.getPIDController();
        frontRightPID = driveRightFront.getPIDController();
        rearLeftPID = driveLeftRear.getPIDController();
        rearRightPID = driveRightRear.getPIDController();

        driverController = new XboxController(2);
        operatorController = new XboxController(3);

        table = NetworkTableInstance.getDefault().getTable("datatable");
    }

    public static HoloTable getInstance() {
        if (instance == null) {
            instance = new HoloTable();
        }
        return instance;
    }

    public WPI_TalonSRX getDriveTurn() {
        return driveTurn;
    }

    public CANSparkMax getDriveRightFront() {
        return driveRightFront;
    }

    public CANSparkMax getDriveLeftFront() {
        return driveLeftFront;
    }

    public CANSparkMax getDriveRightRear() {
        return driveRightRear;
    }

    public CANSparkMax getDriveLeftRear() {
        return driveLeftRear;
    }

    public PigeonIMU getGyro() {
        return gyro;
    }

    public DoubleSolenoid getDropIntake() {
        return dropIntake;
    }

    public XboxController getDriverController() {
        return driverController;
    }

    public XboxController getOperatorController() {
        return operatorController;
    }

    public WPI_TalonSRX getIntake() {
        return intake;
    }

    public DigitalInput getInfraredHopper1() {
        return infraredHopper1;
    }

    public DigitalInput getInfraredHopper2() {
        return infraredHopper2;
    }

    public DigitalInput getInfraredHopper3() {
        return infraredHopper3;
    }

    public DigitalInput getInfraredHopper4() {
        return infraredHopper4;
    }

    public DigitalInput getInfraredHopper5() {
        return infraredHopper5;
    }

    public DigitalInput getInfraredIntake() {
        return infraredIntake;
    }

    public WPI_TalonSRX getHopper() {
        return Hopper;
    }

    public CANSparkMax getTopShooter() {
        return shooterTop;
    }

    public CANSparkMax getBottomShooter() {
        return shooterBottom;
    }

    public WPI_TalonSRX getEjector() {
        return ejector;
    }

    public WPI_TalonSRX getColor() {
        return color;
    }

    public double getDistance0() {
        return (double) table.getEntry("distance0").getNumber(-1.0);
    }

    public CANSparkMax getLeftClimber() {
        return climberLeft;
    }

    public CANSparkMax getRightClimber() {
        return climberRight;
    }

}