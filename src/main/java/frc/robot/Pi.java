package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public final class Pi{
    HoloTable holo = HoloTable.getInstance();

    XboxController gamepad1;
    private NetworkTableInstance netInst;
    private NetworkTable table;
    private NetworkTableEntry cameraSelect;
    private boolean isButtonHeld;

    Pi(){
        gamepad1 = holo.getController();
        netInst = NetworkTableInstance.getDefault();
        table = netInst.getTable("datatable");
        cameraSelect = netInst.getTable("SmartDashboard").getEntry("camNumber");
        CameraServer.getInstance().addServer("10.28.32.4"); // I think this connects to the Raspberry Pi's CameraServer.
        
    }

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
    }

}
