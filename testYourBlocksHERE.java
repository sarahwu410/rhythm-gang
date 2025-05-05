import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import java.util.Random; // To change colors of blocks

public class testYourBlocksHERE implements KeyListener {

    DrawPanel panel;
    Timer timer;
    int milliElapsed;

    Block[] testBlocks;
    double[][] receiveTimes;
    Random rand = new Random();

    //Block testBlock = new TapBlock("easy", "A", 0, 500);
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

        // Store receive timess
        receiveTimes = ReceiveTimeReader.find("A", "res/testReceiveTimes.txt");
        System.out.println("Receive times: " + receiveTimes[0].length);
        // Create an array for the blocks
        testBlocks = new Block[receiveTimes[0].length];
        System.out.println("There are " + testBlocks.length + " blocks.");
        // Load up the blocks
        for (int i = 0; i < receiveTimes[0].length; i++) {
            testBlocks[i] = new TapBlock("easy", "A", 0, (int) (receiveTimes[0][i] * 1000));
            testBlocks[i].calculateVelocity(testReceiver);
            testBlocks[i].calculateEnterTime(testBlocks[i].speed, testBlocks[i].receiveTime, testBlocks[i].x, testBlocks[i].y, testReceiver.x, testReceiver.y);
            System.out.println("Block loaded. Enter time: " + testBlocks[i].enterTime);
        }

        timer = new Timer(1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Block b: testBlocks) {
                    if (milliElapsed > b.enterTime) b.move(); // Only move them if they are meant to appear
                }
                frame.repaint();
                testAudioTime = testAudio.getTime();
                if (testAudioTime%1000 == 0) beat++;
                milliElapsed++; // NOT ACTUALLY COUNTING IN MILLISECONDS? Something weird is going on...
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
                if (milliElapsed > testBlocks[i].enterTime && !  testBlocks[i].received) {
                    g2.fillRect(testBlocks[i].x, testBlocks[i].y, testBlocks[i].length, testBlocks[i].width);
                } if (i == testBlocks.length - 1 && testBlocks[i].received) { // If the last block has been received
                    startOver = true;
                }
            }
            
            // Do the beginning process again
            if (startOver) {
                milliElapsed = 0; // Reset time
                for (int i = 0; i < receiveTimes[0].length; i++) {
                    testBlocks[i] = new TapBlock("easy", "A", 0, (int) receiveTimes[0][i] * 1000);
                    testBlocks[i].received = false;
                    testBlocks[i].calculateVelocity(testReceiver);
                    testBlocks[i].calculateEnterTime(testBlocks[i].speed, testBlocks[i].receiveTime, testBlocks[i].x, testBlocks[i].y, testReceiver.x, testReceiver.y);
                    System.out.println("Block reloaded.");
                }
                startOver = false;
            }

            g2.drawString("testAudio.getTime(): " + Integer.toString(testAudioTime), 10, 500);
            g2.drawString("testAudioTime: " + Integer.toString(beat), 10, 600);
            g2.setPaint(Color.BLUE);
            g2.fillRect(testReceiver.x, testReceiver.y, testReceiver.width, testReceiver.height);

            // Figure out what blocks to draw
            for (int i = 0; i < testBlocks.length; i++) {
                g2.setPaint(new Color(rand.nextInt(10), 252, rand.nextInt(150)));
                // If the block has not been received and has reached its enter time
                if (milliElapsed > testBlocks[i].enterTime && !  testBlocks[i].received) {
                    g2.fillRect(testBlocks[i].x, testBlocks[i].y, testBlocks[i].length, testBlocks[i].width);
                } if (i == testBlocks.length - 1 && testBlocks[i].received) { // If the last block has been received
                    startOver = true;
                }
            }
            
            // Do the beginning process again
            if (startOver) {
                milliElapsed = 0; // Reset time
                for (int i = 0; i < receiveTimes[0].length; i++) {
                    testBlocks[i] = new TapBlock("easy", "A", 0, (int) receiveTimes[0][i] * 1000);
                    testBlocks[i].received = false;
                    testBlocks[i].calculateVelocity(testReceiver);
                    testBlocks[i].calculateEnterTime(testBlocks[i].speed, testBlocks[i].receiveTime, testBlocks[i].x, testBlocks[i].y, testReceiver.x, testReceiver.y);
                    System.out.println("Block reloaded.");
                }
                startOver = false;
            }
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
            if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(b.button) && ! b.received) { 
                if (b.receive(milliElapsed)) {
                    System.out.println("Woohoo!");
                } else {
                    System.out.println("Boo! *Throws tomato");
                }
                System.out.println("Received: " + milliElapsed);
                break;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println("Quitter.");
            System.exit(0);
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {}

    
    public static void main(String[] args) {new testYourBlocksHERE();}
}
