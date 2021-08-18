package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public final class Pi {
    private HoloTable holo;
    private XboxController driverGamepad;
    private NetworkTableInstance netInst;
    private NetworkTable table;
    private NetworkTableEntry cameraSelect;
    private static NetworkTableEntry targetCenterX;
    private static boolean hasFoundObjective;
    private static boolean moveLeft;
    private static boolean moveRight;
    private static final int CAM_X_RES = 640;
    private static final int CAM_Y_RES = 480;
    private static final int CENTERPOINT = 348; //when shooter is centered instead of camera

    
    public Pi() {
        holo = HoloTable.getInstance();
        driverGamepad = holo.getDriverController();
        netInst = NetworkTableInstance.getDefault();
        table = netInst.getTable("datatable");
        targetCenterX = table.getEntry("targetX");
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

    public static void processTargets() {
        Number[] targetCenterArray = targetCenterX.getNumberArray(new Number[0]);
        if (targetCenterArray.length == 0) {
            hasFoundObjective = false;
            moveRight = false;
            moveLeft = false;
            return;
        }
        hasFoundObjective = true; //TODO: do we need this boolean?
        double targetX = (double) targetCenterArray[targetCenterArray.length - 1]; // rightmost target
        // System.out.println("target x value: " + targetX);
        if (targetX < CENTERPOINT - (CAM_X_RES * 0.025)) {
            moveRight = false;
            moveLeft = true;
        } else if (targetX > CENTERPOINT + (CAM_X_RES * 0.025)) {
            moveLeft = false;
            moveRight = true;
        } else {
            moveRight = false;
            moveLeft = false;
        }
    }

    public static boolean getMoveLeft() {
        return moveLeft;
    }

    public static boolean getMoveRight() {
        return moveRight;
    }

}
