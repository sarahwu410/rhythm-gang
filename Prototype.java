/*
 * Eleora Jacob, Teresa Mach, Wilson Wei, Sarah Wu
 * April 15, 2025 - May 11, 2025
 * Prototype for our rhythm game
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import java.util.HashMap;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;

public class Prototype extends JFrame implements ActionListener, KeyListener{
    boolean isPaused = false;

    DrawingPanel panel;
    Timer timer;
    Audio audio = new Audio("res/Audio/Counting Up to 60 Seconds.wav");

    int milliElapsed; // how much time has passed so far

    ArrayList<Block> allBlocks;
    HashMap<String, Receiver> allReceivers = new HashMap<>();

    Image ratingSpriteSheet;
    WordPlayer rater;

    Prototype() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setTitle("Rhythm Game Prototype");
        this.addKeyListener(this);

        panel = new DrawingPanel();

        // Add receivers to the HashMap
        allReceivers.put("A", new Receiver(500, 300, 100,100));
        allReceivers.put("B", new Receiver(650, 300, 100,100));
        allReceivers.put("C", new Receiver(800, 300, 100,100));
        allReceivers.put("X", new Receiver(575, 450, 100,100));
        allReceivers.put("Y", new Receiver(725, 450, 100,100));

        // Create arraylist with the different types of Blocks read from a file
        allBlocks = ReceiveTimeReader.sortBlocks(
            // receiveTime button
            ReceiveTimeReader.loadTapBlocks("res/prototypeTapTimes.txt", "prototype", allReceivers), 
            // receiveTime spamTime numSpam button
            ReceiveTimeReader.loadSpamBlocks("res/prototypeSpamTimes.txt", "prototype", allReceivers),
            // receiveTime holdDuration button
            ReceiveTimeReader.loadHoldBlocks("res/prototypeHoldTimes.txt", "prototype", allReceivers)
        );

        // Get the rate animation initialized
        ratingSpriteSheet = loadImage("res/PERFECT!GOODMISSED.png");
        rater = new WordPlayer(ratingSpriteSheet, 20, 20);

        this.add(panel);
		this.setVisible(true);

        timer = new Timer(1, this);
        timer.start();
        timer.setInitialDelay(0);

        audio.playAudio();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (Block b: allBlocks){
                    if (milliElapsed > b.enterTime) {
                        if (milliElapsed>=b.enterTime && !b.received && !b.missed) {
                            // Make sure the user can receive the block
                            b.canReceive = true;
                        }

                        b.move(audio.getTime()*10); // Only move them if they are meant to appear
                    }
                }

                this.repaint();
                milliElapsed = audio.getTime() * 10;
    }

    public class DrawingPanel extends JPanel {
        DrawingPanel() {
            this.setBackground(Color.BLACK);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            // paint the receivers themselves
            allReceivers.get("A").draw(g2);
            allReceivers.get("B").draw(g2);
            allReceivers.get("C").draw(g2);
            allReceivers.get("X").draw(g2);
            allReceivers.get("Y").draw(g2);

            // paint the letters on the receivers
            g2.setPaint(Color.BLACK);
            g2.setFont(new Font("monospaced", Font.PLAIN, 50));
            g2.drawString("U", allReceivers.get("A").x+35, allReceivers.get("A").y+65);
            g2.drawString("I", allReceivers.get("B").x+35, allReceivers.get("B").y+65);
            g2.drawString("O", allReceivers.get("C").x+35, allReceivers.get("C").y+65);
            g2.drawString("J", allReceivers.get("X").x+35, allReceivers.get("X").y+65);
            g2.drawString("K", allReceivers.get("Y").x+35, allReceivers.get("Y").y+65);

            // paint the instructions
            g2.setPaint(Color.WHITE);
            g2.setFont(new Font("monospaced", Font.PLAIN, 20));
            g2.drawString("Press Q to quit", 10, 500);
            g2.drawString("Press SPACE to pause/unpause", 10, 550);

            // paint rating
            rater.play(g2, milliElapsed);

            // loop through all the blocks in the ArrayList
            for (int i = 0; i<allBlocks.size(); i++) {
                // if block has reached its enter time and not been received
                if (milliElapsed>=allBlocks.get(i).enterTime && !allBlocks.get(i).received && !allBlocks.get(i).missed) {
                    // draw the block
                    allBlocks.get(i).draw(g2);
                }

                // if the block missed the receiver, display "miss"
                if (allBlocks.get(i).missPassed) {
                    rater.setRating(3, allBlocks.get(i));

                    //For hold blocks only
                    try {
                        if (!((HoldBlock)allBlocks.get(i)).beenRated) {
                            allBlocks.get(i).beenRated = true;
                        }
                    } catch (Exception z) {
                        // do nothing
                    }
                }

                if (i == allBlocks.get(i).length - 1 && (allBlocks.get(i).received || allBlocks.get(i).missed)) { // If the last block has been received
                    // This portion would be for the end of a song? probably not necessary, songs end on their own afterall
                }
            }
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

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!isPaused) {
                isPaused = true;
                timer.stop();
                audio.stopAudio();
            } else {
                isPaused = false;
                timer.start();
                audio.playAudio();
            }
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

    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Prototype();
			}
		});
	}
}
