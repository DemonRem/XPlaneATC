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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.Painter;

/**
 * this overlay painter draws a taxiroute to map
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class RoutePainter implements Painter<JXMapViewer> {

    /**
     * do we want antialias
     */
    private boolean antiAlias = true;

    /**
     * the route point list container
     */
    private List<NavPoint> routeList = new ArrayList<>();

    /**
     * labels container
     */
    private Map<String, NavPoint> routeLabels = new HashMap<>();

    /**
     * constructor
     */
    public RoutePainter() {

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

        g.setStroke(new BasicStroke(2));
        drawRoute(g, map);

        g.dispose();
    }

    /**
     * draw route
     *
     * @param g
     * @param map
     */
    private void drawRoute(Graphics2D g, JXMapViewer map) {

        boolean isFirst = true;
        NavPoint oldNavPoint = null;
        g.setColor(Color.GREEN);
        for (NavPoint p : this.routeList) {

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
        g.setColor(Color.MAGENTA);
        for (Entry<String, NavPoint> entry : this.routeLabels.entrySet()) {

            NavPoint labelPoint = entry.getValue();
            String labelName = entry.getKey();

            Point2D labelPoint2D = map.getTileFactory().geoToPixel(labelPoint.getGeoPos(), map.getZoom());
            g.drawString(labelName, (int) labelPoint2D.getX(), (int) labelPoint2D.getY());
        }

    }

    /**
     *
     * @param p
     */
    public void addNavPoint(NavPoint p) {

        this.routeList.add(p);
    }

    /**
     * delete all drawn items
     */
    public void deleteRouteList() {

        this.routeList.clear();
    }

    /**
     *
     * @param list
     */
    public void setRouteList(List<NavPoint> list) {
        this.routeList = list;
    }

    /**
     *
     * @return
     */
    public List<NavPoint> getRouteList() {
        return this.routeList;
    }

    /**
     *
     * @return
     */
    public Map<String, NavPoint> getRouteLabels() {
        return routeLabels;
    }

    /**
     *
     * @param routeLabels
     */
    public void setRouteLabels(Map<String, NavPoint> routeLabels) {
        this.routeLabels = routeLabels;
    }

}
