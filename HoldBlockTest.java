import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HoldBlockTest extends JPanel implements ActionListener, KeyListener {
    private HoldBlock holdBlock;
    private Timer timer;
    private Receiver receiver;

    public HoldBlockTest() {
        // Initialize receiver near bottom right
        receiver = new Receiver(600, 400, 30, 30);
        // Example: Hold the "A" key for 1500ms within 1s of receiveTime
        holdBlock = new HoldBlock("test", "A", 0, System.currentTimeMillis() + 3000, 1500, receiver);

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw hold block as a green square
        g.setColor(Color.GREEN);
        g.fillRect(holdBlock.headX, holdBlock.headY, 30, 30);

        // Draw tail block
        g.setColor(Color.GREEN.darker());
        g.fillRect(holdBlock.tailX, holdBlock.tailY, 30, 30);

        // Draw receiver as a red rectangle
        g.setColor(Color.RED);
        g.fillRect(receiver.x, receiver.y, receiver.width, receiver.height);

        // Show position
        g.setColor(Color.WHITE);
        g.drawString("Block position: (" + holdBlock.headX + ", " + holdBlock.headY + ")", 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        holdBlock.move();
        if (holdBlock.isCompleted()) {
            holdBlock = new HoldBlock("test", "A", 0, System.currentTimeMillis() + 3000, 1500, receiver);
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        holdBlock.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        holdBlock.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("HoldBlock Test");
        HoldBlockTest panel = new HoldBlockTest();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
    }
}