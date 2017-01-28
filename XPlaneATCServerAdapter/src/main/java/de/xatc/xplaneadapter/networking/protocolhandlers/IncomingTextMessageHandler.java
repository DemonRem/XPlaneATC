/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.networking.protocolhandlers;

import de.xatc.commons.networkpackets.client.TextMessagePacket;
import de.xatc.xplaneadapter.config.AdapterConfig;

/**
 *
 * @author Mirko
 */
public class IncomingTextMessageHandler {
    
    
    
    public static void handleIncomingTextMessage(Object msg) {
        
        System.out.println("handleIncomingTextMessage");
        
        TextMessagePacket p = (TextMessagePacket) msg;
        
        System.out.println("TextMessage is: " + p.getMessage());
        
        String htmlMessage = "<font color='gray'>[" + p.getFrequency() + "]</font><font color='black'>" + p.getMessage() + "</font>";
       
        AdapterConfig.getMainFrame().getMainPanel().addTextMessageItem(htmlMessage);
        
    }
    
    
}
