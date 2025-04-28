import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class testYourBlocksHERE implements KeyListener {

    DrawPanel panel;
    Timer timer;


    //you can use this area to create block objects
    //ex: b = new HoldBLock(x,y,speed......
    //or just create an arraylist of blocks
    

    testYourBlocksHERE() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.addKeyListener(this);
        panel = new DrawPanel();


        timer = new Timer(1,new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //use this timer to do actions like move block
                





                

            }

        });


        frame.add(panel);
        frame.setVisible(true);
        timer.start();
    }

    public class DrawPanel extends JPanel{
        
        DrawPanel() {
            this.setBackground(Color.BLACK);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            //draw your blocks here
            //for ex: g2.setPaint(Color.WHITE);
            //g2.fillRect(b.x,b.y,b.width.b.height);









        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {}

    
    public static void main(String[] args) {}

}
