package pacBot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import java.util.Random;

public class GhostBehavior2 implements Behavior {
	
	private final EV3LargeRegulatedMotor leftMotor = InitialiseHardware.leftMotor;
    private final EV3LargeRegulatedMotor rightMotor = InitialiseHardware.rightMotor;
    private final EV3ColorSensor colorSensor = InitialiseHardware.middleSensor;
    
	private Random random = new Random();

	@Override
	public boolean takeControl() {
		return colorSensor.getColorID() == Color.RED;
	}

	@Override
	public void action() {
		System.out.println("Ghost Detected!");
		leftMotor.stop();
		rightMotor.stop();
		Delay.msDelay(100);
		
		leftMotor.backward();
		rightMotor.backward();
		Delay.msDelay(500);
		
		if (random.nextBoolean()) {
			leftMotor.rotate(360);
		} else {
			rightMotor.rotate(360);
		}
		
		leftMotor.stop();
		rightMotor.stop();
		
	}

	@Override
	public void suppress() {
		leftMotor.stop();
		rightMotor.stop();
	}
}