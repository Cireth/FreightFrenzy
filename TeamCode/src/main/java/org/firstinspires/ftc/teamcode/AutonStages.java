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
    private DuckSensor duckSensor;
    private Telemetry telemetry;
    private int stage = 0;
    private double expirationTime;
    private ElapsedTime runtime;
    private double driveTrainGoal;
    private int duck = 0;
    private int driveTrainEncoder;
    private int duckAmount;


    public AutonStages(Color color, Side side, HardwareMap hardwareMap,
                       Telemetry telemetry, ElapsedTime runtime){
        this.color = color;
        this.side = side;
        this.runtime = runtime;
        this.telemetry = telemetry;
        drivetrain = new DriveTrain(hardwareMap, telemetry);
        elevator = new ElevatorMotor(hardwareMap, telemetry);
        bungeeClaw = new BungeeClaw(hardwareMap, telemetry);
        duckSensor = new DuckSensor(hardwareMap, telemetry);
    }

    public void mainStages() {
        elevator.manual();
        int driveEncoderRight = drivetrain.getEncoderRight();
        int driveEncoderLeft = drivetrain.getEncoderLeft();
        driveTrainEncoder = driveEncoderRight;
        duckSensor.broadcastColor();
        drivetrain.outputEncoders();
        telemetry.addData("Duck", duck);


        if (stage == 0) {
            //moves elevator
            elevator.setElevatorGoal(1700);
            expirationTime = runtime.time() + 1.5;
            stage = 1;
        } else if (stage == 1) {
            //gives elevator time to move
            if (runtime.time() > expirationTime) stage = 2;
        } else if (stage == 2) {
            //strafe to dot 2
            if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveTrainEncoder + 1750;
                drivetrain.arcadeDrive(0, -0.6, 0, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.BLUE) {

            }
            if (side == Side.LEFT & color == Color.RED) {

            }
            if (side == Side.RIGHT & color == Color.RED) {

            }
            expirationTime = runtime.time() + 7.0;
            stage = 3;
        } else if (stage == 3) {
            //stop
            if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 23;
            }

        }
        else if (stage == 23){
            if (runtime.time() > expirationTime);
            stage = 4;
        }
        else if (stage == 4) {
            //scan for duck 2
            duck = (duckSensor.isYellow()) ? 2 : 0;
            stage = 5;
        }
        else if (stage == 5) {
            //strafe to 3
            if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveTrainEncoder + 1000;
                drivetrain.arcadeDrive(0, 0, .6, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.BLUE) {

            }
            if (side == Side.LEFT & color == Color.RED) {

            }
            if (side == Side.RIGHT & color == Color.RED) {

            }
            expirationTime = runtime.time() + 5.0;
            stage = 6;
        } else if (stage == 6) {
            //stops
            if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                expirationTime = runtime.time() + 1.0;
                if(duck == 2) {
                    stage = 100;
                }
                else{
                    stage = 7;
                }
            }
        }
        else if (stage == 7){
            //wait
            if (runtime.time() > expirationTime);
            stage = 8;
        }
        else if (stage == 8) {
            //scanning for 3 (or 1)
            duck = (duckSensor.isYellow()) ? 3 : 1;
            stage = 100;
        }
        else if (stage == 100){
            if (duck == 1){
                duckAmount = 1300;
            }
            if (duck == 2){
                duckAmount = 2600;
            }
            if (duck == 3){
                duckAmount = 3800;
            }
            stage = 101;
        }
        else if (stage == 101){
            elevator.setElevatorGoal(duckAmount);
            expirationTime = runtime.time() + 1.5;
            stage = 102;
        }
        else if (stage == 102) {
            if (runtime.time() > expirationTime) stage = 103;
        }
        else if (stage == 103) {
            //turn
            if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderLeft + 1000;
                drivetrain.arcadeDrive(0.5, 0, -.1, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.BLUE) {

            }
            if (side == Side.LEFT & color == Color.RED) {

            }
            if (side == Side.RIGHT & color == Color.RED) {

            }
            expirationTime = runtime.time() + 5.0;
            stage = 104;

        }
        else if (stage == 104) {
            //stopping
            if (driveEncoderLeft > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 105;
            }

        }
        else if (stage == 105) {
            //forward
            if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderLeft + 700;
                drivetrain.arcadeDrive(0, -0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.BLUE) {

            }
            if (side == Side.LEFT & color == Color.RED) {

            }
            if (side == Side.RIGHT & color == Color.RED) {

            }
            expirationTime = runtime.time() + 5.0;
            stage = 106;

        }
        else if (stage == 106) {
            //stopping
            if (driveEncoderLeft > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 107;
            }

        }
        else if(stage == 107){
            //move bungee
            bungeeClaw.manual(0, 0.5, false);
            expirationTime = runtime.time() + 2.5;
            stage = 108;
        }
        else if (stage == 108){
            //bungee stop
           if(runtime.time() > expirationTime){
               bungeeClaw.manual(0,0, false);
               stage = 109;
           }
        }
        else if (stage == 109){

        }




        /*
         if (stage == 1000) {
            elevator.moveElevator(1800);
            expirationTime = runtime.time() + 1.0;
            stage = 1100;
        } else if (stage == 1100) {
            if (runtime.time() > expirationTime) stage = 1200;
        } else if (stage ==1200) {
            if (side == Side.RIGHT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderRight + 5000;
                drivetrain.arcadeDrive(0, -0.6, 0.6, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 5.0;
                stage = 1300;
            } else if (side == Side.LEFT & color == Color.RED) {
                driveTrainGoal = driveEncoderLeft + 5000;
                drivetrain.arcadeDrive(0, -0.6, -0.6, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 5.0;
                stage = 1300;
            } else if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderLeft + 3000;
                drivetrain.arcadeDrive(0, 0, -0.6, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 5.0;
                stage = 1300;
            } else if (side == Side.RIGHT & color == Color.RED) {
                driveTrainGoal = driveEncoderRight + 3000;
                drivetrain.arcadeDrive(0, 0, 0.6, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 5.0;
                stage = 1300;
            }
        } else if (stage == 1300) {
            int encoderValue = 0;
            if (side == Side.RIGHT & color == Color.BLUE) {
                encoderValue = driveEncoderRight;
            } else if (side == Side.LEFT & color == Color.RED) {
                encoderValue = driveEncoderLeft;
            } else if (side == Side.RIGHT & color == Color.RED) {
                encoderValue = driveEncoderRight;
            } else if (side == Side.LEFT & color == Color.BLUE) {
                encoderValue = driveEncoderLeft;
            }
            if (encoderValue > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 1400;
            }
        } else if (stage ==1400) {
            elevator.moveElevator(-1800);
            stage = 1500;
        }

         */

    }
}


