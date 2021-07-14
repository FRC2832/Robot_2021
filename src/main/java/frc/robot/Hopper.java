package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import java.util.logging.Logger;

public final class Hopper {

    HoloTable table = HoloTable.getInstance();

    WPI_TalonSRX hopper;
    XboxController gamepad1;
    WPI_TalonSRX ejector;
    
    DigitalInput infraredHopper1;
    DigitalInput infraredHopper2;
    DigitalInput infraredIntake;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    Hopper() {

        hopper = table.getHopper();
        gamepad1 = table.getController();
        infraredHopper1 = table.getInfraredHopper1();
        infraredHopper2 = table.getInfraredHopper2();
        infraredIntake = table.getInfraredIntake();
        ejector = table.getEjector();


    }

    public void runMotors() {    
 
        if (gamepad1.getAButton()) {
            //if a is pressed, this is shoot mode, so run the hopper
            hopper.set(-.5);           
        }
        else if (gamepad1.getBButton()) {
            //if b is pressed, run the hopper backwards to stop jams
            hopper.set(0.4);           
        }
        else if (gamepad1.getBumper(Hand.kRight)) {
            //if right bumper pressed, intake unless hopper sensor think full
            if (!infraredIntake.get()){
                if (!infraredHopper1.get()){
                    if (!infraredHopper2.get()){
                        hopper.set(0);
                    }else {
                        hopper.set(-.5);
                    }
                }else {
                    hopper.set(-.5);
                }
            }else {
                hopper.set(0);
            }
        } else{
            //no controller pressed, do nothing
            hopper.set(0);
        }
       
    }

}