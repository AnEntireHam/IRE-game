package IRE.Audio;

import IRE.Tools.Tools;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioStream extends Thread {

    private static final int BUFFER_SIZE = 256;
    private final String path;
    private boolean play;

    public AudioStream(String path) {

        this.path = "sounds/" + path + ".wav";
        this.start();

    }

    public void play() {
        this.play = true;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {

        while (true) {

            if (this.play) {
                try {

                    File audioFile = new File(path);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                    AudioFormat format = audioStream.getFormat();

                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

                    SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

                    audioLine.open(format);

                    audioLine.start();

                    //System.out.println("Playback started.");

                    byte[] bytesBuffer = new byte[BUFFER_SIZE];
                    int bytesRead;

                    while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                        audioLine.write(bytesBuffer, 0, bytesRead);
                    }

                    audioLine.drain();
                    audioLine.close();
                    audioStream.close();

                    this.play = false;

                    //System.out.println("Playback completed.");

                } catch (UnsupportedAudioFileException ex) {
                    System.out.println("The specified audio file is not supported.");
                    //ex.printStackTrace();
                    this.play = false;
                } catch (LineUnavailableException ex) {
                    System.out.println("IRE.IREModule.IRE.Audio line for playing back is unavailable.");
                    //ex.printStackTrace();
                    this.play = false;
                } catch (IOException ex) {
                    System.out.println("Error playing the audio file.");
                    //ex.printStackTrace();
                    this.play = false;
                }
            } else {
                Tools.sleep(5);
            }

        }
    }


}
