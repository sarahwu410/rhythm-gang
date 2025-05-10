/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * To test a combination of blocks, will most likely be the prototype
 */

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
    Audio testAudio = new Audio("res/Audio/Counting Up to 60 Seconds.wav");
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
                    if (milliElapsed > b.enterTime) {
                        b.move(testAudio.getTime()*10); // Only move them if they are meant to appear
                        b.canReceive = true;
                    }
                    

                    if (b.x > (testReceiver.x+testReceiver.width) && b.y > (testReceiver.y+testReceiver.height)) {
                        b.canReceive = false;
                        b.missed = true;
                    }
                }
                // See if all the blocks are off screen
                if (testBlocks[testBlocks.length - 1].x > frame.getWidth()) startOver = true;
                frame.repaint();
                testAudioTime = testAudio.getTime();
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
                if (milliElapsed > testBlocks[i].enterTime && !testBlocks[i].received && !testBlocks[i].missed) {
                    g2.fillRect(testBlocks[i].x, testBlocks[i].y, testBlocks[i].length, testBlocks[i].width);
                } if (i == testBlocks.length - 1 && (testBlocks[i].received || testBlocks[i].missed)) { // If the last block has been received
                    startOver = true;
                }
            } 
            
            // Just stop instead
            if (startOver) {
                System.out.println("Have a nice day. Now go.");

                // make the program wait 1 sec before closing to not make it surprising
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.exit(0);
            }

            g2.drawString("testAudio.getTime(): " + Integer.toString(testAudioTime), 10, 500);

            // paint receiver
            g2.setPaint(Color.BLUE);
            g2.fillRect(testReceiver.x, testReceiver.y, testReceiver.width, testReceiver.height);
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        for (Block b: testBlocks) {
            if (b.canReceive && !b.received && !b.missed) {
                // required to set the timeReceived attribute within the Block object itself before calling keyPressed
                b.setTimeReceived(milliElapsed);
                b.keyPressed(e);
                
                break;
            }
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
