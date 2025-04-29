import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class testYourBlocksHERE implements KeyListener {

    DrawPanel panel;
    Timer timer;
    int milliElapsed;

    Block testBlock = new TapBlock("easy", "A", 0, 110);
    Boolean startOver = false;
    Receiver testReceiver = new Receiver(1000, 500);

    testYourBlocksHERE() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.addKeyListener(this);
        panel = new DrawPanel();
        testBlock.calculateVelocity(testReceiver);

        timer = new Timer(1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testBlock.move();
                frame.repaint();
                milliElapsed++; // NOT ACTUALLY COUNTING IN MILLISECONDS?
            }

        });
        frame.add(panel);
        frame.setVisible(true);
        timer.start();
    }

    public class DrawPanel extends JPanel{
        
        DrawPanel() {
            this.setBackground(Color.BLACK);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            g2.setPaint(Color.BLUE);
            g2.fillRect(testReceiver.x, testReceiver.y, 100, 100);

            if (startOver) {
                milliElapsed = 0; // Reset time
                // reset block
                testBlock = new TapBlock("easy", "A", 0, 10000);
                testBlock.calculateVelocity(testReceiver);
                startOver = false;
            } else {
                g2.setPaint(Color.WHITE);
                g2.fillRect(testBlock.x, testBlock.y, testBlock.length, testBlock.width);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }



    @Override
    public void keyPressed(KeyEvent e) {

        // BLOCK DOES NOT PICK UP KEY EVENTS, ONLY WINDOW DOES, MUST REMOVE FROM BLOCKS AT SOME POINT
        // I'M SORRY BUT I USED CHATGPT AND LEARNED THAT THEY SHOULDN'T BOTH BE IMPLEMENTING KEY LISTENERS
        // I tested it myself and only the key events in this window are picked up and nothing in the blocks
        // I tested this by adding a print statement to the TapBlock class and it wasn't picked up

        // If the key event text matches the block button
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(testBlock.button)) { 
            if (testBlock.receive(milliElapsed)) {
                System.out.println("Woohoo!");
            } else {
                System.out.println("Boo! *Throws tomato");
            }
            startOver = true;
            System.out.println(milliElapsed);
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println("Quitter.");
            System.exit(0);
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println("Quitter.");
            System.exit(0);
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {}

    
    public static void main(String[] args) {}

}
