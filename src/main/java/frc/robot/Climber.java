package frc.robot;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public final class Climber{

    HoloTable table = HoloTable.getInstance();

    CANSparkMax climberLeft;
    CANSparkMax climberRight;

    XboxController gamepad1;

    Climber(){
        //shooter side is "front", left is CAN2, right is CAN3
        climberLeft = table.getLeftClimber();
        climberRight = table.getRightClimber();
        gamepad1 = table.getController();
    }

    public void runClimb(){
        int pov = gamepad1.getPOV();
        if (pov == 180){
            //down pressed
            climberLeft.set(-.5);
            climberRight.set(-.5);
        }else if (pov == 270) {
            //right pressed
            climberLeft.set(0);
            climberRight.set(.5);
        }else if (pov == 90){
            //left pressed
            climberLeft.set(.5);
            climberRight.set(0);
        }else if (pov == 0){
            //up pressed
            climberLeft.set(1);
            climberRight.set(1);
        }else {
            //nothing pressed
            climberLeft.set(0);
            climberRight.set(0);
        }
    }


   

}