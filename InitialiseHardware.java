package pacBot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class InitialiseHardware {
	public static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    public static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

    public static final EV3ColorSensor leftSensor = new EV3ColorSensor(SensorPort.S2);
    public static final EV3ColorSensor middleSensor = new EV3ColorSensor(SensorPort.S3);
    public static final EV3ColorSensor rightSensor = new EV3ColorSensor(SensorPort.S4);

    public static final SampleProvider leftColor = leftSensor.getRedMode();
    public static final SampleProvider middleColor = middleSensor.getRedMode();
    public static final SampleProvider rightColor = rightSensor.getRedMode();
    
    public static void shutdown() {
    	leftMotor.close();
    	rightMotor.close();
    	leftSensor.close();
    	middleSensor.close();
    	rightSensor.close();
    }
}
