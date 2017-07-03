/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.xdataparser;

import de.xatc.commons.db.sharedentities.aptmodel.AptAirport;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.db.sharedentities.atcdata.PlainNavPoint;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;

import de.xatc.controllerclient.navigation.NavPoint;
import de.xatc.controllerclient.xdataparser.tools.AptFilesTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * This class parses all apt files and splits all airport into several datasets.
 * this is useful to get the big fat navigational database devided and also
 * split custonm scenery apt files into the same folder with a different suffix.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptParser {

    private static final Logger LOG = Logger.getLogger(AptParser.class.getName());

    /**
     * currently parsed file
     */
    private File currentFile;

    /**
     * found airports inside apt.dat file
     */
    private int airportCounter = 0;

    /**
     * Constructor
     *
     * @param file
     */
    public AptParser(File file) {

        this.currentFile = file;

    }

    /**
     * parse the file
     */
    public void parse() {

        try {

            this.readAndParseFile(currentFile);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);

        }

    }

    /**
     * read and parse file
     *
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readAndParseFile(File file) throws FileNotFoundException, IOException {

        int lineCounter = 0;
        boolean networkDetected = false;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        AptAirport airport = null;

        String airportAptString = "";

        while ((line = br.readLine()) != null) {

            lineCounter++;
            if (line.startsWith("1000 ")) {
                continue;
            }

            if (line.startsWith("1 ") || line.startsWith("17 ") || line.startsWith("16 ")) {

                if (!StringUtils.isEmpty(airportAptString) && airport != null) {

                    // System.out.println(airportAptString);
                    NavPoint initialPos = AptFilesTools.getAirportInitialPosition(airportAptString);

                    if (initialPos != null) {
                        PlainAirport plainAirport = new PlainAirport();
                        plainAirport.setAirportIcao(airport.getIcao());
                        plainAirport.setAirportName(airport.getAiportName());
                        PlainNavPoint plainNav = new PlainNavPoint();
                        plainNav.setLat(Double.parseDouble(initialPos.getLatitudeSTring()));
                        plainNav.setLon(Double.parseDouble(initialPos.getLongitudeSTring()));
                        plainAirport.setPosition(plainNav);
                        Session s = DBSessionManager.getSession();
                        s.saveOrUpdate(plainAirport);
                        DBSessionManager.closeSession(s);
                    }
                }
                airportAptString = "";
                airportAptString += line + "\n";

                networkDetected = false;
                if (XHSConfig.getFileIndexerFrame() != null) {

                    XHSConfig.getFileIndexerFrame().getCounterLabel().setText("Line: " + lineCounter + " Airports: " + this.airportCounter);

                }

                this.airportCounter++;
                LOG.debug("Parsing Airport");
                airport = AptFilesTools.parseAirport(line, this.currentFile.getAbsolutePath());
                airport.setLineNumberStart(lineCounter);
                LOG.debug("************************" + line);

                //hier muss noch geregelt werden, dass die initiale Position auch nocht mit ausgelesen wird
                try {
                    Session session = DBSessionManager.getSession();
                    session.saveOrUpdate(airport);
                    session.flush();
                    DBSessionManager.closeSession(session);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                    //System.out.println("ERR");
                }

            } else {
                airportAptString += line + "\n";
            }

            if (!networkDetected) {
                if (line.startsWith("1202 ")) {
                    networkDetected = true;
                    airport.setTaxiNetworkPresent(true);
                    Session session = DBSessionManager.getSession();
                    session.saveOrUpdate(airport);
                    session.flush();
                    DBSessionManager.closeSession(session);
                    //System.out.println("NETWORK PRESENT: " + airport.getIcao());

                }
            }

        }
        if (!StringUtils.isEmpty(airportAptString) && airport != null) {

            NavPoint initialPos = AptFilesTools.getAirportInitialPosition(airportAptString);
            if (initialPos != null) {
                PlainAirport plainAirport = new PlainAirport();
                plainAirport.setAirportIcao(airport.getIcao());
                plainAirport.setAirportName(airport.getAiportName());
                PlainNavPoint plainNav = new PlainNavPoint();
                plainNav.setLat(Double.parseDouble(initialPos.getLatitudeSTring()));
                plainNav.setLon(Double.parseDouble(initialPos.getLongitudeSTring()));
                plainAirport.setPosition(plainNav);
                Session s = DBSessionManager.getSession();
                s.saveOrUpdate(plainAirport);
                DBSessionManager.closeSession(s);
            }
        }
        br.close();

    }

}
