/*
 * Eleora Jacob
 * April 28, 2025
 * A child of Animation
 */

import java.awt.Image;
import java.awt.Graphics2D;

public class AnimationHorizontal extends Animation {

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
    AnimationHorizontal (Image img, int drawX, int drawY, int anchorX, int anchorY, int spriteFrames, int spriteWidth, int spriteHeight) {
        super(img, drawX, drawY, anchorX, anchorY, spriteFrames, spriteWidth, spriteHeight);
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
    AnimationHorizontal (Image img, int drawX, int drawY, int anchorX, int anchorY, int spriteFrames, int spriteWidth, int spriteHeight, int widthDesired, int heightDesired) {
        super(img, drawX, drawY, anchorX, anchorY, spriteFrames, spriteWidth, spriteHeight, widthDesired, heightDesired);
    }

    /**
	 * Draws the image by taking the graphics object, this method specifically will
	 * loop through the given frames
	 * @param g	The Graphics2D object that should be used to draw
	 */
	@Override
	public void draw(Graphics2D g) {
		if (frame == spriteFrames) frame = 0;
		g.drawImage(img, drawX, drawY, drawX + widthDesired, drawY + heightDesired, anchorX + (spriteWidth * frame),
				anchorY, anchorX + (spriteWidth * frame) + spriteWidth,
				anchorY + spriteHeight, null);
		frame++;
	}

/**
	 * Draws a specific frame
	 * @param g	The Graphics2D object that should be used to draw
	 * @param frame	The number of the frame to be drawn (adjusted, counting from 1)
	 */
	@Override
	public void drawFrame(Graphics2D g, int frame) {
		if (frame > spriteFrames || frame < 1) frame = 1; // To avoid invalid frame number
		g.drawImage(img, drawX, drawY, drawX + widthDesired, drawY + heightDesired, anchorX + (spriteWidth * (frame - 1)),
				anchorY, anchorX + (spriteWidth * (frame - 1)) + spriteWidth,
				anchorY + spriteHeight, null);
	}

	/**
	 * Draws the first frame
	 * @param g	The Graphics2D object that should be used to draw
	 */
	@Override
	public void drawFirstFrame(Graphics2D g) {
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX, anchorY, anchorX + spriteWidth,
				anchorY + spriteHeight, null);
	}
}
