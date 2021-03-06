package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


import autofunctions.Path;
import autofunctions.RobotFunctions;
import autofunctions.TerraCV;
import global.TerraBot;
import util.CodeSeg;
import util.Rect;
import autofunctions.TerraCV.RingNum;

@Autonomous(name = "AutoLegend", group = "Auto")
public class AutoLegend extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    Path path = new Path(0,0,0);

    double vs = 0;

    @Override
    public void runOpMode() {
        initialize();
        //rf.generateRandomIM();
        while (!isStarted() && !bot.isDoneResettingArm()) {
            bot.resetArm();
        }
        bot.intake(0);
        rf.telemetryText("done initializing");
        rf.scanRings();
        waitForStart();
        bot.startOdoThreadAuto(this);

        path.addStop(0.1);
        path.addRF(rf.changeKs(0.8, path), rf.toggleOuttake(bot), rf.changeAcc(1, 1, 1, path), rf.changeOuttakePow(0.88, vs, path), rf.wobbleArm(90,1));
        path.addWaypoint(0, 30, 0);
        path.addSetpoint(5, 20, 0);
        path.addRF(rf.changeKs(1, path));
        path.addStop(0.3);
        path.addRF(rf.shootControl(2));
        path.addStop(0.8);
        path.addRF(rf.shootControl(3));
        path.addStop(0.3);
        path.addRF(rf.shootControl(2));
        path.addStop(0.8);
        path.addRF(rf.shootControl(3));
        path.addStop(0.3);
        path.addRF(rf.shootControl(2));
        path.addStop(0.8);
        path.addRF(rf.shootControl(3));
        path.addStop(0.5);
        path.addRF(rf.lift(0), rf.shootControl(1));
        path.addStop(0.35);
        path.addSetpoint(0, 50, 0);
        path.addRF(rf.shootControl(1), rf.intake(1), rf.changeAcc(3, 3, 3, path));
        path.addStop(0.2);
        path.addSetpoint(0, -5, 0);
        path.addStop(0.8);
        path.addRF(rf.changeKs(0.5, path));
        path.addWaypoint(0, 20, 0);
        path.addRF(rf.changeAcc(1, 1, 1, path));
        path.addSetpoint(0, 10, 0);
        path.addRF(rf.changeKs(1, path));
        path.addStop(0.5);
        path.addRF(rf.lift(1));
        path.addStop(0.5);
        path.addRF(rf.intake(0), rf.changeOuttakePow(0.9, vs, path));
        path.addStop(0.5);
        path.addRF(rf.shootControl(2));
        path.addStop(0.8);
        path.addRF(rf.shootControl(3));
        path.addStop(0.3);
        path.addRF(rf.shootControl(2));
        path.addStop(0.8);
        path.addRF(rf.shootControl(3));
        path.addStop(0.5);
        path.addRF(rf.lift(0), rf.shootControl(1), rf.intake(1));
        path.addStop(0.35);
        path.addSetpoint(0, 20, 0);
        path.addRF(rf.intake(0), rf.lift(1), rf.shootControl(1),  rf.changeOuttakePow(1, vs, path));



        //path.addWaypoint(50,28,0);
//        powerShot();
//        path.addStop(0.1);
//        path.addWaypoint(-55, 105, -103);
//        path.addRF(rf.changeKs(1, path), rf.lift(0), rf.shootControl(1), rf.turnArm(0.68), rf.wobbleArm(130,1));
//        path.addSetpoint(-12, 20, 0);
//        path.addStop(0.2);
//        path.addRF(rf.grab(0));
//        path.addStop(0.1);
//        path.addWaypoint(25,-15,0);
//        path.addRF(rf.wobbleArm(190,1));
//        path.addStop(0.1);
//        path.addRF(rf.changeAcc(3, 3, 2, path));
//        path.addSetpoint(8, -117, -61);
//        path.addRF(rf.changeAcc(3, 2, 3, path), rf.changeKs(1.4, path));
//        path.addSetpoint(0,-40,0);
//        path.addRF(rf.intake(1), rf.changeKs(1, path));
//        path.addStop(0.5);
//        path.addSetpoint(0, -10, 0);
//        path.addStop(0.5);
//        path.addRF(rf.changeKs(0.5, path));
//        path.addWaypoint(0, -23, 0);
//        path.addRF(rf.changeKs(1, path));// intake
//        path.addSetpoint(35,26,180);
//        path.addRF(rf.changeAcc(1,1,1, path), rf.intake(1), rf.toggleOuttake(bot));
//        path.addSetpoint(0, -20, 0);
//        path.addRF(rf.changeOuttakePow(0.9, vs, path), rf.grab(1));
//        path.addStop(0.5);
//        path.addRF(rf.wobbleArm(130, 1));
//        path.addStop(0.5);
//        path.addRF(rf.intake(0), rf.lift(1));
//        path.addSetpoint(-35, 78, -3);
//        path.addRF(rf.shootControl(2));
//        path.addStop(0.35);
//        path.addRF(rf.shootControl(3));
//        path.addStop(0.35);
//        path.addRF(rf.shootControl(2));
//        path.addStop(0.35);
//        path.addRF(rf.shootControl(3));
//        path.addStop(0.35);
//        path.addRF(rf.shootControl(2));
//        path.addStop(0.35);
//        path.addRF(rf.shootControl(3));
//        path.addStop(0.35);
//        path.addRF(rf.shootControl(2));
//        path.addStop(0.35);
//        path.addRF(rf.toggleOuttake(bot), rf.changeAcc(3, 3, 5, path), rf.turnArm(0.68), rf.wobbleArm(150, 1));
//        path.addSetpoint(-15,100,-135);
//        path.addRF(rf.grab(0));
//        path.addWaypoint(15,-15,0);
//        path.addSetpoint(0, -80, 0);


        path.start(bot, this);
        bot.stopOdoThreadAuto();
        bot.move(0,0,0);

    }

    private void initialize(){
        bot.grabStart = 0.45;
        bot.outrController.acc = 1000;
        bot.outlController.acc = 1000;
        bot.init(hardwareMap);
        rf.init(bot, this);
        bot.intake(1);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds() < 1){}
        bot.lift(bot.liftControl.getPos(1));
        bot.shoot(bot.shootControlR.getPos(2), bot.shootControlL.getPos(2));
        vs = bot.getVoltageScale();
        path.shootSpeed = bot.powerShotSpeed;
        bot.outtakeStartL *= vs;
        bot.outtakeStartR *= vs;
        bot.outrController.setStartPow(bot.outtakeStartR*bot.powerShotSpeed);
        bot.outlController.setStartPow(bot.outtakeStartL*bot.powerShotSpeed);
    }

    private void dropWobble(){
        path.addRF(rf.turnArm(0.68), rf.wobbleArm(180,1));
        path.addStop(1);
        path.addRF(rf.grab(0));
        path.addStop(0.5);
        path.addWaypoint(15,-15,0);
        path.addRF(rf.wobbleArm(190,1));
    }

    private void powerShot(){
        path.addSetpoint(13, 10, 0);
        path.addRF(rf.changeAcc(1, 1, 1, path), rf.updateXWithDis(60));
        path.addStop(0.3);
        path.addSetpoint(0,0,0);
        path.addRF(rf.shootControl(3));
        path.addStop(0.3);
        path.addRF(rf.shootControl(2));
        path.addStop(0.3);
        path.addRF(rf.changeKs(1, path));
        path.addSetpoint(0,0,-6);
        path.addRF(rf.shootControl(3));
        path.addStop(0.3);
        path.addRF(rf.shootControl(2));
        path.addStop(0.3);
        path.addSetpoint(0,0,-6);
        path.addRF(rf.shootControl(3));
        path.addStop(0.3);
        path.addRF(rf.shootControl(2));
        path.addStop(0.3);
        path.addRF(rf.toggleOuttake(bot), rf.changeAcc(3, 3, 5, path));
    }
}
