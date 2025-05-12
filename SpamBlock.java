/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be spammed a certain amount of times
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class SpamBlock extends Block {
    int numSpam, spamTime, endTime;
    double slowedFactor = 0.07;
    double distanceTravelledX = 0, distanceTravelledY = 0;
    double timeBeforeSpam, timeDuringSpam, timeAfterSpam;

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
    }

    @Override
    public void draw(Graphics2D g2, int audioTime) {
        g2.setPaint(Color.MAGENTA);
        g2.fillRect(this.x, this.y, this.width, this.length);
        g2.setPaint(Color.WHITE);
        g2.setFont(new Font("monospaced", Font.PLAIN, 50));
        g2.drawString(String.valueOf(this.numSpam), this.x+40, this.y+60);
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
        this.intersectsReceiver(receiver);
        
        if (this.reachedReceiver) {
            if ((this.x > (receiver.x+receiver.width)) || (this.y > (receiver.y+receiver.height)) || ((this.x+this.length)<receiver.x) || ((this.y+this.width)<receiver.y)) {
                this.canReceive = false;
                if (numSpam>0) this.missPassed = true;
            }
        }
    }

    @Override
    void receive(int timeReceived) {
        if (this.receiveTime <= timeReceived && this.endTime >= timeReceived) {
            if (this.numSpam > 0) this.numSpam -= 1;
            if (this.numSpam < 0) this.numSpam = 0;
        }

        if (numSpam == 0) {
            System.out.println("✅ Spam complete!");
            this.received = true;
        }
    }

    @Override
    int rate() {
        if (received) return 1;
        else return 3;
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
