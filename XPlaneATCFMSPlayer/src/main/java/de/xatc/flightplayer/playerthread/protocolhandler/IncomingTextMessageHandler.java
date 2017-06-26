/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.playerthread.protocolhandler;

import de.xatc.commons.networkpackets.pilot.TextMessagePacket;
import de.xatc.flightplayer.playerthread.PlayerControlThread;



/**
 *
 * @author Mirko
 */
public class IncomingTextMessageHandler {
    
    
    
    public static void handleIncomingTextMessage(Object msg,PlayerControlThread controlThread) {
        
        System.out.println("handleIncomingTextMessage");
        
        TextMessagePacket p = (TextMessagePacket) msg;
        
        System.out.println("TextMessage is: " + p.getMessage());
        
        String htmlMessage = "<font color='gray'>[" + p.getFrequency() + "]</font><font color='black'>" + p.getMessage() + "</font>";
       
        
    }
    
    
}
