import java.awt.event.KeyEvent;

public class TapBlock extends Block{
    public TapBlock(String level, String button, int receiveTime, Receiver someReceiver) {
        super(level, button, receiveTime, someReceiver);
    }

    @Override
    boolean receive(int timeReceived) {
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
    	if (accuracy < 500) return this.received = true;
    	else return this.missed = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // If the key event text matches the block button, tell the user the result
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(this.button) && ! this.received) { 
            this.receive(this.timeReceived);
            if (this.received) {
                System.out.println("✅ Woohoo! You hit!");
            }
            else if (this.missed) System.out.println("❌ Boo! *Throws tomato* You missed.");
            else System.out.println("There's been an error.");

            System.out.println("Receive time: " + this.receiveTime + "; Time received: " + this.timeReceived);
            return;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
