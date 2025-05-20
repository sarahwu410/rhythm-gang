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
    Animation ok;
    Animation missed;
    Boolean play;
    int rating; // 1 = perfect, 2 = good, 3 = ok, 4 = missed

    int numPerfects = 0;
    int numGoods = 0;
    int numOks = 0;
    int numMisses = 0;
    int score = 0;
    
    /**
     * Constructor
     * @param wordSpriteSheet Spritesheet with the word ratings
     */
    WordPlayer(Image wordSpriteSheet, int drawX, int drawY) {
        perfect = new AnimationHorizontal(wordSpriteSheet, drawX, drawY, 0, 0, 8, 228, 96);
        good = new AnimationHorizontal(wordSpriteSheet, drawX, drawY, 0, 96, 8, 228, 96);
        ok = new AnimationHorizontal(wordSpriteSheet, drawX, drawY, 0, 192, 8, 228, 96);
        missed = new AnimationHorizontal(wordSpriteSheet, drawX, drawY, 0, 288, 8, 228, 96);

        // Initialize attributes
        play = false;
        rating = 0;
    }

    /**
     * Draws whatever animation is necessary
     * @param g The Graphics object that will draw the image
     */
    public void play(Graphics2D g, int myAudioTime) {
        if (!play) return;
        else {
            if (rating == 1) {
                this.numPerfects++;
                perfect.draw(g, myAudioTime);
                if (perfect.frame == perfect.spriteFrames) play = false;
            } else if (rating == 2) {
                this.numGoods++;
                good.draw(g, myAudioTime);
                if (good.frame == good.spriteFrames) play = false;
            } else if (rating == 3) {
                this.numOks++;
                ok.draw(g, myAudioTime);
                if (ok.frame == ok.spriteFrames) play = false;
            } else if (rating == 4) {
                this.numMisses++;
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
     * @param rating    The rating the user acheived (1 = perfect, 2 = good, 3 = ok, 4 = missed)
     * @param block the block that is to be rated
     */
    public void setRating(int rating, Block block) {
        if (!block.beenRated) {
            this.rating = rating;
            perfect.reset();
            good.reset();
            ok.reset();
            missed.reset();
            play = true;
        }

        try {
            if (!((TapBlock)block).beenRated) {
                block.beenRated = true;
            }
        } catch (Exception z) {
            // do nothing
        }

        try {
            if (!((SpamBlock)block).beenRated) {
                block.beenRated = true;
            }
        } catch (Exception z) {
            // do nothing
        }
    }

    /**
     * calculates the number score up to 1000000 based on number of ratingd the player gets
     * @param perfects number of perfects
     * @param goods number of goods
     * @param oks number of oks
     * @param misses number of misses
     * @return the calculated score
     */
    public int calculateScore() {
        return score = (int)(1000000*((this.numPerfects+(this.numGoods*0.7)+(this.numOks*0.3))/(this.numPerfects+this.numGoods+this.numOks+this.numMisses)));
    }

    /**
     * calculates a letter grade based on the number score gotten
     * @param score the number score that the player got
     * @return the letter grade
     */
    public String calculateGrade() {
        if (this.score == 1000000) return "SS";
        else if (this.score > 900000) return "S";
        else if (this.score > 800000) return "A";
        else if (this.score > 700000) return "B";
        else if (this.score > 500000) return "C";
        else return "D";
    }
}
