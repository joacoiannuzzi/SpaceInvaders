package edu.austral.prog2_2018c2;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String s) {

        try {
            AudioInputStream ais =
                    AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));

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

    public void loop() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.loop(100);
    }

    public void stop() {
        if (clip.isRunning()) clip.stop();
    }

    public void close() {
        stop();
        clip.close();
    }
}
