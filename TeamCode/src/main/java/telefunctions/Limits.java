package telefunctions;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

//Used to define limits for servos and motors
public class Limits {
    ArrayList<DcMotor> motors = new ArrayList<>();
    ArrayList<Double> lowerM = new ArrayList<>();
    ArrayList<Double> upperM = new ArrayList<>();

    ArrayList<CRServo> crServos = new ArrayList<>();
    ArrayList<Double> lowerS = new ArrayList<>();
    ArrayList<Double> upperS = new ArrayList<>();

    //Adds limit to dc motor d with bounds low and high
    public void addLimit(DcMotor d, double low, double high) {
        motors.add(d);
        lowerM.add(low);
        upperM.add(high);
    }
    //Adds limit to cr servo s with bounds low and high
    public void addLimit(CRServo s, double low, double high) {
        crServos.add(s);
        lowerS.add(low);
        upperS.add(high);
    }
    //Checks if the motor is in limits using direction its trying to move in and pos
    public boolean isInLimits(DcMotor d, double dir,  double pos) {
        int i = motors.indexOf(d);
        double lower_bound = lowerM.get(i);
        double upper_bound = upperM.get(i);

        return !((lower_bound >= pos && dir < 0) || (pos >= upper_bound && dir > 0));
    }
    //Checks if the crservo is in limits using direction its trying to move in and pos
    public boolean isInLimits(CRServo s, double dir,  double pos) {
        int i = crServos.indexOf(s);
        double lower_bound = lowerS.get(i);
        double upper_bound = upperS.get(i);

        return !((lower_bound >= pos && dir < 0) || (pos >= upper_bound && dir > 0));
    }


}