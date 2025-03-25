package myPackage;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import java.util.Random;

public class GhostBehavior implements Behavior {
	private EV3ColorSensor SENSOR_PORT_LEFT = new EV3ColorSensor(SensorPort.S2);
	private EV3ColorSensor SENSOR_PORT_RIGHT = new EV3ColorSensor(SensorPort.S4);
	private EV3LargeRegulatedMotor MOTOR_LEFT = new EV3LargeRegulatedMotor(MotorPort.A);
	private EV3LargeRegulatedMotor MOTOR_RIGHT = new EV3LargeRegulatedMotor(MotorPort.B);
	private Random random = new Random();

	@Override
	public boolean takeControl() {
		return SENSOR_PORT_LEFT.getColorID() == Color.RED || SENSOR_PORT_RIGHT.getColorID() == Color.RED;
	}

	@Override
	public void action() {
		System.out.println("Ghost Detected!");
		MOTOR_LEFT.stop();
		MOTOR_RIGHT.stop();
		Delay.msDelay(500);
		
		MOTOR_LEFT.backward();
		MOTOR_RIGHT.backward();
		Delay.msDelay(500);
		
		if (random.nextBoolean()) {
			MOTOR_LEFT.rotate(-180);
		} else {
			MOTOR_RIGHT.rotate(180);
		}
		
		MOTOR_LEFT.stop();
		MOTOR_RIGHT.stop();
		
	}

	@Override
	public void suppress() {
		MOTOR_LEFT.stop();
		MOTOR_RIGHT.stop();
	}
	
	public static void main(String[] args) {
		GhostBehavior ghostBehavior = new GhostBehavior();
		
		if (ghostBehavior.takeControl()) {
			ghostBehavior.action();
		}
		Thread.yield();
	}
}
