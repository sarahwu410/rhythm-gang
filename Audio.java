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
    
    
    public void playAudio() {
        clip.start();
    }
    
    public void stopAudio() {
        clip.stop();
    }

    public int getTime() {
        long mircoseconds = clip.getMicrosecondPosition()/1000;
        int miliseconds = (int)mircoseconds;
        return miliseconds;
    }
    
}
