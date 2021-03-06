package telefunctions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import util.PID;

public class SpeedController {
    ElapsedTime timer = new ElapsedTime();
    PID pid = new PID();

    double lastPos;
    double lastTime;
    public double lastError;

    public double currError = 0;
    public double currDer = 0;
    public double integralOfError = 0;

    public double targetSpeed = 0;
    public double currSpeed = 0;

    double changeTime = 0;

    double oldPos = 0;

    public double pow = 0;

    public double k;
    public double d;
    public double i;

    public boolean isReady = false;

    public double acc = 1000;

    public SpeedController(double k, double d, double i, double startPow){
        k *= 0.00001;
        d *= 0.00001;
        i *=  0.00001;
        timer.reset();
        lastPos = 0;
        lastTime = -0.1;
        lastError = 0;
        oldPos = 0;
        pid.setCoeffecients(k,d,i);
        this.k = k;
        this.d = d;
        this.i = i;
        pow = startPow;
    }

    public void setStartPow(double startPow){
        pow = startPow;
    }


    public void scaleK(double scale){
        pid.setCoeffecients(k*scale,d,i);
    }

    public double getMotorSpeed(double currPos){
        double changePos = currPos-lastPos;
        lastPos = currPos;
        changeTime = timer.seconds()-lastTime;
        lastTime = timer.seconds();
        return changePos/changeTime;
    }

    public void setTargetSpeed(double ts){
        targetSpeed = ts;

    }
    public void reset(){
        integralOfError = 0;
    }

    //add Pause when starting
    public void updateMotorValues(double currPos){
        currSpeed = getMotorSpeed(currPos);
        currError = targetSpeed-currSpeed;

        currDer = (currError-lastError)/changeTime;
        lastError = currError;
        integralOfError += currError*changeTime;

    }

    public boolean isReady(){
        return isReady;
    }

    public double getPercentageError(){
        return ((currError/targetSpeed))*100;
    }


    public double getPow(){
//        if(Math.abs(currError) < 2000){
//            if(Math.abs(currError) < acc){
//                isReady = true;
//                return pow;
//            }
//            isReady = true;
//                //pow += Math.signum(currError) * pid.getPower(currError, currDer, integralOfError);
//            pow += Math.signum(currError) * 0.01;
//            pow = Range.clip(pow, -1, 1);
//            //isReady = false;
//            return pow;
//        }else{
//            isReady = false;
//            if(currError > 0){
//                return 0.4;
//            }else{
//                return -0.3;
//            }
//        }
        if(Math.abs(currError) < 2000) {
            //pow += Math.signum(currError) * 0.0004;
            pow = Range.clip(pow, -1, 1);
        }
        return pow;

    }


}
