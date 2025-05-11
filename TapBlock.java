/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be tapped once
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class TapBlock extends Block{
    public TapBlock(String level, String button, int receiveTime, Receiver someReceiver) {
        super(level, button, receiveTime, someReceiver);
        this.Blocktype = "TapBlock"; //used in easylevel
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setPaint(Color.GREEN);
        g2.fillRect(this.x, this.y, this.width, this.length);
    }

    @Override
    public void move(int audioTime) {
        // Find the duration of the block's "existence"
        int myTime = audioTime - enterTime;

        // Find position based on time
        this.x = (int) (enterX + this.velocityX * myTime);
        this.y = (int) (enterY + this.velocityY * myTime);

        this.passedReceiver(someReceiver);
    }

    @Override
    protected void passedReceiver(Receiver receiver) {
        this.intersectsReceiver(receiver);
        
        if (this.reachedReceiver) {
            if ((this.x > (receiver.x+receiver.width)) || (this.y > (receiver.y+receiver.height)) || ((this.x+this.length)<receiver.x) || ((this.y+this.width)<receiver.y)) {
                this.canReceive = false;
                this.missPassed = true;
            }
        }
    }

    @Override
    void receive(int timeReceived) {
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
    	if (accuracy < 500) {
            System.out.println("✅ Woohoo! You hit!");
            this.received = true;
        } else {
            System.out.println("❌ Boo! *Throws tomato* You missed.");
            this.missed = true;
        }
    	
    }

    @Override
    int rate() {
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
        if (accuracy < 200) return 1;
        if (accuracy < 500) return 2;
        else return 3;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // If the key event text matches the block button, tell the user the result
        if (this.matchesKey(e) && ! this.received) { 
            this.receive(this.timeReceived);
            System.out.println("Receive time: " + this.receiveTime + "; Time received: " + this.timeReceived);
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
