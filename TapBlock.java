import java.awt.event.KeyEvent;

public class TapBlock extends Block{
    public TapBlock(String level, String button, int receiveTime, Receiver someReceiver) {
        super(level, button, receiveTime,someReceiver);
    }

    @Override
    boolean receive(int timeReceived) {
        this.timeReceived = timeReceived;
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
        this.received = true;
    	if (accuracy < 1000) return this.received = true;
    	else return this.received = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
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

    @Override
    public void keyReleased(KeyEvent e) {}
}
