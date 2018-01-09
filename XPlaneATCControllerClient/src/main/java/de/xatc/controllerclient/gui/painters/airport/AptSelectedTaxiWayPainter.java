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

import de.xatc.controllerclient.navigation.NavPoint;
import de.xatc.controllerclient.navigation.NavPointHelpers;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiNetworkNode;
import de.xatc.controllerclient.xdataparser.aptmodel.Taxiway;
import de.xatc.controllerclient.xdataparser.aptmodel.TaxiwaySegment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.Painter;

/**
 * this overlay painter draws taxiways from sector files or airport files on
 * map.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com) //TODO - currently not used,
 * due to the wait for proper taxi network data.
 */
@Deprecated
public class AptSelectedTaxiWayPainter implements Painter<JXMapViewer> {

    /**
     * what color do we want
     */
    private Color color = Color.RED;

    /**
     * use anti alias
     */
    private boolean antiAlias = true;

    private List<TaxiNetworkNode> forwardPath = new ArrayList<>();
    private List<TaxiNetworkNode> backwardPath = new ArrayList<>();
    private boolean drawForwardNodes = false;
    private boolean drawBackwardNodes = false;

    private List<TaxiNetworkNode> paintedNodes = new ArrayList<>();

    private Taxiway taxiway = null;

    /**
     * constructor
     */
    private TaxiNetworkNode closestTaxiwayNode;

    public AptSelectedTaxiWayPainter() {

    }

    /**
     * paintit
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

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        drawRoute(g, map);
        drawForwardNodes(g, map);
        this.drawBackwardNodes(g, map);
        drawClosestNode(g, map);
        drawMouseOverTaxiway(g, map);

    }

    private void drawMouseOverTaxiway(Graphics2D g, JXMapViewer map) {

        if (this.taxiway == null) {
            return;
        }

        for (TaxiwaySegment s : this.taxiway.getSegments()) {

            TaxiNetworkNode f = s.getFromNode();
            TaxiNetworkNode t = s.getToNode();
            NavPoint fn = f.getNavPoint();
            NavPoint tn = t.getNavPoint();
            fn = NavPointHelpers.calcGeoPosition(fn);
            tn = NavPointHelpers.calcGeoPosition(tn);
            Point2D pf = map.getTileFactory().geoToPixel(fn.getGeoPos(), map.getZoom());
            Point2D pt = map.getTileFactory().geoToPixel(tn.getGeoPos(), map.getZoom());
            g.setColor(Color.GREEN);
            g.drawLine((int) pf.getX(), (int) pf.getY(), (int) pt.getX(), (int) pt.getY());

        }

    }

    /**
     * draw route
     *
     * @param g
     * @param map
     */
    private void drawRoute(Graphics2D g, JXMapViewer map) {

        if (this.paintedNodes.isEmpty()) {
            return;
        }

        TaxiNetworkNode toNode = null;
        TaxiNetworkNode fromNode = null;
        int counter = 0;
        for (TaxiNetworkNode n : this.paintedNodes) {

            if (counter == 0) {
                fromNode = n;
                counter++;
                continue;
            }
            toNode = n;
            g.setColor(Color.RED);
            Point2D fromP = map.getTileFactory().geoToPixel(fromNode.getNavPoint().getGeoPos(), map.getZoom());
            Point2D toP = map.getTileFactory().geoToPixel(toNode.getNavPoint().getGeoPos(), map.getZoom());
            g.drawLine((int) fromP.getX(), (int) fromP.getY(), (int) toP.getX(), (int) toP.getY());
            // g.drawString("DOOF",(int) fromP.getX(), (int) fromP.getY());
            fromNode = toNode;

        }

    }

