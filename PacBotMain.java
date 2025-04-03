package pacBot;

import lejos.hardware.Button;
//import lejos.hardware.motor.Motor;
//import lejos.robotics.localization.OdometryPoseProvider;
//import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class PacBotMain {
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
        
        Behavior follow = new LineBehavior();
		Behavior ghost = new GhostBehavior();
		Behavior timer = new TimerBehavior(startTime);

		Behavior[] behaviors = {follow, ghost, timer};
		
		Arbitrator arbitrator = new Arbitrator(behaviors);
		
		System.out.println("Press any button to start");
        Button.waitForAnyPress();
        
		arbitrator.go();
	}
}
