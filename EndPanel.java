import javax.swing.*;
import java.awt.*;

public class EndPanel {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = screenSize.width;
    static int screenHeight = screenSize.height;

    static JPanel endScreenPanel;

    
    public static void createEndScreenPanel(JFrame frame, WordPlayer r) {
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
                String scoreText = "Score: " + r.calculateScore();
                String gradeText = "Grade: " + r.calculateGrade();
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
        frame.add(endScreenPanel);
    }

    public static void showEndScreen() {
        PausePanel.isPaused = true; // Prevent further game actions
        endScreenPanel.setVisible(true); // Show the end screen
    }
}
