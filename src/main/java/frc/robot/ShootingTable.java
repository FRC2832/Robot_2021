/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;
import java.util.Map;

/**
 * Add your docs here.
 */
public class ShootingTable {
    private static ShootingTable instance = null;
    private static Map<Integer, Double> shootingTable = new HashMap<>(); // The integer is the distance in inches, the
                                                                         // double is the multiplier
    private int distanceSh = 0;

    public ShootingTable() {
        shootingTable.put(0, 1.0);
        shootingTable.put(24, 1.0);
        shootingTable.put(48, 1.0);
        shootingTable.put(72, 1.0);
        shootingTable.put(96, 1.0);

        // shootingTable.put(114, 0.5);

        shootingTable.put(120, 1.0);
        shootingTable.put(144, 0.75);
        shootingTable.put(168, 0.75);
        shootingTable.put(192, 0.70);
        shootingTable.put(216, 0.70);
        shootingTable.put(240, 0.72);
        shootingTable.put(264, 0.73);
        shootingTable.put(288, 0.7605);
        shootingTable.put(312, 0.7605);
        shootingTable.put(336, 0.8425);
        shootingTable.put(360, 0.85);
    }

    public static ShootingTable getInstance() {
        if (instance == null) {
            instance = new ShootingTable();
        }
        return instance;
    }

    public double getMultiplier(double distance) {
        System.out.println("Distance " + distance);
        System.out.println("CURRENT TABLE INDEX: " + (int) (((int) distance) - (((int) distance) % 24)));
        distanceSh = (int) (((int) distance) - (((int) distance) % 24));
        double multiplier = 0.85d;
        try {
            multiplier = shootingTable.get(distanceSh);
        } catch (RuntimeException e) {
            // multiplier=0.9;
        }

        return multiplier;
    }

}
