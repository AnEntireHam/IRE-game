package com.ire.audio;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Objects;

public class AudioStream implements Runnable {

    private static final int BUFFER_SIZE = 256;

    private final String path;
    private AudioFormat format;
    private SourceDataLine audioLine;
    private AudioInputStream audioInputStream;

    private boolean play;
    private boolean end;

    public AudioStream(String path) {

        this.path = "sounds/" + path + ".wav";
        this.play = false;
        this.end = false;

        Thread thread = new Thread(this);
        thread.start();
    }

    public void play() {
        this.play = true;
    }

    public void end() {
        this.end = true;
    }

    @Override
    public void run() {

        initializeInputs();

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

        } catch (IOException | LineUnavailableException e) {
            System.out.println("Error playing audio file.");
            e.printStackTrace();
        }
    }

    private void initializeInputs() {

        try {

            ClassLoader loader = this.getClass().getClassLoader();
            InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(
                    loader.getResourceAsStream(path)));

            this.audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            this.format = audioInputStream.getFormat();
            this.audioLine = (SourceDataLine) AudioSystem.getLine(info);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("Error creating audio file.");
            e.printStackTrace();
        }
    }
}
