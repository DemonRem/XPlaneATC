/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.audio;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Mirko
 */
public class Capture implements Runnable {

    private Thread thread;

    private AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
    private TargetDataLine microphone;

    private boolean recording = true;

    public Capture() {
        try {
            microphone = AudioSystem.getTargetDataLine(format);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            
        } catch (LineUnavailableException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void startCapture() {

        this.recording = true;
        thread = new Thread(this);
        thread.setName("Capture");
        thread.start();
    }

    public void stopCapture() {
        this.recording = false;
        thread = null;
    }

    private void shutDown(String message) {
        this.thread = null;
    }

    @Override
    public void run() {

   

            microphone.start();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];

            int bytesRead = 0;

            try {
                while (recording) { // Just so I can test if recording
                    // my mic works...
                    numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                    bytesRead = bytesRead + numBytesRead;
                    System.out.println(bytesRead);
                    out.write(data, 0, numBytesRead);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte audioData[] = out.toByteArray();
            AudioSilo.getAudioList().add(audioData);
            
            microphone.stop();
      

    }
} // End class Capture
