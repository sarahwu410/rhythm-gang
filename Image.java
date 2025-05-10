import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Image {
    private BufferedImage img;
    private String filepath;
	private int limit;
	ArrayList<BufferedImage> images;
	int counter;
	private boolean loopable;


    Image(String filepath) {
		images = new ArrayList<BufferedImage>();
        this.filepath = filepath;
        img = loadImage(filepath);

    }

	Image(String filepath, int limit) {
		images = new ArrayList<BufferedImage>();
		this.filepath = filepath;
		for (int i = 1; i <= limit; i++) {
			images.add(loadImage(filepath + " (" + i + ").png" ));
		}
		this.filepath = filepath;
		this.limit = limit;
		this.counter = 0;
	}

	Image(String filepath, int limit, boolean loop) {
		images = new ArrayList<BufferedImage>();
		for (int i = 1; i <= limit; i++) {
			images.add(loadImage(filepath + " (" + i+ ").png" ));
		}
		this.filepath = filepath;
		this.limit = limit;
		this.loopable = loop;
		this.counter = 0;
	}
	

    public BufferedImage getImage() { 
		if (images.isEmpty())return img;
		else return images.get(counter);
	}
    
	public void updateCount() {
		System.out.println(counter);
		if (counter < limit-1) counter++;
		else if (loopable) counter = 0;
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




}
