import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class ExitBehavior implements Behavior {
    private volatile boolean suppressed = false;

    @Override
    public boolean takeControl() {
    	return Button.ESCAPE.isDown();
    }

    @Override
    public void action() {
        suppressed = false;
        System.out.println("Ending program");
        System.exit(0);
    }

    @Override
    public void suppress() {
        suppressed = true;
    }
}
