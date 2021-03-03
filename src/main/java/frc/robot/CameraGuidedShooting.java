package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CameraGuidedShooting extends Command{
    private boolean hasMoved;
    private boolean hasShot;
    
    private boolean isMoving;
    private boolean isShooting;
    private HoloTable holo = HoloTable.getInstance();
    private DriveTrain driveTrain;
    private Timer timer = new Timer();
  //  private double currentTime;
    
    public CameraGuidedShooting(DriveTrain driveTrain){
        this.driveTrain = driveTrain;
    }
    
    @Override
    protected void initialize() {
        // TODO Auto-generated method stub
        isMoving = true;
        timer.start();
        driveTrain.driveTank(-.25,-.25);
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        if(isMoving) {
            Double timerVal = timer.get();
            if (timerVal >= 2.0 ){
                driveTrain.driveTank(0.0,0.0);
                isMoving = false;
                hasMoved = true;
                isShooting = true;
                timer.reset();
            }else {
                driveTrain.driveTank(-.25,-.25);
            }
        } else if (isShooting) {

        }
    }
    @Override
    protected boolean isFinished(){
        //TODO Auto-generated method stub
        return false;
    }
}
