package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Class for controlling the Arm of an FTC robot.
 */
public class BungeeClaw {

    // Class variables
    DcMotor motor;
    Telemetry telemetry;


    /**
     * Constructor for the drivetrain
     *
     * @param hardwareMap the robot instance of the hardware map
     * @param telemetry the robot instance of the telemetry object
     */
    public BungeeClaw(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Assign hardware objects
        motor = hardwareMap.get(DcMotor.class, RobotMap.BUNGEE_MOTOR);

        // Set the motor directions
        motor.setDirection(RobotMap.BUNGEE_DIRECTION);

    }

    boolean buttonPressed = false;
    double encoderGoal;
    /**
     * Set the arm motor power for both left and right motors
     *
     * @param gamepad The gamepad from which to read joystick values
     */
    public void manual(Gamepad gamepad) {

        manual(gamepad.right_trigger, gamepad.left_trigger, gamepad.a);
    }
    public void manual(double right_trigger, double left_trigger, Boolean aButton){

        double speedLimit = RobotMap.BUNGEE_SLOW;

        if(aButton){
            speedLimit = RobotMap.BUNGEE_SPEED;
        }

        double power = right_trigger - left_trigger;

        // Limit speed of arm
        power *= speedLimit;





        setPower(power);

        //output the encoder value//
        if (RobotMap.DISPLAY_ENCODER_VALUES) {
            telemetry.addData("Bungee Encoder", getEncoder());
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
        return RobotMap.REVERSE_BUNGEE_ENCODER_VALUE * (motor.getCurrentPosition());
    }


}