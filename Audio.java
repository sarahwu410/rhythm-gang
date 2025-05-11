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

    boolean paused;

    /**
     * constructor to do any necessary audio file lines
     * @param file the filepath to the audio file
     */
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
     * sets the time of the audio
     * @param mili the time in milliseconds
     */
    public void setTime(int mili){
        clip.setMicrosecondPosition(mili*1000);
    }
    
    /**
     * plays audio, if its already playing and this is called, it goes back to the beginning
     */
    public void playAudio() {
        if (!paused) clip.setFramePosition(0);
        paused = false;
        clip.start();
    }

    /**
     * stops playing the audio
     */
    public void stopAudio() {
        paused = true;
        clip.stop();
    }

    /**
     * gets the time the audio currently is at
     * @return the time in centiseconds
     */
    public int getTime() { 
        double test;
        long microseconds = clip.getMicrosecondPosition()/10000;
        test = (double)microseconds;
        int miliseconds = (int)test;
        return miliseconds;
    }
    
}
