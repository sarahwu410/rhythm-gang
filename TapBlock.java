import java.awt.event.KeyEvent;

public class TapBlock extends Block{
    public TapBlock(String level, String button, int receiveTime, Receiver someReceiver) {
        super(level, button, receiveTime, someReceiver);
    }

    @Override
    boolean receive(int timeReceived) {
        int accuracy = (int) (Math.abs(receiveTime - timeReceived));
        this.received = true;
    	if (accuracy < 1000) return this.received = true;
    	else return this.received = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(this.button) && ! this.received) { 
                if (this.receive(this.timeReceived)) {
                    System.out.println("Woohoo!");
                } else {
                    System.out.println("Boo! *Throws tomato");
                }
                System.out.println("Received: " + timeReceived);
                return;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
