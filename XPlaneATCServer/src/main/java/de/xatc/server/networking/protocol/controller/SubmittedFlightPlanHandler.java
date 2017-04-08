/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.networkpackets.atc.stripsmgt.ATCRequestStripsPacket;
import de.xatc.commons.networkpackets.client.SubmittedFlightPlan;
import de.xatc.commons.networkpackets.client.SubmittedFlightPlansPacket;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.XATCUserSession;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class SubmittedFlightPlanHandler {

    public static void handleNewIncomingSubmittedFlightPlan(SubmittedFlightPlan plan) {

        System.out.println("Saving new submitted FlightPlan!");
        XATCUserSession user = SessionManagement.findUserSessionBySessionID(plan.getSessionID(), SessionManagement.getUserSessionList());
        if (user == null) {
            System.out.println("Could not find UserSession");
            return;

        }
        plan.setFlightPlanOwner(user.getRegisteredUser());
        Session session = DBSessionManager.getSession();
        session.saveOrUpdate(plan);
        DBSessionManager.closeSession(session);
        if (!SessionManagement.getAtcChannelGroup().isEmpty()) {
            System.out.println("sending new FlightPlan to all controllers!");
            SessionManagement.getAtcChannelGroup().writeAndFlush(plan);
        }

    }

    public static void sendStripsToController(ATCRequestStripsPacket requester, Channel channel) {

        Session session = DBSessionManager.getSession();

        List<SubmittedFlightPlan> list = session.createCriteria(SubmittedFlightPlan.class).list();
        SubmittedFlightPlansPacket p = new SubmittedFlightPlansPacket();
        p.setList(list);
        channel.writeAndFlush(p);

    }

}
