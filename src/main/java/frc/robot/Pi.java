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
    private static double moveTurn;
    private static final int CAM_X_RES = 640;
    private static final int CAM_Y_RES = 480;
    private static final int CENTERPOINT = 315; //when shooter is centered instead of camera

    
    public Pi() {
        holo = HoloTable.getInstance();
        driverGamepad = holo.getDriverController();
        netInst = NetworkTableInstance.getDefault();
        table = netInst.getTable("datatable");
        targetCenterX = table.getEntry("targetX");
        cameraSelect = netInst.getTable("SmartDashboard").getEntry("camNumber");
        CameraServer.getInstance().addServer("10.28.32.4"); // I think this connects to the Raspberry Pi's CameraServer.
        moveTurn = 0;
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
            moveTurn = 0;
            return;
        }
        double targetX = (double) targetCenterArray[targetCenterArray.length - 1]; // rightmost target
        
        //~348 is center of target.  85 is when we are parallel to wall, and need to turn left
        double diff = CENTERPOINT-targetX;
        double error = CAM_X_RES * 0.025;
        if(Math.abs(diff) < error) {
            moveTurn = 0;
        } else {
            //we want a speed of 0.45 when off by 263 (348-85), and a speed of 0.25 at 16 pixels off 
            //that means, start with 25% power, and add more the further we are off
            //if battery is relatively low, use 30%
            moveTurn = Math.signum(diff) * (0.25 + (0.2*(Math.abs(diff)-16)/263));
        }
    }

    public static double getMove() {
        return moveTurn;
    }
}
