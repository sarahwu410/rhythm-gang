import java.awt.event.KeyEvent;

public class HoldBlock extends Block {
    int holdDurationMs;
    long pressStartTime;
    boolean isPressed = false;
    boolean completed = false;

    // Block visual positions (e.g., for diagonal movement)
    int headX, headY;
    int tailX, tailY;

    // Diagonal speed
    int velocityX = 1;
    int velocityY = 1;

    public HoldBlock(String level, String button, int receiveTime, Receiver someReceiver, int holdDurationMs) {
        super(level, button, receiveTime, someReceiver);
        this.holdDurationMs = holdDurationMs;

        this.headX = 0;
        this.headY = 0;
        this.tailX = -30;
        this.tailY = -30;

        // Set velocity towards receiver
        int dx = someReceiver.x - headX;
        int dy = someReceiver.y - headY;
        double length = Math.sqrt(dx * dx + dy * dy);
        velocityX = (int) (dx / length * 2);
        velocityY = (int) (dy / length * 2);
    }

    // Move both head and tail diagonally
    public void move() {
        // Calculate direction vector toward the receiver
        int dx = someReceiver.x - headX;
        int dy = someReceiver.y - headY;
        double length = Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction vector and scale by velocity
        if (length != 0) { // Avoid division by zero
            velocityX = (int) (dx / length * 2); // Adjust speed as needed
            velocityY = (int) (dy / length * 2);
        }

        // Move head toward the receiver
        headX += velocityX;
        headY += velocityY;

        // Move tail to follow the head
        tailX += velocityX;
        tailY += velocityY;
    }

    // Handle key press (start timing)
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isPressed && this.matchesKey(e)) {
            isPressed = true;
            pressStartTime = System.currentTimeMillis();
        }
    }

    // Handle key release (check success)
    @Override
    public void keyReleased(KeyEvent e) {
        if (isPressed && this.matchesKey(e)) {
            isPressed = false;
            long heldTime = System.currentTimeMillis() - pressStartTime;

            // You can replace System.currentTimeMillis with your game time if needed
            long accuracy = Math.abs(System.currentTimeMillis() - receiveTime);

            if (heldTime >= holdDurationMs && accuracy <= 1000) {
                completed = true;
                System.out.println("✅ Hold success!");
            } else {
                System.out.println("❌ Hold failed: heldTime = " + heldTime + ", accuracy = " + accuracy);
            }
        }
    }

    // Unused, but must be implemented
    @Override
    public void keyTyped(KeyEvent e) {}

    // Optional: check externally if the player succeeded
    public boolean isCompleted() {
        return completed;
    }

    @Override
    boolean receive(int timeReceived) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receive'");
    }
}