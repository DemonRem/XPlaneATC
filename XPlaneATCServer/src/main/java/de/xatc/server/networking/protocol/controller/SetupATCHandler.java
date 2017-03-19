/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.beans.ServerMessageToClient;
import de.xatc.commons.networkpackets.atc.stations.SupportedAirportStation;
import de.xatc.commons.networkpackets.atc.stations.SupportedFirStation;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.XATCUserSession;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class SetupATCHandler {
    
    public static void handleAirportSetup(SupportedAirportStation airport, Channel c) {
        
        Session s = DBSessionManager.getSession();
        airport.setId(0);
        XATCUserSession userSession = SessionManagement.findUserSessionByChannelID(c.id().asLongText(), SessionManagement.getAtcSessionList());
        airport.setRegjsteredUser(userSession.getRegisteredUser());
        airport.setActive(true);
        s.saveOrUpdate(airport);
        ServerMessageToClient message = new ServerMessageToClient();
        message.setMessage("Your ATC Session was confirmed! " + airport.getAirport() + " - " + airport.getStationName() + ". Start your ATC Duty now!");
        c.writeAndFlush(message);
        DBSessionManager.closeSession(s);
        
    }
    
    public static void handleFirSetup(SupportedFirStation fir, Channel c) {
        
        
        Session s = DBSessionManager.getSession();
        fir.setId(0);
        XATCUserSession userSession = SessionManagement.findUserSessionByChannelID(c.id().asLongText(), SessionManagement.getAtcSessionList());
        fir.setRegisteredUser(userSession.getRegisteredUser());
        fir.setActive(true);
        s.saveOrUpdate(fir);
        ServerMessageToClient message = new ServerMessageToClient();
        message.setMessage("Your ATC Session was confirmed! " + fir.getFir().getFirNameIcao() + ". Start your ATC Duty now!");
        c.writeAndFlush(message);
        DBSessionManager.closeSession(s);
        
    }
    
    
}
