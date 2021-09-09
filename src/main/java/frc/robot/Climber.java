package frc.robot;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public final class Climber {

    private HoloTable table;
    private CANSparkMax climberLeft;
    private CANSparkMax climberRight;
    private XboxController operatorGamepad;

    public Climber() {
        table = HoloTable.getInstance();
        // shooter side is "front", left is CAN2, right is CAN3
        climberLeft = table.getLeftClimber();
        climberRight = table.getRightClimber();
        operatorGamepad = table.getOperatorController();
    }

    public void runClimb() {
        double leftSpeed;
        double rightSpeed;
        if (table.getIsDriveTrainAutonomous()) {
            leftSpeed = 0.0;
            rightSpeed = 0.0;
        } else if (operatorGamepad.getBackButton()) { // Retract left arm.
            leftSpeed = 0.5;
            rightSpeed = 0.0;
        } else if (operatorGamepad.getStartButton()) { // Retract right arm.
            leftSpeed = 0.0;
            rightSpeed = 0.5;
        } else {
            int pov = operatorGamepad.getPOV();
            switch (pov) {
                case 180: // Down or diagonal of down pressed. retract both arms.
                case 135:
                case 225:
                    leftSpeed = 1.0;
                    rightSpeed = 1.0;
                    break;
                case 0: // Up pressed. Extend both arms.
                case 45:
                case 315:
                    leftSpeed = -0.5;
                    rightSpeed = -0.5;
                    break;
                default: // left, right, or nothing pressed. Move neither arm.
                    leftSpeed = 0.0;
                    rightSpeed = 0.0;
                    break;
            }
        }
        climberLeft.set(leftSpeed);
        climberRight.set(rightSpeed);
    }
}