/*
 * Eleora Jacob
 * Date Created: May 10, 2025
 * Object that plays animations based on how well the note was hit
 */

import java.awt.Image;
import java.awt.Graphics2D;

public class WordPlayer {
    // Class attributes
    Animation perfect;
    Animation good;
    Animation missed;
    Boolean play;
    int rating; // 0 = perfect, 1 = good, 2 = missed
    
    /**
     * Constructor
     * @param words Spritesheet with the word ratings
     */
    WordPlayer(Image words, int drawX, int drawY) {
        perfect = new AnimationHorizontal(words, 20, 20, 0, 0, 5, 228, 96);
        good = new AnimationHorizontal(words, 20, 20, 0, 96, 5, 228, 96);
        missed = new AnimationHorizontal(words, 20, 20, 0, 192, 5, 228, 96);

        // Initialize attributes
        play = false;
        rating = 0;
    }

    /**
     * Draws whatever animation is necessary
     * @param The Graphics object that will draw the image
     */
    public void play(Graphics2D g) {
        if (!play) return;
        else {
            if (rating == 0) {
                perfect.draw(g);
                if (perfect.frame == perfect.spriteFrames) play = false;
            } else if (rating == 1) {
                good.draw(g);
                if (good.frame == good.spriteFrames) play = false;
            } else if (rating == 2) {
                missed.draw(g);
                if (missed.frame == missed.spriteFrames) play = false;
            } else {
                System.out.println("Invalid rating.");
                return;
            }
        }
    }

    /**
     * Sets the rating that this object should play and it will play when play(Graphics2D g) is next called in the program
     * @param rating    The rating the user acheived (0 = perfect, 1 = good, 2 = missed)
     */
    public void setRating(int rating) {
        this.rating = rating;
        play = true;
    }
}
