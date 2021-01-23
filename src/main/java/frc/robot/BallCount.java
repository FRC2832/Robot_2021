package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public final class BallCount{

    HoloTable table;

    DigitalInput infraredHopper1;
    DigitalInput infraredHopper2;
    DigitalInput infraredHopper3;
    DigitalInput infraredHopper4;
    DigitalInput infraredHopper5;
    DigitalInput infraredIntake;
    
    WPI_TalonSRX intake;
    WPI_TalonSRX hopper;
    WPI_TalonSRX color;

    public BallCount(){
        table = HoloTable.getInstance();
        infraredHopper1 = table.getInfraredHopper1();
        infraredHopper2 = table.getInfraredHopper2();
        infraredIntake = table.getInfraredIntake();

        hopper = table.getHopper();
        intake = table.getIntake();
    }

    public String ballCount(DigitalInput dI) {
        if (dI.get()){

            return "On";
        }
        return "Off";
        
    }

    public void saftyCheck(){
        if (!infraredHopper1.get()){
            if (!infraredHopper2.get()){
                intake.set(0);
            }
        }
    }

}