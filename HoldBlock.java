/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be held for a certain duration
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Image;

public class HoldBlock extends Block {
    int holdDurationMs, endTime;
    int pressStartTime, heldTime;
    boolean isPressed = false;
    Animation hold = null; // animation while the user holds the note
    Image held = null; // image while user presses the note

    // Block visual positions (e.g., for diagonal movement)
    // headX and headY are this.x and this.y
    int tailX, tailY;

    /**
     * constructor for hold block with its unique extra attributes
     * @param level the level this HoldBlock is going in
     * @param button the button that will receive this HoldBlock
     * @param receiveTime the time the HoldBlock will be received at
     * @param someReceiver the receiver that corrosponds with the HoldBlock
     * @param holdDurationMs the time in milliseconds the block is meant to be held at
     */
    public HoldBlock(String level, String button, int receiveTime, Receiver someReceiver, int holdDurationMs) {
        super(level, button, receiveTime, someReceiver);
        this.holdDurationMs = holdDurationMs;
        this.endTime = this.enterTime + this.holdDurationMs;
        this.Blocktype = "HoldBlock";
    }

    @Override
    public void draw(Graphics2D g2, int audioTime) {
        // Uncomment this code if you would like to see the fit of your images/animations on the hold block
        //g2.setPaint(Color.BLACK);
        //g2.fillRect(this.x, this.y, this.length, this.width);
        //g2.fillRect(this.tailX, this.tailY, this.length, this.width);
        //g2.setPaint(Color.RED);
        //g2.drawLine(this.x, this.y, this.tailX, this.tailY);

        if (this.isPressed) {
            if (this.hold != null) {
                hold.setX(this.x);
                hold.setY(this.y);
                hold.draw(g2, audioTime);
            }
            else if (this.held != null) g2.drawImage(held, this.x, this.y, null);
        }
        else if (!this.hitPlaying) {
            if (this.movement != null) {
                movement.setX(this.x);
                movement.setY(this.y);
                movement.draw(g2, audioTime);
            }
            else if (this.moving != null) g2.drawImage(moving, this.x, this.y, null);
        }
        else {
            if (this.beenHit != null) {
                beenHit.setX(this.x);
                beenHit.setY(this.y);
                beenHit.draw(g2, audioTime);
                if (beenHit.frame == beenHit.spriteFrames) hitPlaying = false;
            }
            else if (this.amHit != null) {
                g2.drawImage(amHit, this.x, this.y, null);
                if (this.timeReceived + 100 < audioTime) hitPlaying = false;
            }
        }
    }

    /**
     * Sets hold animation for holdblock
     * @param whenHeld  Animation for when the block is held
     */
    public void setHoldAnimation(Animation whenHeld) {
        this.hold = whenHeld;
    }

    /**
     * Sets hold image for holdblock
     * @param whenHeld  Image for when the block is held
     */
    public void setHoldImage(Image whenHeld) {
        this.held = whenHeld;
    }

    // Move both head and tail diagonally
    @Override
    public void move(int audioTime) {
        // Find the duration of the block's "existence"
        int headTime = audioTime - enterTime;
        int tailTime = audioTime - endTime;

        // Find position based on time
        this.x = (int) (enterX + this.velocityX * headTime);
        this.y = (int) (enterY + this.velocityY * headTime);
        this.tailX = (int) (enterX + this.velocityX * tailTime);
        this.tailY = (int) (enterY + this.velocityY * tailTime);

        this.passedReceiver(someReceiver);
        // if (!this.canReceive) System.out.println("cant receive at: " + audioTime);
    }

    @Override
    protected void passedReceiver(Receiver receiver) {
        if ((this.tailX < (receiver.x + receiver.width)) && (this.tailY < (receiver.y + receiver.height)) && ((this.tailX + this.width)>receiver.x) && ((this.tailY + this.length)>receiver.y)) {
            this.reachedReceiver = true;
        }
        
        if (this.reachedReceiver) {
            if ((this.tailX > (receiver.x+receiver.width)) || (this.tailY > (receiver.y+receiver.height)) || ((this.tailX+this.length)<receiver.x) || ((this.tailY+this.width)<receiver.y)) {
                if (!this.hitPlaying) this.canReceive = false;
                if (!this.received) this.missPassed = true;
            }
        }
    }

    @Override
    void receive(int timeReceived) {
        int accuracy = (int) (holdDurationMs-heldTime);

    	if (accuracy <= 200) {
            System.out.println("✅ Hold success!");
            this.received = true;
            this.hitPlaying = true;
        } else {
            System.out.println("❌ Hold failed");
            this.missed = true;
        }

        System.out.println("Received at " + timeReceived);
        System.out.println("Held for " + accuracy);

        this.canReceive = false;
    }

    @Override
    int rate() {
        int accuracy = (int) (holdDurationMs-heldTime);
        if (received && accuracy <= 30) return 1;
        else if (received && accuracy <= 100) return 2;
        else if (received && accuracy <= 200) return 3;
        else return 4;
    }

    /**
     * calculates how accurate the received HoldBlock is
     * @return 1 - most accurate, 2 - less accurate, 3 - not accurate
     */
    int holdRate() {
        int accuracy = (int) (Math.abs(receiveTime - pressStartTime));
        if (accuracy < 30) return 1;
        else if (accuracy < 100) return 2;
        else if (accuracy < 200) return 3;
        else return 4;
    }

    // Handle key press (start timing)
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isPressed && e.getKeyCode() == this.matchesKey(e)) {
            isPressed = true;
            this.pressStartTime = this.timeReceived;
            // System.out.println("HOLD BLOCK PRESSED");
        }
    }

    // Handle key release (check success)
    @Override
    public void keyReleased(KeyEvent e) {
        if (isPressed && e.getKeyCode() == this.matchesKey(e)) {
            isPressed = false;
            heldTime = this.timeReceived - this.pressStartTime;
            this.hitPlaying = true;
            this.receive(this.timeReceived);
        }
    }

    // Unused, but must be implemented
    @Override
    public void keyTyped(KeyEvent e) {}
}