/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: May/2015
 *Have fun!
 */
package de.xatc.controllerclient.navigation;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.db.sharedentities.aptmodel.AptAirport;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.log.DebugMessageLevel;
import de.xatc.controllerclient.xdataparser.aptmodel.AptAirportModel;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiNetworkNode;
import de.xatc.controllerclient.xdataparser.aptmodel.Taxiway;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiwaySegment;
import de.xatc.controllerclient.xdataparser.tools.AptFilesTools;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jdesktop.swingx.mapviewer.GeoPosition;





/**
 * This Class contains all tools processing navpoints
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class NavPointHelpers {

    /**
     * convert latitude and longitude to pixel x/y coordinates
     *
     * @param t
     * @return new processed Navpoint
     */
    public static NavPoint convertToXY(NavPoint t) {

        if (XHSConfig.getMainFrame().getMainPanel() != null) {
            XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().revalidate();
            t.setMapWidth(XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().getSize().width);
            t.setMapHeight(XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().getSize().height);
        } else {
            t.setMapWidth(800);
            t.setMapHeight(800);

        }

        XHSConfig.debugMessage("converting NAVPOINT To XY", DebugMessageLevel.DEBUG);
        //first convert to decimal
        XHSConfig.debugMessage("covnerting to decimal", DebugMessageLevel.DEBUG);
        XHSConfig.debugMessage("PARSING COORDS: " + t.getLatitudeSTring() + "/" + t.getLongitudeSTring(), DebugMessageLevel.DEBUG);
        String[] splitX = t.getLongitudeSTring().split("\\.");
        String[] splitY = t.getLatitudeSTring().split("\\.");
        if (splitX.length > 2) {
            XHSConfig.debugMessage("PARSING HOURS/MIN/SECS", DebugMessageLevel.DEBUG);
            String xDeg = splitX[0];
            XHSConfig.debugMessage("xDEG: " + xDeg, DebugMessageLevel.DEBUG);
            String xMin = splitX[1];
            XHSConfig.debugMessage("XMin: " + xMin, DebugMessageLevel.DEBUG);
            String xSec = splitX[2];
            XHSConfig.debugMessage("xSec: " + xSec, DebugMessageLevel.DEBUG);
            String xSecDec = splitX[3];
            XHSConfig.debugMessage("xSECDEC: " + xSecDec, DebugMessageLevel.DEBUG);

            String yDeg = splitY[0];
            String yMin = splitY[1];
            String ySec = splitY[2];
            String ySecDec = splitY[3];

            t.setLatitudedouble(Float.parseFloat(xDeg) + Float.parseFloat(xMin) / 60 + Float.parseFloat(xSec + "." + xSecDec) / 3600);
            t.setLongitudeDouble(Float.parseFloat(yDeg) + Float.parseFloat(yMin) / 60 + Float.parseFloat(ySec + "." + ySecDec) / 3600);

            XHSConfig.debugMessage("DECIMAL: " + t.getLongitudeDouble() + "/" + t.getLatitudedouble(), DebugMessageLevel.DEBUG);

            t.setX((float) t.getLatitudedouble() / t.getMapWidth());
            t.setY((float) (t.getLongitudeDouble() / t.getMapHeight()));

        } else {
            XHSConfig.debugMessage("SETTING MANUALLY", DebugMessageLevel.DEBUG);

            t.setLatitudedouble(Float.parseFloat(t.getLatitudeSTring()));
            t.setLongitudeDouble(Float.parseFloat(t.getLongitudeSTring()));

            t.setX((float) Float.parseFloat(t.getLatitudeSTring()) / t.getMapWidth());
            t.setY((float) Float.parseFloat(t.getLongitudeSTring()) / t.getMapHeight());

        }

        XHSConfig.debugMessage("XY: " + t.getX() + " " + t.getY(), DebugMessageLevel.DEBUG);
        return t;
    }

    /**
     * find mouse position and capture x/y coordinates
     *
     * @param p
     * @param t
     * @return new processed NavPoint
     */
    public static NavPoint mouseToGeoPos(Point p, NavPoint t) {

        XHSConfig.debugMessage("Generating mouse to GeoPos", DebugMessageLevel.DEBUG);
        XHSConfig.debugMessage("Got Mouse PosX " + p.getX(), DebugMessageLevel.DEBUG);

        Rectangle rect = XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().getMainMap().getViewportBounds();

        p.translate(+rect.x, +rect.y);

        t.setPoint2D(p);

        return point2DToGeoPos(t);

    }

    /**
     * this method calculates the openstreetmap geoposition out of the navpoint
     * long and lat
     *
     * @param t
     * @return new processed Navpoint
     */
    public static NavPoint calcGeoPosition(NavPoint t) {

        t.setGeoPos(new GeoPosition(Double.parseDouble(t.getLatitudeSTring()), Double.parseDouble(t.getLongitudeSTring())));
        return t;

    }

    /**
     * transform a 2d point (from mouse)to openstreetmap geoposition
     *
     * @param t
     * @return ne processed Navpoint
     */
    public static NavPoint point2DToGeoPos(NavPoint t) {
        XHSConfig.debugMessage("Converting Point 2D to GEOPOS", DebugMessageLevel.DEBUG);
        XHSConfig.debugMessage("POINT2d in Navpoint is: " + t.getPoint2D(), DebugMessageLevel.DEBUG);

        t.setGeoPos(XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().getMainMap().getTileFactory().pixelToGeo(t.getPoint2D(), XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().getMainMap().getZoom()));

        XHSConfig.debugMessage("GEOPOS LONG= " + t.getGeoPos().getLongitude(), DebugMessageLevel.DEBUG);
        return t;
    }

    /**
     * translate geo position to screen pixel
     *
     * @param t
     * @return processed navpoint
     */
    public static NavPoint geoPosToPixel(NavPoint t) {

        t.setPoint2D(XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().getMainMap().getTileFactory().geoToPixel(t.getGeoPos(), XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().getMainMap().getZoom()));
        return t;

    }

    /**
     * format the frequency string to paint beside the airport
     *
     * @param s
     * @return processed String
     */
    public static String formatFrequency(String s) {

        String first = s.substring(0, 3);
        String end = s.substring(3);
        return first + "." + end;

    }

    /**
     * find the closest taxi network node from given Navpoint inside given
     * aptairportmodel
     *
     * @param navpoint
     * @param model
     * @return TaxinetworkNode closest to Navpoint
     */
    public static TaxiNetworkNode findClosestTaxiNetworkNode(NavPoint navpoint, AptAirportModel model) {

        TaxiNetworkNode closestNode = null;
        double distance = 10000000;

        navpoint = NavPointHelpers.convertToXY(navpoint);

        for (Map.Entry<Integer, TaxiNetworkNode> entry : model.getTaxiNetworkNodes().entrySet()) {

            NavPoint toPoint = entry.getValue().getNavPoint();
            toPoint = NavPointHelpers.convertToXY(toPoint);

            double currentDistance = NavPointHelpers.calcDistanceOfTwoNavPoints(navpoint, toPoint);
            if (currentDistance < distance) {
                distance = currentDistance;
                closestNode = entry.getValue();

            }

            //System.out.println(NavPointHelpers.calcDistanceOfTwoNavPoints(navpoint, toPoint));
        }
       // System.out.println("Closest Distance: " + distance);
        if (closestNode != null) {
            //System.out.println(closestNode.getNavPoint());
        }
        return closestNode;
    }

    /**
     * find closest taxi network node inside a given taxiway and given airport
     * model
     *
     * @param p
     * @param t
     * @return
     */
    public static TaxiNetworkNode findClosestNodeOfGivenTaxiway(NavPoint p, Taxiway t) {

        TaxiNetworkNode closestNode = null;
        double distance = 10000000;

        p = NavPointHelpers.convertToXY(p);

        for (TaxiwaySegment s : t.getSegments()) {

            List<TaxiNetworkNode> nodes = new ArrayList<>();
            nodes.add(s.getFromNode());
            nodes.add(s.getToNode());

            for (TaxiNetworkNode n : nodes) {

                NavPoint toPoint = n.getNavPoint();
                toPoint = NavPointHelpers.convertToXY(toPoint);

                double currentDistance = NavPointHelpers.calcDistanceOfTwoNavPoints(p, toPoint);
                if (currentDistance < distance) {
                    distance = currentDistance;
                    closestNode = n;

                }
            }
            //System.out.println(NavPointHelpers.calcDistanceOfTwoNavPoints(navpoint, toPoint));

        }
       // System.out.println("Closest Distance: " + distance);
        if (closestNode != null) {
           // System.out.println(closestNode.getNavPoint());
        }
        return closestNode;

    }

    /**
     * calculate a distance of two Navpoints
     *
     * @param from
     * @param to
     * @return
     */
    public static double calcDistanceOfTwoNavPoints(NavPoint from, NavPoint to) {

        from = NavPointHelpers.calcGeoPosition(from);
        to = NavPointHelpers.calcGeoPosition(to);

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(to.getLatitudedouble() - from.getLatitudedouble());
        double dLng = Math.toRadians(to.getLongitudeDouble() - from.getLongitudeDouble());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(from.getLatitudedouble())) * Math.cos(Math.toRadians(to.getLatitudedouble()))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (double) (earthRadius * c);

        return dist;

    }

    /**
     * find a connection point of a network by a taxinetwork node id
     *
     * @param ID
     * @param model
     * @return
     */
    public static TaxiNetworkNode findConnPointNetworkNodeByID(int ID, AptAirportModel model) {

        for (TaxiNetworkNode n : model.getConnPoints()) {
            if (n.getId() == ID) {
                return n;
            }
        }
        return null;

    }

    /**
     * identify a taxiway by a given network node
     *
     * @param n
     * @param model
     * @return
     */
    public static Taxiway findTaxiwayByTaxiNetworkNode(TaxiNetworkNode n, AptAirportModel model) {

        for (Entry<String, Taxiway> entry : model.getTaxiways().entrySet()) {

            for (TaxiwaySegment s : entry.getValue().getSegments()) {

                if (s.getFromNode() == n || s.getToNode() == n) {
                    return entry.getValue();
                }
            }

        }
        return null;
    }

    /**
     * This method identifies if a TaxiNetworknode is a from or a to node. This
     * depends on the value that there is only one ConnectionPoint attached If
     * there are the given node is a node in the middle of two nodes both
     * options are possible.
     *
     * @param n - the start node
     * @param model - current parsed AptAirportModel
     * @return - Given Node is a FromPoint = 1; Given Node is a ToPoint = 2; Not
     * found = -1
     */
    public static int isForwardPath(TaxiNetworkNode n, AptAirportModel model) {

        for (Entry<String, Taxiway> entry : model.getTaxiways().entrySet()) {

            for (TaxiwaySegment s : entry.getValue().getSegments()) {

                if (s.getFromNode().getId() == n.getId()) {
                    return 1;
                } else if (s.getToNode().getId() == n.getId()) {
                    return 2;
                }
            }

        }

        return -1;
    }

    /**
     * count how many nodes are connected by point
     *
     * @param id
     * @param model
     * @return
     */
    public static int countConnectionsOfTaxiNetworkNode(int id, AptAirportModel model) {

        int connPointsCounter = 0;

        for (Map.Entry<String, Taxiway> taxiway : model.getTaxiways().entrySet()) {

            for (TaxiwaySegment s : taxiway.getValue().getSegments()) {

                if (s.getFromPointID() == id || s.getToPointID() == id) {
                    connPointsCounter++;
                }

            }

        }

        return connPointsCounter;

    }

    /**
     * navigate map to an airport identified by a Navpoint
     *
     * @param p
     */
    public static void goToAirport(NavPoint p) {

        p = NavPointHelpers.calcGeoPosition(p);
        XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().setZoom(6);
        XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().setCenterPosition(p.getGeoPos());

        XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().revalidate();
        XHSConfig.getMainFrame().getMainPanel().getMapPanel().getJkit().repaint();

    }

    /**
     * navigate map to an airport identified by an AptAirport Entity
     *
     * @param a
     */
    public static void goToAirport(AptAirport a) {

        try {
            NavPoint p = AptFilesTools.getAirportInitialPosition(AptFilesTools.extractAirportFromFile(a.getFullFileName(), a.getLineNumberStart()));
            goToAirport(p);
        } catch (Exception ex) {
            XHSConfig.debugMessage("Could not goToAirport " + ex.getLocalizedMessage(), DebugMessageLevel.EXCEPTION);
            SwingTools.alertWindow("Could not load aiportdata. Needed data items not found. Part of custom scenery?", XHSConfig.getMainFrame());
        }

    }

    public static boolean isFrequencyStringValid(String freq) {
        
        if (freq.matches("^\\d{3}\\.\\d{3}$") || freq.matches("^\\d{3}\\.\\d{2}$")) {
            return true;
        }
        
        return false;
    }
    

    
    
}
