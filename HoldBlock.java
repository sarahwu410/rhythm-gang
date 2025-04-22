import java.awt.event.KeyEvent;

public class HoldBlock extends Block {
    int holdLen;

    public HoldBlock(String level, String button, int enterTime, int receiveTime, int holdLen) {
        super(level, button, enterTime, receiveTime);
        this.holdLen = holdLen;
    }

    @Override
    boolean receive() {
        if (holdLen == 0) return true;
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
            if (e.getKeyCode()==KeyEvent.VK_A) receive();
        } else if (button.equalsIgnoreCase("B")) {
            if (e.getKeyCode()==KeyEvent.VK_B) receive();
        } else if (button.equalsIgnoreCase("C")) {
            if (e.getKeyCode()==KeyEvent.VK_C) receive();
        } else if (button.equalsIgnoreCase("X")) {
            if (e.getKeyCode()==KeyEvent.VK_X) receive();
        } else if (button.equalsIgnoreCase("Y")) {
            if (e.getKeyCode()==KeyEvent.VK_Y) receive();
        }
    }
}
