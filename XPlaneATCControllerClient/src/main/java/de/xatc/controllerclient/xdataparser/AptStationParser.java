package de.xatc.controllerclient.xdataparser;

import de.xatc.commons.db.sharedentities.aptmodel.AptAirport;
import de.xatc.commons.db.sharedentities.aptmodel.AptFileEntity;
import de.xatc.commons.db.sharedentities.atcdata.AirportStation;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.navigation.NavPointHelpers;
import de.xatc.controllerclient.xdataparser.tools.AptFilesTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mirko
 */
public class AptStationParser {

    public void parseStations() throws FileNotFoundException, IOException {

        if (XHSConfig.getFileIndexerFrame() != null) {

            XHSConfig.getFileIndexerFrame().getIndexProgress().setValue(0);
            XHSConfig.getFileIndexerFrame().getStatusLabel().setText("Parsing Airport Stations and Frequencies....");
            XHSConfig.getFileIndexerFrame().getCounterLabel().setText("");

        }
        
        
        int airportCounter = 0;
        int stationSaved = 0;

        Session s = DBSessionManager.getSession();
        List<AptFileEntity> fileList = s.createCriteria(AptFileEntity.class).list();
        if (fileList.isEmpty()) {

            if (XHSConfig.getFileIndexerFrame() != null) {

                XHSConfig.getFileIndexerFrame().getIndexProgress().setValue(0);
                XHSConfig.getFileIndexerFrame().getStatusLabel().setText(" ");
                XHSConfig.getFileIndexerFrame().getCounterLabel().setText(" ");

            }

            return;
        }
        if (XHSConfig.getFileIndexerFrame() != null) {

            XHSConfig.getFileIndexerFrame().getIndexProgress().setMinimum(0);
            XHSConfig.getFileIndexerFrame().getIndexProgress().setMaximum(fileList.size());

        }
        int counter = 0;
        for (AptFileEntity file : fileList) {
            counter++;

            File aptFile = new File(file.getFileName());
            if (!aptFile.exists()) {
                continue;
            }

            if (XHSConfig.getFileIndexerFrame() != null) {

                XHSConfig.getFileIndexerFrame().getIndexProgress().setValue(counter);
                XHSConfig.getFileIndexerFrame().getStatusLabel().setText("Searching File: " + aptFile.getName());
                XHSConfig.getFileIndexerFrame().getCounterLabel().setText(counter + " / " + fileList.size());

            }

            BufferedReader br = new BufferedReader(new FileReader(aptFile));
            String line = null;
         
            AptAirport airport = null;
            int linecounter = 0;
            while ((line = br.readLine()) != null) {

                
                linecounter ++;

                if (line.startsWith("1 ") || line.startsWith("17 ") || line.startsWith("16 ")) {
                    airportCounter++;
                    System.out.println(line);
                    System.out.println("Getting Airport");
                    airport = AptFilesTools.parseAirport(line, aptFile.getAbsolutePath());
                    System.out.println("AIrPORT FOUND " + airport);
                }
                
                
                if (XHSConfig.getFileIndexerFrame() != null) {

                    if (airport != null) {
                        XHSConfig.getFileIndexerFrame().getCounterLabel().setText(counter + " / " + fileList.size() + " - " + airport.getIcao());
                    }
                }

                if (airport == null) {
                    System.out.println("Airport = null");
                    continue;
                }
                if (line.startsWith("50 ")
                        || line.startsWith("51 ")
                        || line.startsWith("52 ")
                        || line.startsWith("53 ")
                        || line.startsWith("54 ")
                        || line.startsWith("55 ")
                        || line.startsWith("56 ")) {
                    System.out.println("line starts with station-");
                    System.out.println(line);
                    
                    AirportStation station = new AirportStation();
                    String[] splitted = line.split(" ");
                    if (splitted.length == 3) {

                        System.out.println("splitted line" + line + " linenumber: " + linecounter + " " + file.getFileName());
                        
                        station.setFrequency(NavPointHelpers.formatFrequency(splitted[1]));
                        station.setStationName(splitted[2]);
                        
                        
                        List<PlainAirport> plainAirportList = s.createCriteria(PlainAirport.class).add(Restrictions.eq("airportIcao", airport.getIcao())).list();
                        if (plainAirportList.isEmpty()) {
                            System.out.println("AIRPORT NOT FOUND!");
                            System.exit(0);
                            continue;
                        } 
                        PlainAirport p = plainAirportList.get(0);
                        System.out.println(airport.getIcao());
                        station.setAirportIcao(airport.getIcao());
                        station.setAirportName(airport.getAiportName());
                        p.getAirportStations().add(station);
                        
                        
                        if (splitted[2].matches(".*ATIS.*")) {
                            station.setAtis(true);
                        }
                        s.saveOrUpdate(station);
                        s.saveOrUpdate(p);
                        s.flush();
                        s.clear();
                        stationSaved++;
                    }
                    else {
                        System.out.println("NOT SAVED:::::::: " + line);
                        
                    }

                }

            } // end while file

        }
        if (XHSConfig.getFileIndexerFrame() != null) {

            XHSConfig.getFileIndexerFrame().getIndexProgress().setValue(0);
            XHSConfig.getFileIndexerFrame().getStatusLabel().setText("");
            XHSConfig.getFileIndexerFrame().getCounterLabel().setText("");

        }
        DBSessionManager.closeSession(s);
        System.out.println("AIRPORTSFOUND: " + airportCounter);
        System.out.println("AIRPORTSSAVED: " + stationSaved);

    }

}
