import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public abstract class Block implements KeyListener {
    int x, y, length, width;
    double x2,y2;
    int speed;
    String button; // which button corrosponds to a particular block
    int enterTime;
    int receiveTime;
    int timeReceived;
    double velocityX, velocityY;
    String level; // each level has it's own preset coordinates, dimensions, etc.
    Boolean received;

    Block (String level, String button, int enterTime, long receiveTime) {
        this.enterTime = enterTime;
        this.level = level;
        this.button = button;
        this.received = false;

        if (this.level.equalsIgnoreCase("easy")) {
            this.length = 100;
            this.width = 100;
            this.speed = 500;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 300;
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
        this.x2 = x;
        this.y2 = y;
    }

    abstract boolean receive(int timeReceived);
    
    /**
	 * Calculates the time that the block should enter, all double parameters
	 */
	public void calculateEnterTime(double speed, int finalTime, double enterX, double enterY, double receiveX, double receiveY) {
		double distanceX, distanceY, distance;
		
		// Calculate the distance in X and Y components
		distanceX = Math.abs(receiveX - enterX);
		distanceY = Math.abs(receiveY - enterY);
		
		// Find the total distance
		distance = Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));
		
		// Find the enter time
		this.enterTime = (int) (finalTime - (distance/speed));
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
        this.velocityX = (r.x - this.x2) / this.speed;
        this.velocityY = (r.y - this.y2) / this.speed;
    }

    public void move() {
        this.x2 += this.velocityX;
        this.y2 += this.velocityY;
        x = (int)x2;
        y = (int)y2;
    }

    

    @Override
    public abstract void keyTyped(KeyEvent e);

    @Override
    public abstract void keyPressed(KeyEvent e);

    @Override
    public abstract void keyReleased(KeyEvent e);

}
