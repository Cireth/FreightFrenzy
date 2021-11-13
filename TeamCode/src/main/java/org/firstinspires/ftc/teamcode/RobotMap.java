package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class RobotMap {

    // Robot Parameters
    public static final Boolean DISPLAY_TIME = true;
    public static final double DEADZONE = 0.05;

    // Drivetrain Parameters
    public static final String LEFT_FRONT_MOTOR = "left_front_drive";
    public static final String RIGHT_FRONT_MOTOR = "right_front_drive";
    public static final String LEFT_BACK_MOTOR = "left_back_drive";
    public static final String RIGHT_BACK_MOTOR = "right_back_drive";
    public static final DcMotor.Direction LEFT_DRIVE_DIRECTION = DcMotor.Direction.FORWARD;
    public static final DcMotor.Direction RIGHT_DRIVE_DIRECTION = DcMotor.Direction.REVERSE;
    public static final int REVERSE_DRIVETRAIN_ENCODER_VALUE = -1;

    // TankDrive Parameters
    public static final Boolean DISPLAY_MOTOR_VALUES = true;
    public static final Boolean REVERSE_JOYSTICKS = false;
    public static final double HIGHSPEED = 1.0;
    public static final double LOWSPEED = 0.3;

    // Encoder Parameters
    public static final Boolean DISPLAY_ENCODER_VALUES = true;
    public static final int ENCODER_TOLERANCE = 20;

    // Claw Parameters
    public static final String BUNGEE_MOTOR = "claw_motor";
    public static final DcMotor.Direction BUNGEE_DIRECTION = DcMotor.Direction.FORWARD;
    public static final double BUNGEE_SPEED = 0.9;
    public static final double BUNGEE_SLOW = .75;
    public static final int REVERSE_BUNGEE_ENCODER_VALUE = -1;


    // ELevator Parameters
    public static final double ELEVATOR_DIFF = 3200.0;
    public static final String ELEVATOR_MOTOR = "elevator_motor";
    public static final DcMotor.Direction ELEVATOR_DIRECTION = DcMotor.Direction.REVERSE;
    public static final double REVERSE_JOYSTICK_DIRECTION = -1.0;
    public static final double ELEVATOR_SPEED = 0.6;
    public static final double ELEVATOR_SPEED_DOWN = 0.1;
    public static final int REVERSE_ELEVATOR_ENCODER_VALUE = 1;
    public static final double ELEVATOR_KP = 0.01;

    //Webcam Parameters
    public static final String WEBCAM = "webcam";


}
