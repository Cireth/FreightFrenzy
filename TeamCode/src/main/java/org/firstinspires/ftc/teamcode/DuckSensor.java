package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DuckSensor {
    ColorSensor colorSensor;
    Telemetry telemetry;

    public DuckSensor(HardwareMap hardwareMap, Telemetry telemetry){
        this.telemetry = telemetry;

        colorSensor = hardwareMap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, RobotMap.COLOR_SENSOR);
    }

    public boolean isYellow(){
        boolean yellow;
        double red = colorSensor.red();
        double green = colorSensor.green();
        double blue = colorSensor.blue();
        if(red/blue > 1.7 & green/blue > 1.4) {
            yellow = true;
        } else {
            yellow = false;

        }
        return yellow;

    }

    public void broadcastColor(){
        double red = colorSensor.red();
        double green = colorSensor.green();
        double blue = colorSensor.blue();
        telemetry.addData("Red Value: ", red);
        telemetry.addData("Blue Value: ", blue);
        telemetry.addData("Green Value: ", green);
        telemetry.addData("Red:Blue ", red/blue);
        telemetry.addData("Green:Blue ", green/blue);
    }

}
