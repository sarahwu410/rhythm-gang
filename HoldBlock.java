/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be held for a certain duration
 */


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

        // // Calculate direction vector toward the receiver
        // int dx = someReceiver.x - this.x;
        // int dy = someReceiver.y - this.y;
        // double length = Math.sqrt(dx * dx + dy * dy);

        // // Normalize the direction vector and scale by velocity
        // if (length != 0) { // Avoid division by zero
        //     velocityX = (int) (dx / length * 2); // Adjust speed as needed
        //     velocityY = (int) (dy / length * 2);
        // }

        // // Move head toward the receiver
        // this.x += velocityX;
        // this.y += velocityY;

        // // Move tail to follow the head
        // tailX += velocityX;
        // tailY += velocityY;

        this.passedReceiver(someReceiver);
    }

    @Override
    protected void passedReceiver(Receiver receiver) {
        if (this.tailX > (receiver.x+receiver.width) && this.tailY > (receiver.y+receiver.height)) {
            this.canReceive = false;
            this.missPassed = true;
        }
    }

    @Override
    void receive(int timeReceived) {
        int accuracy = (int) (Math.abs(holdDurationMs-heldTime));

    	if (accuracy < 500) {
            System.out.println("✅ Hold success!");
            this.received = true;
        } else {
            System.out.println("❌ Hold failed");
            this.missed = true;
        }

        this.canReceive = false;
    }

    // Handle key press (start timing)
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isPressed && this.matchesKey(e)) {
            isPressed = true;
            this.pressStartTime = this.timeReceived;
        }
    }

    // Handle key release (check success)
    @Override
    public void keyReleased(KeyEvent e) {
        if (isPressed && this.matchesKey(e)) {
            isPressed = false;
            heldTime = this.timeReceived - this.pressStartTime;
            this.receive(this.timeReceived);

            // // You can replace System.currentTimeMillis with your game time if needed
            // long accuracy = Math.abs(System.currentTimeMillis() - receiveTime);

            // if (heldTime >= holdDurationMs && accuracy <= 1000) {
            //     completed = true;
            //     System.out.println("✅ Hold success!");
            // } else {
            //     System.out.println("❌ Hold failed: heldTime = " + heldTime + ", accuracy = " + accuracy);
            // }
        }
    }

    // Unused, but must be implemented
    @Override
    public void keyTyped(KeyEvent e) {}
}