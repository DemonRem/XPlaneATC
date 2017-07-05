/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import com.thoughtworks.xstream.XStream;
import de.xatc.commons.datastructure.atc.ATCStructure;
import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.datastructure.structureaction.PlanePositoinSyncResponse;
import de.xatc.commons.db.sharedentities.atcdata.Country;
import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.networkpackets.atc.datasync.DataStructuresResponsePacket;
import de.xatc.commons.networkpackets.atc.datasync.DataSyncPacket;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class ServerSyncHandler {

    private static final Logger LOG = Logger.getLogger(ServerSyncHandler.class.getName());
    
    private static boolean firRunning = false;
    private static boolean airportRunning = false;
    private static boolean countryRunning = false;

    public static void handleServerSyncRequest(Channel n, String what) {

        if (what.equals("fir")) {

            if (firRunning) {
                return;
            }

            Session s = DBSessionManager.getSession();
            List<Fir> firList = s.createCriteria(Fir.class).list();
            DBSessionManager.closeSession(s);
            int counter = 0;
            firRunning = true;
            for (Fir fir : firList) {

                counter++;
                DataSyncPacket p = new DataSyncPacket();
                p.setCurrentDataSet(counter);
                p.setMaxDataSets(firList.size());
                p.setTransferObject(fir);
                p.setDataSetToSync(what);
                LOG.debug("Sending DataSyncPacket FIR: " + fir.getFirNameIcao());
                n.writeAndFlush(p);

            }
            DataSyncPacket finishedPacket = new DataSyncPacket();
            finishedPacket.setFinished(true);
            finishedPacket.setDataSetToSync("fir");
            n.writeAndFlush(finishedPacket);
            firRunning = false;
        } else if (what.equals("airport")) {

            if (airportRunning) {
                return;
            }

            Session s = DBSessionManager.getSession();
            List<PlainAirport> airportList = s.createCriteria(PlainAirport.class).list();
            DBSessionManager.closeSession(s);
            int counter = 0;
            airportRunning = true;
            for (PlainAirport airport : airportList) {

                counter++;
                DataSyncPacket p = new DataSyncPacket();
                p.setCurrentDataSet(counter);
                p.setMaxDataSets(airportList.size());
                p.setTransferObject(airport);
                p.setDataSetToSync(what);
                LOG.debug("Sending DataSyncPacket Airport: " + airport.getAirportIcao());
                n.writeAndFlush(p);

            }
            DataSyncPacket finishedPacket = new DataSyncPacket();
            finishedPacket.setFinished(true);
            finishedPacket.setDataSetToSync("airport");
            n.writeAndFlush(finishedPacket);
            airportRunning = false;

        } else if (what.equals("country")) {

            if (countryRunning) {
                return;
            }

            Session s = DBSessionManager.getSession();
            List<Country> countryList = s.createCriteria(Country.class).list();
            DBSessionManager.closeSession(s);
            int counter = 0;
            countryRunning = true;
            for (Country country : countryList) {

                counter++;
                DataSyncPacket p = new DataSyncPacket();
                p.setCurrentDataSet(counter);
                p.setMaxDataSets(countryList.size());
                p.setTransferObject(country);
                p.setDataSetToSync(what);
                LOG.debug("Sending DataSyncPacket Country: " + country.getCountryCode());
                n.writeAndFlush(p);

            }
            DataSyncPacket finishedPacket = new DataSyncPacket();
            finishedPacket.setFinished(true);
            finishedPacket.setDataSetToSync("country");
            n.writeAndFlush(finishedPacket);
            countryRunning = false;

        }

    }

    public static void handleStructuresSyncRequest(Channel n) {

        LOG.trace("\n\n\n");
        LOG.trace("handle Structure Sync Request.... assembling...");

        LOG.debug("Sending pilot structures...");

        XStream x = new XStream();
        for (Entry<String, PilotStructure> entry : SessionManagement.getPilotDataStructures().entrySet()) {

            DataStructuresResponsePacket p = new DataStructuresResponsePacket();
            p.setPilotStructure(entry.getValue());
            p.setStructureSsessionID(entry.getKey());
            n.writeAndFlush(p);
            LOG.trace("Sending pilot structure with SessionID: " + p.getStructureSsessionID());
        }

        LOG.debug("Sending atc strcutures....");
        for (Entry<String, ATCStructure> entry : SessionManagement.getAtcDataStructures().entrySet()) {
            DataStructuresResponsePacket p = new DataStructuresResponsePacket();

            p.setAtcStructure(entry.getValue());
            p.setStructureSsessionID(entry.getKey());

            try {
                n.writeAndFlush(p);

            } catch (Exception e) {
                LOG.error(e.getLocalizedMessage());
                e.printStackTrace(System.err);
            }

            LOG.debug("Sending ATC Structure with sessionID: " + p.getStructureSsessionID());

        }

        LOG.debug("data structzure response packet sent!!!!!!!!!!!!!!!!\n\n\n");

    }

    public static void handleSingleATCStructureRequest(Channel n, String sessionID) {

        ATCStructure s = SessionManagement.getAtcDataStructures().get(sessionID);
        
        if (s != null) {
            DataStructuresResponsePacket r = new DataStructuresResponsePacket();
            r.setStructureSsessionID(s.getStructureSessionID());
            r.setAtcStructure(s);
            n.writeAndFlush(r);
        }

    }

    public static void handleSinglePilotStructureRequest(Channel n, String sessionID) {
        PilotStructure s = SessionManagement.getPilotDataStructures().get(sessionID);
        if (s != null) {
            DataStructuresResponsePacket r = new DataStructuresResponsePacket();
            r.setStructureSsessionID(s.getStructureSessionID());
            r.setPilotStructure(s);
            n.writeAndFlush(r);
        }

    }

    public static void syncPlanePositionsOfPilot(Channel n, String sessionID) {
        
        PilotStructure s = SessionManagement.getPilotDataStructures().get(sessionID);
        if (s != null) {
            
            List<PlanePosition> list = s.getPlanePositionList();
            if (list != null) {
                if (!list.isEmpty()) {
                    
                    for (PlanePosition p : list) {
                        
                        PlanePositoinSyncResponse r = new PlanePositoinSyncResponse();
                        r.setStructureSessionID(sessionID);
                        r.setP(p);
                        n.writeAndFlush(r);
                        
                    }
                    
                }
                
                
                
            }
            
            
        }
        
        
    }
    
}
