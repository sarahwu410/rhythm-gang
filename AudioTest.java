import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioTest {
    private AudioInputStream audioInput;
    private AudioFormat format;
    private DataLine.Info info;
    private byte[] buffer;
    private int bytesRead;
    private File file;
    private boolean playing = false;
    // private volatile boolean pauseRequested;
    private long pausedPos = 0; // to save where the audio was before in case it was paused
    private Thread playbackThread;

    AudioTest(String file) {
        this.file = new File(file);
        try {
            // lets you read raw audio bytes sequentially
            audioInput = AudioSystem.getAudioInputStream(this.file); 

            // gets format of audio (sample rate, bit depth, channels, etc.)
            format = audioInput.getFormat(); 

            // prepares info needed to open a SourceDataLine that will play audio in real time
            info = new DataLine.Info(SourceDataLine.class, format); 
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public interface AudioFrameListener {
        // method used to replace timer's actionPerformed()
        void performAction(byte[] frameData, int bytesRead);
    }

    public void start(AudioFrameListener listener) throws Exception {
        new Thread(() -> {
            // opens a SourceDataLine for playback and inside a try block to automatically close the SourceDataLine after
            try (SourceDataLine audioLine = (SourceDataLine)AudioSystem.getLine(info)) {
                audioInput = AudioSystem.getAudioInputStream(file);
                audioInput.skip(pausedPos); // resume previous paused position

                audioLine.open(format); // prepares the SourceDataLine
                audioLine.start(); // starts audio playback

                buffer = new byte[1024]; // read audio in 1024 byte chunks

                // reads from the audio input stream until the end (-1) and each iteration adds the next chunck of audio data to buffer
                while ((bytesRead = audioInput.read(buffer, 0, buffer.length)) != -1) {
                    // sends audio data into speaker (via SourceDataLine)
                    audioLine.write(buffer, 0, bytesRead); // play chunk
                    pausedPos+= bytesRead;

                    // calls performAction() with current audio frame data
                    if (listener != null) {
                        listener.performAction(buffer, bytesRead); // call code in the listener
                    }

                    if (Thread.currentThread().isInterrupted()) {
                        audioLine.stop();
                        return;
                    }
                }

                // ensures all queued audio finishes playing before closing SourceDataLine
                audioLine.drain();
                audioLine.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
 
    public void playAudio(AudioFrameListener listener) {
        if (!playing) {
            playbackThread = new Thread(() -> {
                try {
                    start(listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            playbackThread.start();
            playing = true;
        } else if (playing) {
            pausedPos = 0;
            playbackThread = new Thread(() -> {
                try {
                    start(listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            playbackThread.start();
        }
    }
    
    public void pauseAudio() {
        playing = false;
        if (playbackThread != null) {
            playbackThread.interrupt();
        }
    }

    public int getTimeInMilliseconds() {
        return (int)((pausedPos/(format.getFrameSize()*format.getFrameRate()))*1000);
    }
}
