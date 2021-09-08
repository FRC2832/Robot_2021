package frc.robot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import edu.wpi.first.wpiutil.math.Pair;

import org.junit.*;

public class LinearInterpTest {
    public static final double DELTA = 1e-2; // acceptable deviation range
    private static ArrayList<Pair<Double,Double>> table;

    @Before // this method will run before each test
    public void setup() {
        table = new ArrayList<Pair<Double,Double>>();
        //                                in   out
        table.add(new Pair<Double,Double>(1.0, 3.0));
        table.add(new Pair<Double,Double>(3.0, 5.0));
        table.add(new Pair<Double,Double>(5.0, 7.0));
    }

    @After // this method will run after each test
    public void shutdown() throws Exception {

    }

    @Test
    public void BeforeTable() {
        //if we go before the table, the answer should be the smallest value
        assertEquals(table.get(0).getSecond(), Pi.LinearInterp(table, 0), DELTA);
    }

    @Test
    public void AfterTable() {
        //if we go past the table, the answer should be the smallest value
        assertEquals(table.get(table.size()-1).getSecond(), Pi.LinearInterp(table, 100), DELTA);
    }

    @Test
    public void InTable() {
        //with the first path between 1 and 3, output 3 to 5, the exact middle input (2) should match 4
        assertEquals(4, Pi.LinearInterp(table, 2), DELTA);
    }
}