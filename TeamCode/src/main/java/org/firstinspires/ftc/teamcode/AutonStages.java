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
    private int stage = 1000;
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

        if (stage == 0) {
            elevator.moveElevator(1800);
            expirationTime = runtime.time() + 1.5;
            stage = 1;
        } else if (stage == 1) {
            if (runtime.time() > expirationTime) stage = 2;
        } else if (stage == 2) {
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
            if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                stage = 4;
            }

        } else if (stage == 4) {
            duck = (duckSensor.isYellow()) ? 2 : 0;
            if (duck == 0) stage = 5;
            else if (duck == 2) stage = 102;
        } else if (stage == 5) {
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
            if (driveTrainEncoder > driveTrainGoal || (runtime.time() > expirationTime)) {
                drivetrain.arcadeDrive(0, 0, .0, false, true);
                expirationTime = runtime.time() + 1.0;
                stage = 7;
            }
        }
        else if (stage == 7){
            if (runtime.time() > expirationTime);
            stage = 8;
        }
        else if (stage == 8) {
            duck = (duckSensor.isYellow()) ? 3 : 1;
            if (duck == 1) stage = 101;
            else if (duck == 3) stage = 103;
        } else if (stage == 103 || stage == 101 || stage == 102) {
            telemetry.addData("Duck", duck);
            //temporary "we found a duck, tell the thingy"
        }
    }
}


