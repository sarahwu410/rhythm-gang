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
    WordPlayer(Image wordSpriteSheet, int drawX, int drawY) {
        perfect = new AnimationHorizontal(wordSpriteSheet, drawX, drawY, 0, 0, 8, 228, 96);
        good = new AnimationHorizontal(wordSpriteSheet, drawX, drawY, 0, 96, 8, 228, 96);
        missed = new AnimationHorizontal(wordSpriteSheet, drawX, drawY, 0, 192, 8, 228, 96);

        // Initialize attributes
        play = false;
        rating = 0;
    }

    /**
     * Draws whatever animation is necessary
     * @param The Graphics object that will draw the image
     */
    public void play(Graphics2D g, int myAudioTime) {
        if (!play) return;
        else {
            if (rating == 1) {
                perfect.draw(g, myAudioTime);
                if (perfect.frame == perfect.spriteFrames) play = false;
            } else if (rating == 2) {
                good.draw(g, myAudioTime);
                if (good.frame == good.spriteFrames) play = false;
            } else if (rating == 3) {
                missed.draw(g, myAudioTime);
                if (missed.frame == missed.spriteFrames) play = false;
            } else {
                System.out.println("Invalid rating.");
                play = false;
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
        perfect.reset();
        good.reset();
        missed.reset();
        play = true;
    }
}
