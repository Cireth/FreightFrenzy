package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotMap;

public class CameraAuton {
    private ElapsedTime runtime;
    private BungeeClaw bungeeClaw;
    private ElevatorMotor elevatorMotor;
    private DriveTrain driveTrain;
    Telemetry telemetry;
    private int stage;
    private VuforiaData vuforiaData;


    public CameraAuton(HardwareMap hardwareMap, Telemetry telemetry, ElapsedTime elapsedTime){
        this.runtime = runtime;
        this.telemetry = telemetry;
        bungeeClaw = new BungeeClaw(hardwareMap, telemetry);
        elevatorMotor = new ElevatorMotor(hardwareMap, telemetry);
        driveTrain = new DriveTrain(hardwareMap, telemetry);
        vuforiaData = new VuforiaData(hardwareMap, telemetry);

    }

    public void mainStages(){

        if(stage == 0){

        }
        else if(stage == 10){

        }
        else if(stage == 20){

        }
    }
}
