package globalfunctions;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import global.TerraBot;

public class TelemetryHandler {
    //Telemetry and bot
    Telemetry telemetry;
    TerraBot bot;
    //Init with variables
    public void init(Telemetry telemetry, TerraBot bot){
        this.telemetry = telemetry;
        this.bot = bot;
    }

    //Adds outtake
    public void addOuttake(int howMany) {
        switch (howMany) {
            case 0:
                break;
            case 1:
                telemetry.addData("Right Outtake Position", bot.outr.getCurrentPosition());
                telemetry.addData("Left Outtake Position", bot.outl.getCurrentPosition());
            case 2:
                telemetry.addData("Right Outtake Angular Velocity", bot.getRightAngVel());
                telemetry.addData("Left Outtake Angular Velocity", bot.getLeftAngVel());
        }
    }



    public Telemetry getTelemetry(){
        return telemetry;
    }
}
