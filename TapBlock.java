/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be tapped once
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class TapBlock extends Block{
    /**
     * sets all the variables from the parent class
     * @param level the level this TapBlock is going in
     * @param button the button that will receive this TapBlock
     * @param receiveTime the time the TapBlock will be received at
     * @param someReceiver the receiver that corrosponds with the block
     */
    public TapBlock(String level, String button, int receiveTime, Receiver someReceiver) {
        super(level, button, receiveTime, someReceiver);
        this.Blocktype = "TapBlock"; //used in easylevel
    }

    @Override
    public void draw(Graphics2D g2, int audioTime) {
        // Uncomment this code if you would like to see the fit of your images/animations on the tapblock
        //g2.setPaint(Color.GREEN);
        //g2.fillRect(this.x, this.y, this.width, this.length);

        // Rotate the image if it can
        if (rotate) g2.rotate(angle, this.x + (this.width/2), this.y + (this.length/2));
        
        if (!hitPlaying) { // if the block is moving
            if (this.movement != null) {
                movement.setX(this.x);
                movement.setY(this.y);
                movement.draw(g2, audioTime);
            } else if (this.moving != null) g2.drawImage(moving, this.x, this.y, null);
        } else { // if the block is hit
            if (beenHit != null) {
                beenHit.setX(this.x);
                beenHit.setY(this.y);
                beenHit.draw(g2, audioTime);
                if (beenHit.frame == beenHit.spriteFrames) hitPlaying = false;
            } else if (amHit != null) {
                g2.drawImage(amHit, this.x, this.y, null);
                if (this.timeReceived + 100 < audioTime) hitPlaying = false;
            }
        }

        // Un-rotate the graphics object
        if (rotate) g2.rotate(-angle, this.x + (this.width/2), this.y + (this.length/2));
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
        if (this.intersectsReceiver(receiver)) this.reachedReceiver = true;
        
        if (this.reachedReceiver) {
            if ((this.x > (receiver.x+receiver.width)) || (this.y > (receiver.y+receiver.height)) || ((this.x+this.length)<receiver.x) || ((this.y+this.width)<receiver.y)) {
                if (!this.hitPlaying) this.canReceive = false;
                if (!this.received) this.missPassed = true;
            }
        }
    }

    @Override
    void receive(int timeReceived) {
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
    	if (accuracy < 500) {
            System.out.println("✅ Woohoo! You hit!");
            this.hitPlaying = true;
            this.received = true;
        } else {
            System.out.println("❌ Boo! *Throws tomato* You missed.");
            if (!this.hitPlaying) this.missed = true;
        }
    	
    }

    @Override
    int rate() {
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
        System.out.println("Accuracy: " + accuracy);
        if (accuracy < 30) return 1;
        else if (accuracy < 100) return 2;
        else if (accuracy < 200) return 3;
        else return 4;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == this.matchesKey(e)) { 
            this.receive(this.timeReceived);
            System.out.println("Receive time: " + this.receiveTime + "; Time received: " + this.timeReceived);
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
