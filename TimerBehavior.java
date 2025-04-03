import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;

public class TimerBehavior implements Behavior {
	private EV3ColorSensor SENSOR_PORT_LEFT = new EV3ColorSensor(SensorPort.S2);
	private EV3ColorSensor SENSOR_PORT_MIDDLE = new EV3ColorSensor(SensorPort.S3);
	private EV3ColorSensor SENSOR_PORT_RIGHT = new EV3ColorSensor(SensorPort.S4);
	private EV3LargeRegulatedMotor MOTOR_LEFT = new EV3LargeRegulatedMotor(MotorPort.A);
	private EV3LargeRegulatedMotor MOTOR_RIGHT = new EV3LargeRegulatedMotor(MotorPort.B);
	
	private long startTime;
	
	public TimerBehavior(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public boolean takeControl() {
		return System.currentTimeMillis() - startTime >= 120000;
	}

	@Override
	public void action() {
		SENSOR_PORT_LEFT.close();
		SENSOR_PORT_RIGHT.close();
		SENSOR_PORT_MIDDLE.close();
		MOTOR_LEFT.stop();
		MOTOR_RIGHT.stop();
		LCD.clear();
		LCD.drawString("Timer Ended", 4, 4);
	}

	@Override
	public void suppress() {
		// don't need
	}
}
