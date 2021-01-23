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
        if (gamepad1.getBumper(Hand.kLeft)) {
            hopper.set(.5);
            logger.warning("Backwards");
            
        }
        else if (gamepad1.getBumper(Hand.kRight)) {
            hopper.set(-.5);
            logger.warning("Fowards");
        } 
       
    }

}