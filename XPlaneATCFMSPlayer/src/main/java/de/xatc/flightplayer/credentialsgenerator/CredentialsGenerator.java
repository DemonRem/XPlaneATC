/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.credentialsgenerator;

import de.xatc.flightplayer.config.PlayerConfig;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author c047
 */
public class CredentialsGenerator {

    private static int counter = 0;
    private static Map<String,String> usedCredentials = new HashMap<>();
    
    
    public static Entry<String,String> getCredentials() {
        
        String username = PlayerConfig.getUsernamePrefix() + counter;
        counter++;
        String password = username;
        Entry<String,String> creds = new AbstractMap.SimpleEntry<String, String>(username,password);
        usedCredentials.put(username, password);
        return creds;
        
    }
    
    



    
}
