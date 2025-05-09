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


    public BufferedImage getImage(){return img;}
    




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
