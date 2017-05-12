/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.audio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Mirko
 */
public class Playback implements Runnable {

    Thread thread;
    AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
    TargetDataLine microphone;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;

    public void startPlayback() {
        System.out.println("starting new playbackthread");

        thread = new Thread(this);
        thread.setName("Playback");
        thread.start();
    }

    public void stopPlayback() {
        thread = null;
    }

    private void shutDown(String message) {
        System.out.println("Playback shutdown called");
        this.thread = null;

    }

    @Override
    public void run() {

        try {
        byte[] audioData = AudioSilo.getAudioList().get(0);
        AudioSilo.getAudioList().remove(0);
        InputStream byteArrayInputStream = new ByteArrayInputStream(
                    audioData);
            audioInputStream = new AudioInputStream(byteArrayInputStream,format, audioData.length / format.getFrameSize());
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(format);
            sourceDataLine.start();
            int cnt = 0;
            byte tempBuffer[] = new byte[10000];
            try {
                while ((cnt = audioInputStream.read(tempBuffer, 0,tempBuffer.length)) != -1) {
                    if (cnt > 0) {
                        // Write data to the internal buffer of
                        // the data line where it will be
                        // delivered to the speaker.
                        sourceDataLine.write(tempBuffer, 0, cnt);
                    }// end if
                }
                sourceDataLine.drain();
                sourceDataLine.stop();
                sourceDataLine.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch(LineUnavailableException ex) {
            ex.printStackTrace(System.err);
        }
        
    }

} // End class Playback
