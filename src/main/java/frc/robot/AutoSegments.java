package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutoSegments extends CommandGroup{
   

    public AutoSegments(int segment){
        if(segment==1){
            System.out.println("******************************8888888");
            AutoNav1 autoNav1 = new AutoNav1();
            autoNav1.execute();
            autoNav1.close();
        }else{
            
        }
       
    }
   
}
