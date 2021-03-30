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

                    //File audioFile = new File(path);

                    ClassLoader loader = this.getClass().getClassLoader();
                    InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(loader.getResourceAsStream(path)));

                    //AudioInputStream audioInputStream = new AudioInputStream(inputStream);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

                    audioInputStream = new AudioInputStream(inputStream, audioInputStream.getFormat(), audioInputStream.getFrameLength());

                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();


                    //AudioFormat format = audioIn.getFormat();

                    //DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    //DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

                    //SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

                    //audioLine.open(format);

                    //audioLine.start();

                    //System.out.println("Playback started.");

                    /*byte[] bytesBuffer = new byte[BUFFER_SIZE];
                    int bytesRead;

                    while ((bytesRead = audioIn.read(bytesBuffer)) != -1) {
                        audioLine.write(bytesBuffer, 0, bytesRead);
                    }

                    audioLine.drain();
                    audioLine.close();
                    audioIn.close();*/

                    this.play = false;

                    //System.out.println("Playback completed.");

                } catch (LineUnavailableException ex) {
                    System.out.println("The line for playing back is unavailable.");
                    //ex.printStackTrace();
                    this.play = false;
                } catch (IOException ex) {
                    System.out.println("Error playing the audio file. (Probably a FileNotFound Exception)");
                    ex.printStackTrace();
                    this.play = false;
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
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
