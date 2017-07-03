/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.pilot;

import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlan;
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
public class SubmittedFlightPlanActionHandlerPilot {
    
    public static void handleNewIncomingSubmittedFlightPlan(SubmittedFlightPlansActionPacket action) {

        System.out.println("Saving new submitted FlightPlan!");
        PilotStructure pilotStructure = SessionManagement.getPilotDataStructures().get(action.getSessionID());
        if (pilotStructure == null) {
            System.out.println("Could not find pilotDataStructure");
            return;

        }
        pilotStructure.setSubmittedFlightPlan(action.getSubmittedFlightPlan());
        SubmittedFlightPlan plan = action.getSubmittedFlightPlan();
        plan.setActive(true);
        plan.setRevoked(false);
        plan.setPilotsSessionID(action.getSessionID());
        plan.setId(0);
        Session session = DBSessionManager.getSession();
        session.saveOrUpdate(plan);
        DBSessionManager.closeSession(session);
        
        if (!SessionManagement.getAtcDataStructures().isEmpty()) {
            System.out.println("sending new FlightPlan to all controllers!");
            
            NetworkBroadcaster.broadcastATC(action);
        }

    }
    
    public static void revokeSubmittedFlightPlan(SubmittedFlightPlansActionPacket p) {

        
        PilotStructure pilotStructure = SessionManagement.getPilotDataStructures().get(p.getSessionID());
        if (pilotStructure == null) {
            System.out.println("Could not revoke flightplan. PilotStrcuture not found");
            return;
        }
        
        
        SubmittedFlightPlan plan = p.getSubmittedFlightPlan();
        plan.setRevoked(true);
        plan.setActive(false);
        plan.setAssingedControllerSessionID(null);
        plan.setId(0);
        Session session = DBSessionManager.getSession();
        session.saveOrUpdate(plan);
        DBSessionManager.closeSession(session);
        if (SessionManagement.getAtcDataStructures().size() > 0) {
            NetworkBroadcaster.broadcastATC(p);
            
        }
        pilotStructure.setSubmittedFlightPlan(null);

    }
    
    
    
}
