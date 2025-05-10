/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Class that extends Block.java for a type of block that only has to be spammed a certain amount of times
 */

import java.awt.event.KeyEvent;

public class SpamBlock extends Block {
    int numSpam, endTime;

    public SpamBlock(String level, String button, int receiveTime, Receiver someReceiver, int numSpam, int endTime) {
        super(level, button, receiveTime, someReceiver);
        this.numSpam = numSpam;
        this.endTime = endTime;
    }

    /**
     * checks whether the current audio time is within the set time bounds of the spam block
     * @param milliElapsed the current time of the audio in milliseconds
     * @return returns true if within bounds, false if out of bounds
     */
    boolean checkTime(int milliElapsed) {
        if (this.receiveTime <= milliElapsed && this.endTime >= milliElapsed) return true;
        else return false;
    }

    /**
     * updates the number of times still left to be spammed
     */
    void updateSpamNum() {
        if (this.numSpam > 0) this.numSpam -= 1;
        if (this.numSpam < 0) this.numSpam = 0;
    }

    @Override
    boolean receive(int timeReceived) {
        int accuracy = (int) (Math.abs(receiveTime) - timeReceived);
    	if (accuracy < 500 && numSpam == 0) {
            System.out.println("✅ Spam complete!");
            return this.received = true;
        } else {
            System.out.println("❌ Spam fail :(");
            return this.missed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.matchesKey(e)) {
            this.updateSpamNum();
        }
    }
}
