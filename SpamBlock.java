import java.awt.event.KeyEvent;

public class SpamBlock extends Block {
    int numSpam, endTime;

    public SpamBlock(String level, String button, int enterTime, int receiveTime, int numSpam, int endTime) {
        super(level, button, enterTime, receiveTime);
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
        if (button.equalsIgnoreCase("A")) {
            if (e.getKeyCode()==KeyEvent.VK_A) {
                if (numSpam>0) numSpam-=1;
            }
        } else if (button.equalsIgnoreCase("B")) {
            if (e.getKeyCode()==KeyEvent.VK_B) {
                if (numSpam>0) numSpam-=1;
            }
        } else if (button.equalsIgnoreCase("C")) {
            if (e.getKeyCode()==KeyEvent.VK_C) {
                if (numSpam>0) numSpam-=1;
            }
        } else if (button.equalsIgnoreCase("X")) {
            if (e.getKeyCode()==KeyEvent.VK_X) {
                if (numSpam>0) numSpam-=1;
            }
        } else if (button.equalsIgnoreCase("Y")) {
            if (e.getKeyCode()==KeyEvent.VK_Y) {
                if (numSpam>0) numSpam-=1;
            }
        }
    }
}
