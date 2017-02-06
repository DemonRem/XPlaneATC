package de.xatc.controllerclient.gui.setupatc.painters;

import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.db.sharedentities.atcdata.PlainNavPoint;
import de.xatc.controllerclient.config.XHSConfig;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

/**
 *
 * @author Mirko
 */
public class AtcSetupMapFirPainter implements Painter<JXMapViewer> {

    private boolean antiAlias = false;
    private List<Fir> firList = new ArrayList<>();
    private List<PlainAirport> airports = new ArrayList<>();
    private double aiportRange = 5;

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

        drawFirs(g, map);
        drawAirports(g, map);

        g.dispose();
    }

    private void drawFirs(Graphics2D g, JXMapViewer map) {

        for (Fir fir : this.firList) {

            Point2D firLabelPos = map.getTileFactory().geoToPixel(new GeoPosition(fir.getPosition().getLat(), fir.getPosition().getLon()), map.getZoom());
            g.drawString(fir.getFirName(), (int) firLabelPos.getX(), (int) firLabelPos.getY());

            PlainNavPoint lastNavPoint = null;
            //TODO hier vielleicht noch ein poligon shape.

            for (PlainNavPoint p : fir.getPoligonList()) {

                if (lastNavPoint == null) {
                    lastNavPoint = p;
                    continue;
                }
                g.setStroke(new BasicStroke(8));
                Point2D fromP = map.getTileFactory().geoToPixel(new GeoPosition(lastNavPoint.getLat(), lastNavPoint.getLon()), map.getZoom());
                Point2D toP = map.getTileFactory().geoToPixel(new GeoPosition(p.getLat(), p.getLon()), map.getZoom());
                g.drawLine((int) fromP.getX(), (int) fromP.getY(), (int) toP.getX(), (int) toP.getY());
                lastNavPoint = p;
            }

        }

    }

    private void drawAirports(Graphics2D g, JXMapViewer map) {

        if (this.airports.isEmpty()) {
            System.out.println("Airport List is empty!");
            return;
        }
        System.out.println("Drawing airports");
        g.setStroke(new BasicStroke(8));

        for (PlainAirport airport : this.airports) {
            double metersPerPixelOnMap = XHSConfig.getEarthCircumference() * Math.abs(Math.cos(airport.getPosition().getLat() * 180 / Math.PI)) / Math.pow(2, map.getZoom());

            System.out.println("METERS PER PIXEL: " + metersPerPixelOnMap);

            g.setStroke(new BasicStroke(8));
            Point2D airportPos = map.getTileFactory().geoToPixel(new GeoPosition(airport.getPosition().getLat(), airport.getPosition().getLon()), map.getZoom());

            g.drawString(airport.getAirportIcao(), (int) airportPos.getX() + 5, (int) airportPos.getY() + 5);

            float circledouble = (float) (aiportRange / this.getMeterPerPixel(new Point((int)airportPos.getX() + 1, (int)airportPos.getY()),map));
            System.out.println("CIRCLE DOUBLE: " + circledouble);
            int circle = Math.round(circledouble / 2);
            System.out.println("CIRCLE! rounded " + circle);

            System.out.println("METHOD GET METERS PER PIXEL::::::::: " + this.getMeterPerPixel(new Point((int)airportPos.getX() + 1, (int)airportPos.getY()),map));
            
            
            g.drawOval((int) airportPos.getX() - (circle / 2), (int) airportPos.getY() - (circle / 2), circle, circle);

        }

    }

    public double getMeterPerPixel(Point p, JXMapViewer map) {
        Point2D from = p;
        Point2D to = new Point((int)p.getX() + 1, (int)p.getY());

        double pDistance = to.distance(from);

        
        GeoPosition originCoord = map.getTileFactory().pixelToGeo(from,map.getZoom());
        GeoPosition centerCoord = map.getTileFactory().pixelToGeo(to,map.getZoom());
        double d2r = Math.PI / 180;

        double dlong = (centerCoord.getLongitude() - originCoord.getLongitude()) * d2r;
        double dlat = (centerCoord.getLatitude() - originCoord.getLatitude()) * d2r;
        double a
                = Math.pow(Math.sin(dlat / 2.0), 2)
                + Math.cos(centerCoord.getLatitude() * d2r)
                * Math.cos(originCoord.getLatitude() * d2r)
                * Math.pow(Math.sin(dlong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double mDistance = (6367 * c) * 1000;

        return mDistance / pDistance;
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    public List<Fir> getFirList() {
        return firList;
    }

    public void setFirList(List<Fir> firList) {
        this.firList = firList;
    }

    public List<PlainAirport> getAirports() {
        return airports;
    }

    public void setAirports(List<PlainAirport> airports) {
        this.airports = airports;
    }

    public void resetAirports() {
        this.airports = new ArrayList<>();

    }

    public double getAiportRange() {
        return aiportRange;
    }

    public void setAiportRange(double aiportRange) {
        this.aiportRange = aiportRange;
    }

}
