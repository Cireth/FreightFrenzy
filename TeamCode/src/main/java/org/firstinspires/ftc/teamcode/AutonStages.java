package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutonStages {

    public static enum Color {RED, BLUE;}
    public static enum Side {LEFT, RIGHT;}
    Color color;
    Side side;

    private DriveTrain drivetrain;
    private ElevatorMotor elevator;
    private BungeeClaw bungeeClaw;
    private int stage = 0;
    private double expirationTime;
    private ElapsedTime runtime;
    private double driveTrainGoal;
    private int duck = 0;
    private int duckNumber;
    private int driveTrainEncoder;


    public AutonStages(Color color, Side side, HardwareMap hardwareMap,
                       Telemetry telemetry, ElapsedTime runtime){
        this.color = color;
        this.side = side;
        this.runtime = runtime;
        drivetrain = new DriveTrain(hardwareMap, telemetry);
        elevator = new ElevatorMotor(hardwareMap, telemetry);
        bungeeClaw = new BungeeClaw(hardwareMap, telemetry);
    }

    public void mainStages(){
        elevator.manual();
        driveTrainEncoder = drivetrain.rightEncoder.getCurrentPosition();

        if (stage == 0){
            elevator.moveElevator(500);
            expirationTime = runtime.time() + 1.5;
            stage = 1;
        }
        else if (stage == 1){
            if (runtime.time() > expirationTime) stage = 2;
        }
        else if (stage == 2){
            if (side == Side.LEFT & color == Color.BLUE){
                driveTrainGoal = driveTrainEncoder + 3000;
                drivetrain.arcadeDrive(0, 0.6, -.1, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side. RIGHT & color == Color.BLUE){

            }
            if (side == Side.LEFT & color == Color.RED){

            }
            if (side == Side.RIGHT & color == Color.RED){

            }
            expirationTime = runtime.time() + 7.0;
            stage = 3;
        }
        else if (stage == 3){
            if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 4;
            }

        }
        else if (stage == 4){
            duck = (DuckSensor.isYellow()) ? 1: 0;
            if (duck == 0) stage = 5;
            else if (duck == 1) stage = 100;
            }
        }
        else if (stage == 5){
            if (side == Side.LEFT & color == Color.BLUE){
                driveTrainGoal = driveTrainEncoder + 1000;
                drivetrain.arcadeDrive(0, 0, -.6, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side. RIGHT & color == Color.BLUE){

            }
            if (side == Side.LEFT & color == Color.RED){

            }
            if (side == Side.RIGHT & color == Color.RED){

            }
            expirationTime = runtime.time() + 7.0;
            stage = 6;
        }
        else if (stage == 7){
            if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 8;

            }
        }
        else if (stage == 8){
        duck = (DuckSensor.isYellow()) ? 2: 3;
        if (duck == 2) stage = 101;
        else if (duck == 3) stage = 102;

    }

}


