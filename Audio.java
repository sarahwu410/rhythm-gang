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
    
    public void stopAudio() {
        clip.stop();
    }

    public int getTime() {
        double test;
        long mircoseconds = clip.getMicrosecondPosition()/1000;
        test = (double)mircoseconds;
        int miliseconds = (int) test;
        return miliseconds;
    }
    
}
