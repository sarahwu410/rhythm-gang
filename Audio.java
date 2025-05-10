/*
 * Wilson Wei
 * April 15, 2025 - May 11, 2025
 * Takes an audio file and plays it, stops it, or gets the current time of it
 */


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
    Clip clip;
    AudioInputStream audioinput;
    File file;
    boolean playing = false;
    Audio(String file) {
        this.file = new File(file);
        try {
            audioinput = AudioSystem.getAudioInputStream(this.file);
            clip = AudioSystem.getClip();
            clip.open(audioinput);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }	
    }
    
    /**
     * starts playing the audio file
     * if the audio file is already playing and this is called, restart from beginning
     */
    public void playAudio() {
        if(!playing) {
            clip.start();
            playing = true;
        }
        if (playing) {
            clip.setFramePosition(0);
            clip.start();
            playing = false;
        }
        
    }
    
    /**
     * stops playing the audio file
     */
    public void stopAudio() {
        clip.stop();
    }

    /**
     * get the current time of the audio
     * @return the current time of the audio in centiseconds
     */
    public int getTime() {
        double test;
        long mircoseconds = clip.getMicrosecondPosition()/10000;
        test = (double)mircoseconds;
        int miliseconds = (int) test;
        return miliseconds;
    }
    
}
