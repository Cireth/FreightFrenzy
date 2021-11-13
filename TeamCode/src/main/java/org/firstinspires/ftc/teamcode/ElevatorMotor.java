package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Class for controlling the Arm of an FTC robot.
 */
public class ElevatorMotor {

    // Class variables
    double encoderGoal;
    DcMotor motor;
    Telemetry telemetry;
    double startEncoder;


    /**
     * Constructor for the drivetrain
     *
     * @param hardwareMap the robot instance of the hardware map
     * @param telemetry the robot instance of the telemetry object
     */
    public ElevatorMotor(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Assign hardware objects
        motor = hardwareMap.get(DcMotor.class, RobotMap.ELEVATOR_MOTOR);

        // Set the motor directions
        motor.setDirection(RobotMap.ELEVATOR_DIRECTION);

        //Set the encoder starting position
        encoderGoal = motor.getCurrentPosition();
        startEncoder = encoderGoal;
    }


    /**
     * Set the arm motor power for both left and right motors
     *
     * @param gamepad The gamepad from which to read joystick values
     */
    public void manual(Gamepad gamepad) {

        manual(gamepad.left_stick_y);
    }
    public void manual(double leftStick){
        double power;
        double encoderMax = startEncoder + RobotMap.ELEVATOR_DIFF;
        double encoderValue = getEncoder();
        double speedLimit = RobotMap.ELEVATOR_SPEED;
        double speedLimitDown = RobotMap.ELEVATOR_SPEED_DOWN;

        // greater than and less than signs may need to be switched

        power = leftStick * RobotMap.REVERSE_JOYSTICK_DIRECTION;

        if(encoderValue >= encoderMax && power > 0){
            power = 0;
        }
        else if(encoderValue <= startEncoder && power < 0){
            power = 0;
        }


        if (Math.abs(power) < RobotMap.DEADZONE) {
            double error = encoderGoal - encoderValue;
            power = RobotMap.ELEVATOR_KP * error;
        }
        else {
            encoderGoal = encoderValue;
        }

        // Limit speed of arm
        if(power < 0) {
            power *= speedLimitDown;
        }
        else {
            power *= speedLimit;
        }

        setPower(power);

        //output the encoder value//
        if (RobotMap.DISPLAY_ENCODER_VALUES) {
            telemetry.addData("Elevator Encoder", getEncoder());
        }



    }

    private void setPower(double power){
        // Make sure power levels are within expected range
        power = safetyCheck(power);

        // Send calculated power to motors
        motor.setPower(power);
    }

    private double safetyCheck(double inp) {
        double out = inp;
        out = Math.max(-1.0, out);
        out = Math.min(1.0, out);
        return out;
    }

    public int getEncoder () {
        return RobotMap.REVERSE_ELEVATOR_ENCODER_VALUE * (motor.getCurrentPosition());
    }


}