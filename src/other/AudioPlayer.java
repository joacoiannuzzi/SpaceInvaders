package other;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String name) {

        try {
            AudioInputStream ais =
                    AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sounds/" + name));

            clip = AudioSystem.getClip();
            clip.open(ais);


        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

    }

    public void play() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop(int count) {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.loop(count);
    }

    public void stop() {
        if (clip.isRunning()) clip.stop();
    }

    public void close() {
        stop();
        clip.close();
    }
}
