/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2015
 * Have fun!
 *
 */
package de.xatc.controllerclient.gui.painters;

import de.xatc.controllerclient.navigation.NavPoint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

/**
 * this overlay painter draws labels to map
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class RosePainter implements Painter<JXMapViewer> {

    private static final Logger LOG = Logger.getLogger(RosePainter.class.getName());
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
    private Map<Integer, NavPoint> roseMap = new HashMap<>();

    /**
     *
     */
    public RosePainter() {

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

        drawRoses(g, map);

        g.dispose();
    }

    /**
     * draw labels
     *
     * @param g
     * @param map
     */
    private void drawRoses(Graphics2D g, JXMapViewer map) {

        if (this.roseMap.isEmpty()) {
            return;
        }

        for (Entry<Integer, NavPoint> entry : this.roseMap.entrySet()) {

            NavPoint p = entry.getValue();
            int width = entry.getKey();
            int radius = width / 2;

            Point2D midPoint = map.getTileFactory().geoToPixel(new GeoPosition(p.getLatitudedouble(), p.getLongitudeDouble()), map.getZoom());

            int midPointX = (int) midPoint.getX() - (width / 2);
            int midPointY = (int) midPoint.getY() - (width / 2);
            //FMCConfig.debugMessage(text2D.getX() + " " + text2D.getY());
            g.drawOval(midPointX, midPointY, width, width);
            g.drawOval((int) midPoint.getX(), (int) midPoint.getY(), 3, 3);

            Line2D line = new Line2D.Double((int) midPoint.getX(), (int) midPoint.getY(), (int) midPoint.getX(), (int) midPoint.getY() - radius);
         
            
            g.draw(line);
            
            int angle = 30;
            while (angle <= 360) {

                AffineTransform at
                        = AffineTransform.getRotateInstance(
                                Math.toRadians(angle), line.getX1(), line.getY1());

                
                
                Shape rotatedLine =  at.createTransformedShape(line);
                LOG.debug(rotatedLine.getBounds());
                g.draw(rotatedLine);
              
                g.drawString(angle + "", (int) at.getScaleX(), (int) at.getScaleY());
                angle += 30;
            }

            

        }

    }

    public Map<Integer, NavPoint> getRoseMap() {
        return roseMap;
    }

    public void setRoseMap(Map<Integer, NavPoint> roseMap) {
        this.roseMap = roseMap;
    }

}
