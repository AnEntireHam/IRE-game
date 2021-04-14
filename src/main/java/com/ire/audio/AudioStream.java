package com.ire.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Objects;

public class AudioStream implements Runnable, Serializable {

    private static final int BUFFER_SIZE = 256;

    private final String path;
    private boolean play;
    private boolean end;
    private transient Thread thread;

    public AudioStream(String path) {

        this.path = "sounds/" + path + ".wav";
        this.play = false;
        this.end = false;

        this.thread = new Thread(this);
        thread.start();
    }

    public void play() {
        this.play = true;
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
            if (this.play) {

                    playAudioLine();

            } else {

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void playAudioLine() {

        try {
            ClassLoader loader = this.getClass().getClassLoader();
            InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(
                    loader.getResourceAsStream(path)));

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            AudioFormat format = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

            audioLine.open(format);
            audioLine.start();

            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = audioInputStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            audioInputStream.close();

            this.play = false;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing audio file");
            e.printStackTrace();
            this.play = false;
        }
    }
}
