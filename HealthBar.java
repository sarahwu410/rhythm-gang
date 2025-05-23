import java.awt.*;

public class HealthBar {
    private int attacksBeforeDeath = 5;

    HealthBar() {

    }

    /**
     * draws the different stages of the health bar depending on attacks before death
     * @param g2 the Graphics2D object to draw with
     */
    public void draw(Graphics2D g2) {
        if (this.attacksBeforeDeath == 5) {
            g2.setPaint(Color.GREEN);
            g2.fillRect(10, 10, 1000, 100);
        } else if (this.attacksBeforeDeath == 4) {
            g2.setPaint(Color.GREEN);
            g2.fillRect(10, 10, 800, 100);
        } else if (this.attacksBeforeDeath == 3) {
            g2.setPaint(Color.YELLOW);
            g2.fillRect(10, 10, 600, 100);
        } else if (this.attacksBeforeDeath == 2) {
            g2.setPaint(Color.YELLOW);
            g2.fillRect(10, 10, 400, 100);
        } else if (this.attacksBeforeDeath == 1) {
            g2.setPaint(Color.RED);
            g2.fillRect(10, 10, 200, 100);
        } else if (this.attacksBeforeDeath == 0) {
            System.out.println("DRAGON DEATH");
        }

        // draw outline of health bar
        g2.setColor(Color.MAGENTA);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(10, 10, 1000, 100);
    }

    public void determineAttackSuccess(SpamBlock block) {
        if (block.numSpam <= 0) {
            this.attacksBeforeDeath -= 1;
        } 
    }
}
