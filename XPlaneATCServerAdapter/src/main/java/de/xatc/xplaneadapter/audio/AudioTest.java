/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioTest extends Thread {

    private AudioFormat audioFormat = new AudioFormat(8000.0f, 16, 1, true, true);
    private TargetDataLine microphone;
    private AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;
    private DataLine.Info info;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private int numBytesRead;
    private int CHUNK_SIZE = 1024;
    private boolean recording = false;
    private byte audioData[];
    private boolean running;

    public AudioTest() throws LineUnavailableException {

        microphone = AudioSystem.getTargetDataLine(audioFormat);
        info = new DataLine.Info(TargetDataLine.class, audioFormat);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        this.running = true;
    }

    public void record() {

        while (running) {
            if (!this.recording) {
                continue;
            }
            try {
                microphone.open(audioFormat);
                microphone.start();

                byte[] data = new byte[microphone.getBufferSize() / 5];
                int bytesRead = 0;
                try {
                    while (this.recording) { // Just so I can test if recording
                        // my mic works...
                        numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                        bytesRead = bytesRead + numBytesRead;
                        System.out.println(bytesRead);
                        out.write(data, 0, numBytesRead);
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }

                out.flush();
                out.close();
                audioData = out.toByteArray();

                microphone.stop();
                microphone.close();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace(System.err);
            }
            catch  (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public void playAudio(byte[] audioData) throws LineUnavailableException {

        InputStream byteArrayInputStream = new ByteArrayInputStream(
                audioData);
        audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, audioData.length / audioFormat.getFrameSize());
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
        int cnt = 0;
        byte tempBuffer[] = new byte[10000];
        try {
            while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                if (cnt > 0) {
                    // Write data to the internal buffer of
                    // the data line where it will be
                    // delivered to the speaker.
                    sourceDataLine.write(tempBuffer, 0, cnt);
                }// end if
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Block and wait for internal buffer of the
        // data line to empty.
        sourceDataLine.drain();
        sourceDataLine.close();

    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    public TargetDataLine getMicrophone() {
        return microphone;
    }

    public void setMicrophone(TargetDataLine microphone) {
        this.microphone = microphone;
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    public void setAudioInputStream(AudioInputStream audioInputStream) {
        this.audioInputStream = audioInputStream;
    }

    public SourceDataLine getSourceDataLine() {
        return sourceDataLine;
    }

    public void setSourceDataLine(SourceDataLine sourceDataLine) {
        this.sourceDataLine = sourceDataLine;
    }

    public DataLine.Info getInfo() {
        return info;
    }

    public void setInfo(DataLine.Info info) {
        this.info = info;
    }

    public ByteArrayOutputStream getOut() {
        return out;
    }

    public void setOut(ByteArrayOutputStream out) {
        this.out = out;
    }

    public int getNumBytesRead() {
        return numBytesRead;
    }

    public void setNumBytesRead(int numBytesRead) {
        this.numBytesRead = numBytesRead;
    }

    public int getCHUNK_SIZE() {
        return CHUNK_SIZE;
    }

    public void setCHUNK_SIZE(int CHUNK_SIZE) {
        this.CHUNK_SIZE = CHUNK_SIZE;
    }

    public boolean isRecording() {
        return recording;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    public byte[] getAudioData() {
        return audioData;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    

}
