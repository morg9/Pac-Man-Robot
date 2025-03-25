import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class FollowLine {
    private static final Port SENSOR_PORT_LEFT = BrickFinder.getDefault().getPort("S2");
    private static final Port SENSOR_PORT_RIGHT = BrickFinder.getDefault().getPort("S4");
    private static final EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
    private static final EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
    private static final float LIGHT_THRESHOLD = 0.68f; //0.14-15 is RED and 0.3 is BLACK i use .16 because its below it. 0.55-0.68 current height
    
    public static void main(String[] args) {
        EV3ColorSensor leftSensor = new EV3ColorSensor(SENSOR_PORT_LEFT);
        EV3ColorSensor rightSensor = new EV3ColorSensor(SENSOR_PORT_RIGHT);
        SampleProvider leftMode = leftSensor.getRedMode();
        SampleProvider rightMode = rightSensor.getRedMode();
        float[] leftSample = new float[leftMode.sampleSize()];
        float[] rightSample = new float[rightMode.sampleSize()];
        
        System.out.println("Press ENTER to start");
        Button.ENTER.waitForPressAndRelease();
        
        LEFT_MOTOR.setSpeed(300);
        RIGHT_MOTOR.setSpeed(300);
        
        while (!Button.ENTER.isDown()) {
            leftMode.fetchSample(leftSample, 0);
            rightMode.fetchSample(rightSample, 0);

            float leftLight = leftSample[0];
            float rightLight = rightSample[0];
            
            if (leftLight < LIGHT_THRESHOLD && rightLight < LIGHT_THRESHOLD) {
            	LEFT_MOTOR.forward();
            	RIGHT_MOTOR.forward();
            } else if (leftLight < LIGHT_THRESHOLD && rightLight > LIGHT_THRESHOLD) {
            	RIGHT_MOTOR.forward();
            	LEFT_MOTOR.stop();
            } else if (rightLight < LIGHT_THRESHOLD && leftLight > LIGHT_THRESHOLD) {
            	LEFT_MOTOR.forward();
            	RIGHT_MOTOR.stop();
            } else {
            	boolean turnLeft = Math.random() < 0.5;
            	if (turnLeft) {
            		LEFT_MOTOR.backward();
            		RIGHT_MOTOR.forward();
            	} else {
            		LEFT_MOTOR.forward();
            		RIGHT_MOTOR.stop();
            	}
            }
        }
        
        LEFT_MOTOR.stop();
        RIGHT_MOTOR.stop();
        leftSensor.close();
        rightSensor.close();
        System.out.println("Program stopped");
    }
}
//add mapping, it doesnt need to know where it is going yet, it can just explore and map out where its going ?
//use two sensors... replace the sound sensor and the object one... would make movement easier. 
//if use two, then it can do the turns easily too since you can determine when its a turn and not just off-tracks by if they both stop sensing the line
//may need thicker lines prolly tho