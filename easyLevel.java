import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;




public class easyLevel implements KeyListener {


    static Audio testaudio = new Audio("res/Audio/15-minutes-of-silence.wav");
    Audio mainMusic = new Audio("res/Audio/jungle_music.wav");
    JFrame frame;
    JPanel panel;
    Timer timer;

    boolean hit = false;

    Audio swordhit = new Audio("res/easylevel/sword_hit.wav");
    Audio swordhit2 = new Audio("res/easylevel/sword_hit.wav");
    int pause = 0;
    int section = 0;
    int intro = 1;
    //for things that can only be activated once . like hitting play once.
    boolean[] firstTimeOnly = {true,true,true};
    
    int score;
    int time;
    Image pillar;
    ArrayList<Receiver> rBlocks = new ArrayList<Receiver>();
    ArrayList<Block> tBlocks1 = new ArrayList<Block>();
    ArrayList<Block> tBlocks2 = new ArrayList<Block>();

    Image run;

    ArrayList<Block> allBlocks = new ArrayList<Block>();

    

    HashMap<Integer,Integer> hashblocks = new HashMap<Integer, Integer>();


    Image intro1;
    Image intro2;
    Image intro3;
    Image intro4;
    Image intro5;
    Image intro6;
    Image intro7;
    Image intro8;
    Image intro9;
    Image outline;
    Image text1;
    Image text2;
    Image text3;
    Image transition;

    Image sword1;
    Image sword2;
    Image sword3;
    Image jump;

    boolean jumping = false;

    Image scene1;
    int pwidth, pheight;
    int timercounter;
    int speed = 10;
    int scounter = 0;

    public static void main(String[] args) {
        new easyLevel();
    }

