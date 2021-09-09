package frc.robot;

public class ShotChoice {
    private int topCommand;
    private int bottomCommand;
    private int topRpm;
    private int bottomRpm;
    private double shotSpeed;
    private double shotAngle;
    private double Vx;
    private double Vy;

    public ShotChoice(int topCommand, int bottomCommand, int topRpm, int bottomRpm, double speed, double angle)
    {
        this.topCommand = topCommand;
        this.bottomCommand = bottomCommand;
        this.topRpm = topRpm;
        this.bottomRpm = bottomRpm;
        this.shotSpeed = speed;
        this.shotAngle = angle;

        //small optimization, sin and cos are slow, so we will just calculate them once here
        double rad = Math.toRadians(angle);
        Vx = speed * Math.cos(rad);
        Vy = speed * Math.sin(rad);

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

    /**
     * Based on how far away from the target we are, calculate how far we will miss by
     * @param distance Distance from target in meters
     * @return How far in meters from center of target we are, negative is low, positive high
     */
    public double getShotError(double distance) {
        //equations are:
        //Dx=T*Vx
        //Dy=T*Vy-1/2*g*t^2
        final double G = 9.81; //m/s^2
        final double Di = 0.278;  //how high in meters the balls start from the ground (aka shooter initial position)
        final double Df = 2.49;  //how high in meters the target is (8' 2.25")

        //first calculate T based on horizonal velocity
        double T = distance/Vx;

        //then calculate height 
        double height = (T * Vy) - (1/2*G*T*T);

        return height + Di - Df;
    }
}
