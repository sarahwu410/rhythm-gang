/*
 * Teresa Mach
 * April 15, 2025 - May 11, 2025
 * Second level for our rhythm game (Medium difficulty)
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

public class MediumLevel extends JFrame implements ActionListener, KeyListener{
    boolean isPaused = false;

    DrawingPanel panel;
    Timer timer;
    Audio audio = new Audio("res/Audio/firstSong.wav");
    int songLength = 10000 ;

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

    JPanel pausePanel;
    JPanel endScreenPanel;

    Image resumeSelectedImage;
    Image resumeSelectedImage2;
    Image quitSelectedImage;
    Image quitSelectedImage2;

    private int pauseMenuSelection = 0; // 0 = Resume, 1 = Quit

    MediumLevel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setTitle("Star of the Show");
        this.addKeyListener(this);

        panel = new DrawingPanel();

        // Add the receivers to the HashMap, center the receivers dynamically based on screen size
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        allReceivers.put("A", new Receiver(centerX - 200, centerY - 100, 100, 100));
        allReceivers.put("B", new Receiver(centerX - 50, centerY - 100, 100, 100));
        allReceivers.put("C", new Receiver(centerX + 100, centerY - 100, 100, 100));
        allReceivers.put("X", new Receiver(centerX - 125, centerY + 50, 100, 100));
        allReceivers.put("Y", new Receiver(centerX + 25, centerY + 50, 100, 100));

        // Animations
        smiley = loadImage("res/smilingCube.png");

        // Create arraylist with the different types of Blocks read from a file
        allBlocks = ReceiveTimeReader.sortBlocks(
            // receiveTime button
            ReceiveTimeReader.loadTapBlocks("res/mediumTapTimes.txt", "medium", allReceivers), 
            // receiveTime spamTime numSpam button
            ReceiveTimeReader.loadSpamBlocks("res/mediumSpamTimes.txt", "medium", allReceivers),
            // receiveTime holdDuration button
            ReceiveTimeReader.loadHoldBlocks("res/mediumHoldTimes.txt", "medium", allReceivers)
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
        loadPauseMenuImages();
        createPausePanel();

        // Initialize end screen
        createEndScreenPanel();

        this.add(panel);
		this.setVisible(true);

        timer = new Timer(1, this);
        timer.start();
        timer.setInitialDelay(0);

        audio.playAudio();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        milliElapsed = audio.getTime() * 10;

        // Check if the song has ended
        if (milliElapsed >= songLength) { // Compare elapsed time with song length
            timer.stop();
            audio.stopAudio();
            calculateScore(numPerfects, numGoods, numOks, numMisses); // Calculate final score
            showEndScreen();
            return;
        }

        for (Block b : allBlocks) {
            if (milliElapsed > b.enterTime) {
                if (milliElapsed >= b.enterTime && !b.received && !b.missed) {
                    // Make sure the user can receive the block
                    b.canReceive = true;
                }

                b.move(audio.getTime() * 10); // Only move them if they are meant to appear
            }

            // If the block missed the receiver, display "miss"
            if (b.missPassed && !b.hitPlaying) {
                rater.setRating(3, b);

                // For hold blocks only
                try {
                    if (!((HoldBlock) b).beenRated) {
                        b.beenRated = true;
                    }
                } catch (Exception z) {
                    // Do nothing
                }
            }
        }

        this.repaint();
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
            rater.play(g2, milliElapsed);

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

        // Handle end screen input
        if (endScreenPanel.isVisible()) {
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0); // Exit the game
            }
            return; // Prevent other actions while on the end screen
        }

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

                // Reset AlphaComposite to full opacity for the images
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

                // Draw the appropriate image based on the current selection
                Image currentImage = null;
                if (pauseMenuSelection == 0) {
                    currentImage = resumeSelectedImage;
                } else if (pauseMenuSelection == 1) {
                    currentImage = quitSelectedImage;
                } else if (pauseMenuSelection == 2) {
                    currentImage = quitSelectedImage2;
                } else if (pauseMenuSelection == 3) {
                    currentImage = resumeSelectedImage2;
                }

                if (currentImage != null) {
                    int imageWidth = currentImage.getWidth(null);
                    int imageHeight = currentImage.getHeight(null);
                    int x = (getWidth() - imageWidth) / 2;
                    int y = (getHeight() - imageHeight) / 2;
                    g2.drawImage(currentImage, x, y, null);
                }
            }
        };
        pausePanel.setOpaque(false);
        pausePanel.setBounds(0, 0, screenWidth, screenHeight); // Cover the entire screen
        pausePanel.setVisible(false); // Initially hidden
        this.add(pausePanel);
    }

    private void createEndScreenPanel() {
        endScreenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                // Draw semi-transparent black background
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Draw "Good Job!" text
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2.setFont(new Font("Arial", Font.BOLD, 60));
                g2.setColor(Color.WHITE);

                String message = "Good Job!";
                String scoreText = "Score: " + score;
                String gradeText = "Grade: " + calculateGrade(score);
                String exitInstruction = "Press Q to Exit";

                FontMetrics metrics = g2.getFontMetrics(g2.getFont());
                int messageX = (getWidth() - metrics.stringWidth(message)) / 2;
                int messageY = (getHeight() / 2) - 100;
                int scoreX = (getWidth() - metrics.stringWidth(scoreText)) / 2;
                int scoreY = (getHeight() / 2);
                int gradeX = (getWidth() - metrics.stringWidth(gradeText)) / 2;
                int gradeY = (getHeight() / 2) + 100;
                int instructionX = (getWidth() - metrics.stringWidth(exitInstruction)) / 2;
                int instructionY = (getHeight() / 2) + 200;

                g2.drawString(message, messageX, messageY);
                g2.drawString(scoreText, scoreX, scoreY);
                g2.drawString(gradeText, gradeX, gradeY);
                g2.drawString(exitInstruction, instructionX, instructionY);
            }
        };
        endScreenPanel.setOpaque(false);
        endScreenPanel.setBounds(0, 0, screenWidth, screenHeight); // Cover the entire screen
        endScreenPanel.setVisible(false); // Initially hidden
        this.add(endScreenPanel);
    }

    private void loadPauseMenuImages() {
        try {
            resumeSelectedImage = loadImage("res/PauseScreen/resumeSelectedImage.png");
            resumeSelectedImage2 = loadImage("res/PauseScreen/resumeSelectedImage2.png");
            quitSelectedImage = loadImage("res/PauseScreen/quitSelectedImage.png");
            quitSelectedImage2 = loadImage("res/PauseScreen/quitSelectedImage2.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePauseMenuInput(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: // Move selection up
                pauseMenuSelection = (pauseMenuSelection - 1 + 2) % 2; // Wrap around
                pausePanel.repaint();
                break;
            case KeyEvent.VK_DOWN: // Move selection down
                pauseMenuSelection = (pauseMenuSelection + 1) % 2; // Wrap around
                pausePanel.repaint();
                break;
            case KeyEvent.VK_J: // Select the current option
                if (pauseMenuSelection == 0) { // Resume the game
                    // button pressed animation
                    pauseMenuSelection = 3;
                    pausePanel.repaint();

                    Timer showResumeImage2Timer = new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pauseMenuSelection = 0;
                            pausePanel.repaint();

                            Timer showResumeImageTimer = new Timer(100, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    resumeGame(); // Resume the game
                                }
                            });
                            showResumeImageTimer.setRepeats(false); // Only run once
                            showResumeImageTimer.start();
                        }
                    });
                    showResumeImage2Timer.setRepeats(false); // Only run once
                    showResumeImage2Timer.start();
                } 
                else if (pauseMenuSelection == 1) { // Quit the game
                    // button animation
                    pauseMenuSelection = 2;
                    pausePanel.repaint();

                    Timer showQuitImageTimer = new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pauseMenuSelection = 1;
                            pausePanel.repaint();

                            Timer quitTimer = new Timer(100, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0); // Quit the game
                                }
                            });
                            quitTimer.setRepeats(false); // Only run once
                            quitTimer.start();
                        }
                    });
                    showQuitImageTimer.setRepeats(false); // Only run once
                    showQuitImageTimer.start();
                }
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

    private void showEndScreen() {
        isPaused = true; // Prevent further game actions
        endScreenPanel.setVisible(true); // Show the end screen
    }

    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MediumLevel();
			}
		});
	}
}
