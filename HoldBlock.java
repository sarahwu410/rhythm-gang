import java.awt.event.KeyEvent;

public class HoldBlock extends Block {
    int holdLen;

    public HoldBlock(String level, String button, int enterTime, int receiveTime, int holdLen) {
        super(level, button, enterTime, receiveTime);
        this.holdLen = holdLen;
    }

    boolean receive(int timeReceived) {
        this.timeReceived = timeReceived;
        int accuracy = (int) (Math.abs(receiveTime) - timeReceived);
    	if (accuracy < 1000 && holdLen == 0) return true;
    	else return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (button.equalsIgnoreCase("A")) {
            if (e.getKeyCode()==KeyEvent.VK_A) {
                if (holdLen>0) holdLen-=1;
            }
        } else if (button.equalsIgnoreCase("B")) {
            if (e.getKeyCode()==KeyEvent.VK_B) {
                if (holdLen>0) holdLen-=1;
            }
        } else if (button.equalsIgnoreCase("C")) {
            if (e.getKeyCode()==KeyEvent.VK_C) {
                if (holdLen>0) holdLen-=1;
            }
        } else if (button.equalsIgnoreCase("X")) {
            if (e.getKeyCode()==KeyEvent.VK_X) {
                if (holdLen>0) holdLen-=1;
            }
        } else if (button.equalsIgnoreCase("Y")) {
            if (e.getKeyCode()==KeyEvent.VK_Y) {
                if (holdLen>0) holdLen-=1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (button.equalsIgnoreCase("A")) {
            if (e.getKeyCode()==KeyEvent.VK_A) receive(this.timeReceived);
        } else if (button.equalsIgnoreCase("B")) {
            if (e.getKeyCode()==KeyEvent.VK_B) receive(this.timeReceived);
        } else if (button.equalsIgnoreCase("C")) {
            if (e.getKeyCode()==KeyEvent.VK_C) receive(this.timeReceived);
        } else if (button.equalsIgnoreCase("X")) {
            if (e.getKeyCode()==KeyEvent.VK_X) receive(this.timeReceived);
        } else if (button.equalsIgnoreCase("Y")) {
            if (e.getKeyCode()==KeyEvent.VK_Y) receive(this.timeReceived);
        }
    }
}
