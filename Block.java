import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public abstract class Block implements KeyListener {
    int x, y, length, width;
    int speed;
    String button; // which button corrosponds to a particular block
    int enterTime;
    int receiveTime;
    int timeReceived;
    String level; // each level has it's own preset coordinates, dimensions, etc.

    Block (String level, String button, int enterTime, int receiveTime) {
        this.level = level;
        this.button = button;

        if (this.level.equalsIgnoreCase("easy")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            }
        } else if (this.level.equalsIgnoreCase("medium")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            }
        } else if (this.level.equalsIgnoreCase("hard")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            }
        } else if (this.level.equalsIgnoreCase("boss")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            }
        }
    }

    abstract boolean receive(int timeReceived);
    
    /**
	 * Calculates the time that the block should enter, all double parameters
	 */
	public double calculateEnterTime(double speed, double finalTime, double enterX, double enterY, double receiveX, double receiveY) {
		double distanceX, distanceY, distance;
		
		// Calculate the distance in X and Y components
		distanceX = Math.abs(receiveX - enterX);
		distanceY = Math.abs(receiveY - enterY);
		
		// Find the total distance
		distance = Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));
		
		// Find the enter time
		return (finalTime - (distance/speed));
	}

    /**
     * Determines movement of blocks
     */
    public static double blockMovement(double enterX, double enterY, double receiveX, double receiveY, double speed, double finalTime) {
        double distanceX, distanceY, distance;

        // Calculate the distance in X and Y components
        distanceX = Math.abs(receiveX - enterX);
        distanceY = Math.abs(receiveY - enterY);

        // Find the total distance
        distance = Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));

        // Return the calculated enter time based on speed and final time
        return finalTime - (distance / speed);
    }

    @Override
    public abstract void keyTyped(KeyEvent e);

    @Override
    public abstract void keyPressed(KeyEvent e);

    @Override
    public abstract void keyReleased(KeyEvent e);
}
