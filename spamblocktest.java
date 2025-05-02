import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;


public class spamblocktest implements KeyListener {

    DrawPanel panel;
    Timer timer;
    int milliElapsed;
    Audio testAudio = new Audio("res/Audio/15-minutes-of-silence.wav");
    Audio hitsound = new Audio("res/Audio/hitsound.wav");
    //Audio perfectsound = new Audio("res/Audio/perfect.wav");

    int simplec = 0;
    int stairc = 0;
    SpamBlock testBlock = new SpamBlock("easy", "A", 0, 450, 1000, 1000000);
    Boolean startOver = false;
    Receiver testReceiver = new Receiver(300,700,100,100);
    int hit = 0;
    String miss = "miss";
    effect hiteffect = new effect("res/effects/hit_effect.png", testBlock.x, testBlock.y, 35);
    effect perfect = new effect("res/effects/perfect.png", 0, 0, 30);

    ArrayList<Image> simplerun = new ArrayList<Image>();
    ArrayList<Image> staircase = new ArrayList<Image>();
    JFrame frame;


    spamblocktest() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.addKeyListener(this);
        panel = new DrawPanel();
        testBlock.calculateVelocity(testReceiver);
        ratgame.loadAll("res/easylevel/simple_run (", 17, simplerun);
        
        timer = new Timer(1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hiteffect.life();

                milliElapsed = testAudio.getTime();
                if (stairc < 700) stairc +=5;
                else stairc = 0;
                
                if (milliElapsed % 4 == 0) {
                    if (simplec < 14 ) simplec ++;
                    else simplec = 0;
                }
                //added an audio so that the milliseconds passed will be based off how far into the audio it has been
                testBlock.move();
                frame.repaint();
            }

        });

        frame.add(panel);
        frame.setVisible(true);
        timer.start();
        testAudio.playAudio();
        runGame();
        
    }

    public void runGame() {

        while (true) {
            
            
            
            if (milliElapsed % 50 == 0) {
                
                perfect.move();
            }
           
                
            
        }
    }

    public class DrawPanel extends JPanel{
        
        DrawPanel() {
            this.setBackground(Color.BLACK);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            g2.setPaint(Color.BLUE);
            g2.fillRect(testReceiver.x, testReceiver.y, 100, 100);

            if (startOver) {
                milliElapsed = 0; // Reset time
                // reset block
                testBlock = new SpamBlock("easy", "A", 0, 10000,0,0);
                testBlock.calculateVelocity(testReceiver);
                startOver = false;
            } else {
                g2.setPaint(Color.WHITE);
                g2.fillRect(testBlock.x, testBlock.y - testBlock.width, testBlock.length, testBlock.width);
                g2.drawString(miss, 100,100);
                g2.drawString(String.valueOf(hit), 100, 200);
                g2.drawString(String.valueOf(milliElapsed), 100, 150);
                hiteffect.draw(g2);
                perfect.draw(g2);
                g2.fillRect((panel.getWidth()/2)-300, 0, 600,panel.getWidth());
                g2.setPaint(Color.BLACK);
                for (int i = 0; i < 1000; i+= 100) {
                    g2.drawLine(panel.getWidth()/2-300, stairc - i, panel.getWidth()/2+375, stairc - i);
                    g2.drawLine(panel.getWidth()/2-300, stairc + i, panel.getWidth()/2+375, stairc + i);
                }
                

                g2.drawImage(simplerun.get(simplec).getImage(), (panel.getWidth()/2) - 375, panel.getHeight()-750, 750, 750, null);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }



    @Override
    public void keyPressed(KeyEvent e) {

        // BLOCK DOES NOT PICK UP KEY EVENTS, ONLY WINDOW DOES, MUST REMOVE FROM BLOCKS AT SOME POINT
        // I'M SORRY BUT I USED CHATGPT AND LEARNED THAT THEY SHOULDN'T BOTH BE IMPLEMENTING KEY LISTENERS
        // I tested it myself and only the key events in this window are picked up and nothing in the blocks
        // I tested this by adding a print statement to the TapBlock class and it wasn't picked up

        // If the key event text matches the block button
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(testBlock.button)) {
            if (testBlock.checkTime(milliElapsed)) {
                hitsound.playAudio();
                hit += 1;
                hiteffect.appear = true;
                hiteffect.resetLife();
                hiteffect.x = testReceiver.x;
                hiteffect.y = testReceiver.y;
            }

            testBlock.updateSPAM(milliElapsed); 
            if (testBlock.receive(milliElapsed)) {
                if (miss.equals("miss")){
                    perfect.appear = true;
                    perfect.x = testBlock.x;
                    perfect.y = testBlock.y;
                }
                miss = "hit";
                System.out.println("Woohoo!");
            } else {
                System.out.println("Boo! *Throws tomato");
            }
            
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.out.println("Quitter.");
            System.exit(0);
        }

        
    }

    public class effect {
        int x,y;
        int width,length;
        int lifespan;
        boolean appear;
        BufferedImage img;

        effect(String filepath, int x, int y, int lifespan) {
            img = Image.loadImage(filepath);
            this.x = x;
            this.y = y;
            width = 100;
            length = 100;
            this.lifespan = lifespan;
            appear = false;
        }

        public void resetLife() {
            lifespan = 35;
        }

        public void move() {
            if (appear) y+= 1;
        }

        public void draw(Graphics2D g) {
            if (appear)g.drawImage(img,x,y,width,length,null);
        } 

        public void life() {
            if (appear) {
                this.lifespan -= 1;
                if (lifespan <= 0) appear = false;
        }
    }
        
        
    }



    @Override
    public void keyReleased(KeyEvent e) {}

    
    public static void main(String[] args) {new  spamblocktest();}
        
}
