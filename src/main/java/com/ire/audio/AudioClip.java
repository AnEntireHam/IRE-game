package com.ire.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AudioClip implements Runnable, LineListener{

    private final String path;
    private boolean play;
    private boolean playCompleted;
    private boolean end;

    public AudioClip(String path) {

        this.path = "sounds/" + path + ".wav";
        this.play = false;
        this.playCompleted = false;
        this.end = false;

        Thread thread = new Thread(this);
        thread.start();
    }

    public void play() {
        this.play = true;
        this.playCompleted = false;
    }

    public void end() {
        this.end = true;
    }

    // TODO: Figure out if the supposed latency reduction is worth memory/CPU use.
    @Override
    public void run() {

        while (!end) {
            if (play) {

                playClip();

            } else {

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void playClip() {

        try {
            ClassLoader loader = this.getClass().getClassLoader();
            InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(
                    loader.getResourceAsStream(path)));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

            Clip clip = AudioSystem.getClip();
            clip.addLineListener(this);
            clip.open(audioInputStream);
            clip.start();

            while (!playCompleted) {
                Thread.sleep(5);
            }

            clip.close();
            this.play = false;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.out.println("Error playing audio file");
            e.printStackTrace();
            this.play = false;
        }
    }

    @Override
    public void update(LineEvent event) {

        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP) {
            playCompleted = true;
        }
    }
}
