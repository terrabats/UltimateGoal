package global;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import globalfunctions.Constants;
import globalfunctions.Storage;
import globalfunctions.TimeData;
import telefunctions.ButtonController;

public class TerraBot {

    //Drive train Motors
    public DcMotor r1;
    public DcMotor l1;
    public DcMotor r2;
    public DcMotor l2;

    //Outtake Motors
    public DcMotorEx outr;
    public DcMotorEx outl;

    //Intake motor
    public DcMotor in;

    public BNO055IMU gyro;

    //Ring Shooter crservo
    public CRServo rs;

    //AutoAimer - Has methods for angle to target and shooting
    public AutoAimer autoAimer = new AutoAimer();

    //Is the robot intaking?
    public boolean intaking = false;
    //Is the robot outtaking?
    public boolean outtaking = false;


    //Is the robot in fastMode?
    public boolean fastMode = false;

    //Button Controls - Make delay between clicks to only count them once
    public ButtonController outtakeButtonController = new ButtonController();
    public ButtonController fastModeController = new ButtonController();
    public ButtonController intakeController = new ButtonController();
    public ButtonController globalModeController = new ButtonController();


    //Is movement available in teleop?
    public boolean isMovementAvailable = true;

    //Is outtake available in teleop?
    public boolean isOuttakeAvailable = true;

    //Is intake available in teleop?
    public boolean isIntakeAvailable = true;

    public boolean globalMode = false;

    public double gyroStart = 0.0;

    public Storage storage = new Storage();


    public void init(HardwareMap hwMap) {
        //Get drivetrain
        l1 = getMotor(hwMap, "l1", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        l2 = getMotor(hwMap, "l2", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r1 = getMotor(hwMap, "r1", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r2 = getMotor(hwMap, "r2", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Get Intake, Outtake, Wobble Goal
        in = getMotor(hwMap, "in", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outr = getMotorEx(hwMap, "outr", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outl = getMotorEx(hwMap, "outl", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.FLOAT, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Reset encoders for outtake and wobble goal
        outr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Get CR Servos - Ring Shooter, Ring knocker, Wobble goal extender
        rs = getCRServo(hwMap, "rs", CRServo.Direction.FORWARD);

        gyro = hwMap.get(BNO055IMU.class, "gyror");

        initGyro();

        gyroStart = getGyroAngle();

        //Set coeffs for shooter
        outr.setVelocityPIDFCoefficients(54, 0, 0, 14);
        outl.setVelocityPIDFCoefficients(54, 0, 0, 14);

    }

    //Initialize gyro sensors
    public void initGyro(){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        gyro.initialize(parameters);
    }

    public double getGyroAngle() {
        return gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - gyroStart;
    }

    //Helper methods for getting hardware
    public DcMotor getMotor(HardwareMap hwMap, String name, DcMotor.Direction dir, DcMotor.ZeroPowerBehavior zpb, DcMotor.RunMode mode){
        DcMotor dcMotor = hwMap.get(DcMotor.class, name);
        dcMotor.setPower(0);
        dcMotor.setDirection(dir);
        dcMotor.setZeroPowerBehavior(zpb);
        dcMotor.setMode(mode);
        return dcMotor;
    }

    public DcMotorEx getMotorEx(HardwareMap hwMap, String name, DcMotor.Direction dir, DcMotor.ZeroPowerBehavior zpb, DcMotor.RunMode mode){
        DcMotorEx dcMotor = hwMap.get(DcMotorEx.class, name);
        dcMotor.setPower(0);
        dcMotor.setDirection(dir);
        dcMotor.setZeroPowerBehavior(zpb);
        dcMotor.setMode(mode);
        return dcMotor;
    }

    public Servo getServo(HardwareMap hwMap, String name, Servo.Direction dir, double startpos){
        Servo servo = hwMap.get(Servo.class, name);
        servo.setDirection(dir);
        servo.setPosition(startpos);
        return servo;
    }

    public CRServo getCRServo(HardwareMap hwMap, String name, CRServo.Direction dir){
        CRServo crServo = hwMap.get(CRServo.class, name);
        crServo.setPower(0);
        crServo.setDirection(dir);
        return crServo;
    }

    //Method for moving
    public void move(double ft, double st, double t){
        double gyroAngle = Math.toRadians(getGyroAngle());
        double f = !globalMode ? ft : ft*Math.cos(gyroAngle)-st*Math.sin(gyroAngle);
        double s = !globalMode ? st : ft*Math.sin(gyroAngle)+st*Math.cos(gyroAngle);
        l1.setPower(f + s - t);
        l2.setPower(-f + s + t);
        r1.setPower(f - s + t);
        r2.setPower(-f - s - t);
    }
    //Intake power p and check if not outtake to control ring shooter
    public void intake(double p){
        in.setPower(p);
        isIntakeAvailable = p == 0;
        if (!isIntakeAvailable && isOuttakeAvailable) { shootRings(-Math.signum(p)*Constants.RS_POW);}
    }
    //Outtake at power p
    public void outtake(double p){
        outr.setPower(p);
        outl.setPower(p);
        isOuttakeAvailable = p == 0;
    }

    public void shootRings(double pow){
        rs.setPower(pow);
    }

    //Update intake for teleop
    public void updateIntake(boolean left_bumper, boolean right_bumper) {
        //Set intake on with right bumper and turn it off with left bumper and also go backward with left bumper
        if(isIntakeAvailable) {
            if (right_bumper) {
                intaking = true;
            } else if (left_bumper) {
                intaking = false;
                intake(-0.5);
            } else if (intaking) {
                intake(1);
            } else {
                intake(0);
            }
        }
    }

    //Move for teleop
    public void moveTeleOp(double f, double s, double t, boolean rt){
        //If movement is available in teleop then move
        if(isMovementAvailable){
            //Fastmode movements are about twice as fast as not fastmode
            if (fastMode) {
                move(Math.signum(f) * Math.pow(Math.abs(f), 0.5), Math.signum(s) * Math.pow(Math.abs(s), 0.5), Math.signum(t) * Math.pow(Math.abs(t), 0.5));
            } else {
                move(0.5 * Math.signum(f) * Math.pow(Math.abs(f), 0.5), 0.5 * Math.signum(s) * Math.pow(Math.abs(s), 0.5), 0.4 * Math.signum(t) * Math.pow(Math.abs(t), 0.5));
            }
        }
        //Switch fastmode using button controller
        if (fastModeController.isPressedOnce(rt)) {
            fastMode = !fastMode;
            isMovementAvailable = true;
        }
    }

    //Get angular velocities of outtake motors
    public double getRightAngVel(){ return (outr.getVelocity()/Constants.GOBUILDA1_Ticks)*Constants.pi2;}
    public double getLeftAngVel(){ return (outl.getVelocity()/Constants.GOBUILDA1_Ticks)*Constants.pi2; }

    public void saveTimeData(TimeData in){
        storage.saveTimeData(in);
    }

}
