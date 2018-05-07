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



import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.navigation.NavLine;
import de.xatc.controllerclient.navigation.NavPoint;
import de.xatc.controllerclient.navigation.NavPoligon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.Painter;

/**
 * this overlay painter draws airport lines to map
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AirportChartPainter implements Painter<JXMapViewer> {

     private static final Logger LOG = Logger.getLogger(AirportChartPainter.class.getName());
    /**
     * what colors do we draw with
     */
    private Color color = Color.RED;

    /**
     * do we want anti alias
     */
    private boolean antiAlias = true;

    /**
     * the label container
     */
    private List<NavPoint> labelList = new ArrayList<>();

    /**
     * runway lines container
     */
    private List<NavPoligon> runwayLines = new ArrayList<>();

    /**
     * taxilines container
     */
    private List<NavPoligon> taxiWayLines = new ArrayList<>();

    /**
     * parking labels container
     */
    private List<NavLine> parkingLineList = new ArrayList<>();

    /**
     * helpers container
     */
    private List<NavPoint> helpersList = new ArrayList<>();

    /**
     *
     */
    public AirportChartPainter() {

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
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.setColor(Color.RED);

        Font font = new Font("SansSerif", Font.BOLD, 12);
        g.setFont(font);

        this.drawLabels(g, map);
        this.drawHelpersList(g, map);
        this.drawTaxiWays(g, map);
        this.drawParkings(g, map);
        this.drawRunways(g, map);

        g.dispose();
    }

    /**
     * draw labels
     *
     * @param g
     * @param map
     */
    private void drawLabels(Graphics2D g, JXMapViewer map) {

        for (NavPoint p : this.labelList) {
            LOG.debug("DRAW LABEL: " + p.getName());

            Point2D text2D = map.getTileFactory().geoToPixel(p.getGeoPos(), map.getZoom());

            LOG.debug(text2D.getX() + " " + text2D.getY());
            g.setColor(Color.BLUE);
            g.drawString(p.getName(), (int) text2D.getX(), (int) text2D.getY());

        }

    }

    /**
     * draw runways
     *
     * @param g
     * @param map
     */
    public void drawRunways(Graphics2D g, JXMapViewer map) {

        boolean isFirst = true;
        NavPoint oldNavPoint = null;
        g.setColor(Color.RED);
        for (NavPoligon poly : this.runwayLines) {
            for (NavPoint p : poly.getNavPointList()) {
                Point2D navPoint2D = map.getTileFactory().geoToPixel(p.getGeoPos(), map.getZoom());
                if (isFirst) {

                    g.fillRect((int) navPoint2D.getX() - 5, (int) navPoint2D.getY() - 5, 10, 10);
                    oldNavPoint = p;
                    isFirst = false;
                    continue;
                }

                Point2D oldNavPoint2D = map.getTileFactory().geoToPixel(oldNavPoint.getGeoPos(), map.getZoom());

                g.drawLine((int) oldNavPoint2D.getX(), (int) oldNavPoint2D.getY(), (int) navPoint2D.getX(), (int) navPoint2D.getY());

                g.fillRect((int) navPoint2D.getX() - 5, (int) navPoint2D.getY() - 5, 10, 10);
                oldNavPoint = p;
            }
            isFirst = true;
        }
    }

    /**
     * draw helpers
     *
     * @param g
     * @param map
     */
    public void drawHelpersList(Graphics2D g, JXMapViewer map) {

        boolean isFirst = true;
        NavPoint oldNavPoint = null;
        g.setColor(Color.GREEN);
        for (NavPoint p : this.helpersList) {

            Point2D navPoint2D = map.getTileFactory().geoToPixel(p.getGeoPos(), map.getZoom());
            if (isFirst) {

                g.fillRect((int) navPoint2D.getX() - 5, (int) navPoint2D.getY() - 5, 10, 10);
                oldNavPoint = p;
                isFirst = false;
                continue;
            }

            Point2D oldNavPoint2D = map.getTileFactory().geoToPixel(oldNavPoint.getGeoPos(), map.getZoom());

            g.drawLine((int) oldNavPoint2D.getX(), (int) oldNavPoint2D.getY(), (int) navPoint2D.getX(), (int) navPoint2D.getY());

            g.fillRect((int) navPoint2D.getX() - 5, (int) navPoint2D.getY() - 5, 10, 10);
            oldNavPoint = p;

        }
    }

    /**
     * draw parking positions
     *
     * @param g
     * @param map
     */
    public void drawParkings(Graphics2D g, JXMapViewer map) {

        for (NavLine pd : this.parkingLineList) {

            NavPoint from = pd.getNavPointFrom();
            NavPoint to = pd.getNavPointTo();

            Point2D from2D = map.getTileFactory().geoToPixel(from.getGeoPos(), map.getZoom());
            Point2D to2D = map.getTileFactory().geoToPixel(to.getGeoPos(), map.getZoom());

            //FMCConfig.debugMessage("LINEDRAW XY : " + from2D.getX() + " " + from2D.getY());
            g.setColor(Color.BLUE);
            g.drawLine((int) from2D.getX(), (int) from2D.getY(), (int) to2D.getX(), (int) to2D.getY());
            g.drawString(from.getName(), (int) from2D.getX(), (int) from2D.getY());
        }
    }

    /**
     * draw taxiways
     *
     * @param g
     * @param map
     */
    public void drawTaxiWays(Graphics2D g, JXMapViewer map) {

        boolean isFirst = true;
        NavPoint oldNavPoint = null;
        g.setColor(Color.MAGENTA);
        for (NavPoligon poly : this.taxiWayLines) {
            for (NavPoint p : poly.getNavPointList()) {
                Point2D navPoint2D = map.getTileFactory().geoToPixel(p.getGeoPos(), map.getZoom());
                if (isFirst) {

                    g.fillRect((int) navPoint2D.getX() - 5, (int) navPoint2D.getY() - 5, 10, 10);
                    oldNavPoint = p;
                    isFirst = false;
                    continue;
                }

                Point2D oldNavPoint2D = map.getTileFactory().geoToPixel(oldNavPoint.getGeoPos(), map.getZoom());

                g.drawLine((int) oldNavPoint2D.getX(), (int) oldNavPoint2D.getY(), (int) navPoint2D.getX(), (int) navPoint2D.getY());

                g.fillRect((int) navPoint2D.getX() - 5, (int) navPoint2D.getY() - 5, 10, 10);
                oldNavPoint = p;
            }
            isFirst = true;
        }
    }

    /**
     * clear all containers
     */
    public void clearLists() {

        this.helpersList = new ArrayList<>();
        this.labelList = new ArrayList<>();
        this.parkingLineList = new ArrayList<>();
        this.runwayLines = new ArrayList<>();
        this.taxiWayLines = new ArrayList<>();

    }

    /**
     *
     * @param list
     */
    public void setLabelList(List<NavPoint> list) {

        this.labelList = list;
    }

    /**
     *
     * @return
     */
    public List<NavPoint> getLabelList() {
        return this.labelList;
    }

    /**
     *
     * @return
     */
    public List<NavPoligon> getRunwayLines() {
        return runwayLines;
    }

    /**
     *
     * @param runwayLines
     */
    public void setRunwayLines(List<NavPoligon> runwayLines) {
        this.runwayLines = runwayLines;
    }

    /**
     *
     * @return
     */
    public List<NavPoligon> getTaxiWayLines() {
        return taxiWayLines;
    }

    /**
     *
     * @param taxiWayLines
     */
    public void setTaxiWayLines(List<NavPoligon> taxiWayLines) {
        this.taxiWayLines = taxiWayLines;
    }

    /**
     *
     * @return
     */
    public List<NavLine> getParkingLineList() {
        return parkingLineList;
    }

    /**
     *
     * @param parkingLineList
     */
    public void setParkingLineList(List<NavLine> parkingLineList) {
        this.parkingLineList = parkingLineList;
    }

    /**
     *
     * @return
     */
    public List<NavPoint> getHelpersList() {
        return helpersList;
    }

    /**
     *
     * @param helpersList
     */
    public void setHelpersList(List<NavPoint> helpersList) {
        this.helpersList = helpersList;
    }

}
