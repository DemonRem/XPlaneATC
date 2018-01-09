/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.gui.airportdrawing.file;

import de.twyhelper.tools.FMCConfig;
import de.twyhelper.tools.debug.DebugMessageLevel;
import de.xatc.commons.db.sharedentities.aptmodel.AptAirport;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.xdataparser.tools.AptFilesTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

/**
 * This class parses all apt files and splits all airport into several datasets.
 * this is useful to get the big fat navigational database devided and also
 * split custonm scenery apt files into the same folder with a different suffix.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptParser {

    /**
     * currently parsed file
     */
    private File currentFile;

    /**
     * shared database session inside parser
     */
    private Session session = DBSessionManager.getSession();
    /**
     * found airports inside apt.dat file
     */
    private int airportCounter = 0;

    ;

    /**
     * Constructor
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
        while ((line = br.readLine()) != null) {

            lineCounter++;

            if (line.startsWith("1 ") || line.startsWith("17 ") || line.startsWith("16 ")) {
                networkDetected = false;
                if (FMCConfig.getMainFrame().getMainPanel().getToolPanel() != null) {

                    FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptFilesLineCounterLabel().setText("Line: " + lineCounter + " Airports: " + this.airportCounter);

                }

                this.airportCounter++;
                FMCConfig.debugMessage("Parsing Airport", DebugMessageLevel.WARN);
                airport = this.parseAirport(line);
                airport.setLineNumberStart(lineCounter);
                FMCConfig.debugMessage("************************" + line, DebugMessageLevel.WARN);

                //hier muss noch geregelt werden, dass die initiale Position auch nocht mit ausgelesen wird
                try {

                    session.saveOrUpdate(airport);
                    session.flush();
                } catch (Exception ex) {
                    //System.out.println("ERR");
                }

            }
            if (!networkDetected) {
                if (line.startsWith("1202 ")) {
                    networkDetected = true;
                    airport.setTaxiNetworkPresent(true);
                    session.saveOrUpdate(airport);
                    session.flush();
                    //System.out.println("NETWORK PRESENT: " + airport.getIcao());

                }
            }

        }
        br.close();
        DBSessionManager.closeSession();
    }

    /**
     * parse the airport line
     *
     * @param s
     * @return
     */
    private AptAirport parseAirport(String s) {

        AptAirport airport = new AptAirport();

        String[] splitted = StringUtils.split(s);

        airport.setAirportType(Integer.parseInt(splitted[0]));
        airport.setIcao(splitted[4]);
        airport.setAiportName(AptFilesTools.joinArray(splitted, 5, splitted.length - 1));
        airport.setFullFileName(this.currentFile.getAbsolutePath());
        airport.setShortFileName(AptFilesTools.extractShortAptFileName(airport.getFullFileName()));

        return airport;
    }

}
