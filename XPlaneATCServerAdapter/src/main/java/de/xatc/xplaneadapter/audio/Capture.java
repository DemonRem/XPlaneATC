/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.audio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Capture {

    protected boolean running;
    ByteArrayOutputStream out;

    public void captureAudio() {
        try {
            final AudioFormat format = AudioTools.getFormat();
            DataLine.Info info = new DataLine.Info(
                    TargetDataLine.class, format);
            final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            Runnable runner = new Runnable() {
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                public void run() {
                    out = new ByteArrayOutputStream();
                  
                    try {
                        boolean inputFloating = true;
                        while (running && inputFloating) {
                           
                            int count = line.read(buffer, 0, buffer.length);

                            line.flush();
                            
                            out.write(buffer, 0, count);
                            
                            if (count > 0) {
                                inputFloating = true;
                               
                            }
                            else {
                                inputFloating = false;
                            }
                            System.out.println("b: " + count);
                        }
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        System.exit(-1);
                    }
                    
                    line.stop();
                    line.close();
                }
            };
            Thread captureThread = new Thread(runner);
            captureThread.start();
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);

        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ByteArrayOutputStream getOut() {
        return out;
    }

    public void setOut(ByteArrayOutputStream out) {
        this.out = out;
    }

    

}
