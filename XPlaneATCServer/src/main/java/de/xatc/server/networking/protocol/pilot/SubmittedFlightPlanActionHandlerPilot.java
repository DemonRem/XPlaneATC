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
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class SubmittedFlightPlanActionHandlerPilot {

    private static final Logger LOG = Logger.getLogger(SubmittedFlightPlanActionHandlerPilot.class.getName());

    public static void handleNewIncomingSubmittedFlightPlan(SubmittedFlightPlansActionPacket action) {

        LOG.info("Saving NEW or UPDATED submitted FlightPlan!");
        PilotStructure pilotStructure = SessionManagement.getPilotDataStructures().get(action.getSessionID());
        if (pilotStructure == null) {
            LOG.warn("Could not find pilotDataStructure");
            return;

        }

        SubmittedFlightPlan plan = action.getSubmittedFlightPlan();
        plan.setActive(true);
        plan.setRevoked(false);

        plan.setPilotsSessionID(action.getSessionID());
        plan.setId(0);
        plan.setAssingedControllerSessionID(null);
        pilotStructure.setSubmittedFlightPlan(plan);
        Session session = DBSessionManager.getSession();
        session.saveOrUpdate(plan);
        DBSessionManager.closeSession(session);

        action.setSubmittedFlightPlan(plan);
        LOG.info("sending new FlightPlan to all controllers!");

        NetworkBroadcaster.broadcastATC(action);

    }

    public static void revokeSubmittedFlightPlan(SubmittedFlightPlansActionPacket action) {

        PilotStructure pilotStructure = SessionManagement.getPilotDataStructures().get(action.getSessionID());
        if (pilotStructure == null) {
            LOG.info("Could not revoke flightplan. PilotStrcuture not found");
            return;
        }

        SubmittedFlightPlan plan = action.getSubmittedFlightPlan();
        plan.setRevoked(true);
        plan.setActive(false);
        plan.setPilotsSessionID(action.getSessionID());
        plan.setAssingedControllerSessionID(null);
        plan.setId(0);
        Session session = DBSessionManager.getSession();
        session.saveOrUpdate(plan);
        DBSessionManager.closeSession(session);
        
        NetworkBroadcaster.broadcastATC(action);

        pilotStructure.setSubmittedFlightPlan(null);

    }

}
