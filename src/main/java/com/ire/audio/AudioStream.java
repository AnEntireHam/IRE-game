package com.ire.audio;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Objects;

public class AudioStream implements Runnable {

    private static final int BUFFER_SIZE = 256;

    private final String path;
    private boolean play;
    private boolean end;

    public AudioStream(String path) {

        this.path = "sounds/" + path + ".wav";
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

    // TODO: figure out if running separate threads for each AudioStream is a poor idea.
    // TODO: Fix audio distortion, switch this back to a stream.
    @Override
    public void run() {

        while (!end) {
            if (this.play) {
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

                    //System.out.println("Playback completed.");

                } catch (UnsupportedAudioFileException e) {
                    System.out.println("This audio format is not supported.");
                    //e.printStackTrace();
                    this.play = false;

                } catch (LineUnavailableException e) {
                    System.out.println("The line for playing back is unavailable.");
                    e.printStackTrace();
                    this.play = false;

                } catch (IOException e) {
                    System.out.println("Error playing the audio file. (Probably a FileNotFound Exception)");
                    e.printStackTrace();
                    this.play = false;
                }
            } else {

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
