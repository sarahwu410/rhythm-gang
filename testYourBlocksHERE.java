import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import java.util.Random; // To change colors of blocks
import java.util.HashMap;

public class testYourBlocksHERE implements KeyListener {

    DrawPanel panel;
    Timer timer;
    int milliElapsed;

    Block[] testBlocks;
    double[][] receiveTimes;
    Random rand = new Random();

    HashMap<String, Receiver> theseReceivers = new HashMap<>();

    Boolean startOver = false;
    Audio testAudio = new Audio("res/Audio/15-minutes-of-silence.wav");
    int beat = 0;
    int testAudioTime;
    Receiver testReceiver = new Receiver(1000, 500, 100, 100);

    testYourBlocksHERE() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.addKeyListener(this);
        panel = new DrawPanel();

        // Add receivers to the hashmap
        theseReceivers.put("A", testReceiver);
        theseReceivers.put("B", testReceiver);

        // Load up the blocks
        testBlocks = ReceiveTimeReader.loadTapBlocks("res/testReceiveTimes.txt", "easy", theseReceivers);
        System.out.println("There are " + testBlocks.length + " blocks.");

        timer = new Timer(1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Block b: testBlocks) {
                    if (milliElapsed > b.enterTime) b.move((testAudio.getTime()) * 10); // Only move them if they are meant to appear
                }
                // See if all the blocks are off screen
                if (testBlocks[testBlocks.length - 1].x > frame.getWidth()) startOver = true;
                //if (milliElapsed > testHold.enterTime) testHold.move();
                frame.repaint();
                testAudioTime = testAudio.getTime();
                if (testAudioTime%1000 == 0) beat++;
                milliElapsed = testAudioTime * 10;
            }

        });
        frame.add(panel);
        frame.setVisible(true);
        timer.start();
        testAudio.playAudio();
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
            g2.fillRect(testReceiver.x, testReceiver.y, testReceiver.width, testReceiver.height);

            // Figure out what blocks to draw
            for (int i = 0; i < testBlocks.length; i++) {
                g2.setPaint(new Color(rand.nextInt(10), 252, rand.nextInt(150)));
                // If the block has not been received and has reached its enter time
                if (milliElapsed > testBlocks[i].enterTime && ! testBlocks[i].received) {
                    g2.fillRect(testBlocks[i].x, testBlocks[i].y, testBlocks[i].length, testBlocks[i].width);
                } if (i == testBlocks.length - 1 && testBlocks[i].received) { // If the last block has been received
                    startOver = true;
                }
            } 
            
            //if (milliElapsed >= testHold.enterTime && ! testHold.received) {
                //g2.setPaint(new Color(rand.nextInt(10), 252, rand.nextInt(150)));
                //g2.fillRect(testHold.headX, testHold.headY, testHold.length, testHold.width);
                //g2.fillRect(testHold.tailX, testHold.tailY, testHold.length, testHold.width);
            //}
            
            // Just stop instead
            if (startOver) {
                System.out.println("Have a nice day. Now go.");
                System.exit(0);
            }

            g2.drawString("testAudio.getTime(): " + Integer.toString(testAudioTime), 10, 500);
            g2.drawString("testAudioTime: " + Integer.toString(beat), 10, 600);
            g2.setPaint(Color.BLUE);
            g2.fillRect(testReceiver.x, testReceiver.y, testReceiver.width, testReceiver.height);
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If the key event text matches the block button, tell the user the result
        for (Block b: testBlocks) {
            b.setTimeReceived(milliElapsed);
            b.keyPressed(e);
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println("Quitter.");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {new testYourBlocksHERE();}
}
