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

import de.xatc.controllerclient.navigation.NavLine;
import de.xatc.controllerclient.navigation.NavPoint;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

/**
 * this overlay painter draws taxiways from sector files or airport files on
 * map.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class SectorTaxiWayPainter implements Painter<JXMapViewer> {

    /**
     * what color do we want
     */
    private Color color = Color.RED;

    /**
     * use anti alias
     */
    private boolean antiAlias = true;

    /**
     * the navpoint container of taxiway
     */
    private List<NavLine> track = new ArrayList<>();

    /**
     * constructor
     */
    public SectorTaxiWayPainter() {

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

    }

    /**
     * draw route
     *
     * @param g
     * @param map
     */
    private void drawRoute(Graphics2D g, JXMapViewer map) {

        for (NavLine pd : track) {

            NavPoint from = pd.getNavPointFrom();
            NavPoint to = pd.getNavPointTo();

            Point2D from2D = map.getTileFactory().geoToPixel(new GeoPosition(from.getLatitudedouble(), from.getLongitudeDouble()), map.getZoom());
            Point2D to2D = map.getTileFactory().geoToPixel(new GeoPosition(to.getLatitudedouble(), to.getLongitudeDouble()), map.getZoom());

            //FMCConfig.debugMessage("LINEDRAW XY : " + from2D.getX() + " " + from2D.getY());
            g.setColor(Color.RED);
            g.drawLine((int) from2D.getX(), (int) from2D.getY(), (int) to2D.getX(), (int) to2D.getY());

        }

    }

    /**
     *
     * @param list
     */
    public void setTrackList(List<NavLine> list) {
        this.track = list;
    }

    /**
     *
     * @return
     */
    public List<NavLine> getNavLineList() {
        return this.track;
    }
}
