/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.db.sharedentities.user.XATCUserSession;
import de.xatc.commons.networkpackets.pilot.TextMessagePacket;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.mq.producers.MQMessageSender;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;

/**
 *
 * @author Mirko
 */
public class TextMessageHandler {
    
    
    public static void handleUserTextMessage(Channel n,Object msg) {
        
        TextMessagePacket p = (TextMessagePacket) msg;
        System.out.println("Incoming TextMessage in Handler");
        XATCUserSession s = SessionManagement.findOverallUserSessionByChannelID(n.id().asLongText());
       
        
        if (s == null) {
            System.out.println("No session found, returning");
            return;
        }
        
        
        
        MQMessageSender m = ServerConfig.getMessageSenders().get("broadcastTextMessages");
        
        if (m != null) {
            System.out.println("Sending Message..... "  + p.getMessage());
            m.sendObjectMessage(p);
            
        }
        else {
            System.out.println("Could not find MQ Message Producer to send textmessage to");
        }
        
    }
    
    
}