    easyLevel() {
        intro1 = new Image("res/easylevel/Intro1", 10);
        intro2 = new Image("res/easylevel/intro2", 8);
        intro3 = new Image("res/easylevel/intro3", 7);
        intro4 = new Image("res/easylevel/walk in", 12);
        intro5 = new Image("res/easylevel/intro5.png");
        intro6 = new Image("res/easylevel/intro6.png");
        intro7 = new Image("res/easylevel/intro7", 31);
        intro8 = new Image("res/easylevel/intro8.png");
        intro9 = new Image("res/easylevel/intro9", 11);
        text1 = new Image("res/easylevel/text1.png");
        text2 = new Image("res/easylevel/text2.png");
        text3 = new Image("res/easylevel/text3.png");
        sword1 = new Image("res/easylevel/sword1", 8);
        sword2 = new Image("res/easylevel/sword2", 8);
        sword3 = new Image("res/easylevel/sword3", 4);
        transition = new Image("res/easylevel/transition", 12);
        scene1 = new Image("res/easylevel/scene1",67 );
        jump = new Image("res/easylevel/jump", 7);
        run = new Image("res/easylevel/simple_run", 17, true);
        pillar = new Image("res/easylevel/pillar", 15);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.addKeyListener(this);

        panel = new DrawPanel();

        
        frame.add(panel);
        frame.setVisible(true);

        pwidth = frame.getWidth();
        pheight = frame.getHeight();

        int rwidth = pwidth/15;
        int rheight = pheight/8;
       
        rBlocks.add(new Receiver((pwidth/2)-(rwidth*3), (pheight/3)*2,rwidth,rheight));
        rBlocks.add(new Receiver((pwidth/2)+(rwidth*3), (pheight/3)*2,rwidth,rheight));
        rBlocks.add(new Receiver((pwidth/2)-(rwidth*6), (pheight/3)*2,rwidth,rheight));
        tBlocks2.add(new TapBlock("easy", "B", 8589, rBlocks.get(0)));
        tBlocks2.add(new TapBlock("easy", "B", 13661, rBlocks.get(0)));
        tBlocks2.add(new TapBlock("easy", "B", 13861, rBlocks.get(0)));
        tBlocks2.add(new TapBlock("easy", "B", 14061, rBlocks.get(0)));
    
        
        allBlocks.add(new SpamBlock("easy", "C", 38261, rBlocks.get(0), 10, 49164));

        

        hashblocks.put(3955,4955);
        hashblocks.put(5155,6155);
        hashblocks.put(6465,7465);
        hashblocks.put(7944,8944);
        hashblocks.put(9399,10369);
        hashblocks.put(10713,11713);
        hashblocks.put(12103,13103);
        hashblocks.put(13435,14435);
        hashblocks.put(27591,28691);
        hashblocks.put(30644,31744);
        hashblocks.put(33000,34100);
        hashblocks.put(35900,37000);
        hashblocks.put(13435,14535);





        createBlocks(tBlocks1, hashblocks, "A");
        
        
        timer = new Timer(1, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                time = mainMusic.getTime();
                if (jumping && time % 8 == 0) jump.updateCount();
                if (jump.counter == 6) {
                    jump.counter = 0;
                    jumping = false;
                }

                if (time/10 == 488) section = 5;
                if (time/10 >= 37 && time/10 < 47 && time % 4 == 0) transition.updateCount();

                if (section == 3) {
                    if (time >2759 && time% 5 == 0) {
                        pillar.updateCount();
                        switch (time/10) {
                            case 300-> pillar.counter = 0;
                            case 330 -> pillar.counter = 0;
                            case 350 -> pillar.counter = 0;
                        }
                    }
                }

                if (intro == 9) {
                    switch(time/10) {
                        case 7 -> intro9.counter = 1;
                        case 13 -> intro9.counter = 2;
                        case 19 -> intro9.counter = 3;
                        case 23 -> intro9.counter = 4;
                        case 26 -> intro9.counter = 5;
                        case 29 -> intro9.counter = 6;
                        case 31 -> intro9.counter = 7;
                        case 33 -> intro9.counter = 8;
                        case 35 -> intro9.counter = 9;
                        case 36 -> intro9.counter = 10;
                    }
                }

                if (time/10 == 40 || (time/10 > 40 && time/10 < 170)) {
                    section = 1;
                    intro++;
                }

               
                if (time/10 == 392) section = 4;
                if (time >= 3921 && time % speed == 0) {   
                    run.updateCount();
                }

                

                if (intro == 7 && timercounter % 5 == 0) {
                    intro7.updateCount();
                } 

                if (intro7.counter == 30) {
                    intro = 8;
                    intro7.counter = 0;
                }

                
                if (time/10 == 270) section = 3;

                
                if (time >= 1690 && time %10 == 0 && time <= 2800) {
                    scene1.updateCount();
                }

                if (time/10 == 170) {
                    section = 2;
                }

                if (hit) {
                    sword1.counter = 5;
                }
                
                if (time/10 == 99 || time == 939) hit = false;

                if(section == 1) {
                    if (time > 430 && time % 5== 0 && !hit) sword1.updateCount();
                    if (time >939 && time < 1030 && time % 3 == 0) {
                        if (hit) sword2.counter = 5;
                        if (!hit)sword2.updateCount();
                    }
                    
                    switch (time/10) {
                        case 53 -> {
                            sword1.counter = 0;
                            hit = false;
                            break;
                        }
                        case 67 -> {
                            sword1.counter = 0;
                            hit = false;
                            break;
                        }
                        case 82 -> {
                            sword1.counter = 0;
                            hit = false;
                            break;
                        }
                        case 95 -> {
                            sword1.counter = 0;
                            hit = false;
                            break;
                        }
                        
                        case 110 -> {
                            sword1.counter = 0;
                            hit = false;
                            break;
                        }
                        case 124 -> {
                            sword1.counter = 0;
                            hit = false;
                            break;
                        }
                        case 137-> {
                            sword1.counter = 0;
                            hit = false;
                            break;
                        }
                    }
                }


                if (intro4.counter == 11) {
                    intro = 5;
                    intro4.counter = 0; 
                }



                if (intro == 4 && timercounter % 15 == 0) {
                    intro4.updateCount();
                }
                
                if (intro >= 1 && timercounter % 5==0) {
                    intro1.updateCount();
                    if (intro >= 2) {
                        intro2.updateCount();
                        if (intro >= 3) {
                            intro3.updateCount();
                        }
                    }
                }


                moveArrayBlocks(tBlocks1);
                moveArrayBlocks(tBlocks2); 
                moveArrayBlocks(allBlocks);   
                checkHit(tBlocks1);
                checkHit(tBlocks2);
                checkHit(allBlocks);
                
                frame.repaint();
                timercounter++;
            }
            
        });
        
        
        timer.start();


       
    }

    public void createBlocks(ArrayList<Block> ta, HashMap<Integer,Integer> m, String button) {
        for (int i: m.keySet() ) {
            ta.add(new TapBlock("easy", button, i, rBlocks.get(0)));
        }
    }

    public void checkHit(ArrayList<Block> ba) {
        for (Block b: ba) {
            if (b.received) {
                if(!b.hit) {
                    b.hit = true;
                    score++;
                }

                if (b.Blocktype.equals("SpamBlock")) {
                    b.hit = false;
                    b.received = false;
                }
            }
        }
    }

    public void checkKeyPress(KeyEvent e, ArrayList<Block> ta)  {
        for (Block t: ta) {
                if (t.button.equalsIgnoreCase("A")) {
                    if (e.getKeyCode()==KeyEvent.VK_A) {
                       if (t.received) {
                        if (section == 1) {
                            swordhit.playAudio();
                            hit = true;
                           
                        }
                    
                        if (section == 3) jumping = true;
                       }
                    }
                
                } else if (t.button.equalsIgnoreCase("B")) {
                    if (e.getKeyCode()==KeyEvent.VK_B) {
                        if (t.received && section == 1) {
                            swordhit2.playAudio();
                            hit = true;
                            if (time > 1560) sword3.updateCount();
                        }

                        
                    
                    }
                } else if (t.button.equalsIgnoreCase("C")) {
                    if (e.getKeyCode()==KeyEvent.VK_C) t.receive(time*10);
                } else if (t.button.equalsIgnoreCase("X")) {
                    if (e.getKeyCode()==KeyEvent.VK_X) t.receive(time*10);
                } else if (t.button.equalsIgnoreCase("Y")) {
                    if (e.getKeyCode()==KeyEvent.VK_Y) t.receive(time*10);
                }
            }
        }
    


    public void moveArrayBlocks(ArrayList<Block> t) {
        for (Block t1: t) {
            
            if (t1.enterTime/10 < time) {
                if (t1.Blocktype.equals("SpamBlock")) {
                    if (t1.x < rBlocks.get(2).x && t1.y < rBlocks.get(2).y) t1.move(time);
                }
                if (t1.Blocktype.equals("TapBlock")) t1.move(time);
            }
        }
    }

    public void drawArrayBlocks(ArrayList<Block> t, Graphics2D g2) {
        for (Block t1: t) {
            if (t1.enterTime/10 < time && !t1.received)  {
                if (t1.Blocktype.equals("TapBlock"))g2.fillRect(t1.x,t1.y,t1.width,t1.length);
            }
            }
        }

    public void drawIntro(Graphics2D g2) {
        
        if (intro >= 1 && intro < 4) {
            if (intro < 4)g2.drawImage(intro1.getImage(), pwidth/46, pheight/10, pwidth/3, pheight/3, null);
            if (intro == 1)g2.drawImage(text1.getImage(), pwidth/4, pheight/3, pwidth/4, pheight/4, null);
            if (intro >= 2) {
                g2.drawImage(intro2.getImage(), pwidth/3 + pwidth/20, pheight/25, pwidth/2, pheight/2, null);
                if (intro == 2)g2.drawImage(text2.getImage(), (pwidth/4)*3, pheight/3, pwidth/4, pheight/4, null);
                if (intro >= 3) {
                    g2.drawImage(intro3.getImage(), pwidth/20, pheight/2 + pheight/10, (pwidth/4)*3, (pheight/2) - pheight/7, null);
                    if (intro == 3) g2.drawImage(text3.getImage(), (pwidth/4)*2, (pheight/4)*2, pwidth/4, pheight/4, null);
                }
            }

            
        }
        if (intro == 5) g2.drawImage(intro5.getImage(), 0, 0, pwidth, pheight, null);
        if (intro == 4) g2.drawImage(intro4.getImage(), 0, 0, pwidth, pheight, null);
        if (intro == 6) g2.drawImage(intro6.getImage(), 0, 0, pwidth, pheight, null);
        if (intro == 7) g2.drawImage(intro7.getImage(), 0, 0, pwidth, pheight, null);
        if (intro == 8) g2.drawImage(intro8.getImage(), 0, 0, pwidth, pheight, null);
    }

    public void drawSection1(Graphics2D g2) {
        if (time <900 || time > 980 && time < 1500)g2.drawImage(sword1.getImage(), -pwidth/10, 0, pwidth, pheight, null);
        if (time >900 && time < 980) {
            g2.drawImage(sword2.getImage(), 0, 0, pwidth, pheight, null);
        }
        if (time > 1500 && time < 1700) g2.drawImage(sword3.getImage(), 0,0, pwidth, pheight, null); 
    }

    public void drawSection3(Graphics2D g2) {
        g2.drawImage(pillar.getImage(), 0,0, pwidth, pheight, null);
        g2.drawImage(jump.getImage(), 0, 0, pwidth,pheight, null);
    }

    public void drawScene1(Graphics2D g2) {
        if (time > 1690 && section == 2) g2.drawImage(scene1.getImage(), 0,0, pwidth,pheight,null);
    }

    public void drawSection4(Graphics2D g2) {
        g2.drawImage(run.getImage(), pwidth/3,0, pwidth/2, pheight, null);
    }


    public class DrawPanel extends JPanel {
        DrawPanel() {
            this.setBackground(Color.LIGHT_GRAY);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            
            

            g2.setPaint(Color.BLACK);
            g2.drawString(String.valueOf(time), 10, 10);
            g2.drawString(String.valueOf(score), 10, 20);
            g2.setPaint(Color.RED);
            if(section == 1 || section == 3 || section == 4) {
                drawSection1(g2);   
                if(intro >3) {
                    for (Receiver r: rBlocks) {
                        g2.fillRect(r.x,r.y,r.width,r.height);
                    }
                }
                g2.setPaint(Color.BLUE);

                drawArrayBlocks(tBlocks1, g2);
                drawArrayBlocks(tBlocks2, g2);
                drawArrayBlocks(allBlocks, g2);
            }

            if (section == 3) drawSection3(g2);
            if (section == 4) drawSection4(g2);

            if (intro <= 8) drawIntro(g2);
            if ((time/10) < 45) drawTransition(g2);
            
            drawScene1(g2);
            
            
        }
        
    }

    

    public void drawTransition(Graphics2D g2) {
        if (intro == 9) g2.drawImage(intro9.getImage(), 0, 0, pwidth, pheight, null);
        if (time/10 >= 36) g2.drawImage(transition.getImage(), 0, 0, pwidth, pheight, null);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            mainMusic.setTime(27000);
        }
        if (e.getKeyCode() == KeyEvent.VK_B) {
            if (firstTimeOnly[0]) {
                intro ++;
                if(intro > 8) {
                    mainMusic.playAudio();
                    firstTimeOnly[0] = false;
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) System.exit(0);
        
        checkKeyPress(e, tBlocks1);
        checkKeyPress(e, tBlocks2);
        checkKeyPress(e, allBlocks);
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        //if (e.getKeyCode == VK_A || e.getKeyCode() == VK_B) 
        
    }
}
