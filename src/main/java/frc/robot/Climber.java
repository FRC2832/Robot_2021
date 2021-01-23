package frc.robot;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public final class Climber{

    HoloTable table = HoloTable.getInstance();

    CANSparkMax climberTop;
    CANSparkMax climberBottom;

    XboxController gamepad1;

    Climber(){
        climberTop = table.getTopClimber();
        climberBottom = table.getBottomClimber();
        gamepad1 = table.getController();

    }

    public void runClimb(){
        if (gamepad1.getPOV() == 180){
            climberBottom.set(.50);
            
        }else if (gamepad1.getPOV() == 270) {
            climberTop.set(-.50);
        }else if (gamepad1.getPOV() == 90){
            climberBottom.set(-.5);
        }else if (gamepad1.getPOV() == 0){
            climberTop.set(.5);
        }else {
            climberTop.set(0);
            climberBottom.set(0);
        }
    }


   

}