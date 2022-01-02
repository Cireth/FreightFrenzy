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
        telemetry.addData("STAGE:", stage);


        if (stage == 0) {
            //moves elevator
            elevator.setElevatorGoal(1550);
            expirationTime = runtime.time() + 1.5;
            stage = 10;
        } else if (stage == 10) {
            //gives elevator time to move
            if (runtime.time() > expirationTime) stage = 20;
        } else if (stage == 20) {
            //strafe to dot 2
            driveTrainGoal = driveTrainEncoder + 1750;
            drivetrain.arcadeDrive(0, -0.6, 0, false, true);
            //rotate, forward/back, strafe
            expirationTime = runtime.time() + 7.0;
            stage = 30;
        } else if (stage == 30) {
            //stop
            if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 40;
            }

        }
        else if (stage == 40){
            expirationTime = runtime.time() + 1.0;
            stage = 50;
        }
        else if (stage == 50){
            //wait for two
            if (runtime.time() > expirationTime){
                stage = 60;
            }
        }
        else if (stage == 60) {
            //scan for duck 2
            duck = (duckSensor.isYellow()) ? 2 : 0;
            stage = 65;
        }
        else if (stage == 65) {
            //strafe to 3
            if (side == Side.RIGHT & color == Color.RED) {

            }
            else {
                driveTrainGoal = driveTrainEncoder + 1000;
                drivetrain.arcadeDrive(0, 0, .6, false, true);
                //rotate, forward/back, strafe
            }
            expirationTime = runtime.time() + 5.0;
            stage = 70;
        }
        else if (stage == 70) {
            //stops
            if (side == Side.RIGHT & color == Color.RED){
                //same as below flip sign? other direction
            }
            else {
                if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                    drivetrain.arcadeDrive(0, 0, .0, false, true);
                    //if two it skips scanning for three
                    if(duck == 2) {
                        stage = 100;
                    }
                    else{
                        expirationTime = runtime.time() + 1.0;
                        stage = 80;
                    }
                }
            }
        }
        else if (stage == 80){
            //wait
            if (runtime.time() > expirationTime){
                stage = 90;
            }
        }
        else if (stage == 90) {
            //scanning for 3 (or 1)
            duck = (duckSensor.isYellow()) ? 3 : 1;
            stage = 100;
        }
        else if (stage == 100){
            //sets elevator goal based on duck number, weird if red right
            if (side == Side.RIGHT & color == Color.RED){
                if (duck == 3){
                    duckAmount = 1500;
                }
                if (duck == 2){
                    duckAmount = 2600;
                }
                if (duck == 1){
                    duckAmount = 3800;
                }
            }
            else{
                if (duck == 1){
                    duckAmount = 1500;
                }
                if (duck == 2){
                    duckAmount = 2600;
                }
                if (duck == 3){
                    duckAmount = 3800;
                }
            }
            stage = 110;
        }
        else if (stage == 110){
            //move elevator to hub height
            elevator.setElevatorGoal(duckAmount);
            expirationTime = runtime.time() + 1;
            stage = 120;
        }
        else if (stage == 120) {
            if (runtime.time() > expirationTime) stage = 130;
        }
        else if (stage == 130) {
            //toward hub
            if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderLeft - 1000;
                drivetrain.arcadeDrive(0, 0, .6, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderLeft + 4000;
                drivetrain.arcadeDrive(0, 0, -.6, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.LEFT & color == Color.RED) {

            }
            if (side == Side.RIGHT & color == Color.RED) {

            }
            expirationTime = runtime.time() + 3.0;
            stage = 140;

        }
        else if (stage == 140) {
            //stopping
            if (side == Side.LEFT) {
                if (driveEncoderLeft < driveTrainGoal || (runtime.time() > expirationTime)) {
                    drivetrain.arcadeDrive(0, 0, .0, false, true);
                    stage = 150;
                }
            }
            if (side == Side.RIGHT) {
                if (driveEncoderLeft > driveTrainGoal || (runtime.time() > expirationTime)) {
                    drivetrain.arcadeDrive(0, 0, .0, false, true);
                    stage = 150;
                }
            }

        }
        else if (stage == 150) {
            //forward (toward hub)
            if (side == Side.RIGHT & color == Color.RED) {
                if (duck == 3){
                    driveTrainGoal = driveEncoderLeft + 930;
                }
                if (duck == 2){
                    driveTrainGoal = driveEncoderLeft + 980;
                }
                if (duck == 1) {
                    driveTrainGoal = driveEncoderLeft + 990;
                }
                drivetrain.arcadeDrive(0, -0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            else {
                if (duck == 1){
                    driveTrainGoal = driveEncoderLeft + 930;
                }
                if (duck == 2){
                    driveTrainGoal = driveEncoderLeft + 980;
                }
                if (duck == 3) {
                    driveTrainGoal = driveEncoderLeft + 990;
                }
                drivetrain.arcadeDrive(0, -0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            expirationTime = runtime.time() + 5.0;
            stage = 160;
        }
        else if (stage == 160) {
            //stopping
            if (driveEncoderLeft > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 170;
            }

        }
        else if(stage == 170){
            //move bungee release block
            bungeeClaw.manual(0, 0.5, false);
            expirationTime = runtime.time() + 1.5;
            stage = 175;
        }
        else if (stage == 175){
            //bungee stop
            if(runtime.time() > expirationTime){
                bungeeClaw.manual(0,0, false);
                stage = 180;
            }
        }
        else if (stage == 180) {
            //away from hub
            if (side == Side.LEFT & color == Color.BLUE){
                driveTrainGoal = driveEncoderLeft - 2300;
                drivetrain.arcadeDrive(0, 0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.RED){
                driveTrainGoal = driveEncoderLeft - 2300;
                drivetrain.arcadeDrive(0, 0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.BLUE){
                driveTrainGoal = driveEncoderLeft - 1900;
                drivetrain.arcadeDrive(0, 0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.LEFT & color == Color.RED){
                driveTrainGoal = driveEncoderLeft - 1900;
                drivetrain.arcadeDrive(0, 0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            expirationTime = runtime.time() + 2.5;
            stage = 183;
        }
        else if (stage == 183) {
            //stopping
            if (driveEncoderLeft < driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 185;
            }
        }
        else if (stage == 185) {
            //turn to duck ROTATE
            if (side == Side.LEFT & color == Color.BLUE) {
                //end possibly park in warehouse but i don't have the space for that
                stage = 10000;
            }
            if (side == Side.RIGHT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderRight - 2200;
                drivetrain.arcadeDrive(0.5, 0, 0, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 1.5;
                stage = 186;
            }
            if (side == Side.LEFT & color == Color.RED) {
                driveTrainGoal = driveEncoderRight + 3500;
                drivetrain.arcadeDrive(-0.5, 0, 0, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 1.5;
                stage = 186;
            }
            if (side == Side.RIGHT & color == Color.RED) {
                //end possibly park in warehouse but i don't have the space for that
                stage = 10000;
            }

        }
        else if (stage == 186) {
            //stopping
           if (side == Side.RIGHT){
               if (driveEncoderRight < driveTrainGoal || (runtime.time() > expirationTime)) {
                   drivetrain.arcadeDrive(0, 0, .0, false, true);
                   stage = 190;
               }
           }
           if (side == Side.LEFT){
               if (driveEncoderRight > driveTrainGoal || (runtime.time() > expirationTime)) {
                   drivetrain.arcadeDrive(0, 0, .0, false, true);
                   stage = 190;
               }
           }

        }
        else if (stage == 190){
            elevator.setElevatorGoal(RobotMap.DUCK_HEIGHT);
            expirationTime = runtime.time() + 1;
            stage = 191;
        }
        else if (stage == 191) {
            if (runtime.time() > expirationTime) stage = 200;
        }
        else if (stage == 200) {
            //forward (toward spin)
            driveTrainGoal = driveEncoderLeft + 3700;
            drivetrain.arcadeDrive(0, -0.6, 0, false, true);
            //rotate, forward/back, strafe
            expirationTime = runtime.time() + 3.0;
            stage = 210;

        }
        else if (stage == 210) {
            //stopping
            if (driveEncoderLeft > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 221;
            }

        }
        else if (stage == 221) {
            //turn to duck ROTATE PART TWO
            if (side == Side.RIGHT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderRight - 550;
                drivetrain.arcadeDrive(0.5, 0, 0, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 1.0;
                stage = 222;
            }
            if (side == Side.LEFT & color == Color.RED) {
                driveTrainGoal = driveEncoderRight + 500;
                drivetrain.arcadeDrive(-0.5, 0, 0, false, true);
                //rotate, forward/back, strafe
                expirationTime = runtime.time() + 1.0;
                stage = 222;
            }

        }
        else if (stage == 222) {
            //stopping
            if (side == Side.RIGHT){
                if (driveEncoderRight < driveTrainGoal || (runtime.time() > expirationTime)) {
                    drivetrain.arcadeDrive(0, 0, .0, false, true);
                    stage = 223;
                }
            }
            if (side == Side.LEFT){
                if (driveEncoderRight > driveTrainGoal || (runtime.time() > expirationTime)) {
                    drivetrain.arcadeDrive(0, 0, .0, false, true);
                    stage = 223;
                }
            }

        }

        else if(stage == 223){
            //move bungee
            bungeeClaw.manual(0, 0.5, false);
            expirationTime = runtime.time() + 2.5;
            stage = 230;
        }
        else if (stage == 230){
            //bungee stop
           if(runtime.time() > expirationTime){
               bungeeClaw.manual(0,0, false);
               stage = 240;
           }

        }
        /*
        else if (stage == 109){
            //backward & turn
            if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderLeft - 700;
                drivetrain.arcadeDrive(0.3, -0.5, 0, false, true);
                //rotate, forward/back, strafe
            }
            if (side == Side.RIGHT & color == Color.BLUE) {

            }
            if (side == Side.LEFT & color == Color.RED) {

            }
            if (side == Side.RIGHT & color == Color.RED) {

            }
            expirationTime = runtime.time() + 5.0;
            stage = 110;
        }
        else if (stage == 111){
            //stopping
            if (driveEncoderLeft > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 112;
            }
        }
        /* DUCK SPINNY
        else if (stage == 113){
            //to duck
            if (side == Side.LEFT & color == Color.BLUE) {
                driveTrainGoal = driveEncoderLeft + 1500;
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
            stage = 114;
        }
        else if (stage == 115){
            //stopping
            if (driveEncoderLeft > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 116;
            }
        }
        else if (stage == 116){
            //move elevator to duck height
            elevator.setElevatorGoal(2639);
            expirationTime = runtime.time() + 1.5;
            stage = 117;
        }
        else if (stage == 117) {
            if (runtime.time() > expirationTime) stage = 118;
        }
        else if (stage == 118){
            //im fairly convinced this is a job for the camera, position for ducks
            stage = 119;
        }
        else if (stage == 120){
            //move bungee to get duck
            bungeeClaw.manual(0, 0.5, false);
            expirationTime = runtime.time() + 3.5;
            stage = 121;
        }
        else if (stage == 121){
            //bungee stop
            if(runtime.time() > expirationTime){
                bungeeClaw.manual(0,0, false);
                stage = 122;
            }
        }
        else if (stage == 122){
            //park, warehouse on other side is 5 partial or 10 full, storage near is 3 or 6
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


