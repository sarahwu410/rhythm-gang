/*
 * Eleora Jacob
 * April 10, 2025
 * An object that needs an image and will then perform "animation", only will work vertical for now
 */

import java.awt.Image;
import java.awt.Graphics2D;

public class Animation {

	int anchorX, anchorY, widthDesired, heightDesired, spriteWidth, spriteHeight, spriteFrames, drawX, drawY,
			frame;
	Image img;

	/**
	 * constructor, the object needs to have its image, where it should start, and
	 * how far it can go
	 */
	Animation(Image img, int anchorX, int anchorY, int spriteFrames, int spriteWidth, int spriteHeight) {
		this.img = img;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.spriteFrames = spriteFrames;
		this.spriteWidth = spriteWidth;
		this.widthDesired = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.heightDesired = spriteHeight;
		drawX = 0;
		drawY = 0;
		frame = 0;
	}

	/**
	 * Another constructor, this one takes the height or width desired in addition
	 */
	Animation(Image img, int anchorX, int anchorY, int spriteFrames, int spriteWidth, int spriteHeight,
			int widthDesired, int heightDesired) {
		this.img = img;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.spriteFrames = spriteFrames;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.widthDesired = widthDesired;
		this.heightDesired = heightDesired;
		drawX = 0;
		drawY = 0;
		frame = 0;
	}

	/**
	 * Draws the image by taking the graphics object, this method specifically will
	 * loop through the given frames
	 */
	public void draw(Graphics2D g) {
		if (frame == spriteFrames) frame = 0;
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX,
				anchorY + (spriteHeight * frame), anchorX + spriteWidth,
				anchorY + (spriteHeight * frame) + spriteHeight, null);
		frame++;
	}
	
	/**
	 * Draws a specific frame
	 * loop through the given frames
	 */
	public void drawFrame(Graphics2D g, int frame) {
		if (frame == spriteFrames) frame = 0;
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX,
				anchorY + (spriteHeight * frame), anchorX + spriteWidth,
				anchorY + (spriteHeight * frame) + spriteHeight, null);
	}
	
	/**
	 * Draws the first frame
	 */
	public void drawFirstFrame(Graphics2D g) {
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX, anchorY, anchorX + spriteWidth,
				anchorY + spriteHeight, null);
	}
	
	/**
	 * gets frame number
	 */
	public int getFrame() {
		return frame;
	}

	/**
	 * gets width
	 */
	public int getWidth() {
		return widthDesired;
	}

	 /**
	 * gets height
	 */
	public int getHeight() {
		return heightDesired;
	}

	/**
	 * gets x-coordinate
	 */
	public int getX() {
		return drawX;
	}

	/**
	 * gets y-coordinate
	 */
	public int getY() {
		return drawY;
	}
}
