package com.ire.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AudioClip implements Runnable, LineListener{

    private final String path;
    private AudioInputStream audioInput;
    private Clip clip;

    private boolean play;
    private boolean completed;
    private boolean end;

    public AudioClip(String path) {

        this.path = "sounds/" + path + ".wav";
        this.play = false;
        this.completed = false;
        this.end = false;

        Thread thread = new Thread(this);
        thread.start();
    }

    public void play() {
        this.play = true;
        this.completed = false;
    }

    public void end() {
        this.end = true;
    }

    // TODO: Figure out if the supposed latency decrease for sfx is worth the memory/CPU usage.
    @Override
    public void run() {

        initializeInputs();

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
            clip.addLineListener(this);
            clip.open(audioInput);
            clip.start();


            while (!completed) {
                Thread.sleep(5);
            }

            clip.close();
            this.play = false;

        } catch (LineUnavailableException | IOException | InterruptedException e) {
            System.out.println("Error playing audio file.");
            e.printStackTrace();
        }
    }

    private void initializeInputs() {

        try {

            ClassLoader loader = this.getClass().getClassLoader();
            InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(loader.getResourceAsStream(path)));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            this.audioInput = AudioSystem.getAudioInputStream(bufferedInputStream);
            this.clip = AudioSystem.getClip();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error creating audio file.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(LineEvent event) {

        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP) {
            completed = true;
        }
    }
}
