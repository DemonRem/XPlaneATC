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
import de.xatc.commons.db.sharedentities.aptmodel.NavDataEntity;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.navigation.NavPoint;
import de.xatc.controllerclient.navigation.NavPointHelpers;
import de.xatc.controllerclient.xdataparser.aptmodel.AptAirportModel;
import de.xatc.controllerclient.xdataparser.aptmodel.AptChunkModel;
import de.xatc.controllerclient.xdataparser.aptmodel.AptPavementModel;
import de.xatc.controllerclient.xdataparser.aptmodel.AptRunwayModel;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiNetworkNode;
import de.xatc.controllerclient.xdataparser.aptmodel.Taxiway;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiwaySegment;
import de.xatc.controllerclient.xdataparser.tools.AptFilesTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;



/**
 * this is the new airort apt file parser to display on map.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptAirportModelParser {

    private static final Logger LOG = Logger.getLogger(AptAirportModelParser.class.getName());
    
    /**
     * are we inside the pavement declaration
     */
    private boolean pavementStarted = false;

    /**
     * are we inside a chunk declaration
     */
    private boolean chunkStarted = false;

    /**
     * the current aptAirport entity
     */
    private AptAirport currentAirport;
    /**
     * the target apt airport model object containing all parsed data
     */
    private AptAirportModel airportModel = new AptAirportModel();
    /**
     * the pavement model
     */
    private AptPavementModel pavementModel = new AptPavementModel();
    /**
     * the chunk model
     */
    private AptChunkModel chunkModel;

    /**
     * current taxiway parsing
     */
    private String currentTaxiwayName = "unamed";

    /**
     * the most northern navigation point of the airport where the airport name
     * will be painted
     */
    private NavPoint northernNavPoint;

    /**
     * Constructor
     *
     * @param a
     */
    public AptAirportModelParser(AptAirport a) {

        this.currentAirport = a;

    }

    /**
     * parse the airport
     */
    public void parseAirport() {

        LOG.debug("parsing Airport");
        File airportFile = new File(currentAirport.getFullFileName());
        if (!airportFile.exists()) {
            return;
        }

        String airportString;

        try {
            airportString = AptFilesTools.extractAirportFromFile(currentAirport.getFullFileName(), currentAirport.getLineNumberStart());
        } catch (IOException ex) {
            LOG.error(ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new StringReader(airportString));
            String line;
            String lastTaxiNodeName = null;
            while ((line = br.readLine()) != null) {

                String[] splittedString = StringUtils.split(line);
                
                //remove empty arrayfields
                LOG.debug("LINESPLIT SIZE: " + splittedString.length);
                ArrayList<String> splittedAsList = new ArrayList<>(Arrays.asList(splittedString));
                splittedAsList.removeAll(Arrays.asList("",null));
                splittedString = splittedAsList.toArray(splittedString);
                LOG.debug("NEW LINESPLIT SIZE: " + splittedString.length);
                
                int lineCode = 0;
                if (splittedString.length == 0) {
                    LOG.warn("splitted Line = 0");
                    continue;
                }
                lineCode = Integer.parseInt(splittedString[0]);

                //airport
                if (lineCode == 1 || lineCode == 17 || lineCode == 16) {

                    if (splittedString.length < 4) {
                        LOG.debug("Could not parse Airport");
                        return;
                    }

                    String icao = splittedString[4];
                    String altitude = splittedString[1];
                    this.airportModel.setIcao(icao);
                    this.airportModel.setAltitude(altitude);
                    this.airportModel.setAirportName(AptFilesTools.joinArray(splittedString, 5, splittedString.length - 1));
                    continue;

                }

                //pavement
                if ((lineCode < 111 || lineCode > 114) && lineCode != 10) {
                    if (this.pavementStarted) {
                        LOG.debug("Pavement finished");
                        this.airportModel.getPavement().add(pavementModel);
                        this.pavementStarted = false;
                    }
                }
                switch (lineCode) {

                    case 100:
                        LOG.debug("creating runway land");
                        //Runway land
                        float width = Float.parseFloat(splittedString[1]);
                        String name1 = splittedString[8];
                        String name2 = splittedString[17];
                        NavPoint p1 = new NavPoint();
                        NavPoint p2 = new NavPoint();
                        p1.setLatitudeSTring(splittedString[9]);
                        p1.setLongitudeSTring(splittedString[10]);
                        p2.setLatitudeSTring(splittedString[18]);
                        p2.setLongitudeSTring(splittedString[19]);
                        p1 = NavPointHelpers.calcGeoPosition(p1);
                        p2 = NavPointHelpers.calcGeoPosition(p2);
                        this.findNorthernPoint(p1);
                        this.findNorthernPoint(p2);
                        AptRunwayModel runway = new AptRunwayModel();
                        runway.setType(100);
                        runway.setP1(p1);
                        runway.setP2(p2);
                        runway.setNameP1(name1);
                        runway.setNameP2(name2);
                        runway.setWidth(width);
                        this.airportModel.getRunwayList().add(runway);
                        break;
                    case 102:
                        LOG.debug("creating helipad");
                        //helipad
                        String name = splittedString[1];
                        NavPoint padcenter = new NavPoint();
                        padcenter.setLatitudeSTring(splittedString[2]);
                        padcenter.setLongitudeSTring(splittedString[3]);
                        padcenter = NavPointHelpers.calcGeoPosition(padcenter);
                        this.findNorthernPoint(padcenter);

                        AptRunwayModel helipad = new AptRunwayModel();
                        helipad.setType(102);
                        helipad.setNameP1(name);
                        helipad.setP1(padcenter);
                        this.airportModel.getRunwayList().add(helipad);
                        break;
                    case 101:
                        //runway water
                        LOG.debug("creating runway sea");
                        float waterWidth = Float.parseFloat(splittedString[1]);
                        String waterName1 = splittedString[3];
                        String waterName2 = splittedString[6];
                        NavPoint waterP1 = new NavPoint();
                        NavPoint waterP2 = new NavPoint();
                        waterP1.setLatitudeSTring(splittedString[4]);
                        waterP1.setLongitudeSTring(splittedString[5]);
                        waterP2.setLatitudeSTring(splittedString[7]);
                        waterP2.setLongitudeSTring(splittedString[8]);
                        waterP1 = NavPointHelpers.calcGeoPosition(waterP1);
                        waterP2 = NavPointHelpers.calcGeoPosition(waterP2);
                        this.findNorthernPoint(waterP1);
                        this.findNorthernPoint(waterP2);
                        AptRunwayModel waterRunway = new AptRunwayModel();
                        waterRunway.setType(101);
                        waterRunway.setP1(waterP1);
                        waterRunway.setP2(waterP2);
                        waterRunway.setNameP1(waterName1);
                        waterRunway.setNameP2(waterName2);
                        waterRunway.setWidth(waterWidth);
                        this.airportModel.getRunwayList().add(waterRunway);
                        break;

                    case 110:
                        LOG.debug("Case 110 - pavement started");
                        this.pavementStarted = true;

                        this.pavementModel.setName(AptFilesTools.joinArray(splittedString, 4, splittedString.length - 1));
                        this.chunkStarted = true;
                        this.chunkModel = new AptChunkModel();
                        this.pavementModel = new AptPavementModel();

                        break;
                    case 130:
                        LOG.debug("Case 130 - Airport Boundary");
                        this.pavementStarted = true;

                        this.pavementModel.setName("Airport Boundary");
                        this.chunkStarted = true;
                        this.chunkModel = new AptChunkModel();
                        this.pavementModel = new AptPavementModel();

                        break;
                    case 111:
                        LOG.debug("Case 111 - nothing to do");
                    case 112:
                        LOG.debug("case 112");
                        if (pavementStarted && !chunkStarted) {
                            LOG.debug("new Chunk Model");
                            this.chunkModel = new AptChunkModel();
                            chunkStarted = true;
                        }
                    case 113:
                        LOG.debug("case 113 nothing to do");
                    case 114:
                        if (!chunkStarted) {
                            continue;

                        }
                        LOG.debug("LineCode: " + lineCode + " case 114");
                        NavPoint p = new NavPoint();
                        p.setLatitudeSTring(splittedString[1]);
                        p.setLongitudeSTring(splittedString[2]);
                        p = NavPointHelpers.calcGeoPosition(p);
                        this.findNorthernPoint(p);
                        this.chunkModel.getNavPointList().add(p);

                        if (lineCode == 113 || lineCode == 114) {
                            LOG.debug("LineCode 113 / 114 chunk ended");
                            if (this.chunkModel.getNavPointList().size() > 0) {
                                LOG.debug("adding Chunk to pavement");
                                this.pavementModel.getChunkList().add(chunkModel);
                            }
                            this.chunkStarted = false;
                        }
                        break;
                    case 1201:
                       LOG.debug("*******************1201***************");
                       LOG.debug(Arrays.asList(splittedString));
                        
                        TaxiNetworkNode node = new TaxiNetworkNode();
                        NavPoint networkPoint = new NavPoint();
                        
                        networkPoint.setLatitudeSTring(splittedString[1]);
                        networkPoint.setLongitudeSTring(splittedString[2]);
                        networkPoint = NavPointHelpers.calcGeoPosition(networkPoint);

                        
                        
                        if (splittedString.length > 5) {
                            node.setName(AptFilesTools.joinArray(splittedString, 5, splittedString.length - 1));
                        }
                        node.setNavPoint(networkPoint);
                        node.setId(Integer.parseInt(splittedString[4]));
                        node.setUsage(splittedString[3]);
                        LOG.debug("Adding taxinode to model");
                        this.airportModel.getTaxiNetworkNodes().put(Integer.parseInt(splittedString[4]), node);

                        break;
                    case 1202:

                        if (splittedString.length < 5) {
                            break;
                        }

                        //we do not want runway or other routes. just taxiways
                        if (!splittedString[4].matches(".*taxiway.*")) {
                            continue;
                        }
                        
                        //ok, first gather needed information
                        int fromPoint = Integer.parseInt(splittedString[1]);
                        int toPoint = Integer.parseInt(splittedString[2]);

                        String direction = splittedString[3];

                        String txwType = splittedString[4];

                        //get Nodes
                        TaxiNetworkNode fromNode = this.airportModel.getTaxiNetworkNodes().get(fromPoint);
                        TaxiNetworkNode toNode = this.airportModel.getTaxiNetworkNodes().get(toPoint);

                        String taxiwayName = null;
                        Taxiway taxiway = null;

                        if (splittedString.length >= 6) {
                            taxiwayName = AptFilesTools.joinArray(splittedString, 5, splittedString.length - 1);
                            taxiwayName = taxiwayName.trim();
                            if (!StringUtils.isEmpty(taxiwayName)) {
                                this.currentTaxiwayName = taxiwayName;
                            } else {
                                this.currentTaxiwayName = "unamed";
                            }
                        }

                        TaxiwaySegment segment = new TaxiwaySegment();
                        segment.setFromNode(fromNode);
                        segment.setToNode(toNode);
                        segment.setType(txwType);
                        segment.setName(this.currentTaxiwayName);
                        segment.setDirection(direction);
                        segment.setFromPointID(fromPoint);
                        segment.setToPointID(toPoint);

                        taxiway = this.airportModel.getTaxiways().get(this.currentTaxiwayName);
                        if (taxiway == null) {

                            LOG.debug("no Taxiway found for: " + this.currentTaxiwayName);
                            taxiway = new Taxiway();
                            taxiway.getSegments().add(segment);
                            taxiway.setName(this.currentTaxiwayName);
                            this.airportModel.getTaxiways().put(this.currentTaxiwayName, taxiway);
                            LOG.debug("Putting NEW Taxiway '" + this.currentTaxiwayName + "'");

                        } else {
                            LOG.trace("TAXIWAY FOUND FOR: " + this.currentTaxiwayName);
                            if (!taxiway.getSegments().contains(segment)) {
                                taxiway.getSegments().add(segment);
                            }
                            LOG.trace("SEGMENTS IN TAXIWAY: " + taxiway.getSegments().size());
                            taxiway.setName(this.currentTaxiwayName);
                            this.airportModel.getTaxiways().put(this.currentTaxiwayName, taxiway);

                            LOG.trace("Putting Taxiway '" + this.currentTaxiwayName + "'");
                        }

                       LOG.trace("TAXIWAYS IN MODEL: " + this.airportModel.getTaxiways().size());

                        break;
                    case 1300:
                    case 15:
                        NavPoint parking = new NavPoint();
                        parking.setLatitudeSTring(splittedString[1]);
                        parking.setLongitudeSTring(splittedString[2]);
                        parking.setName(AptFilesTools.joinArray(splittedString, 6, splittedString.length - 1));
                        parking = NavPointHelpers.calcGeoPosition(parking);
                        this.airportModel.getParkings().add(parking);
                        break;
                    case 1204:

                        break;
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                        
                        String[] splittedFreq = splittedString[1].split("");
                        if (splittedFreq.length != 5) {
                            break;
                        }
                        String freq = splittedFreq[0] + splittedFreq[1] + splittedFreq[2] + "." + splittedFreq[3] + splittedFreq[4];
                        this.airportModel.getAirportFrequencies().put(splittedString[2],freq);
                        break;
                        
                    case 1302:
                        if (splittedString.length != 3) {
                            break;
                        }
                        LOG.trace(Arrays.asList(splittedString));
                        this.airportModel.getAttributeMap().put(splittedString[1], splittedString[2]);
                        break;
                }

            }
            Session session = DBSessionManager.getSession();
            List<NavDataEntity> navDataList = session.createCriteria(NavDataEntity.class)
                    .add(Restrictions.like("icao", "%" + this.currentAirport.getIcao() + "%")).list();
            DBSessionManager.closeSession(session);
            airportModel.setNavData(navDataList);

            LOG.trace("adding airport to painter. airport finished");
            this.airportModel.setMostNorthernPoint(this.northernNavPoint);
            this.findConnPoints();

        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
            e.printStackTrace(System.err);
        }

    }

    /**
     * find the most northern navi point of the parsed airport
     *
     * @param p
     */
    private void findNorthernPoint(NavPoint p) {

        if (this.northernNavPoint == null) {
            this.northernNavPoint = p;
            return;
        }
        double north = this.northernNavPoint.getGeoPos().getLatitude();
        if (p.getGeoPos().getLatitude() < north) {
            this.northernNavPoint = p;

        }

    }

    /**
     * find taxi network nodes of the taxi network which do connect multiple
     * taxiways
     */
    private void findConnPoints() {

        LOG.trace("******************************************");
        LOG.trace("******************************************");
        LOG.trace("******************************************");

        for (Entry<Integer, TaxiNetworkNode> entry : this.airportModel.getTaxiNetworkNodes().entrySet()) {

            int connPointsCounter = 0;
            int ID = entry.getKey();
            TaxiNetworkNode n = entry.getValue();

            for (Entry<String, Taxiway> taxiway : this.airportModel.getTaxiways().entrySet()) {

                for (TaxiwaySegment s : taxiway.getValue().getSegments()) {

                    if (s.getFromNode() == n || s.getToNode() == n) {

                        if (!n.getConnNames().contains(s.getName())) {
                            n.getConnNames().add(s.getName());                          
                        }
                    
                        
                        if (!n.getConnectionPoints().contains(s.getFromNode())) {
                            n.getConnectionPoints().add(s.getFromNode());
                        }
                        if (!n.getConnectionPoints().contains(s.getToNode())) {
                            n.getConnectionPoints().add(s.getToNode());
                        }
                        connPointsCounter++;
                    
                    
                    }
     
                }

            }

            if (connPointsCounter > 2) {
                this.airportModel.getConnPoints().add(n);
            }
             LOG.trace("CONN POINTS COUNTER: " + ID + " - " + connPointsCounter);

        }

    }
 

    /**
     * getter
     *
     * @return
     */
    public AptAirportModel getAirportModel() {
        return airportModel;
    }

    /**
     * setter
     *
     * @param airportModel
     */
    public void setAirportModel(AptAirportModel airportModel) {
        this.airportModel = airportModel;
    }

}
