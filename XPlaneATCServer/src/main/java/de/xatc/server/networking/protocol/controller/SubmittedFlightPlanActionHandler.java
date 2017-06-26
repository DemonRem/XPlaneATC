/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.sessionmanagment.NetworkBroadcaster;
import de.xatc.server.sessionmanagment.SessionManagement;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko
 */
public class SubmittedFlightPlanActionHandler {

    public static void revokeSubmittedFlightPlan(SubmittedFlightPlansActionPacket p) {

        
        //TODO with pilotsstructure
        Session session = DBSessionManager.getSession();
        Transaction a = session.beginTransaction();
        Query q = session.createQuery("delete from SubmittedFlightPlan where id = " + p.getServersID());
        q.executeUpdate();
        a.commit();
        DBSessionManager.closeSession(session);
        if (SessionManagement.getAtcDataStructures().size() > 0) {
            NetworkBroadcaster.broadcastATC(p);
            
        }

    }

}
