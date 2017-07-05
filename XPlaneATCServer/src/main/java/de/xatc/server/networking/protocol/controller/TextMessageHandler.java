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
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko
 */
public class TextMessageHandler {
    
    private static final Logger LOG = Logger.getLogger(TextMessageHandler.class.getName());
    
    public static void handleUserTextMessage(Channel n,Object msg) {
        
        TextMessagePacket p = (TextMessagePacket) msg;
        LOG.info("Incoming TextMessage in Handler");
        XATCUserSession s = SessionManagement.findOverallUserSessionByChannelID(n.id().asLongText());
       
        
        if (s == null) {
            LOG.warn("No session found, returning");
            return;
        }
        
        
        
        MQMessageSender m = ServerConfig.getMessageSenders().get("broadcastTextMessages");
        
        if (m != null) {
            LOG.info("Sending Message..... "  + p.getMessage());
            m.sendObjectMessage(p);
            
        }
        else {
            LOG.warn("Could not find MQ Message Producer to send textmessage to");
        }
        
    }
    
    
}
