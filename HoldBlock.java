/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be held for a certain duration
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class HoldBlock extends Block {
    int holdDurationMs, endTime;
    int pressStartTime, heldTime;
    boolean isPressed = false;
    // boolean completed = false;

    // Block visual positions (e.g., for diagonal movement)
    // headX and headY are this.x and this.y
    int tailX, tailY;

    public HoldBlock(String level, String button, int receiveTime, Receiver someReceiver, int holdDurationMs) {
        super(level, button, receiveTime, someReceiver);
        this.holdDurationMs = holdDurationMs;
        this.endTime = this.enterTime + this.holdDurationMs;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setPaint(Color.RED);
        g2.fillRect(this.x, this.y, this.length, this.width);
        g2.fillRect(this.tailX, this.tailY, this.length, this.width);
        g2.setPaint(Color.WHITE);
        g2.drawLine(this.x, this.y, this.tailX, this.tailY);
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
                this.canReceive = false;
                this.missPassed = true;
            }
        }
    }

    @Override
    void receive(int timeReceived) {
        int accuracy = (int) (holdDurationMs-heldTime);

    	if (accuracy <= 0) {
            System.out.println("✅ Hold success!");
            this.received = true;
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
        if (received && accuracy <= 0) return 1;
        else if (received && accuracy <= 200) return 2;
        else return 3;
    }

    int holdRate() {
        int accuracy = (int) (Math.abs(receiveTime - pressStartTime));
        if (accuracy < 200) return 1;
        else if (accuracy < 500) return 2;
        else return 3;
    }

    // Handle key press (start timing)
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isPressed && this.matchesKey(e)) {
            isPressed = true;
            this.pressStartTime = this.timeReceived;
            // System.out.println("HOLD BLOCK PRESSED");
        }
    }

    // Handle key release (check success)
    @Override
    public void keyReleased(KeyEvent e) {
        if (isPressed && this.matchesKey(e)) {
            isPressed = false;
            heldTime = this.timeReceived - this.pressStartTime;
            this.receive(this.timeReceived);
        }
    }

    // Unused, but must be implemented
    @Override
    public void keyTyped(KeyEvent e) {}
}