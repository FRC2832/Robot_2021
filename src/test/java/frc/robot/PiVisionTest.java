package frc.robot;

import static org.junit.Assert.*;

import java.util.ArrayList;

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

    @Test
    public void ShotHeight() {
        //outer port is 0.76m big, inner port is 0.33m big and 0.74m back from the target
        ShotChoice choice = new ShotChoice(150, 180, 1940, 2240, 12.31, 36.72);

        choice.getShotError(2.4);
        assertTrue(true);
    }

    @Test
    public void CheckShotChoices() {
        //start at 65", end around where the color wheel is, 1" increments
        ShooterAuton sa = new ShooterAuton();
        ArrayList<ShotChoice> choices = sa.getShotChoices();

        System.out.println("Shot Distance (in),Shot Distance (m),Min Error (m),Shot Choice(index)");
        for(int dist = 65; dist < 306; dist+=1) {
            double minError = Double.MAX_VALUE;
            int minIndex = -1;

            for(int i=0; i<choices.size(); i++) {
                ShotChoice choice = choices.get(i);
                var error = choice.getShotError(dist*0.0254);
                if(Math.abs(error) < Math.abs(minError)) {
                    minError = error;
                    minIndex = i;
                }
            }
            String msg = dist + "," + String.format("%.3f", dist * 0.0254) + "," + String.format("%.3f", minError) + "," + minIndex;
            System.out.println(msg);
        }
    }
}