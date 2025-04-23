import java.awt.event.KeyEvent;

public class TapBlock extends Block{
    public TapBlock(String level, String button, int enterTime, int receiveTime) {
        super(level, button, enterTime, receiveTime);
    }

    boolean receive() {
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
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

    @Override
    public void keyReleased(KeyEvent e) {}
}
