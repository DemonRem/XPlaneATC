/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import com.thoughtworks.xstream.XStream;
import de.xatc.commons.datastructure.atc.ATCStructure;
import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.db.sharedentities.atcdata.Country;
import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.networkpackets.atc.datasync.DataStructuresResponsePacket;
import de.xatc.commons.networkpackets.atc.datasync.DataSyncPacket;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.List;
import java.util.Map.Entry;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class ServerSyncHandler {

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
                System.out.println("Sending DataSyncPacket FIR: " + fir.getFirNameIcao());
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
                System.out.println("Sending DataSyncPacket Airport: " + airport.getAirportIcao());
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
                System.out.println("Sending DataSyncPacket Country: " + country.getCountryCode());
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

        System.out.println("\n\n\n");
        System.out.println("handle Structure Sync Request.... assembling...");

        System.out.println("Sending pilot structures...");

        XStream x = new XStream();
        for (Entry<String, PilotStructure> entry : SessionManagement.getPilotDataStructures().entrySet()) {

            DataStructuresResponsePacket p = new DataStructuresResponsePacket();
            p.setPilotStructure(entry.getValue());
            p.setStructureSsessionID(entry.getKey());
            n.writeAndFlush(p);
            System.out.println("Sending pilot structure with SessionID: " + p.getStructureSsessionID());
        }

        System.out.println("Sending atc strcutures....");
        for (Entry<String, ATCStructure> entry : SessionManagement.getAtcDataStructures().entrySet()) {
            DataStructuresResponsePacket p = new DataStructuresResponsePacket();
           
            p.setAtcStructure(entry.getValue());
            p.setStructureSsessionID(entry.getKey());
  
            try {
                n.writeAndFlush(p);

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            
            System.out.println("Sending ATC Structure with sessionID: " + p.getStructureSsessionID());

        }

        System.out.println("data structzure response packet sent!!!!!!!!!!!!!!!!\n\n\n");

    }

}
