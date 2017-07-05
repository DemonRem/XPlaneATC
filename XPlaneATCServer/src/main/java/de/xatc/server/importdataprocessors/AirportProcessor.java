/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.importdataprocessors;

import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.db.sharedentities.atcdata.PlainNavPoint;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.db.DBSessionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko
 */
public class AirportProcessor extends Thread {

    private static final Logger LOG = Logger.getLogger(AirportProcessor.class.getName());
    
    private File airportFile = new File(ServerConfig.getAirportFile());

    @Override
    public void run() {

        if (!airportFile.exists()) {
            LOG.error("FirDetailList not found.... returning");
            return;
        }

        Session s = DBSessionManager.getSession();

        Transaction tx = s.beginTransaction();
        Query q = s.createQuery("delete from PlainAirport");
        q.executeUpdate();
        tx.commit();

        DBSessionManager.closeSession(s);

        s = DBSessionManager.getSession();

        try (BufferedReader br = new BufferedReader(new FileReader(airportFile))) {

            String line = null;
            while ((line = br.readLine()) != null) {

                boolean continueDueToErrors = false;

                if (StringUtils.isEmpty(line)) {
                    continue;
                }

                String[] splitted = line.split(":");
                if (splitted.length != 6) {
                    continue;
                }
                for (String st : splitted) {
                    if (StringUtils.isEmpty(st)) {
                        LOG.trace(line + " Values in Line are empty.... continue");
                        continueDueToErrors = true;
                        break;
                    }
                }
                if (continueDueToErrors) {
                    continue;
                }
                PlainAirport airport = new PlainAirport();
                airport.setAirportIcao(splitted[0]);
                airport.setAirportName(splitted[1]);
                airport.setCityName(splitted[2]);
                airport.setCountryCode(splitted[3]);

                PlainNavPoint position = new PlainNavPoint();
                position.setLat(Double.parseDouble(splitted[4]));
                position.setLon(Double.parseDouble(splitted[5]));
                airport.setPosition(position);

                s.saveOrUpdate(airport);
                s.flush();
                s.clear();
                LOG.trace(line + "... imported");

            }
            DBSessionManager.closeSession(s);
            br.close();
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
            e.printStackTrace(System.err);
        }
       

    }

    public static void main(String[] arg) {
        new AirportProcessor().start();
    }
    
    
}
