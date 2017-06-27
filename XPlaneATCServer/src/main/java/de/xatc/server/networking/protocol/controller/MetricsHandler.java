/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.commons.networkpackets.atc.servercontrol.RequestServerMetrics;
import de.xatc.commons.networkpackets.atc.servercontrol.ServerMetrics;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;

/**
 *
 * @author Mirko
 */
public class MetricsHandler {

    public static void handleMetrics(Channel n, Object msg) {

        System.out.println("Handle Metrics");
        RequestServerMetrics m = (RequestServerMetrics) msg;
        UserRole u = SessionManagement.getATCUserRoleBySessionID(m.getSessionID());

        System.out.println("UserRole is: " + u);

        if (u != UserRole.ADMINISTRATOR) {
            System.out.println("Server Metrics requested of non Admin. Nothing to do. returning");
            return;
        }

        String b = "";
        b += "<HTML><BODY>\n";

        b += "Client Sessions: " + SessionManagement.getPilotDataStructures().size() + "<br>";
        b += "ATC Sessions: " + SessionManagement.getAtcDataStructures().size() + "<br>";

        if (ServerConfig.getDataServerBootStrap() == null) {
            b += "Accepting Client Connections: false<br>";
        } else {
            b += "Accepting Client Connections: true<br>";
        }
        
        b += "PilotStructures present: " + SessionManagement.getPilotDataStructures().size() + "<br>";
        b += "ATCSTructures present: " + SessionManagement.getAtcDataStructures().size() + "<br>";
        
        
        b += "Messaging Consumers running: " + ServerConfig.getMessageReceivers().size() + "<br>";
        b += "Messaging Producers running: " + ServerConfig.getMessageSenders().size() + "<br>";
        
        
        if (ServerConfig.getMqBrokerManager() == null) {
            b += "MQ Broker running: false <br>"; 
        }
        else {
            b += "MQ Broker running: true <br>";
        }
        
        
        b += "</BODY></HTML>";
        System.out.println(b);
        ServerMetrics metrics = new ServerMetrics();
        metrics.setMessage(b);
        n.writeAndFlush(metrics);

    }

}
