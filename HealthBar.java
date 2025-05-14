import java.awt.*;

public class HealthBar {
    private int attacksBeforeDeath = 5;

    HealthBar() {

    }

    /**
     * draws the different stages of the health bar
     * depending on attacks before death
     * @param g2 the Graphics2D object to draw with
     */
    public void draw(Graphics2D g2) {
        if (attacksBeforeDeath == 5) {
            g2.setPaint(Color.GREEN);
            g2.fillRect(0, 0, 100, 10);
        } else if (attacksBeforeDeath == 4) {
            g2.setPaint(Color.GREEN);
            g2.fillRect(0, 0, 80, 10);
        } else if (attacksBeforeDeath == 3) {
            g2.setPaint(Color.YELLOW);
            g2.fillRect(0, 0, 60, 10);
        } else if (attacksBeforeDeath == 2) {
            g2.setPaint(Color.YELLOW);
            g2.fillRect(0, 0, 40, 10);
        } else if (attacksBeforeDeath == 1) {
            g2.setPaint(Color.RED);
            g2.fillRect(0, 0, 20, 10);
        } else if (attacksBeforeDeath == 0) {
            System.out.println("DRAGON DEATH");
        }

        // draw outline of health bar
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(0, 0, 100, 10);
    }

    public void determineAttackSuccess(SpamBlock block) {
        if (block.numSpam <= 0) {
            attacksBeforeDeath -= 1;
        } 
    }
}
