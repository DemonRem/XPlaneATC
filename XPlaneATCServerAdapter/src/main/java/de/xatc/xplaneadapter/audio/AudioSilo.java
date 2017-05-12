/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.audio;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author Mirko
 */
public class AudioSilo {
    
    private static List<byte[]> audioList = new ArrayList<>();

    public static List<byte[]> getAudioList() {
        return audioList;
    }

    public static void setAudioList(List<byte[]> audioList) {
        AudioSilo.audioList = audioList;
    }
    
     
    
    
}
