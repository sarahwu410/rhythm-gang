/*
 * Eleora Jacob
 * April 10, 2025
 * An object that needs an image and will then perform "animation", only will work vertical for now
 */

import java.awt.Image;
import java.awt.Graphics2D;

public class Animation {

	int anchorX, anchorY, widthDesired, heightDesired, spriteWidth, spriteHeight, spriteFrames, drawX, drawY,
			frame, interval;
	int newTime, lastTime; // assuming 12fps
	Image img;

	/**
	 * constructor, the object needs to have its image, where it should start, and
	 * how far it can go
	 * @param img	spritesheet for the animation
	 * @param drawX	The x-coordinate where the image should be drawn
	 * @param drawY	The y-coordinate where the image should be drawn
	 * @param anchorX	The starting x-coordinate on the sprite sheet
	 * @param anchorY	The starting y-coordinate on the sprite sheet
	 * @param spriteFrames	The amount of frames in the animation
	 * @param spriteWidth	The width of the sprite
	 * @param spriteHeight	The height of the sprite
	 */
	Animation(Image img, int drawX, int drawY, int anchorX, int anchorY, int spriteFrames, int spriteWidth, int spriteHeight) {
		this.img = img;
		// coordinates
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		// frame info
		this.spriteFrames = spriteFrames;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		// Drawing info
		this.drawX = drawX;
		this.drawY = drawY;
		this.widthDesired = spriteWidth;
		this.heightDesired = spriteHeight;
		// animation stuff
		this.lastTime = 0;
		this.newTime = 0;
		frame = 0;
	}

	/**
	 * Another constructor, this one takes the height or width desired in addition
	 * @param img	spritesheet for the animation
	 * @param drawX	The x-coordinate where the image should be drawn
	 * @param drawY	The y-coordinate where the image should be drawn
	 * @param anchorX	The starting x-coordinate on the sprite sheet
	 * @param anchorY	The starting y-coordinate on the sprite sheet
	 * @param spriteFrames	The amount of frames in the animation
	 * @param spriteWidth	The width of the sprite
	 * @param spriteHeight	The height of the sprite
	 */
	Animation(Image img, int drawX, int drawY, int anchorX, int anchorY, int spriteFrames, int spriteWidth, int spriteHeight,
			int widthDesired, int heightDesired) {
		this.img = img;
		// coordinates
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		// frame info
		this.spriteFrames = spriteFrames;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		// Drawing info
		this.drawX = drawX;
		this.drawY = drawY;
		this.widthDesired = widthDesired;
		this.heightDesired = heightDesired;
		// animation stuff
		this.lastTime = 0;
		this.newTime = 0;
		frame = 0;
	}

	/**
	 * Draws the image by taking the graphics object, this method specifically will
	 * loop through the given frames
	 * @param g	The Graphics2D object that should be used to draw
	 */
	public void draw(Graphics2D g, int audioTime) {
		newTime = audioTime;
		if (frame == spriteFrames) frame = 0;
		g.drawImage(img, drawX, drawY, drawX + widthDesired, drawY + heightDesired, anchorX,
				anchorY + (spriteHeight * frame), anchorX + spriteWidth,
				anchorY + (spriteHeight * frame) + spriteHeight, null);
		if (newTime - lastTime >= 83) {
			lastTime = newTime;
			frame++;
		} else return;

	}
	
	/**
	 * Draws a specific frame
	 * @param g	The Graphics2D object that should be used to draw
	 * @param frame	The number of the frame to be drawn (adjusted, counting from 1)
	 */
	public void drawFrame(Graphics2D g, int frame) {
		if (frame > spriteFrames || frame < 1) frame = 1; // To avoid invalid frame number
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX,
				anchorY + (spriteHeight * (frame - 1)), anchorX + spriteWidth,
				anchorY + (spriteHeight * (frame - 1)) + spriteHeight, null);
	}
	
	/**
	 * Draws the first frame
	 * @param g	The Graphics2D object that should be used to draw
	 */
	public void drawFirstFrame(Graphics2D g) {
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX, anchorY, anchorX + spriteWidth,
				anchorY + spriteHeight, null);
	}
	
	/**
	 * gets frame number
	 * @return	The number of the current frame, not adjusted (counting from zero)
	 */
	public int getFrame() {
		return frame;
	}

	/**
	 * gets width
	 * @return	The width of the image being drawn (not spritesheet width)
	 */
	public int getWidth() {
		return widthDesired;
	}

	 /**
	 * gets height
	 * @return	The height of the image being drawn (not spritesheet height)
	 */
	public int getHeight() {
		return heightDesired;
	}

	/**
	 * gets x-coordinate
	 * @return	The x-coordinate of the image being drawn (not spritesheet coordinate)
	 */
	public int getX() {
		return drawX;
	}

	/**
	 * gets y-coordinate
	 * @return	The x-coordinate of the image being drawn (not spritesheet coordinate)
	 */
	public int getY() {
		return drawY;
	}

	/**
	 * sets x-coordinate
	 * @param newX	The new x-coordinate for the image to be drawn
	 */
	public void setX(int newX) {
		this.drawX = newX;
	}

	/**
	 * gets y-coordinate
	 * @param newY	The new y-coordinate for the image to be drawn
	 */
	public void setY(int newY) {
		this.drawY = newY;
	}

	/**
	 * resets the animation
	 */
	public void reset() {
		frame = 0;
		lastTime = 0;
		newTime = 0;
	}
}
