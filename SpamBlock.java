import java.awt.event.KeyEvent;

public class SpamBlock extends Block {
    int numSpam, endTime;

    public SpamBlock(String level, String button, int receiveTime, Receiver someReceiver, int numSpam, int endTime) {
        super(level, button, receiveTime, someReceiver);
        this.numSpam = numSpam;
        this.endTime = endTime;
    }

    boolean checkTime(int milliElapsed) {
        if (this.receiveTime <= milliElapsed && this.endTime >= milliElapsed) return true;
        else return false;
    }

    void updateSPAM(int milliElapsed) {
        if (this.receiveTime <= milliElapsed && this.endTime >= milliElapsed) {
            if (this.numSpam > 0) this.numSpam -= 1;
            if (this.numSpam < 0) this.numSpam = 0;
        }
    }

    boolean receive(int timeReceived) {
        this.timeReceived = timeReceived;
        int accuracy = (int) (Math.abs(receiveTime) - timeReceived);
    	if (accuracy < 1000 && numSpam == 0) return true;
    	else return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.matchesKey(e)) {
            if (numSpam>0) numSpam-=1;
        }
    }
}
