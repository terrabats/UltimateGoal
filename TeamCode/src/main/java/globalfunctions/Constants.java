package globalfunctions;


import util.Geometry;

public class Constants {
    //Outtake Constants
    //--------------------------------------------------------------------------------------------------------------
    //Maximuim outtake speed possible
    public static final double MAX_OUTTAKE_SPEED = 100*2*Math.PI; // rad/s
    //Offest speed between two outtake motors
    public static final double OUT_SPEED_OFFSET = 0.05*MAX_OUTTAKE_SPEED;
    //Outtake angle
    public static final double OUTTAKE_ANGLE = 26 * Math.PI/180; // degrees -> radians
    //Height of the outtake from the ground
    public static final double SHOOTER_HEIGHT = 0.19; // meters
    //Radius of the outtake wheels
    public static final double SHOOTER_WHEEL_RADIUS = 0.05; // meters
    //Ring shooter power
    public static final double RS_POW = 0.5;


    //Field Constants
    //--------------------------------------------------------------------------------------------------------------
    //Distance the goal is from the left wall
    public static final double GOAL_FROM_LEFT = 0.9; // meters
    //The height of the goal from the ground
    public static final double GOAL_HEIGHT = 0.9; // meters
    //The distance the left powershot is from the left wall
    public static final double POWERSHOT_FROM_LEFT = 1.3; //meters
    //The height of the powershots from the ground
    public static final double POWERSHOT_HEIGHT = 0.77; // meters
    //The distance between powershots
    public static final double DIS_BETWEEN_POWERSHOTS = 0.2; //meters
    //The length of the field
    public static final double FIELD_LENGTH = 3.6576; //meters

    //Motor Constants
    //--------------------------------------------------------------------------------------------------------------
    //Outtake motor ticks
    public static final double GOBUILDA1_Ticks = 28;

    //PI Constants
    //--------------------------------------------------------------------------------------------------------------
    //2*pi
    public static final double pi2 = Math.PI*2;
    //0.5*pi
    public static final double halfPi = Math.PI/2;


    //AutoAimer Constants
    //--------------------------------------------------------------------------------------------------------------
    //Distance rings are touching outtake wheels
    public static final double SHOOT_DIS = 0.06; //m
    //Acceleration due to friction
    public static double FRICTION_ACCEL = 340; // 360 m/s^2


}
