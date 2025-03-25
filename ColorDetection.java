import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorDetection {
    private static final Port SENSOR_PORT = BrickFinder.getDefault().getPort("S2"); // Change if needed

    public static void main(String[] args) {
        EV3ColorSensor colorSensor = new EV3ColorSensor(SENSOR_PORT);
        SampleProvider redMode = colorSensor.getRedMode();
        float[] sample = new float[redMode.sampleSize()];

        System.out.println("Press ENTER to start");
        Button.ENTER.waitForPressAndRelease();

        while (!Button.ENTER.isDown()) {
            redMode.fetchSample(sample, 0);
            float redValue = sample[0];

            LCD.clear();
            LCD.drawString("Red Value: " + redValue, 0, 2);
            System.out.println("Red Value: " + redValue);

            try {
                Thread.sleep(200); // Small delay to prevent spam
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        colorSensor.close();
        System.out.println("Program stopped");
    }
}
