import java.util.ArrayList;
import java.util.List;
import lejos.robotics.subsumption.Behavior;

public class MappingBehavior implements Behavior {
    private int x = 0; 
    private int y = 0; 
    private Direction direction = Direction.NORTH; 

    public static List<String> movementHistory = new ArrayList<>();

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private boolean suppressed = false;

    @Override
    public boolean takeControl() {
        return true; 
    }

    @Override
    public void action() {
        suppressed = false;

        moveForward(); // This is just an example call

        while (!suppressed) {
            Thread.yield();
        }
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    public void moveForward() {
        if (direction == Direction.NORTH) {
            y++; 
        } else if (direction == Direction.EAST) {
            x++; 
        } else if (direction == Direction.SOUTH) {
            y--; 
        } else if (direction == Direction.WEST) {
            x--;
        }
        movementHistory.add("FORWARD");
        printStatus();
    }

    public void turnLeft() {
        if (direction == Direction.NORTH) {
            direction = Direction.WEST;
        } else if (direction == Direction.WEST) {
            direction = Direction.SOUTH;
        } else if (direction == Direction.SOUTH) {
            direction = Direction.EAST;
        } else if (direction == Direction.EAST) {
            direction = Direction.NORTH;
        }
        movementHistory.add("LEFT");
        printStatus();
    }

    public void turnRight() {
        if (direction == Direction.NORTH) {
            direction = Direction.EAST;
        } else if (direction == Direction.EAST) {
            direction = Direction.SOUTH;
        } else if (direction == Direction.SOUTH) {
            direction = Direction.WEST;
        } else if (direction == Direction.WEST) {
            direction = Direction.NORTH;
        }
        movementHistory.add("RIGHT");
        printStatus();
    }

    private void printStatus() {
        System.out.println("Position: (" + x + ", " + y + "), Facing: " + direction);
    }
}
