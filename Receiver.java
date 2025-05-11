/*
 * Teresa Mach
 * April 15, 2025 - May 11, 2025
 * A class for the recaiver to set specific coordinates and dimensions
 */

import java.awt.Color;
import java.awt.Graphics2D;

public class Receiver {
    int x, y, width, height;

    public Receiver(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2) {
        g2.setPaint(Color.BLUE);
        g2.fillRect(this.x, this.y, this.width, this.height);
    }
}