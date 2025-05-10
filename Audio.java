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

    public void setTime(int mili){
        clip.setMicrosecondPosition(mili*1000);
    }
    
    
    public void playAudio() {
            clip.setFramePosition(0);
            clip.start();
    }

    
    public void stopAudio() {
        clip.stop();
    }

    public int getTime() { 
        double test;
        long microseconds = clip.getMicrosecondPosition()/10000;
        test = (double)microseconds;
        int miliseconds = (int)test;
        return miliseconds;
    }
    
}
