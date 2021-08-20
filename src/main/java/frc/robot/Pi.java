package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public final class Pi {
    private HoloTable holo;
    private XboxController driverGamepad;
    private NetworkTableInstance netInst;
    //private NetworkTable table;
    private NetworkTableEntry cameraSelect;
    
    public Pi() {
        holo = HoloTable.getInstance();
        driverGamepad = holo.getDriverController();
        netInst = NetworkTableInstance.getDefault();
        //table = netInst.getTable("datatable");
        cameraSelect = netInst.getTable("SmartDashboard").getEntry("camNumber");
        CameraServer.getInstance().addServer("10.28.32.4"); // I think this connects to the Raspberry Pi's CameraServer.
    }

    public void switchCameras() {
        if (driverGamepad.getBButtonPressed()) {
            System.out.println("B BUTTON HAS BEEN PRESSED");
            cameraSelect.setNumber(0.0); // or setString("My Pi Camera Name")
        } else if (driverGamepad.getAButtonPressed()) {
            System.out.println("A BUTTON HAS BEEN PRESSED");
            cameraSelect.setNumber(1.0);
        }
    }

}
