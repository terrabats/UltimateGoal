package developing;

import com.qualcomm.robotcore.util.ElapsedTime;

import globalfunctions.Constants;

public class CRServoPositionTracker {
    public double pos;
    public double lastTime = 0;
    public ElapsedTime timer = new ElapsedTime();

    public CRServoPositionTracker(double startPos) {
        pos = startPos;
        timer.reset();
    }

    public void update(double pow) {
        double curSpeed = Constants.CR_SERVO_MAX_SPEED * pow;
        pos += curSpeed * (timer.seconds() - lastTime);
        lastTime = timer.seconds();
    }

    public double getPosCM(double radius) {
        return pos * radius;
    }

}
