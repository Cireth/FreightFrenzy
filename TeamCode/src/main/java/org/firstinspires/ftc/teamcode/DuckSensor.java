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
        if(colorSensor.red() > 35) {
            yellow = true;
        } else {
            yellow = false;
        }
        return yellow;

    }

    public void broadcastColor(){

        //telemetry.addData("Red Value: ", colorSensor.red());
        //telemetry.addData("Blue Value: ", colorSensor.blue());
        //telemetry.addData("Green Value: ", colorSensor.green());
    }

}
