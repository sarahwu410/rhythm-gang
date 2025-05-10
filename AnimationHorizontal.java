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
        super(null, anchorX, anchorY, spriteFrames, spriteWidth, spriteHeight);
    }

    /**
	 * Draws a specific fram
	 * loop through the given frames
	 * @param g Graphics object for drawing
	 * @param frame 
	 */
	public void drawFrame(Graphics2D g, int frame) {
		if (frame == spriteFrames) frame = 0;
		g.drawImage(img, drawX, drawY, widthDesired, heightDesired, anchorX  + (spriteWidth * frame),
				anchorY, anchorX + (spriteWidth * frame) + spriteWidth,
				anchorY + spriteHeight, null);
	}
}
