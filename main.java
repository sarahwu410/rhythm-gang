import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class main implements KeyListener {
	
	BufferedImage title;
	BufferedImage play;
	BufferedImage instruction;
	BufferedImage quit;
	TitlePanel titlepanel;
	BufferedImage arrow;
	BufferedImage play2;
	BufferedImage instruction2;
	BufferedImage quit2;
	BufferedImage playbutton;
	BufferedImage instructionbutton;
	BufferedImage quitbutton;
	BufferedImage ratpanel;
	CardLayout cardlayout;
	BufferedImage mp;
	BufferedImage hp;
	BufferedImage bp;
	JPanel mainpanel;
	mediumPanel mediumpanel;
	hardPanel hardpanel;
	bossPanel bosspanel;
	String currentpanel = "title";
	ArrayList<BufferedImage> sa = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> ta = new ArrayList<BufferedImage>();
	boolean scroll = false;
	Timer timer;
	int tier = 1;
	int sc = 0;
	int testcount = 0;
	int tc = 0;
	boolean scrollpaused = false;
	easyPanel easypanel;
	testpanel testpanel;
	public static void main(String[] args) {
		new main();
	}
	
	main() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		title = loadImage("res/title.png");
		play = loadImage("res/play.png");
		instruction = loadImage("res/instruction.png");
		quit = loadImage("res/quit.png");
		arrow = loadImage("res/arrow.png");
		play2 = loadImage("res/play2.png");
		instruction2 = loadImage("res/instruction2.png");
		quit2 = loadImage("res/quit2.png");
		ratpanel = loadImage("res/ratpanel.png");
		mp = loadImage("res/mediumpanel.png");
		hp = loadImage("res/hardpanel.png");
		bp = loadImage("res/bosspanel.png");
		sa.add(loadImage("res/scroll (1).png"));
		sa.add(loadImage("res/scroll (2).png"));
		sa.add(loadImage("res/scroll (3).png"));
		sa.add(loadImage("res/scroll (4).png"));
		sa.add(loadImage("res/scroll (5).png"));
		sa.add(loadImage("res/scroll (6).png"));
		sa.add(loadImage("res/scroll (7).png"));
		sa.add(loadImage("res/scroll (8).png"));
		sa.add(loadImage("res/scroll (9).png"));
		sa.add(loadImage("res/scroll (10).png"));
		sa.add(loadImage("res/scroll (11).png"));
		sa.add(loadImage("res/scroll (12).png"));
		sa.add(loadImage("res/scroll (13).png"));
		sa.add(loadImage("res/scroll (14).png"));
		sa.add(loadImage("res/scroll (15).png"));
		sa.add(loadImage("res/scroll (16).png"));
		sa.add(loadImage("res/scroll (17).png"));
		sa.add(loadImage("res/scroll (18).png"));
		sa.add(loadImage("res/scroll (19).png"));
		sa.add(loadImage("res/scroll (20).png"));
		sa.add(loadImage("res/scroll (21).png"));
		sa.add(loadImage("res/scroll (22).png"));
		sa.add(loadImage("res/scroll (23).png"));
		ta.add(loadImage("res/test (1).png"));
		ta.add(loadImage("res/test (2).png"));
		ta.add(loadImage("res/test (3).png"));
		ta.add(loadImage("res/test (4).png"));
		ta.add(loadImage("res/test (5).png"));
		ta.add(loadImage("res/test (6).png"));
		ta.add(loadImage("res/test (7).png"));
		ta.add(loadImage("res/test (8).png"));
		ta.add(loadImage("res/test (9).png"));
		ta.add(loadImage("res/test (10).png"));
		ta.add(loadImage("res/test (11).png"));
		ta.add(loadImage("res/test (12).png"));
		ta.add(loadImage("res/test (13).png"));

		cardlayout = new CardLayout();
		mainpanel = new JPanel(cardlayout);
		mediumpanel = new mediumPanel();
		hardpanel = new hardPanel();
		bosspanel = new bossPanel();
		testpanel = new testpanel();
		titlepanel = new TitlePanel();
		easypanel = new easyPanel();
		
		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tc++;
				if (testcount == 12) testcount = 0;
				else if (tc%8==0)testcount++;
				if (scroll) {
					if (tc%10 == 0 && !scrollpaused) sc++;
					if (sc == 11) scrollpaused = true;
					if (sc == 23) {
						scrollpaused = false;
						sc = 0;
						scroll = false;
					}
				}
				frame.repaint();
			}
			
		});
		
	
		frame.addKeyListener(this);
		
		mainpanel.add(titlepanel, "title");
		mainpanel.add(testpanel, "test");
		mainpanel.add(easypanel, "easy");
		mainpanel.add(mediumpanel, "medium");
		mainpanel.add(hardpanel, "hard");
		mainpanel.add(bosspanel, "boss");
		
		frame.add(mainpanel);
		frame.setVisible(true);
		timer.start();
	}
	
	static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null, "An image failed to load: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
		}
		//DEBUG
		//if (img == null) System.out.println("null");
		//else System.out.printf("w=%d, h=%d%n",img.getWidth(), img.getHeight());
		return img;
	}
	public class mediumPanel extends JPanel {
		
		mediumPanel() {
			
		}
		
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g;
			
			g2.drawImage(mp, 0, 0, titlepanel.getWidth(), titlepanel.getHeight(), null);
			
		}
	}

	public class testpanel extends JPanel{

		testpanel() {
			this.setBackground(Color.BLACK);
		}
	
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
		
			g2.drawImage(ta.get(testcount),0,0,mainpanel.getWidth(),mainpanel.getHeight(),null);
	
		}
	}
	public class hardPanel extends JPanel {
			
			hardPanel() {
				
			}
			
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics2D g2 = (Graphics2D)g;
				
				g2.drawImage(hp, 0, 0, titlepanel.getWidth(), titlepanel.getHeight(), null);
				
			}
		}
	public class bossPanel extends JPanel {
			
			bossPanel() {
				
			}
			
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics2D g2 = (Graphics2D)g;
				
				g2.drawImage(bp, 0, 0, titlepanel.getWidth(), titlepanel.getHeight(), null);
				
			}
		}
	public class easyPanel extends JPanel {
		
		easyPanel() {
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g;
			
			g2.drawImage(ratpanel, 0, 0, titlepanel.getWidth(), titlepanel.getHeight(), null);
			
		}
	}

	
	public class TitlePanel extends JPanel {
		
		TitlePanel() {
			this.setBackground(Color.WHITE);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g;
			
			playbutton = play;
			instructionbutton = instruction;
			quitbutton = quit;
			
			switch (tier) {
			case 1 -> playbutton = play2;
			case 2 -> instructionbutton = instruction2;
			case 3 -> quitbutton = quit2;
			}
			
			g2.drawImage(title, title.getWidth()/20, -titlepanel.getHeight()/10, titlepanel.getWidth(), titlepanel.getHeight(), null);
			g2.drawImage(playbutton, titlepanel.getWidth()/2 - titlepanel.getWidth()/6, (titlepanel.getHeight()/6)*2, titlepanel.getWidth()/3,titlepanel.getHeight()/3,null);
			g2.drawImage(instructionbutton, titlepanel.getWidth()/2 - titlepanel.getWidth()/6, (titlepanel.getHeight()/6)*3, titlepanel.getWidth()/3,titlepanel.getHeight()/3,null);
			g2.drawImage(quitbutton, titlepanel.getWidth()/2 - titlepanel.getWidth()/6, (titlepanel.getHeight()/6)*4, titlepanel.getWidth()/3,titlepanel.getHeight()/3,null);
			g2.drawImage(arrow, titlepanel.getWidth()/3 - titlepanel.getWidth()/12, (titlepanel.getHeight()/6)*(tier+1), titlepanel.getWidth()/5,titlepanel.getHeight()/3,null);
			if (scroll)g2.drawImage(sa.get(sc), 0, 0, titlepanel.getWidth(), titlepanel.getHeight(), null);
		}
		
		
		
		
		
	}


	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (currentpanel.equals("title")) {
			if (e.getKeyCode() == KeyEvent.VK_S) {
				if (tier <= 3) tier++;
				if (tier > 3) tier = 1;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_W) {
				if (tier > 1) tier--;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_K) {
			if (currentpanel.equals("easy")) {
				mainpanel.setVisible(false);
				new ratgame();
			}

			if (currentpanel.equals("test")) {
				mainpanel.setVisible(false);
				new testYourBlocksHERE();
			}
			if (tier == 3) System.exit(0);
			if (tier == 2) scroll = true;
			if (tier == 1 && currentpanel.equals("title")) {
				currentpanel = "test";
				cardlayout.next(mainpanel);
				
			}
			
			
		}
		
		if (e.getKeyCode() == KeyEvent.VK_L) {
			if (scroll) {
				scrollpaused = false;
				if (sc < 24) sc++;
			}
			
			if (currentpanel.equals("test") || currentpanel.equals("easy") || currentpanel.equals("medium") || currentpanel.equals("hard") || currentpanel.equals("boss")) {
				currentpanel = "title";
				cardlayout.first(mainpanel);
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_O) {
			if (currentpanel.equals("hard")) {
				
				currentpanel = "boss";
				cardlayout.show(mainpanel,currentpanel);
			}
			
			if (currentpanel.equals("medium")) {
				
				currentpanel = "hard";
				cardlayout.show(mainpanel,currentpanel);
			}
			
			if (currentpanel.equals("easy")) {
				currentpanel = "medium";
				cardlayout.show(mainpanel,currentpanel);
				
			}

			if (currentpanel.equals("test")) {
				currentpanel = "easy";
				cardlayout.show(mainpanel,currentpanel);
			}
			
			
			
			
		
		}
		if (e.getKeyCode() == KeyEvent.VK_U) {
			if (currentpanel.equals("easy")) {
				currentpanel = "test";
				cardlayout.show(mainpanel,currentpanel);
			}
			if (currentpanel.equals("medium")) {
				currentpanel = "easy";
				cardlayout.show(mainpanel,currentpanel);
			}
			
			if (currentpanel.equals("hard")) {
				currentpanel = "medium";
				cardlayout.show(mainpanel,currentpanel);
			}
			
			if (currentpanel.equals("boss")) {
				currentpanel = "hard";
				cardlayout.show(mainpanel,currentpanel);
			}
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
