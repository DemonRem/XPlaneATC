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
import java.awt.Color;
import java.awt.Font;
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
 * this overlay painter draws labels to map
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class LabelPainter implements Painter<JXMapViewer> {

    /**
     * what color do we draw with
     */
    private Color color = Color.RED;

    /**
     * do we want anti alias
     */
    private boolean antiAlias = true;

    /**
     * labels container
     */
    private List<NavPoint> labelList = new ArrayList<>();

    /**
     *
     */
    public LabelPainter() {

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

        g.setColor(Color.BLUE);

        Font font = new Font("SansSerif", Font.BOLD, 12);
        g.setFont(font);

        drawLabels(g, map);

        drawLabels(g, map);

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
            //FMCConfig.debugMessage("DRAW LABEL: " + p.getName());

            Point2D text2D = map.getTileFactory().geoToPixel(new GeoPosition(p.getLatitudedouble(), p.getLongitudeDouble()), map.getZoom());

            //FMCConfig.debugMessage(text2D.getX() + " " + text2D.getY());
            g.drawString(p.getName(), (int) text2D.getX(), (int) text2D.getY());

        }

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

}
