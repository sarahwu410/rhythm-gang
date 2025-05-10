/*
 * Wilson Wei
 * April 15, 2025 - May 11, 2025
 * Takes an image file and loads it
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Image {
    private BufferedImage img;
    private String filepath;
	private int counter;

    Image(String filepath) {
        this.filepath = filepath;
        img = loadImage(filepath);
    }

	Image(String filepath, int counter) {
		this.filepath = filepath;
		img = loadImage(filepath);
		this.counter = counter;
	}

	/**
	 * gets the image
	 * @return the image
	 */
    public BufferedImage getImage(){return img;}
    
	/**
	 * loads the image without having to re-type this code everytime an image is loaded
	 * @param filename the file path for the image
	 * @return the loaded image
	 */
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
