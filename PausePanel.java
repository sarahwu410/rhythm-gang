import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Image;

public class PausePanel {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = screenSize.width;
    static int screenHeight = screenSize.height;

    static boolean isPaused = false;

    static JPanel pausePanel;
    private static int pauseMenuSelection = 0; // 0 = Resume, 1 = Quit

    static Image resumeSelectedImage;
    static Image resumeSelectedImage2;
    static Image quitSelectedImage;
    static Image quitSelectedImage2;

    public static void createPausePanel(JFrame frame) {
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
        frame.add(pausePanel);
    }

    public static void handlePauseMenuInput(KeyEvent e, Timer t, Audio a) {
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
                                    resumeGame(t, a); // Resume the game
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

    private static void resumeGame(Timer t, Audio a) {
        isPaused = false;
        t.start();
        a.playAudio();
        pausePanel.setVisible(false); // Hide the pause menu
    }

    public static void loadPauseMenuImages() {
        try {
            resumeSelectedImage = loadImage("res/PauseScreen/resumeSelectedImage.png");
            resumeSelectedImage2 = loadImage("res/PauseScreen/resumeSelectedImage2.png");
            quitSelectedImage = loadImage("res/PauseScreen/quitSelectedImage.png");
            quitSelectedImage2 = loadImage("res/PauseScreen/quitSelectedImage2.png");
        } catch (Exception e) {
            e.printStackTrace();
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
}
