import lejos.hardware.Button;
//import lejos.hardware.motor.Motor;
//import lejos.robotics.localization.OdometryPoseProvider;
//import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Battery;

public class Main {
	public static void main(String[] args) {

        System.out.println("Authors: Morgan & Hugo\nVersion 2.1\nPress ENTER to start");
        Button.ENTER.waitForPressAndRelease();
        
        if (Battery.getVoltage() < 2.0) {
            System.out.println("Battery too low, exiting program.");
            return;
        }
        long startTime = System.currentTimeMillis();
        
        Behavior follow = new LineBehavior();
		Behavior ghost = new GhostBehavior();
		Behavior exit = new ExitBehavior();
		Behavior mapping = new MappingBehavior();
		Behavior timer = new TimerBehavior(startTime);
		
		//lowest to highest priority
		Behavior[] behaviors = {follow, mapping, timer, ghost, exit};
		
		Arbitrator arbitrator = new Arbitrator(behaviors);
		
		arbitrator.go();
	}
}
