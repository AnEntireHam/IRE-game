package com.ire.audio;

import com.ire.tools.PrintControl;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Objects;

public class AudioClip implements Runnable, LineListener, Serializable {

    private static final long serialVersionUID = 4182021L;
    private final String path;
    private boolean play;
    private boolean playCompleted;
    private boolean end;
    private transient Thread thread;

    public AudioClip(String path) {

        this.path = "sounds/" + path + ".wav";
        this.play = false;
        this.playCompleted = false;
        this.end = false;

        this.thread = new Thread(this);
        thread.start();
    }

    public void play() {
        this.play = true;
        this.playCompleted = false;
        if (thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void end() {
        this.end = true;
    }

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
            if (PrintControl.isBotClient()) {
                System.out.println("SFX" + path);
                this.play = false;
                return;
            }
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
