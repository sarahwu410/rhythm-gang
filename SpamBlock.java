/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be spammed a certain amount of times
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Image;

public class SpamBlock extends Block {
    int numSpam, spamTime, endTime;
    double distanceTravelledX = 0, distanceTravelledY = 0;
    double timeBeforeSpam, timeDuringSpam, timeAfterSpam;
    double slowedFactor = 0;
    Boolean spammed;
    Animation spamming = null;
    Image amSpammed = null;

    /**
     * constructor for spam block with its unique extra attributes 
     * @param level the level this SpamBlock is going in
     * @param button the button that will receive this SpamBlock
     * @param receiveTime the time the SpamBlock will be received at
     * @param someReceiver the receiver that corrosponds with the block
     * @param numSpam the number of times the block is meant to be spammed
     * @param endTime the time the spam block will stop accepting spams
     */
    public SpamBlock(String level, String button, int receiveTime, Receiver someReceiver, int numSpam, int spamTime) {
        super(level, button, receiveTime, someReceiver);
        this.numSpam = numSpam;
        this.spamTime = spamTime;
        this.endTime = this.receiveTime + this.spamTime;
        this.Blocktype = "SpamBlock"; //used in easy level
        this.spammed = false; // used for drawing
    }

    @Override
    public void draw(Graphics2D g2, int audioTime) {
        // Uncomment this code if you would like to see the fit of your images/animations on the spamblock
        //g2.setPaint(Color.ORANGE);
        //g2.fillRect(this.x, this.y, this.width, this.length);
        //g2.setPaint(Color.WHITE);
        //g2.setFont(new Font("monospaced", Font.PLAIN, 50));
        //g2.drawString(String.valueOf(this.numSpam), this.x+40, this.y+60);

        if (this.spammed == true) {
            if (this.spamming != null) {
                spamming.setX(this.x);
                spamming.setY(this.y);
                spamming.draw(g2, audioTime);
                if (spamming.frame == spamming.spriteFrames) this.spammed = false;
            } else if (this.amSpammed != null) {
                g2.drawImage(moving, this.x, this.y, null);
                if (this.timeReceived + 40 < audioTime) spammed = false;
            }
        } else if (!hitPlaying) {
            if (this.movement != null) {
                movement.setX(this.x);
                movement.setY(this.y);
                movement.draw(g2, audioTime);
            }
            else if (this.moving != null) g2.drawImage(moving, this.x, this.y, null);
            else {
                g2.setPaint(Color.ORANGE);
                g2.fillRect(this.x, this.y, this.width, this.length);
                g2.setPaint(Color.WHITE);
                g2.setFont(new Font("monospaced", Font.PLAIN, 50));
                g2.drawString(String.valueOf(this.numSpam), this.x+40, this.y+60);
            }
        } else {
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

        // Draw the amount of spams left
        g2.setPaint(Color.BLUE);
        g2.setFont(new Font("monospaced", Font.PLAIN, 50));
        g2.drawString(String.valueOf(this.numSpam), this.x+40, this.y+60);
    }

    /**
     * Sets animation for when the spamblock is spammed
     * @param whenSpamming  The animation the spamblock plays when spammed
     */
    public void setSpammingAnimation(Animation whenSpamming) {
        this.spamming = whenSpamming;
    }

    /**
     * Sets image for when the spamblock is spammed
     * @param whenSpamming  The image the spamblock draws when spammed
     */
    public void setSpammingImage(Image whenSpamming) {
        this.amSpammed = whenSpamming;
    }

    @Override
    public void move(int audioTime) {
        this.timeBeforeSpam = 0;
        this.timeDuringSpam = 0;
        this.timeAfterSpam = 0;

        // calculate the time that passed before, during, and after spam
        if (audioTime <= this.receiveTime) { // if the current audio time is before spam
            this.timeBeforeSpam = audioTime - this.enterTime;
        } else if (audioTime <= this.endTime) { // if the current audio time is during spam
            this.timeBeforeSpam = this.receiveTime - enterTime;
            this.timeDuringSpam = audioTime - this.receiveTime;
        } else {
            this.timeBeforeSpam = this.receiveTime - enterTime;
            this.timeDuringSpam = this.endTime - this.receiveTime;
            this.timeAfterSpam = audioTime - this.endTime;
        }

        // calculate total distance travelled based on each segment (before, during, and after spam)
        this.distanceTravelledX = (this.timeBeforeSpam + (this.slowedFactor*this.timeDuringSpam) + this.timeAfterSpam) * this.velocityX;
        this.distanceTravelledY = (this.timeBeforeSpam + (this.slowedFactor*this.timeDuringSpam) + this.timeAfterSpam) * this.velocityY;

        // Find position based on time
        this.x = (int) (enterX + this.distanceTravelledX);
        this.y = (int) (enterY + this.distanceTravelledY);

        this.passedReceiver(someReceiver);
    }

    @Override
    protected void passedReceiver(Receiver receiver) {
        if (this.intersectsReceiver(receiver)) this.reachedReceiver = true;
        
        if (this.reachedReceiver) {
            if ((this.x > (receiver.x+receiver.width)) || (this.y > (receiver.y+receiver.height)) || ((this.x+this.length)<receiver.x) || ((this.y+this.width)<receiver.y)) {
                if (!this.hitPlaying) this.canReceive = false;
                if (numSpam>0 && !this.received) this.missPassed = true;
            }
        }
    }

    @Override
    void receive(int timeReceived) {
        if (this.receiveTime <= timeReceived && this.endTime >= timeReceived) {
            if (this.numSpam > 0) {
                this.numSpam -= 1;
                spammed = true;
            }
            if (this.numSpam < 0) this.numSpam = 0;
        }

        if (numSpam == 0) {
            System.out.println("✅ Spam complete!");
            this.received = true;
            this.hitPlaying = true;
        }
    }

    @Override
    int rate() {
        if (received) return 1;
        else return 4;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == this.matchesKey(e)) {
            this.receive(this.timeReceived);
            System.out.println("Receive time: " + this.receiveTime + "; Time received: " + this.timeReceived + "; numSpam: "+ this.numSpam);
        }
    }
}
