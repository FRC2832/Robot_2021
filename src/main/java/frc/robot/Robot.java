/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private static final String AUTORUN = "AutoRun";
    private static final String AUTOSHOOT = "AutoShoot";

    private Command autonomousCommand;


    private HoloTable holo = HoloTable.getInstance();
    private ShootingTable shTable = ShootingTable.getInstance();
    private Shooter shooter = new Shooter();
    private Ingestor ingestor = new Ingestor();
    private Hopper hopper = new Hopper();
    private Climber climber = new Climber();

      private final Pi pi = new Pi();
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, fastTopRPM, fastBottomRPM, emptyTopRPM,
            emptyBottomRPM, setTop, setBottom;
    private static DriveTrain driveTrain;
    private static int visionCenterX = 640;
    private NetworkTableInstance netInst;
    private NetworkTable table;
    private final double[] defaultValue = { -1.0 };
    private boolean isCamValueUpdated;
    private XboxController gamepad1;
    private NetworkTableEntry cameraSelect, centerXEntry;
    // NetworkTableEntry cameraSelect =
    // NetworkTableInstance.getDefault().getEntry("/camselect");

    /*
     * UsbCamera piCamera1; UsbCamera piCamera2; VideoSink server;
     */
    private JoystickButton buttonA, buttonB, buttonX;

    // NetworkTableEntry cameraSelect =
    // NetworkTableInstance.getDefault().getEntry("/camselect");

    private NetworkTableEntry lidarDist;

    /*
     * UsbCamera camera1; UsbCamera camera2; NetworkTableEntry cameraSelection;
     */

    private UsbCamera piCamera1;
    private UsbCamera piCamera2;
    private VideoSink server;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        gamepad1 = new XboxController(2);
        netInst = NetworkTableInstance.getDefault();
        table = netInst.getTable("datatable");
        lidarDist = table.getEntry("distance");
        m_chooser.addOption("Auto Shoot", "Shoot");
        m_chooser.addOption("Camera Guided Shooting", "Camera Guided Shooting");
        m_chooser.setDefaultOption("Auto Nav 1", "Run Auto Nav 1");
        SmartDashboard.putData("Auto choices", m_chooser);


        kP = 0;
       // kP = 6e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.0023;
        kMaxOutput = 1.0;
        kMinOutput = -1.0;
        fastTopRPM = -5700.0;
        fastBottomRPM = 5700.0;
        emptyTopRPM = -3000.0;
        emptyBottomRPM = 3000.0;

        buttonA = new JoystickButton(gamepad1, 1);
        buttonB = new JoystickButton(gamepad1, 2);
        buttonX = new JoystickButton(gamepad1, 3);

        // set PID coefficients

        //Autonomous variables

        holo.frontLeftPID.setP(Robot.kP);
        holo.rearLeftPID.setP(Robot.kP);
        holo.frontRightPID.setP(Robot.kP);
        holo.rearRightPID.setP(Robot.kP);
  
        holo.frontLeftPID.setI(Robot.kI);
        holo.rearLeftPID.setI(Robot.kI);
        holo.frontRightPID.setI(Robot.kI);
        holo.rearRightPID.setI(Robot.kI);
  
        holo.frontLeftPID.setD(Robot.kD);
        holo.rearLeftPID.setD(Robot.kD);
        holo.frontRightPID.setD(Robot.kD);
        holo.rearRightPID.setD(Robot.kD);
  
        holo.frontLeftPID.setIZone(Robot.kIz);
        holo.rearLeftPID.setIZone(Robot.kIz);
        holo.frontRightPID.setIZone(Robot.kIz);
        holo.rearRightPID.setIZone(Robot.kIz);
  
        holo.frontLeftPID.setFF(Robot.kFF);
        holo.rearLeftPID.setFF(Robot.kFF);
        holo.frontRightPID.setFF(Robot.kFF);
        holo.rearRightPID.setFF(Robot.kFF);
          
        holo.frontLeftPID.setOutputRange(Robot.kMinOutput, Robot.kMaxOutput);
        holo.rearLeftPID.setOutputRange(Robot.kMinOutput, Robot.kMaxOutput);
        holo.frontRightPID.setOutputRange(Robot.kMinOutput, Robot.kMaxOutput);
        holo.rearRightPID.setOutputRange(Robot.kMinOutput, Robot.kMaxOutput);
        /*
         * holo.bottomPID.setP(kP); holo.bottomPID.setI(kI); holo.bottomPID.setD(kD);
         * holo.bottomPID.setIZone(kIz); holo.bottomPID.setFF(kFF);
         * holo.bottomPID.setOutputRange(kMinOutput, kMaxOutput); holo.topPID.setP(kP);
         * holo.topPID.setI(kI); holo.topPID.setD(kD); holo.topPID.setIZone(kIz);
         * holo.topPID.setFF(kFF); holo.topPID.setOutputRange(kMinOutput, kMaxOutput);
         */
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
        driveTrain = new DriveTrain();
        CameraServer.getInstance().addServer("10.28.32.4"); // I think this connects to the Raspberry Pi's CameraServer.
        // CameraServer.getInstance().startAutomaticCapture(); // UNCOMMENT IF REVERTING
        // camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        piCamera1 = CameraServer.getInstance().startAutomaticCapture(0);
        piCamera2 = CameraServer.getInstance().startAutomaticCapture(1);
        server = CameraServer.getInstance().getServer();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Lidar Distance", (double) table.getEntry("distance0").getNumber(-1.0));
        pi.processTargets();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString line to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional comparisons to the
     * switch structure below with additional strings. If using the SendableChooser
     * make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();

        m_autoSelected = SmartDashboard.getString("Auto Selector", m_autoSelected);
        
        System.out.println("Auto selected: " + m_autoSelected);
        switch (m_autoSelected) {
            case "Shoot":
                autonomousCommand =new AutoRunAndShoot();
                break;
            case "Camera Guided Shooting":
                autonomousCommand = new CameraGuidedShooting(driveTrain);
                break;
            case "Run Auto Nav 1":
            default:
                autonomousCommand =new AutoPath();
                break;
            
        }

        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        
        Scheduler.getInstance().run();

    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        ingestor.runIngestor();

            hopper.runMotors();
            climber.runClimb();
        try {
            shooter.runShooter();
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        driveTrain.driveTank();
        //pi.switchCameras();

      
        /*
         * if (gamepad1.getXButtonPressed()) { cameraSelect.setDouble(2); }
         */
        /*
         * if (isCamValueUpdated) { if ((int) cameraSelect.getNumber(-1.0) == 0)
         * System.out.println("SUCCESSFULLY WROTE 0.0 TO NETWORK TABLE"); else if ((int)
         * cameraSelect.getNumber(-1.0) == 1)
         * System.out.println("SUCCESSFULLY WROTE 1.0 TO NETWORK TABLE");
         * isCamValueUpdated = false; }
         */

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }

    
}
