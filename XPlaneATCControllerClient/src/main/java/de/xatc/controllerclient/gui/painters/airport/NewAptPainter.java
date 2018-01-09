/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2015
 * Have fun!
 *
 */
package de.xatc.controllerclient.gui.painters.airport;

import de.twyhelper.tools.FMCConfig;
import de.xatc.commons.db.sharedentities.aptmodel.NavDataEntity;
import de.xatc.controllerclient.navigation.NavPoint;
import de.xatc.controllerclient.xdataparser.aptmodel.AptAirportModel;
import de.xatc.controllerclient.xdataparser.aptmodel.AptChunkModel;
import de.xatc.controllerclient.xdataparser.aptmodel.AptPavementModel;
import de.xatc.controllerclient.xdataparser.aptmodel.AptRunwayModel;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiNetworkNode;
import de.xatc.controllerclient.xdataparser.aptmodel.Taxiway;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiwaySegment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.Map.Entry;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.Painter;

/**
 * this overlay painter draws airport lines to map from apt files
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class NewAptPainter implements Painter<JXMapViewer> {

    /**
     * do we want anti alias
     */
    private boolean antiAlias = true;

    /**
     * the label container
     */
    private AptAirportModel model;

    /**
     * segment counter inside a taxiway
     */
    private int segmentCounter = 0;

    /**
     * do we paint runways?
     */
    private boolean paintRunways = false;
    /**
     * do we paint pavements
     */
    private boolean paintPavements = false;
    /**
     * do we paint taxiways
     */
    private boolean paintTaxiways = false;
    /**
     * paint connectionpoints (not used in production. only for debugging)
     */
    private boolean paintConnectionPoints = false;
    /**
     * paint airport ils freqs
     */
    private boolean paintFrequencies = false;
    /**
     * paint parkings
     */
    private boolean paintParkings = false;

    /**
     * constructor
     */
    public NewAptPainter() {
        //nothing to do here
    }

    /**
     * paint it
     *
     * @param g
     * @param map
     * @param w
     * @param h
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {

        if (this.model == null) {
            return;
        }

        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.setColor(Color.RED);

        Font font = new Font("SansSerif", Font.PLAIN, 12);
        g.setFont(font);

        this.drawAirports(g, map);

        g.dispose();
    }

    /**
     * draw the airport
     *
     * @param g
     * @param map
     */
    private void drawAirports(Graphics2D g, JXMapViewer map) {

        if (model.getMostNorthernPoint() == null) {
            return;
        }
        
        Point2D airportLabelPoint = map.getTileFactory().geoToPixel(model.getMostNorthernPoint().getGeoPos(), map.getZoom());

        g.setColor(Color.BLACK);
        int labelY = 70;

        Font font = new Font("SansSerif", Font.BOLD, 15);
        g.setFont(font);
        g.drawString(model.getIcao() + " - " + model.getAirportName(), (int) airportLabelPoint.getX() + 10, (int) airportLabelPoint.getY() + labelY / map.getZoom());

        font = new Font("SansSerif", Font.PLAIN, 12);
        g.setFont(font);

        if (this.paintFrequencies) {
            this.drawFreqs(g, airportLabelPoint);
        }

        if (model.getParkings().size() > 0 && this.paintParkings) {
            this.drawParkings(g, map);
        }

        if (model.getTaxiNetworkNodes().size() > 0 && this.paintTaxiways) {
            this.drawTaxiNetwork(g, map);
        }

        if (this.paintPavements) {
            this.drawPavements(g, map);
        }
        if (this.paintRunways) {
            this.drawRunways(g, map);
        }
        if (this.paintConnectionPoints) {
            this.drawConnectionPoints(g, map);
        }

    }

    /**
     * draw the taxinetwork
     *
     * @param g
     * @param map
     */
    private void drawTaxiNetwork(Graphics2D g, JXMapViewer map) {

        g.setColor(Color.BLACK);

        for (Entry<String, Taxiway> entry : model.getTaxiways().entrySet()) {

//            if (!entry.getKey().equals("H")) {
//                continue;
//            }
            Taxiway b = entry.getValue();

            for (TaxiwaySegment s : b.getSegments()) {
                this.segmentCounter++;

                Point2D fromPoint2D = map.getTileFactory().geoToPixel(s.getFromNode().getNavPoint().getGeoPos(), map.getZoom());
                Point2D toPoint2D = map.getTileFactory().geoToPixel(s.getToNode().getNavPoint().getGeoPos(), map.getZoom());

                Point2D midPoint = this.calcLineMidpoint(fromPoint2D, toPoint2D);
                Font font = new Font("SansSerif", Font.PLAIN, 12);
                g.setFont(font);
                g.drawString(s.getName(), (int) midPoint.getX(), (int) midPoint.getY());
                font = new Font("SansSerif", Font.PLAIN, 12);
                g.setFont(font);

                g.drawLine((int) fromPoint2D.getX(), (int) fromPoint2D.getY(), (int) toPoint2D.getX(), (int) toPoint2D.getY());
                //  g.drawOval((int) fromPoint2D.getX(), (int) fromPoint2D.getY(), 5, 5);

                if (FMCConfig.isDoDebug()) {
                    //g.drawString(s.getToNode().getConnNames().toString(), (int) fromPoint2D.getX() + 20, (int) fromPoint2D.getY());

//                    String connStringF = "";
//                    for (TaxiNetworkNode connNode : s.getFromNode().getConnectionPoints()) {
//                        connStringF += " " + connNode.getId();
//                    }
//
//                    String connStringT = "";
//                    for (TaxiNetworkNode connNode : s.getToNode().getConnectionPoints()) {
//                        connStringT += " " + connNode.getId();
//                    }
                    //g.drawString("-> " + connStringF, (int) toPoint2D.getX() + 100, (int) toPoint2D.getY());
                    //g.drawString("-> " + connStringT, (int) fromPoint2D.getX() + 100, (int) fromPoint2D.getY());
                    if (FMCConfig.isToggleSelectTaxiways()) {
                        g.drawString(s.getFromNode().getId() + "", (int) fromPoint2D.getX(), (int) fromPoint2D.getY());
                        g.drawString(s.getToNode().getId() + "", (int) toPoint2D.getX(), (int) toPoint2D.getY());
                    }
                }
            }

        }

    }

    /**
     * draw Connection Points
     *
     * @param g
     * @param map
     */
    private void drawConnectionPoints(Graphics2D g, JXMapViewer map) {

        for (TaxiNetworkNode n : this.model.getConnPoints()) {

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1));

            Point2D point2D = map.getTileFactory().geoToPixel(n.getNavPoint().getGeoPos(), map.getZoom());
            g.drawOval((int) point2D.getX(), (int) point2D.getY(), 5, 5);
            g.drawString(n.getConnNames().toString(), (int) point2D.getX(), (int) point2D.getY());

        }

    }

    /**
     * draw airport ils frequencies
     *
     * @param g
     * @param point
     */
    private void drawFreqs(Graphics2D g, Point2D point) {

        if (model.getNavData().size() > 0) {
            int yLabel = (int) point.getY() + 30;
            for (NavDataEntity navData : model.getNavData()) {
                yLabel += 20;

                g.drawString(navData.getRunway() + " - " + navData.getFreq() + " - " + navData.getType(), (int) point.getX() + 10, yLabel);

            }

        }

    }

    /**
     * draw parking positions
     *
     * @param g
     * @param map
     */
    private void drawParkings(Graphics2D g, JXMapViewer map) {

        g.setColor(Color.BLUE);
        for (NavPoint p : model.getParkings()) {

            Point2D parkingPoint = map.getTileFactory().geoToPixel(p.getGeoPos(), map.getZoom());

            g.drawOval((int) parkingPoint.getX(), (int) parkingPoint.getY(), 5, 5);
            g.drawString(p.getName(), (int) parkingPoint.getX() + 4, (int) parkingPoint.getY());

        }

    }

    /**
     * draw runways
     *
     * @param g
     * @param map
     */
    private void drawRunways(Graphics2D g, JXMapViewer map) {

        for (AptRunwayModel r : model.getRunwayList()) {

            switch (r.getType()) {

                case 100:
                    NavPoint p1 = r.getP1();
                    NavPoint p2 = r.getP2();

                    Point2D p12d = map.getTileFactory().geoToPixel(p1.getGeoPos(), map.getZoom());
                    Point2D p22d = map.getTileFactory().geoToPixel(p2.getGeoPos(), map.getZoom());
                    g.setColor(Color.RED);
                    g.setStroke(new BasicStroke(r.getWidth() / map.getZoom()));
                    g.drawLine((int) p12d.getX(), (int) p12d.getY(), (int) p22d.getX(), (int) p22d.getY());
                    g.setColor(Color.RED);
                    g.drawString(r.getNameP1(), (int) p12d.getX() + 10, (int) p12d.getY() + 10);
                    g.drawString(r.getNameP2(), (int) p22d.getX() + 10, (int) p22d.getY() + 10);

                    break;
                case 102:
                    //helipad
                    NavPoint padcenter = r.getP1();
                    Point2D padpoint = map.getTileFactory().geoToPixel(padcenter.getGeoPos(), map.getZoom());
                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.DARK_GRAY);
                    int radius = 20 / map.getZoom();
                    int diameter = radius * 2;

                    g.drawOval((int) padpoint.getX() - radius, (int) padpoint.getY() - radius, diameter, diameter);
                    g.drawString(r.getNameP1(), (int) padpoint.getX(), (int) padpoint.getY() - 10);
                    break;
                case 101:
                    //sea
                    NavPoint waterP1 = r.getP1();
                    NavPoint waterP2 = r.getP2();

                    Point2D waterP12d = map.getTileFactory().geoToPixel(waterP1.getGeoPos(), map.getZoom());
                    Point2D waterP22d = map.getTileFactory().geoToPixel(waterP2.getGeoPos(), map.getZoom());
                    g.setColor(Color.BLUE);
                    g.setStroke(new BasicStroke(r.getWidth() / map.getZoom()));
                    g.drawLine((int) waterP12d.getX(), (int) waterP12d.getY(), (int) waterP22d.getX(), (int) waterP22d.getY());
                    g.drawString("Water " + r.getNameP1(), (int) waterP12d.getX() + 10, (int) waterP12d.getY() + 10);
                    g.drawString("Water " + r.getNameP2(), (int) waterP22d.getX() + 10, (int) waterP22d.getY() + 10);
                    break;
            }

        }

    }

    /**
     * draw pavements
     *
     * @param g
     * @param map
     */
    private void drawPavements(Graphics2D g, JXMapViewer map) {

        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(1));

        for (AptPavementModel p : model.getPavement()) {

            for (AptChunkModel c : p.getChunkList()) {

                NavPoint oldNavpoint = null;
                boolean isFirst = true;

                for (NavPoint nav : c.getNavPointList()) {

                    if (isFirst) {

                        oldNavpoint = nav;
                        isFirst = false;
                        if (p.getName() != null) {
                            Point2D labelPoint = map.getTileFactory().geoToPixel(oldNavpoint.getGeoPos(), map.getZoom());

                        }
                        continue;
                    }
                    Point2D chunkPoint1 = map.getTileFactory().geoToPixel(oldNavpoint.getGeoPos(), map.getZoom());
                    Point2D chunkPoint2 = map.getTileFactory().geoToPixel(nav.getGeoPos(), map.getZoom());

                    g.drawLine((int) chunkPoint1.getX(), (int) chunkPoint1.getY(), (int) chunkPoint2.getX(), (int) chunkPoint2.getY());
                    oldNavpoint = nav;

                }
                isFirst = true;

            }

        }

    }

    /**
     * clear all containers
     */
    public void clearLists() {

        this.paintConnectionPoints = false;
        this.paintFrequencies = false;
        this.paintParkings = false;
        this.paintPavements = false;
        this.paintRunways = false;
        this.paintTaxiways = false;
        this.model = null;
    }

    /**
     * calculate the middlepoint of a line
     *
     * @param start
     * @param end
     * @return
     */
    private Point2D calcLineMidpoint(Point2D start, Point2D end) {

        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();

        double newX = (x1 + x2) / 2;
        double newY = (y1 + y2) / 2;

        Point2D returnValue = new Point();
        returnValue.setLocation(newX, newY);
        return returnValue;

    }

    /**
     * getter
     *
     * @return
     */
    public AptAirportModel getModel() {
        return model;
    }

    /**
     * setter
     *
     * @param model
     */
    public void setModel(AptAirportModel model) {

        clearLists();
        this.model = model;

    }

    /**
     * getter
     *
     * @return
     */
    public boolean isAntiAlias() {
        return antiAlias;
    }

    /**
     * setter
     *
     * @param antiAlias
     */
    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    /**
     * getter
     *
     * @return
     */
    public int getSegmentCounter() {
        return segmentCounter;
    }

    /**
     * setter
     *
     * @param segmentCounter
     */
    public void setSegmentCounter(int segmentCounter) {
        this.segmentCounter = segmentCounter;
    }

    /**
     * getter
     *
     * @return
     */
    public boolean isPaintRunways() {
        return paintRunways;
    }

    /**
     * getter
     *
     * @param paintRunways
     */
    public void setPaintRunways(boolean paintRunways) {
        this.paintRunways = paintRunways;
    }

    /**
     * setter
     *
     * @return
     */
    public boolean isPaintPavements() {
        return paintPavements;
    }

    /**
     * getter
     *
     * @param paintPavements
     */
    public void setPaintPavements(boolean paintPavements) {
        this.paintPavements = paintPavements;
    }

    /**
     * getter
     *
     * @return
     */
    public boolean isPaintTaxiways() {
        return paintTaxiways;
    }

    /**
     * setter
     *
     * @param paintTaxiways
     */
    public void setPaintTaxiways(boolean paintTaxiways) {
        this.paintTaxiways = paintTaxiways;
    }

    /**
     * getter
     *
     * @return
     */
    public boolean isPaintConnectionPoints() {
        return paintConnectionPoints;
    }

    /**
     * setter
     *
     * @param paintConnectionPoints
     */
    public void setPaintConnectionPoints(boolean paintConnectionPoints) {
        this.paintConnectionPoints = paintConnectionPoints;
    }

    /**
     * getter
     *
     * @return
     */
    public boolean isPaintFrequencies() {
        return paintFrequencies;
    }

    /**
     * setter
     *
     * @param paintFrequencies
     */
    public void setPaintFrequencies(boolean paintFrequencies) {
        this.paintFrequencies = paintFrequencies;
    }

    /**
     * getter
     *
     * @return
     */
    public boolean isPaintParkings() {
        return paintParkings;
    }

    /**
     * setter
     *
     * @param paintParkings
     */
    public void setPaintParkings(boolean paintParkings) {
        this.paintParkings = paintParkings;
    }

}
