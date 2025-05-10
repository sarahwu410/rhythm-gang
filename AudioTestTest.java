import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class AudioTestTest extends JFrame implements KeyListener {
    DrawingPanel panel;
    AudioTest audio = new AudioTest("res/Audio/Chess Type Beat Slowed.wav");
    int rectx;
    boolean isPlaying;
    AudioTest.AudioFrameListener listener = (buffer, len) -> {
            rectx=(int)(audio.getTimeInMilliseconds() * 1); // move 1 px per millisecond
            panel.repaint();
        };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AudioTestTest();
			}
		});
    }

    AudioTestTest() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.addKeyListener(this);
        panel = new DrawingPanel();

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        audio.playAudio(listener);
        isPlaying = true;
    }

    private class DrawingPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            g2.fillRect(rectx, 200, 100, 100);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_Q) {
            System.exit(0);
        }

        if (e.getKeyCode()==KeyEvent.VK_P) {
            if (isPlaying) {
                audio.pauseAudio();
                isPlaying = false;
            } else if (!isPlaying) {
                audio.playAudio(listener);
                isPlaying = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
