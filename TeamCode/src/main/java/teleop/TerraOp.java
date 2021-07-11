package teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import global.TerraBot;
import globalfunctions.Constants;
import globalfunctions.TelemetryHandler;

@TeleOp(name = "TerraOp")
public class TerraOp extends OpMode {
    // Define the bot, telemetryHandler, optimizer
    TerraBot bot = new TerraBot();
    TelemetryHandler telemetryHandler = new TelemetryHandler();

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

//        if(bot.isOuttakeAvailable) {
//            if (gamepad2.right_bumper) {
//                bot.shootRings(Constants.RS_POW);
//            } else if (gamepad2.left_bumper) {
//                bot.shootRings(-Constants.RS_POW);
//            } else {
//                bot.shootRings(0);
//            }
//        }

        telemetry.addData("globalMode", bot.globalMode);
        telemetry.update();
    }

}
