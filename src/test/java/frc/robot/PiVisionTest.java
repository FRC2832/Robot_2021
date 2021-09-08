package frc.robot;

import static org.junit.Assert.*;
import org.junit.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTableInstance;

public class PiVisionTest {
    public static final double DELTA = 1e-2; // acceptable deviation range
    private NetworkTableInstance netInst;
    private Pi piObject;

    @Before // this method will run before each test
    public void setup() {
        assert HAL.initialize(500, 0); // initialize the HAL, crash if failed
        netInst = NetworkTableInstance.getDefault();
        piObject = new Pi();
    }

    @After // this method will run after each test
    public void shutdown() throws Exception {

    }

    @Test
    public void DistanceReading() {
        //get the second item from the distance table
        var tableItem = piObject.getDistanceTable().get(1);

        //setup the network tables with the proper data.
        netInst.getTable("datatable").getEntry("targetX").setNumberArray(new Number[]{480});
        netInst.getTable("datatable").getEntry("widthBox").setNumberArray(new Number[]{tableItem.getFirst()});

        //run it
        Pi.processTargets();

        //check result. Since we picked an exact point in the distance table, the result should match the point we picked.
        assertEquals(tableItem.getSecond(), Pi.getTargetDistance(), DELTA);
    }
}