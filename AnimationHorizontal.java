/*
 * Eleora Jacob
 * April 28, 2025
 * A child of Animation
 */

import java.awt.Image;
import java.awt.Graphics2D;

public class AnimationHorizontal extends Animation {

    /**
     * Constructor
     */
    AnimationHorizontal (Image img, int anchorX, int anchorY, int spriteFrames, int spriteWidth, int spriteHeight) {
        super(img, anchorX, anchorY, spriteFrames, spriteWidth, spriteHeight);
    }

    /**
	 * Draws the image by taking the graphics object, this method specifically will
	 * loop through the given frames
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
	 * Draws the first frame
	 */
	@Override
	public void drawFirstFrame(Graphics2D g) {
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX, anchorY, anchorX + spriteWidth,
				anchorY + spriteHeight, null);
	}
}