    /**
     * draw route
     *
     * @param g
     * @param map
     */
    private void drawBackwardNodes(Graphics2D g, JXMapViewer map) {

        if (!this.drawBackwardNodes) {
            return;
        }

        if (this.backwardPath.isEmpty()) {
            return;
        }

        TaxiNetworkNode toNode = null;
        TaxiNetworkNode fromNode = null;
        int counter = 0;
        for (TaxiNetworkNode n : this.backwardPath) {

            if (counter == 0) {
                fromNode = n;
                counter++;
                continue;
            }
            toNode = n;
            g.setColor(Color.GREEN);
            Point2D fromP = map.getTileFactory().geoToPixel(fromNode.getNavPoint().getGeoPos(), map.getZoom());
            Point2D toP = map.getTileFactory().geoToPixel(toNode.getNavPoint().getGeoPos(), map.getZoom());
            g.drawLine((int) fromP.getX(), (int) fromP.getY(), (int) toP.getX(), (int) toP.getY());
            fromNode = toNode;

        }

    }

    /**
     * draw route
     *
     * @param g
     * @param map
     */
    private void drawForwardNodes(Graphics2D g, JXMapViewer map) {

        if (!this.drawForwardNodes) {
            return;
        }

        if (this.forwardPath.isEmpty()) {
            return;
        }

        TaxiNetworkNode toNode = null;
        TaxiNetworkNode fromNode = null;
        int counter = 0;
        for (TaxiNetworkNode n : this.forwardPath) {

            if (counter == 0) {
                fromNode = n;
                counter++;
                continue;
            }
            toNode = n;
            g.setColor(Color.GREEN);
            Point2D fromP = map.getTileFactory().geoToPixel(fromNode.getNavPoint().getGeoPos(), map.getZoom());
            Point2D toP = map.getTileFactory().geoToPixel(toNode.getNavPoint().getGeoPos(), map.getZoom());
            g.drawLine((int) fromP.getX(), (int) fromP.getY(), (int) toP.getX(), (int) toP.getY());
            fromNode = toNode;

        }

    }

    private void drawClosestNode(Graphics2D g, JXMapViewer map) {

        if (this.closestTaxiwayNode == null) {
            //System.out.println("CLOSEST POINT IS NULL");
            return;
        }

        Point2D closesPoint = map.getTileFactory().geoToPixel(this.getClosestTaxiwayNode().getNavPoint().getGeoPos(), map.getZoom());
        g.setColor(Color.RED);
        g.drawOval((int) closesPoint.getX(), (int) closesPoint.getY(), 5, 5);
        g.drawString(this.closestTaxiwayNode.getId() + "", (int) closesPoint.getX(), (int) closesPoint.getY());
        g.drawString(this.closestTaxiwayNode.getConnNames().toString() + "", (int) closesPoint.getX(), (int) closesPoint.getY() + 12);
    }

    public TaxiNetworkNode getClosestTaxiwayNode() {
        return closestTaxiwayNode;
    }

    public void setClosestTaxiwayNode(TaxiNetworkNode closestTaxiwayNode) {
        this.closestTaxiwayNode = closestTaxiwayNode;

    }

    public List<TaxiNetworkNode> getPaintedNodes() {
        return paintedNodes;
    }

    public void setPaintedNodes(List<TaxiNetworkNode> paintedNodes) {
        this.paintedNodes = paintedNodes;
    }

    public List<TaxiNetworkNode> getForwardPath() {
        return forwardPath;
    }

    public void setForwardPath(List<TaxiNetworkNode> forwardPath) {
        this.forwardPath = forwardPath;
    }

    public List<TaxiNetworkNode> getBackwardPath() {
        return backwardPath;
    }

    public void setBackwardPath(List<TaxiNetworkNode> backwardPath) {
        this.backwardPath = backwardPath;
    }

    public boolean isDrawForwardNodes() {
        return drawForwardNodes;
    }

    public void setDrawForwardNodes(boolean drawForwardNodes) {
        this.drawForwardNodes = drawForwardNodes;
    }

    public boolean isDrawBackwardNodes() {
        return drawBackwardNodes;
    }

    public void setDrawBackwardNodes(boolean drawBackwardNodes) {
        this.drawBackwardNodes = drawBackwardNodes;
    }

    public Taxiway getTaxiway() {
        return taxiway;
    }

    public void setTaxiway(Taxiway taxiway) {
        this.taxiway = taxiway;
    }

}
