/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.audio;

/**
 *
 * @author Mirko
 */
public class SiloWatcher extends Thread {

    private boolean running = true;
    Playback playback = new Playback();

    public void run() {

        while (running) {
            System.out.println("AudiSilo size: " + AudioSilo.getAudioList().size());
            if (AudioSilo.getAudioList().size() > 0) {
                playback.startPlayback();
            }
            try {
                this.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
        }

    }

}
