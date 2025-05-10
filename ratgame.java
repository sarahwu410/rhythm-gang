/*
 * Wilson Wei
 * April 15, 2025 - May 11, 2025
 * A test for animation and audio
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;
public class ratgame implements KeyListener{
	
	
	public static void main(String[] args) {
		new ratgame();
	}
	
	DrawPanel panel;
	Audio backgroundmusic;
	ArrayList<Image> ratspin = new ArrayList<Image>();
	int ratspincount = 0;
	int ratdancecount = 0;
	boolean loading = true;
	int timercounter = 0;
	ArrayList<Block> blocks = new ArrayList<Block>();
	ArrayList<Image> ratdance = new ArrayList<Image>();
	int miliseconds;
	int score = 0;
	Timer timer;
	boolean timerstopped = false;
	boolean hit = false;
	boolean miss = false;
	Font font = new Font("Monospaced",50,50);
	ratgame() {
		JFrame frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		
	
		backgroundmusic = new Audio("res/Audio/Chess Type Beat Slowed.wav");
		
		loadAll("res/ratdance/ratspin (",21,ratspin);
		loadAll("res/ratdance/ratdance (", 29, ratdance);
		frame.addKeyListener(this);
		
		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				if (!loading) {
					timercounter++;
					
					if (timercounter%50 == 0) miss = false;
					
					switch (timercounter/10) {
					case 0: {
						break;
					}
					
					case 17: {
						if (timercounter == 170) {
							if (!hit)miss = true;
						}
						break;
					}
					case 19: {
							
						}
						
					
					
					}
					for (Block b: blocks) {
						b.checkAppear(miliseconds);
						if (miliseconds % 1 == 0) b.moveBlock();
						b.checkEnd();
						if (b.appear + 501 == miliseconds) hit = false;
					}
				}
				
				
				
			}
			
		});
		
		
		
		
		panel = new DrawPanel();
		
		frame.add(panel);
		frame.setVisible(true);
		
		blocks.add(new Block("left", 1000));
		blocks.add(new Block("Right", 47000));

		timer.start();
		GameLoop();
	}


	public void GameLoop() {
		while (true) {
			if (loading) {
				if (ratspincount == ratspin.size() -1) ratspincount = 0;
				else ratspincount++;
			}
			if (!loading) {
				miliseconds = backgroundmusic.getTime();
				if (ratdancecount == ratdance.size() -1) ratdancecount = 0;
				else if (miliseconds%45 == 0)ratdancecount++;
			}
			panel.repaint();
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void loadAll(String filepath, int amount, ArrayList<Image> array) {
		for (int i = 1; i <= amount; i++ ) {
			array.add(new Image(filepath + String.valueOf(i) + ").png"));
		}
	}
	
	public class Block {
		int x,y,v,width,height;
		int endx,endy;
		String side;
		int appear;
		
		Block(String side, int time) {
			this.side = side;
			appear = time;
			if (side.equals("Left")) {
				
				width = 0;
				height = 0;
				x = 0-width;
				y = (panel.getHeight()/20)*8;
				endx = ((panel.getWidth()/20)*8) + width/2;
				v = 0;
			}
			
			if (side.equals("Right")) {
				width = 0;
				height = 0;
				x = panel.getWidth() + width;
				y = (panel.getHeight()/20)*8;
				endx = ((panel.getWidth()/20)*11) - width/2;
				v= 0;
			}
		}
		
		public void checkAppear(int d) {
			if (appear < d) {
				if (side.equals("Left")) {
					v = 4;
					width = panel.getWidth()/30;
					height = panel.getHeight()/10;
				}
				
				if (side.equals("Right")) {
					v = -(endx/4);
					width = panel.getWidth()/30;
					height = panel.getHeight()/10;
				}
				
			}
		}
		
		public void moveBlock() {
			x += v;
		}
		
		public void checkEnd() {
			if (x >= endx || hit) {
				width = 0;
				height = 0;
				
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
			
			if (loading) {
				g2.setFont(font);
				g2.setPaint(Color.WHITE);
				g2.drawImage(ratspin.get(ratspincount).getImage(), 0, 0, panel.getWidth(), panel.getHeight(), null);
				g2.drawString("Press A to Play", (panel.getWidth()/20)*7, (panel.getHeight()/10)*8);
			}
			
			if (!loading) {
				g2.setPaint(Color.WHITE);
				g2.setFont(font);
				g2.drawImage(ratdance.get(ratdancecount).getImage(), 0, 0, panel.getWidth(), panel.getHeight(), null);
				//else g2.drawImage(ratspin.get(5).getImage(), 0, 0, panel.getWidth(), panel.getHeight(), null);
				g2.drawString(String.valueOf(miliseconds), 50, 50);
				g2.drawLine((panel.getWidth()/10)*4, 0, (panel.getWidth()/10)*4, panel.getHeight() );
				g2.drawLine((panel.getWidth()/20)*13, 0, (panel.getWidth()/20)*13, panel.getHeight() );
				g2.drawLine(0, (panel.getHeight()/20)*10, panel.getWidth(), (panel.getHeight()/20)*10);
				
				for (Block b: blocks) {
					g2.fillRect(b.x, b.y, b.width, b.height);
				}
				g2.drawString(String.valueOf(timercounter), 50, 100);
				if (hit) g2.drawString("Perfect", (panel.getWidth()/10)*4, (panel.getHeight()/20)*10);
			}
			
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			backgroundmusic.playAudio();
			loading = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			System.exit(0);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (!timerstopped) {
				backgroundmusic.stopAudio();
				timer.stop();
				timerstopped = true;
			}
			else {
				backgroundmusic.playAudio();
				timer.restart();
				timerstopped = false;
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {}

}
