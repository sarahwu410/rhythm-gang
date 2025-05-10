/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be spammed a certain amount of times
 */

import java.awt.event.KeyEvent;

public class SpamBlock extends Block {
    int numSpam, endTime;
    double slowedFactor = 0.07;
    double distanceTravelledX = 0, distanceTravelledY = 0;
    double timeBeforeSpam, timeDuringSpam, timeAfterSpam;
    boolean whatever;

    public SpamBlock(String level, String button, int receiveTime, Receiver someReceiver, int numSpam, int endTime) {
        super(level, button, receiveTime, someReceiver);
        this.numSpam = numSpam;
        this.endTime = endTime;
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
        x = (int) (enterX + this.distanceTravelledX);
        y = (int) (enterY + this.distanceTravelledY);

        this.passedReceiver(someReceiver);
    }

    @Override
    protected void passedReceiver(Receiver receiver) {
        if (this.x > (receiver.x+receiver.width) && this.y > (receiver.y+receiver.height)) {
            this.canReceive = false;
            this.missPassed = true;
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
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.matchesKey(e)) {
            this.receive(this.timeReceived);
            System.out.println("Receive time: " + this.receiveTime + "; Time received: " + this.timeReceived + "; numSpam: "+ this.numSpam);
        }
    }
}
