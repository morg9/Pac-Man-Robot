package myPackage;

import java.util.Set;
import java.util.HashSet;

import lejos.hardware.motor.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class PelletBehavior implements Behavior {
	private int pelletsRemaining = 10;
	private OdometryPoseProvider poseProvider;
	private Set<String> visitedLocations;
	
	public PelletBehavior(PoseProvider poseProvider) {
		this.poseProvider = (OdometryPoseProvider) poseProvider;
		this.visitedLocations = new HashSet<>();
	}

	@Override
	public boolean takeControl() {
		return pelletsRemaining > 0;
	}

	@Override
	public void action() {
		Pose currentPose = poseProvider.getPose();
		String positionKey = formatPosition(currentPose.getX(), currentPose.getY());
		
		if (!visitedLocations.contains(positionKey)) {
			visitedLocations.add(positionKey);
			pelletsRemaining--;
			System.out.println("+1 Point! Remaining: " + pelletsRemaining);
		} else {
			System.out.println("Pellet already visited");
		}
		
		Delay.msDelay(5000);
	}

	@Override
	public void suppress() {
		// Not needed
	}
	
	private String formatPosition(float x, float y) {
		int roundedX = Math.round(x/5)*5;
		int roundedY = Math.round(y/5)*5;
		return roundedX + "," + roundedY;
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("deprecation")
		MovePilot pilot = new MovePilot(5.6, 11.2, Motor.B, Motor.C);
        OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
        
		PelletBehavior pelletBehavior = new PelletBehavior(poseProvider);
		
		if (pelletBehavior.takeControl()) {
			pelletBehavior.action();
		}
		System.out.println("All Pellets Eaten");
        Delay.msDelay(3000);
	}
}
