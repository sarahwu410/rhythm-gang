/*
 * Sarah Wu
 * Created on Wed, May 14, 2025
 * Boss level for our rhythm game
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;

public class BossLevel extends JFrame implements ActionListener, KeyListener{
    boolean isPaused = false;

    DrawingPanel panel;
    Timer timer;
    Audio audio = new Audio("res/Audio/Carrier (Dreamcast) - File 1.wav");

    int milliElapsed; // how much time has passed so far
    int numPerfects = 0;
    int numGoods = 0;
    int numOks = 0;
    int numMisses = 0;
    int score;

    ArrayList<Block> allBlocks;
    ArrayList<Block> aBlocks;
    ArrayList<Block> bBlocks;
    ArrayList<Block> cBlocks;
    ArrayList<Block> xBlocks;
    ArrayList<Block> yBlocks;
    HashMap<String, Receiver> allReceivers = new HashMap<>();
    Set<Integer> heldKeys;
    Image smiley;

    Image ratingSpriteSheet;
    WordPlayer rater;

    FontMetrics metrics;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    HealthBar healthBar = new HealthBar();

    JPanel pausePanel;

    BossLevel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setTitle("Rhythm Game BossLevel");
        this.addKeyListener(this);

        panel = new DrawingPanel();

        // Add the recievers to the HashMap, center the receivers dynamically based on screen size
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        allReceivers.put("A", new Receiver(centerX - 200, centerY - 100, 100, 100));
        allReceivers.put("B", new Receiver(centerX - 50, centerY - 100, 100, 100));
        allReceivers.put("C", new Receiver(centerX + 100, centerY - 100, 100, 100));
        allReceivers.put("X", new Receiver(centerX - 125, centerY + 50, 100, 100));
        allReceivers.put("Y", new Receiver(centerX + 25, centerY + 50, 100, 100));

        // animations
        smiley = loadImage("res/smilingCube.png");

        // Create arraylist with the different types of Blocks read from a file
        allBlocks = ReceiveTimeReader.sortBlocks(
            // receiveTime button
            ReceiveTimeReader.loadTapBlocks("res/PrototypeTapTimes.txt", "Prototype", allReceivers), 
            // receiveTime spamTime numSpam button
            ReceiveTimeReader.loadSpamBlocks("res/PrototypeSpamTimes.txt", "Prototype", allReceivers),
            // receiveTime holdDuration button
            ReceiveTimeReader.loadHoldBlocks("res/PrototypeHoldTimes.txt", "Prototype", allReceivers)
        );
        aBlocks = ReceiveTimeReader.pull(allBlocks, "A");
        bBlocks = ReceiveTimeReader.pull(allBlocks, "B");
        cBlocks = ReceiveTimeReader.pull(allBlocks, "C");
        xBlocks = ReceiveTimeReader.pull(allBlocks, "X");
        yBlocks = ReceiveTimeReader.pull(allBlocks, "Y");
        heldKeys = new HashSet<>();
        for (Block b: allBlocks) {
            try {
                if (b.Blocktype.equals("TapBlock")) {
                    b.movement = new AnimationHorizontal(smiley, b.x, b.y, 0, 0, 2, 100, 100);
                    b.beenHit = new AnimationHorizontal(smiley, b.x, b.y, 100, 0, 10, 100, 100);
                }
            } catch (Exception e) {
                System.out.println("No block type.");
            }
        }

        // Get the rate animation initialized
        ratingSpriteSheet = loadImage("res/RATINGS.png");
        rater = new WordPlayer(ratingSpriteSheet, 20, 20);

        // Initialize pause menu
        createPausePanel();

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
                    if (milliElapsed > 109000) System.exit(0);

                    if (milliElapsed > b.enterTime) {
                        if (milliElapsed>=b.enterTime && !b.received && !b.missed) {
                            // Make sure the user can receive the block
                            b.canReceive = true;
                        }

                        b.move(audio.getTime()*10); // Only move them if they are meant to appear
                    }

                    // if the block missed the receiver, display "miss"
                    if (b.missPassed && !b.hitPlaying) {
                        rater.setRating(4, b);

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

            metrics = g2.getFontMetrics(g2.getFont());

            // paint the receivers themselves
            allReceivers.get("A").draw(g2);
            allReceivers.get("B").draw(g2);
            allReceivers.get("C").draw(g2);
            allReceivers.get("X").draw(g2);
            allReceivers.get("Y").draw(g2);

            // paint the letters on the receivers
            g2.setPaint(Color.BLACK);
            g2.setFont(new Font("monospaced", Font.PLAIN, 50));
            g2.drawString("U", allReceivers.get("A").x + 35, allReceivers.get("A").y + 65);
            g2.drawString("I", allReceivers.get("B").x + 35, allReceivers.get("B").y + 65);
            g2.drawString("O", allReceivers.get("C").x + 35, allReceivers.get("C").y + 65);
            g2.drawString("J", allReceivers.get("X").x + 35, allReceivers.get("X").y + 65);
            g2.drawString("K", allReceivers.get("Y").x + 35, allReceivers.get("Y").y + 65);

            // paint the instructions
            g2.setPaint(Color.WHITE);
            g2.setFont(new Font("monospaced", Font.PLAIN, 20));
            g2.drawString("Press L to open pause menu", 10, screenHeight - 50);

            // paint rating
            rater.play(g2, milliElapsed, numPerfects, numGoods, numOks, numMisses);

            // loop through all the blocks in the ArrayList
            for (int i = 0; i<allBlocks.size(); i++) {
                // if block has reached its enter time and not been received
                if (milliElapsed>=allBlocks.get(i).enterTime && ! (allBlocks.get(i).received && !allBlocks.get(i).hitPlaying) && !allBlocks.get(i).missed) {
                    // draw the block
                    allBlocks.get(i).draw(g2, milliElapsed);
                }

                if (i == allBlocks.get(i).length - 1 && (allBlocks.get(i).received || allBlocks.get(i).missed)) { // If the last block has been received
                    // This portion would be for the end of a song? probably not necessary, songs end on their own afterall
                }
            }

            healthBar.draw(g2);
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
        heldKeys.add(e.getKeyCode());

        // Pause the game and show the pause menu
        if (e.getKeyCode() == KeyEvent.VK_L && !isPaused) {
            isPaused = true;
            timer.stop();
            audio.stopAudio();
            pausePanel.setVisible(true); // Show the pause menu
            return; // Prevent other actions while the pause menu is active
        }

        // Handle pause menu input
        if (isPaused) {
            handlePauseMenuInput(e);
            return; // Prevent other game actions while paused
        }

        // Activate the corresponding receiver
        switch (e.getKeyCode()) {
            case KeyEvent.VK_U:
                allReceivers.get("A").isActive = true;
                break;
            case KeyEvent.VK_I:
                allReceivers.get("B").isActive = true;
                break;
            case KeyEvent.VK_O:
                allReceivers.get("C").isActive = true;
                break;
            case KeyEvent.VK_J:
                allReceivers.get("X").isActive = true;
                break;
            case KeyEvent.VK_K:
                allReceivers.get("Y").isActive = true;
                break;
        }

        handleHeldKeys(e);

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println(allBlocks.size() + " blocks left.");
            System.out.println("Quitter.");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        heldKeys.remove(e.getKeyCode());

        // Deactivate the corresponding receiver
        switch (e.getKeyCode()) {
            case KeyEvent.VK_U:
                allReceivers.get("A").isActive = false;
                break;
            case KeyEvent.VK_I:
                allReceivers.get("B").isActive = false;
                break;
            case KeyEvent.VK_O:
                allReceivers.get("C").isActive = false;
                break;
            case KeyEvent.VK_J:
                allReceivers.get("X").isActive = false;
                break;
            case KeyEvent.VK_K:
                allReceivers.get("Y").isActive = false;
                break;
        }

        handleReleasedKeys(e);
    }

    // Takes care of events that are supposed to happen when keys are held
    public void handleHeldKeys(KeyEvent e) {
        if (heldKeys.contains(KeyEvent.VK_U)) {
            for (Block b: aBlocks) {
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
                    }
                    break;
                }
            }
        }

        if (heldKeys.contains(KeyEvent.VK_I)) {
            for (Block b: bBlocks) {
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
                    }
                    break;
                }
            }
        }

        if (heldKeys.contains(KeyEvent.VK_O)) {
            for (Block b: cBlocks) {
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
                    }
                    break;
                }
            }
        }

        if (heldKeys.contains(KeyEvent.VK_J)) {
            for (Block b: xBlocks) {
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
                    }
                    break;
                }
            }
        }

        if (heldKeys.contains(KeyEvent.VK_K)) {
            for (Block b: yBlocks) {
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
                    }
                    break;
                }
            }
        }
    }

    // Takes care of events that are supposed to happen when keys are released
    public void handleReleasedKeys(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_U) {
            for (Block b: aBlocks) {
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

        if (e.getKeyCode() == KeyEvent.VK_I) {
            for (Block b: bBlocks) {
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

        if (e.getKeyCode() == KeyEvent.VK_O) {
            for (Block b: cBlocks) {
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

        if (e.getKeyCode() == KeyEvent.VK_J) {
            for (Block b: xBlocks) {
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

        if (e.getKeyCode() == KeyEvent.VK_K) {
            for (Block b: yBlocks) {
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
    }

    private void createPausePanel() {
        pausePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                // Draw semi-transparent black background
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Draw menu options
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2.setFont(new Font("Arial", Font.BOLD, 40));
                g2.setColor(Color.WHITE);

                // display text centered
                FontMetrics metrics = g2.getFontMetrics(g2.getFont());
                String resumeText = "Press P to Resume";
                String exitText = "Press L to Exit";

                int resumeX = (getWidth() - metrics.stringWidth(resumeText)) / 2;
                int resumeY = (getHeight() / 2) - 50;
                int exitX = (getWidth() - metrics.stringWidth(exitText)) / 2;
                int exitY = (getHeight() / 2) + 50;

                g2.drawString(resumeText, resumeX, resumeY);
                g2.drawString(exitText, exitX, exitY);
            }
        };
        pausePanel.setOpaque(false);
        pausePanel.setBounds(0, 0, screenWidth, screenHeight); // Cover the entire screen
        pausePanel.setVisible(false); // Initially hidden
        this.add(pausePanel);
    }

    private void handlePauseMenuInput(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_P: // Resume the game
                resumeGame();
                break;
            case KeyEvent.VK_L: // Exit the game
                System.exit(0);
                break;
        }
    }

    private void resumeGame() {
        isPaused = false;
        timer.start();
        audio.playAudio();
        pausePanel.setVisible(false); // Hide the pause menu
    }

    private void calculateScore(int perfects, int goods, int oks, int misses) {
        score = (int)(1000000*((perfects+(goods*0.7)+(oks*0.3))/(perfects+goods+oks+misses)));
    }

    private String calculateGrade(int score) {
        if (score == 1000000) return "SS";
        else if (score > 900000) return "S";
        else if (score > 800000) return "A";
        else if (score > 700000) return "B";
        else if (score > 500000) return "C";
        else return "D";
    }

    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BossLevel();
			}
		});
	}
}
