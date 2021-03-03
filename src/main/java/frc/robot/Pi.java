package frc.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import java.util.logging.Logger;
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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Pi{
    HoloTable holo = HoloTable.getInstance();

    XboxController gamepad1;
    private NetworkTableInstance netInst;
    private NetworkTable table;
    private NetworkTableEntry cameraSelect;
    private boolean isButtonHeld;
    private NetworkTableEntry targetEntry;
    private static boolean turnLeft;
    private static boolean turnRight;

    private static final double X_RESOLUTION = 1280.0;
    Pi(){
        gamepad1 = holo.getController();
        netInst = NetworkTableInstance.getDefault();
        table = netInst.getTable("datatable");
        cameraSelect = netInst.getTable("SmartDashboard").getEntry("camNumber");
        targetEntry = table.getEntry("targets");
        CameraServer.getInstance().addServer("10.28.32.11"); // I think this connects to the Raspberry Pi's CameraServer.
    }

    public void processTargets() {
        Number[] targetListAsNumbers = targetEntry.getNumberArray(new Number[0]);
        if(targetListAsNumbers.length == 0) {
            return;
        }

        double xVal = (double) targetListAsNumbers[0]; // get first targert in list
        if(xVal <(0.5 * X_RESOLUTION) - 0.05 * X_RESOLUTION) {
            turnLeft =true;
            turnRight =false;
        } else if(xVal > (0.5 * X_RESOLUTION) + 0.05 * X_RESOLUTION) {
            turnLeft = false;
            turnRight = true;
        }else {
            turnLeft = false;
            turnRight= false;
        }
    }
    public static boolean getTurnLeft() {
        return turnLeft;
    }

    public static boolean getTurnRight(){
        return turnRight;
    }
    public static boolean getTurnCentered(){
        return !turnRight && !turnLeft;
    }    





    /*
    public void switchCameras(){

        if (gamepad1.getBButton()) {
            if (!isButtonHeld) {
                System.out.println("A BUTTON HAS BEEN PRESSED");
                cameraSelect.setNumber(0.0); // or setString("My Pi Camera Name")
                isButtonHeld = true;
            }
        } else if (gamepad1.getAButton()) {
            if (!isButtonHeld) {
                System.out.println("B BUTTON HAS BEEN PRESSED");
                cameraSelect.setNumber(1.0);
                isButtonHeld = true;
            }
        } else {
            isButtonHeld = false;
        }
    }*/

}
