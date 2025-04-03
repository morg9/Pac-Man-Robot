package pacBot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import java.util.ArrayList;
import java.util.List;


public class LineBehavior implements Behavior {
    private static final float BLACK_THRESHOLD = 0.2f;
    private static final int BASE_SPEED = 200;
    private static final int TURN_SPEED = 150;
    
    private final EV3LargeRegulatedMotor leftMotor = InitialiseHardware.leftMotor;
    private final EV3LargeRegulatedMotor rightMotor = InitialiseHardware.rightMotor;
    private final EV3ColorSensor leftSensor = InitialiseHardware.leftSensor;
    private final EV3ColorSensor middleSensor = InitialiseHardware.middleSensor;
    private final EV3ColorSensor rightSensor = InitialiseHardware.rightSensor;
    
    SampleProvider leftColor = leftSensor.getRedMode();
    SampleProvider middleColor = middleSensor.getRedMode();
    SampleProvider rightColor = rightSensor.getRedMode();
    
    boolean lastSeenLeft = true;
    private volatile boolean suppressed = false;
    
    public static List<String> movementHistory = new ArrayList<>();

	@Override
	public boolean takeControl() {
		return true; // TODO: change to when not red
	}

	@Override
	public void action() {
		float[] leftSample = new float[1];
        float[] middleSample = new float[1];
        float[] rightSample = new float[1];
        
        while (!suppressed ) {
	        leftColor.fetchSample(leftSample, 0);
	        middleColor.fetchSample(middleSample, 0);
	        rightColor.fetchSample(rightSample, 0);
	
	        boolean leftOnBlack = leftSample[0] < BLACK_THRESHOLD;
	        boolean middleOnBlack = middleSample[0] < BLACK_THRESHOLD;
	        boolean rightOnBlack = rightSample[0] < BLACK_THRESHOLD;
	
	        if (middleOnBlack) {
	            leftMotor.setSpeed(BASE_SPEED);
	            rightMotor.setSpeed(BASE_SPEED);
	            leftMotor.forward();
	            rightMotor.forward();
	            movementHistory.add("FORWARD");
	        }
	        else {
	        	if (leftOnBlack) {
	        		lastSeenLeft = true;
	        		leftMotor.setSpeed(TURN_SPEED);
	        		rightMotor.setSpeed(BASE_SPEED);
	        		leftMotor.backward();
	        		rightMotor.forward();
	        		movementHistory.add("LEFT");
	        	}
	        	else if (rightOnBlack) {
	        		lastSeenLeft = false;
	        		leftMotor.setSpeed(BASE_SPEED);
	        		rightMotor.setSpeed(TURN_SPEED);
	        		leftMotor.forward();
	        		rightMotor.backward();
	        		movementHistory.add("RIGHT");
	        	}
	        	else {
	        		if (lastSeenLeft) {
	            		leftMotor.setSpeed(TURN_SPEED);
	            		rightMotor.setSpeed(TURN_SPEED);
	            		leftMotor.backward();
	            		rightMotor.forward();
	            		
	        		} else {
	        			leftMotor.setSpeed(TURN_SPEED);
	            		rightMotor.setSpeed(TURN_SPEED);
	            		leftMotor.forward();
	            		rightMotor.backward();
	        		}
	        		
	        		while(!suppressed) {
	        			middleColor.fetchSample(middleSample, 0);
	        			if (middleSample[0] < BLACK_THRESHOLD) {
	        				break;
	        			}
	        		}
	        	}
	        }
        }
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

