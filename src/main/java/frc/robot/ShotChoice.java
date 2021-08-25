package frc.robot;

public class ShotChoice {
    private int topCommand;
    private int bottomCommand;
    private int topRpm;
    private int bottomRpm;
    
    public ShotChoice(int topCommand, int bottomCommand, int topRpm, int bottomRpm)
    {
        this.topCommand = topCommand;
        this.bottomCommand = bottomCommand;
        this.topRpm = topRpm;
        this.bottomRpm = bottomRpm;
    }

    public int getTopCommand() {
        return topCommand;
    }

    public int getBottomCommand() {
        return bottomCommand;
    }

    public int getTopRpm() {
        return topRpm;
    }

    public int getBottomRpm() {
        return bottomRpm;
    }
}
