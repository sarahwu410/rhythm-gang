/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * To test a combination of blocks
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import java.util.Random; // To change colors of blocks
import java.util.HashMap;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;

public class testYourBlocksHERE implements KeyListener {
    DrawPanel panel;
    Timer timer;
    int milliElapsed;

    Random rand = new Random();

    HashMap<String, Receiver> theseReceivers = new HashMap<>();

    Boolean startOver = false;
    Audio testAudio = new Audio("res/Audio/Counting Up to 60 Seconds.wav");
    int testAudioTime;
    Receiver testReceiver = new Receiver(1000, 500, 100, 100);

    ArrayList<Block> allBlocks;
    Image ratingSpriteSheet;
    WordPlayer rater;

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

        // How to use ReceiveTimeReader to sort blocks
        allBlocks = ReceiveTimeReader.sortBlocks(
            ReceiveTimeReader.loadTapBlocks("res/testReceiveTimes.txt", "easy", theseReceivers), 
            ReceiveTimeReader.loadSpamBlocks("res/testSpamTimes.txt", "easy", theseReceivers), 
            ReceiveTimeReader.loadHoldBlocks("res/testHoldTimes.txt", "easy", theseReceivers)
        );

        // Get the rate animation initialized
        ratingSpriteSheet = loadImage("res/PERFECT!GOODMISSED.png");
        rater = new WordPlayer(ratingSpriteSheet, 20, 20);

        timer = new Timer(1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Block b: allBlocks){
                    if (milliElapsed > b.enterTime) {
                        b.move(testAudio.getTime()*10); // Only move them if they are meant to appear
                    }

                    // if the block missed the receiver, display "miss"
                    if (b.missPassed) {
                        rater.setRating(3, b);

                        //For hold blocks only
                        try {
                            if (!((HoldBlock)b).beenRated) {
                                b.beenRated = true;
                            }
                        } catch (Exception z) {
                            // do nothing
                        }
                    }
                }

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

            // paint receiver
            g2.setPaint(Color.BLUE);
            g2.fillRect(testReceiver.x, testReceiver.y, testReceiver.width, testReceiver.height);

            rater.play(g2, milliElapsed);

            // Figure out what blocks to draw
            for (int i = 0; i < allBlocks.size(); i++) {
                // If the block has not been received and has reached its enter time
                if (milliElapsed >= allBlocks.get(i).enterTime && !allBlocks.get(i).received && !allBlocks.get(i).missed) {
                    // Make sure the user can receive the block
                    allBlocks.get(i).canReceive = true;
                    //// g2.fillRect(allBlocks.get(i).x, allBlocks.get(i).y, allBlocks.get(i).length, allBlocks.get(i).width);

                    allBlocks.get(i).draw(g2, milliElapsed);
                } 
                
                if (i == allBlocks.get(i).length - 1 && (allBlocks.get(i).received || allBlocks.get(i).missed)) { // If the last block has been received
                    // This portion would be for the end of a song? probably not necessary, songs end on their own afterall
                }
            }

            // g2.setPaint(Color.WHITE);
            // g2.drawString("testAudio.getTime(): " + Integer.toString(testAudioTime), 10, 500);
        }
    }

    // Getting an image
	static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null, "An image failed to load: " + filename, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		// DEBUG
		// if (img == null) System.out.println("null");
		// else System.out.printf("w=%d, h=%d%n",img.getWidth(), img.getHeight());
		return img;
	}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        for (Block b: allBlocks) {
            if (b.canReceive && !b.received && !b.missed && !b.missPassed) {
                // required to set the timeReceived attribute within the Block object itself before calling keyPressed
                b.setTimeReceived(milliElapsed);
                b.keyPressed(e);
                
                //For hold blocks only
                try {
                    if (((HoldBlock)b).isPressed) {
                        rater.setRating(((HoldBlock) b).holdRate(), b);
                    }
                } catch (Exception z) {
                    // do nothing
                }

                if (b.received || b.missed) {
                    rater.setRating(b.rate(),b);

                    System.out.println("RECEIVED: " + b.received + "; MISSED: " + b.missed + "; MISSPASSED: "+b.missPassed);
                    allBlocks.remove(b);
                }
                
                break;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println(allBlocks.size() + " blocks left.");
            System.out.println("Quitter.");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (Block b: allBlocks) {
            if (b.canReceive && !b.received && !b.missed && !b.missPassed) {
                // required to set the timeReceived attribute within the Block object itself before calling keyPressed
                b.setTimeReceived(milliElapsed);
                b.keyReleased(e);
                
                if (b.received || b.missed) {
                    rater.setRating(b.rate(), b);

                    System.out.println("RECEIVED: " + b.received + "; MISSED: " + b.missed + "; MISSPASSED: "+b.missPassed);
                }

                //For hold blocks only
                try {
                    if (!((HoldBlock)b).beenRated) {
                        b.beenRated = true;
                    }
                } catch (Exception z) {
                    // do nothing
                }

                break;
            }
        }
    }

    public static void main(String[] args) {new testYourBlocksHERE();}
}
