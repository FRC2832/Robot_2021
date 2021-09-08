package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpiutil.math.Pair;

import java.util.ArrayList;

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
    private static NetworkTableEntry boxWidth;
    private static double moveTurn;
    private static double targetDistance;
    private static final int CAM_X_RES = 640;
    private static final int CAM_Y_RES = 480;
    private static final int CENTERPOINT = 348; //when shooter is centered instead of camera
    private static ArrayList<Pair<Double,Double>> distTable;
    
    public Pi() {
        holo = HoloTable.getInstance();
        driverGamepad = holo.getDriverController();
        netInst = NetworkTableInstance.getDefault();
        table = netInst.getTable("datatable");
        targetCenterX = table.getEntry("targetX");
        boxWidth = table.getEntry("widthBox");
        cameraSelect = netInst.getTable("SmartDashboard").getEntry("camNumber");
        CameraServer.getInstance().addServer("10.28.32.4"); // I think this connects to the Raspberry Pi's CameraServer.
        moveTurn = 0;

        distTable = new ArrayList<Pair<Double,Double>>();
        //table is input: pixel width, output: meters from target
        distTable.add(new Pair<Double, Double>(94.0, 7.7724));
        distTable.add(new Pair<Double, Double>(100.0, 7.112));
        distTable.add(new Pair<Double, Double>(112.0, 6.35));
        distTable.add(new Pair<Double, Double>(126.0, 5.4864));
        distTable.add(new Pair<Double, Double>(130.0, 5.3086));
        distTable.add(new Pair<Double, Double>(142.0, 4.8768));
        distTable.add(new Pair<Double, Double>(158.0, 4.3688));
        distTable.add(new Pair<Double, Double>(167.0, 4.064));
        distTable.add(new Pair<Double, Double>(188.0, 3.5814));
        distTable.add(new Pair<Double, Double>(211.0, 3.1496));
        distTable.add(new Pair<Double, Double>(245.0, 2.667));
        distTable.add(new Pair<Double, Double>(298.0, 2.1336));
        distTable.add(new Pair<Double, Double>(372.0, 1.651));
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
            targetDistance = -1;
            return;
        }
        int index = targetCenterArray.length - 1;  //TODO find a better way for this?  sometimes near the white line, this is incorrect
        double targetX = (double) targetCenterArray[index]; // rightmost target
        
        //~348 is center of target.  85 is when we are parallel to wall, and need to turn left
        double diff = CENTERPOINT-targetX;
        double error = CAM_X_RES * 0.025;
        if(Math.abs(diff) < error) {
            moveTurn = 0;
        } else {
            //we want a speed of 0.45 when off by 263 (348-85), and a speed of 0.25 at 16 pixels off 
            //that means, start with 25% power, and add more the further we are off
            moveTurn = Math.signum(diff) * (0.25 + (0.2*(Math.abs(diff)-16)/263));
        }

        //distance estimation
        Number[] widthArray = boxWidth.getNumberArray(new Number[0]);
        if(index >= widthArray.length) {
            targetDistance = -1;
            return;
        }
        targetDistance = LinearInterp(distTable, widthArray[index].doubleValue());
    }

    public static double getMove() {
        return moveTurn;
    }

    public static double getTargetDistance() {
        return targetDistance;
    }

    public ArrayList<Pair<Double,Double>> getDistanceTable() {
        return distTable;
    }
    
    public static double LinearInterp(ArrayList<Pair<Double,Double>> list, double input) {
        //if input is smaller than the table, return the first element
        if(input < list.get(0).getFirst()) {
            return list.get(0).getSecond();
        }
        //if input is larger than the table, return the last element
        if(input > list.get(list.size()-1).getFirst()) {
            return list.get(list.size()-1).getSecond();
        }
        //otherwise the value is in the table
        for(int i=0; i<list.size()-1; i++) {
            double x0 = list.get(i).getFirst();
            double x1 = list.get(i+1).getFirst();
            if ((x0 <= input) && (input <= x1)) {
                //see https://en.wikipedia.org/wiki/Linear_interpolation
                double y0 = list.get(i).getSecond();
                double y1 = list.get(i+1).getSecond();
                return (y0*(x1-input) + y1*(input-x0))/(x1-x0);
            }
        }
        //should never happen...
        return Double.NaN;
    }
}
