/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * An abstract class for the unique block types: TapBlock, HoldBlock, SpamBlock
 */

import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public abstract class Block implements KeyListener {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    
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
    String Blocktype; //used in easylevel
    boolean hit; //used in easylevel
    boolean received, missed, missPassed;
    boolean canReceive = false; // false so that the block can't be received if it's not on screen
    boolean reachedReceiver = false;
    boolean beenRated = false;
    Animation movement, beenHit;
    Image moving, amHit;
    boolean hitPlaying;

    /**
     * constructor to set the unique coordinates, buttons, and receive time for evey block per level and button
     * @param level the level this Block is going in
     * @param button the button that will receive this Block
     * @param receiveTime the time the Block will be received at
     * @param someReceiver the receiver that corrosponds with the block
     */
    Block (String level, String button, int receiveTime, Receiver someReceiver) {
        this.receiveTime = receiveTime;
        this.level = level;
        this.button = button;
        this.someReceiver = someReceiver;
        this.received = false;
        this.missed = false;
        this.missPassed = false;
        this.movement = null; // animation
        this.beenHit = null; // animation
        this.moving = null; // image
        this.amHit = null; // image
        this.hitPlaying = false;

        // each level will have unique block coordinates for each receiver
        // assigns those coordinates here
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
        } else if (this.level.equalsIgnoreCase("prototype")) {
            this.length = 100;
            this.width = 100;
            this.speed = 0.5;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = (int)((this.screenWidth*0.5)-(this.width*0.5));
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = this.screenWidth-this.width;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = this.screenHeight-this.length;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = this.screenWidth-this.width;
                this.y = this.screenHeight-this.length;
            }
        }

        this.enterX = this.x;
        this.enterY = this.y;
        calculateEnterTime(this.speed, this.receiveTime, this.enterX, this.enterY, this.someReceiver);
    }

    /**
     * sets the variable this.timeReceived into the time that the block was received at
     * @param timeReceived time that the block was received at
     */
    public void setTimeReceived(int timeReceived) {
        if (hitPlaying) return;
        this.timeReceived = timeReceived;
    }

    /**
     * sets different requirements for what counts as a hit or a miss when a block is received
     * @param timeReceived the time that the block is received at
     * @return whether it was a hit (this.received = true) or miss (this.missed = true)
     */
    abstract void receive(int timeReceived);

    /**
     * checks if the block has intersected a receiver
     * @param receiver the receiver to be intersected with
     */
    protected boolean intersectsReceiver(Receiver receiver) {
        if ((this.x < (receiver.x + receiver.width)) && 
            (this.y < (receiver.y + receiver.height)) && 
            ((this.x + this.width)>receiver.x) && 
            ((this.y + this.length)>receiver.y)
        ) {return true;}
        return false;
    }

    /**
     * figure out whether the block passed the receiver completely
     * @param receiver the receiver the block is supposed to go to
     */
    protected abstract void passedReceiver(Receiver receiver);
    
    /**
     * Calculates the time that the block should enter
     * @param speed speed that the block is supposed to travel at
     * @param finalTime the time that the block is meant to be received
     * @param enterX the initial x coordiante of the block
     * @param enterY the initial y coordinate of the block
     * @param myReceiver the receiver that the block is supposed to go to
     */
	private void calculateEnterTime(double speed, int finalTime, double enterX, double enterY, Receiver myReceiver) {
		double distanceX, distanceY, distance;
		
		// Calculate the distance in X and Y components
		distanceX = Math.abs(myReceiver.x - enterX);
		distanceY = Math.abs(myReceiver.y - enterY);
		
		// Find the total distance
		distance = Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));
		
		// Find the enter time
		this.enterTime = (int) (finalTime - (distance/speed));

        // Calculate velocity
        calculateVelocity(myReceiver);
	}

    /**
     * calculates the valocity of the block
     * @param r the receiver the block is supposed to arrive at
     */
    private void calculateVelocity(Receiver r) {
        this.velocityX = (r.x - this.enterX) / (this.receiveTime - this.enterTime);
        this.velocityY = (r.y - this.enterY) / (this.receiveTime - this.enterTime);
    }

    /**
     * how to uniquely draw each block
     * @param g2 used to draw with different commands
     */
    public abstract void draw(Graphics2D g2, int audioTime);

    /**
     * moves the block based on the audio time so it syncs with the audio
     * @param audioTime the current time the audio is at
     */
    public abstract void move(int audioTime);

    /**
     * Decides the rating of the block and returns an integer to represent it
     */
    abstract int rate();
    
    /**
     * Sets the moving animation of a block from null to an animation
     * @param movingAnimation
     */
    public void setMoveAnimation(Animation movingAnimation) {
        this.movement = movingAnimation;
    }

    /**
     * Sets the hit animation of a block from null to an animation
     * @param hitAnimation
     */
    public void setHitAnimation(Animation hitAnimation) {
        this.beenHit = hitAnimation;
    }

    /**
     * Sets the moving animation of a block from null to an animation
     * @param movingAnimation
     */
    public void setMoveImage(Image movingImage) {
        this.moving = movingImage;
    }

    /**
     * Sets the hit animation of a block from null to an animation
     * @param hitAnimation
     */
    public void setHitImage(Image hitImage) {
        this.amHit = hitImage;
    }
    
    /**
     * helper method that checks whether the correct key is pressed for the corrosponding button
     * saves code
     * @param e the key event
     * @return the appropriate key code with the corrosponding button
     */
    protected int matchesKey(KeyEvent e) {
        switch (button.toUpperCase()) {
            case "A": return KeyEvent.VK_U;
            case "B": return KeyEvent.VK_I;
            case "C": return KeyEvent.VK_O;
            case "X": return KeyEvent.VK_J;
            case "Y": return KeyEvent.VK_K;
            default: return -1;
        }
    }

    @Override
    public abstract void keyTyped(KeyEvent e);

    @Override
    public abstract void keyPressed(KeyEvent e);

    @Override
    public abstract void keyReleased(KeyEvent e);

}
