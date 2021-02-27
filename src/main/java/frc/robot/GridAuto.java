// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GridAuto extends SequentialCommandGroup {
  private DriveTrain m_driveTrain;
  /** Creates a new GridAuto. */
  public GridAuto(DriveTrain driveTrain) {
    m_driveTrain = driveTrain;
    System.out.print("STARTING COMMAND GROUP");
    addCommands(
    new TurnTo180(m_driveTrain),
    new DriveToOrigin(m_driveTrain)
    //new TurnToCoords(driveTrain, 2000.0, 2000.0),
    //new DriveToCoords(driveTrain,2000.0, 2000.0),
    //new TurnToCoords(driveTrain,4000.0, 0.0),
    //new DriveToCoords(driveTrain,4000.0, 0.0),
    //new TurnToCoords(driveTrain, 2000.0, 2000.0),
    //new DriveToCoords(driveTrain,2000.0, 2000.0),
    //new TurnToCoords(driveTrain, 2000.0, 0.0),
    //new DriveToCoords(driveTrain,2000.0, 0.0),

    //new TurnToCoordsSmall(driveTrain, 0.0, 0.0),
    //new DriveToCoords(driveTrain,0.0, 0.0)
    //new TurnToCoords(driveTrain,4000.0, 0.0),
    //new DriveToCoords(driveTrain,4000.0, 0.0)
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    );
  }
}
