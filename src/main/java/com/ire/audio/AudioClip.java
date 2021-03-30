package com.ire.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AudioClip implements Runnable, LineListener{

    private static final int BUFFER_SIZE = 256;

    private int startTime;
    private int endTime;

    private final String path;
    private boolean play = false;
    private boolean playCompleted = false;
    private boolean end = false;

    public AudioClip(String path) {

        this.path = "sounds/" + path + ".wav";
        this.end = false;
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {

        while (!end) {
            if (play) {
                try {

                    ClassLoader loader = this.getClass().getClassLoader();
                    InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(loader.getResourceAsStream(path)));
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

                } catch (InterruptedException e) {
                    System.out.println("Error playing the audio file.");
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

    public void play() {
        this.play = true;
        this.playCompleted = false;
    }

    public void end() {
        this.end = true;
    }

    @Override
    public void update(LineEvent event) {

        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP) {
            playCompleted = true;
        }
    }
}
