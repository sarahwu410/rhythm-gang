/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be tapped once
 */

import java.awt.event.KeyEvent;

public class TapBlock extends Block{
    public TapBlock(String level, String button, int receiveTime, Receiver someReceiver) {
        super(level, button, receiveTime, someReceiver);
    }

    @Override
    boolean receive(int timeReceived) {
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
    	if (accuracy < 500) {
            System.out.println("✅ Woohoo! You hit!");
            return this.received = true;
        } else {
            System.out.println("❌ Boo! *Throws tomato* You missed.");
            return this.missed = true;
        }
    	
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // If the key event text matches the block button, tell the user the result
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(this.button) && ! this.received) { 
            this.receive(this.timeReceived);
            System.out.println("Receive time: " + this.receiveTime + "; Time received: " + this.timeReceived);
            return;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
