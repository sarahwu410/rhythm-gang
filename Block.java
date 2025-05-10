import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public abstract class Block implements KeyListener {
    int x, y, length, width;
    double enterX, enterY;
    double speed;
    String button; // which button corrosponds to a particular block
    Receiver someReceiver;
    int enterTime;
    int receiveTime;
    int timeReceived;
    double velocityX, velocityY;
    String level; // each level has it's own preset coordinates, dimensions, etc.
    boolean received, missed;
    boolean canReceive = true;

    Block (String level, String button, int receiveTime, Receiver someReceiver) {
        this.receiveTime = receiveTime;
        this.level = level;
        this.button = button;
        this.someReceiver = someReceiver;
        this.received = false;
        this.missed = false;

        if (this.level.equalsIgnoreCase("easy")) {
            this.length = 100;
            this.width = 100;
            this.speed = 0.5; // Speed is in pixels/millisecond
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 300;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 600;
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
            this.length = 100;
            this.width = 100;
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
            this.length = 100;
            this.width = 100;
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
            this.length = 100;
            this.width = 100;
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
        this.enterX = x;
        this.enterY = y;
        calculateEnterTime(this.speed, this.receiveTime, this.enterX, this.enterY, this.someReceiver);
    }

    public void setTimeReceived(int timeReceived) {this.timeReceived = timeReceived;}

    abstract boolean receive(int timeReceived);
    
    /**
	 * Calculates the time that the block should enter, all double parameters
	 */
	private void calculateEnterTime(double speed, int finalTime, double enterX, double enterY, Receiver myReceiver) {
		double distanceX, distanceY, distance;
		
		// Calculate the distance in X and Y components
		distanceX = Math.abs(myReceiver.x - enterX);
		distanceY = Math.abs(myReceiver.y - enterY);
		
		// Find the total distance
		distance = Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));
        System.out.println("Distance: " + distance + " pixels");
        System.out.println("Ms to cover: " + distance/speed + " milliseconds");
		
		// Find the enter time
		this.enterTime = (int) (finalTime - (distance/speed));

        // Calculate velocity
        calculateVelocity(myReceiver);
	}

    public void redoCalculations() {

    }

    // /**
	//  * Block Movemeent
	//  */
    // public void setReceiver(Receiver aReceiver, Receiver bReceiver, Receiver cReceiver, Receiver xReceiver, Receiver yReceiver) {
    //     switch (button.toUpperCase()) {
    //         case "A" -> this.receiver = aReceiver;
    //         case "B" -> this.receiver = bReceiver;
    //         case "C" -> this.receiver = cReceiver;
    //         case "X" -> this.receiver = xReceiver;
    //         case "Y" -> this.receiver = yReceiver;
    //     }
    // }

    public void calculateVelocity(Receiver r) {
        this.velocityX = (r.x - this.enterX) / (this.receiveTime - this.enterTime);
        this.velocityY = (r.y - this.enterY) / (this.receiveTime - this.enterTime);
    }

    public void move(int audioTime) {
        // Find the duration of the block's "existence"
        int myTime = audioTime - enterTime;
        // Find position based on time
        x = (int) (enterX + this.velocityX * myTime);
        y = (int) (enterY + this.velocityY * myTime);
    }

    // Helper: check if the correct key is pressed
    protected boolean matchesKey(KeyEvent e) {
        switch (button.toUpperCase()) {
            case "A": return e.getKeyCode() == KeyEvent.VK_A;
            case "B": return e.getKeyCode() == KeyEvent.VK_B;
            case "C": return e.getKeyCode() == KeyEvent.VK_C;
            case "X": return e.getKeyCode() == KeyEvent.VK_X;
            case "Y": return e.getKeyCode() == KeyEvent.VK_Y;
            default: return false;
        }
    }

    @Override
    public abstract void keyTyped(KeyEvent e);

    @Override
    public abstract void keyPressed(KeyEvent e);

    @Override
    public abstract void keyReleased(KeyEvent e);

}
