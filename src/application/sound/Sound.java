package application.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    private Clip swoosh;
    private Clip hit;
    private Clip point;
    private Clip wing;

    private static Sound instance = null;

    private Sound() {
        try {
            AudioInputStream audioInputStreamSwoosh = AudioSystem.getAudioInputStream(getClass().getResource("/application/resources/audio/swoosh.wav"));
            AudioInputStream audioInputStreamHit = AudioSystem.getAudioInputStream(getClass().getResource("/application/resources/audio/hit.wav"));
            AudioInputStream audioInputStreamPoint = AudioSystem.getAudioInputStream(getClass().getResource("/application/resources/audio/point.wav"));
            AudioInputStream audioInputStreamWing = AudioSystem.getAudioInputStream(getClass().getResource("/application/resources/audio/wing.wav"));

            swoosh = AudioSystem.getClip();
            swoosh.open(audioInputStreamSwoosh);

            hit = AudioSystem.getClip();
            hit.open(audioInputStreamHit);

            point = AudioSystem.getClip();
            point.open(audioInputStreamPoint);

            wing = AudioSystem.getClip();
            wing.open(audioInputStreamWing);

        } catch (Exception e) {
            swoosh = null;
            hit = null;
            point = null;
            wing = null;
        }
    }

    public static Sound getInstance() {
        if(instance == null)
            instance = new Sound();
        return instance;
    }

    public void startSwoosh() {
        if(swoosh != null) {
            if(swoosh.getFramePosition() == swoosh.getFrameLength())
                swoosh.setFramePosition(0);
            swoosh.start();
        }
    }

    public void startHit() {
        if(hit != null) {
            if(hit.getFramePosition() == hit.getFrameLength())
                hit.setFramePosition(0);
            hit.start();
        }
    }

    public void startPoint() {
        if(point != null) {
            if(point.getFramePosition() == point.getFrameLength())
                point.setFramePosition(0);
            point.start();
        }
    }

    public void startWing() {
        if(wing != null) {
            if(wing.getFramePosition() == wing.getFrameLength())
                wing.setFramePosition(0);
            wing.start();
        }
    }
}
