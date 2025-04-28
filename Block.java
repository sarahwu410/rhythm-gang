import java.awt.event.KeyListener;

import javax.sound.midi.Receiver;

import java.awt.event.KeyEvent;

public abstract class Block implements KeyListener {
    int x, y, length, width;
    int speed;
    String button; // which button corrosponds to a particular block
    int enterTime;
    int receiveTime;
    int timeReceived;
    double velocityX, velocityY;
    Receiver receiver;
    String level; // each level has it's own preset coordinates, dimensions, etc.

    Block (String level, String button, int enterTime, int receiveTime) {
        this.enterTime = enterTime;
        this.receiveTime = receiveTime;
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
	 * Block Movemeent
	 */
    public void setReceiver(Receiver aReceiver, Receiver bReceiver, Receiver cReceiver, Receiver xReceiver, Receiver yReceiver) {
        switch (button.toUpperCase()) {
            case "A" -> this.receiver = aReceiver;
            case "B" -> this.receiver = bReceiver;
            case "C" -> this.receiver = cReceiver;
            case "X" -> this.receiver = xReceiver;
            case "Y" -> this.receiver = yReceiver;
        }
    }

    public void calculateVelocity() {
        double framesToReach = receiveTime - enterTime;
        if (framesToReach == 0) framesToReach = 1;
        velocityX = (double)(receiver.x - x) / framesToReach;
        velocityY = (double)(receiver.y - y) / framesToReach;
    }

    public void move() {
        this.x += velocityX;
        this.y += velocityY;
    }

	}

    @Override
    public abstract void keyTyped(KeyEvent e);

    @Override
    public abstract void keyPressed(KeyEvent e);

    @Override
    public abstract void keyReleased(KeyEvent e);
}
