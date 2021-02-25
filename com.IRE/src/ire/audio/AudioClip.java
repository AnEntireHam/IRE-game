package ire.audio;

import ire.tools.Tools;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("InfiniteLoopStatement")
public class AudioClip extends Thread {

    //private static final int BUFFER_SIZE = 1024;

    private int startTime;
    private int endTime;
    private int play = 0;

    private AudioInputStream audioStream;
    private Clip audioClip;

    @Override
    public void run() {

        try {

            audioClip.open(audioStream);

            if (this.endTime == 0) {
                this.startTime = 0;
                this.endTime = (int) (audioClip.getMicrosecondLength());
            }

            double multiplier = ((double) audioClip.getMicrosecondLength() / (double) audioClip.getFrameLength());
            //System.out.println(multiplier);
            //System.out.println(this.startTime);
            System.out.println(this.endTime);
            System.out.println((this.endTime - 10 ) / multiplier);
            audioClip.setLoopPoints(Tools.round(this.startTime / multiplier), (int) Math.floor((this.endTime - 10) / multiplier));
            audioClip.setMicrosecondPosition(this.startTime);
            audioClip.start();
            //audioClip.loop(1);
                /*audioClip.setMicrosecondPosition(2287000);
                audioClip.setFramePosition(100857);*/

        } catch (LineUnavailableException ex) {
            System.out.println("IRE.IREModule.IRE.Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }

        while (true) {

            if (this.play == 1) {

                //System.out.println("Now playing... ");
                audioClip.loop(1);

                Tools.sleep((this.endTime - this.startTime) / 1000);

                this.play = 0;

            }
            else if (this.play == 0) {
                Tools.sleep(1);
            } else {
                audioClip.close();
            }
        }
    }

    /*public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            //System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            //System.out.println("Playback completed.");
        }
    }*/

    public AudioClip(String path) {

        /*if (name.equals("hit1")) {
            this.startTime = 110 * 1000;
            this.endTime = 430 * 1200;
        } else if (name.equals("skeletonDeath")) {
            this.startTime = 545 * 1000;
            this.endTime = 2220 * 1000;*/
        //} else {
        this.startTime = 0;
        this.endTime = 0;
        //}

        try {
            File audioFile = new File("sounds/" + path + ".wav");

            this.audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            this.audioClip = (Clip) AudioSystem.getLine(info);

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("IRE.IREModule.IRE.Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }

        this.start();
    }

    public void play() {
        this.play = 1;
    }

    public void close() {
        this.play = -1;
    }
    /*public void playSoundClip(String path, int milliseconds) imple {

        try {

            File audioFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();

            Utilities.sleep(milliseconds);

            audioClip.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("IRE.IREModule.IRE.Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }*/

    //public void playSoundSourceDataLine() {}
}
