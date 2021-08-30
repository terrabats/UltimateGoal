package teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;

import global.TerraBot;
import globalfunctions.Constants;
import globalfunctions.TelemetryHandler;
import globalfunctions.TimeData;

@TeleOp(name = "TerraOp")
public class TerraOp extends OpMode {
    // Define the bot, telemetryHandler, optimizer
    TerraBot bot = new TerraBot();
    TelemetryHandler telemetryHandler = new TelemetryHandler();

    ArrayList<double[]> headingData = new ArrayList<>();

    @Override
    public void init() {
        telemetry.addData("Ready?", "No.");
        telemetry.update();
        bot.init(hardwareMap);
        // Initialize telemetryHandler and tell the driver that initialization is done
        telemetryHandler.init(telemetry, bot);
        telemetry.addData("Ready?", "Yes!");
        telemetry.update();


    }



    @Override
    public void loop() {

        // move the robot using the joysticks
        bot.moveTeleOp(-gamepad1.right_stick_y, gamepad1.right_stick_x, -gamepad1.left_stick_x, gamepad1.left_bumper);

        // update outtake wheels manually with gamepad2's right stick y
        bot.outtake(gamepad1.right_trigger);

        if (bot.intakeController.isPressedOnce(gamepad1.right_bumper)) bot.intaking = !bot.intaking;

        bot.intake(bot.intaking ? 1 : 0);

        // update the outtake servo manually with gamepad2's left stick y
        if (bot.isIntakeAvailable) bot.shootRings(gamepad1.left_trigger);

        if (bot.globalModeController.isPressedOnce(gamepad1.y)) bot.globalMode = !bot.globalMode;

        headingData.add(new double[]{Math.round(bot.getGyroAngle())});


        telemetry.addData("globalMode", bot.globalMode);
        telemetry.update();
    }

    @Override
    public void stop(){
        TimeData headingTimeData = new TimeData("headingData", headingData, false);
        bot.saveTimeData(headingTimeData);
    }

}
