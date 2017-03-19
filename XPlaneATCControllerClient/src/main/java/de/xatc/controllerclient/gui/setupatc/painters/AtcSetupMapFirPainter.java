package de.xatc.controllerclient.gui.setupatc.painters;

import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.db.sharedentities.atcdata.PlainNavPoint;
import de.xatc.commons.networkpackets.atc.stations.SupportedAirportStation;
import de.xatc.commons.networkpackets.atc.stations.SupportedFirStation;
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
    private SupportedFirStation setupFir;
    private SupportedAirportStation setupAirport;
    private List<SupportedFirStation> activeFirs = new ArrayList<>();
    private List<SupportedAirportStation> activeAirports = new ArrayList<>();
    private Fir selectedFir;
    private List<PlainAirport> includedAirportsInFir = new ArrayList<>();
    private PlainAirport selectedAirport;

    private double defaultAiportRange = 5;
    private double selectedAirportRange = 5;

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

        if (this.setupFir != null) {
            drawSupportedFir(this.setupFir, g, map, Color.BLUE);
        }
        if (!this.activeFirs.isEmpty()) {

            for (SupportedFirStation fir : this.activeFirs) {
                drawSupportedFir(fir, g, map, Color.DARK_GRAY);
            }

        }
        if (this.setupAirport != null) {
            this.drawSupportedAirport(setupAirport, g, map, Color.BLUE);
        }
        if (!this.activeAirports.isEmpty()) {
            
            for (SupportedAirportStation airport : this.activeAirports) {
                this.drawSupportedAirport(airport, g, map, Color.DARK_GRAY);
            }
            
        }
        if (this.selectedAirport != null) {
            this.drawSelectedAirport(selectedAirport, g, map, Color.RED);
        }
        if (this.selectedFir != null) {
            this.drawSelectedFir(selectedFir, g, map, Color.RED);
        }
        
        if (!this.includedAirportsInFir.isEmpty()) {
            for (PlainAirport airport : this.includedAirportsInFir) {
                
                this.drawIncludedAirport(airport, g, map, Color.LIGHT_GRAY);
                
            }
        }
        
        

        g.dispose();
    }

    private void drawSupportedFir(SupportedFirStation supFir, Graphics2D g, JXMapViewer map, Color color) {

        g.setColor(color);
        Point2D firLabelPos = map.getTileFactory().geoToPixel(new GeoPosition(supFir.getFir().getPosition().getLat(), supFir.getFir().getPosition().getLon()), map.getZoom());
        g.drawString(supFir.getFir().getFirName(), (int) firLabelPos.getX(), (int) firLabelPos.getY());

        PlainNavPoint lastNavPoint = null;
        //TODO hier vielleicht noch ein poligon shape.

        for (PlainNavPoint p : supFir.getFir().getPoligonList()) {

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
    
    private void drawSelectedFir(Fir fir, Graphics2D g, JXMapViewer map, Color color) {

        g.setColor(color);
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
    
    
    

    private void drawSupportedAirport(SupportedAirportStation airport, Graphics2D g, JXMapViewer map, Color color) {

        System.out.println("Drawing supported Airport");
        System.out.println("VISIBILITY IN PAINTER: " + airport.getVisibility());
        g.setColor(color);
        g.setStroke(new BasicStroke(8));

        double metersPerPixelOnMap = XHSConfig.getEarthCircumference() * Math.abs(Math.cos(airport.getAirport().getPosition().getLat() * 180 / Math.PI)) / Math.pow(2, map.getZoom());

        System.out.println("METERS PER PIXEL: " + metersPerPixelOnMap);

        g.setStroke(new BasicStroke(8));
        Point2D airportPos = map.getTileFactory().geoToPixel(new GeoPosition(airport.getAirport().getPosition().getLat(), airport.getAirport().getPosition().getLon()), map.getZoom());

        g.drawString(airport.getAirport().getAirportIcao(), (int) airportPos.getX() + 5, (int) airportPos.getY() + 5);

        float circledouble = (float) (airport.getVisibility() / this.getMeterPerPixel(new Point((int) airportPos.getX() + 1, (int) airportPos.getY()), map));
        System.out.println("CIRCLE DOUBLE: " + circledouble);
        int circle = Math.round(circledouble / 2);
        System.out.println("CIRCLE! rounded " + circle);

        System.out.println("METHOD GET METERS PER PIXEL::::::::: " + this.getMeterPerPixel(new Point((int) airportPos.getX() + 1, (int) airportPos.getY()), map));

        g.drawOval((int) airportPos.getX() - (circle / 2), (int) airportPos.getY() - (circle / 2), circle, circle);

    }
    
    private void drawSelectedAirport(PlainAirport airport, Graphics2D g, JXMapViewer map, Color color) {

        System.out.println("Drawing airports");
        g.setColor(color);
        g.setStroke(new BasicStroke(8));

        double metersPerPixelOnMap = XHSConfig.getEarthCircumference() * Math.abs(Math.cos(airport.getPosition().getLat() * 180 / Math.PI)) / Math.pow(2, map.getZoom());

        System.out.println("METERS PER PIXEL: " + metersPerPixelOnMap);

        g.setStroke(new BasicStroke(8));
        Point2D airportPos = map.getTileFactory().geoToPixel(new GeoPosition(airport.getPosition().getLat(), airport.getPosition().getLon()), map.getZoom());

        g.drawString(airport.getAirportIcao(), (int) airportPos.getX() + 5, (int) airportPos.getY() + 5);

        float circledouble = (float) (this.selectedAirportRange / this.getMeterPerPixel(new Point((int) airportPos.getX() + 1, (int) airportPos.getY()), map));
        System.out.println("CIRCLE DOUBLE: " + circledouble);
        int circle = Math.round(circledouble / 2);
        System.out.println("CIRCLE! rounded " + circle);

        System.out.println("METHOD GET METERS PER PIXEL::::::::: " + this.getMeterPerPixel(new Point((int) airportPos.getX() + 1, (int) airportPos.getY()), map));

        g.drawOval((int) airportPos.getX() - (circle / 2), (int) airportPos.getY() - (circle / 2), circle, circle);

    }
    
     private void drawIncludedAirport(PlainAirport airport, Graphics2D g, JXMapViewer map, Color color) {

        System.out.println("Drawing airports");
        g.setColor(color);
        g.setStroke(new BasicStroke(8));

        double metersPerPixelOnMap = XHSConfig.getEarthCircumference() * Math.abs(Math.cos(airport.getPosition().getLat() * 180 / Math.PI)) / Math.pow(2, map.getZoom());

        System.out.println("METERS PER PIXEL: " + metersPerPixelOnMap);

        g.setStroke(new BasicStroke(8));
        Point2D airportPos = map.getTileFactory().geoToPixel(new GeoPosition(airport.getPosition().getLat(), airport.getPosition().getLon()), map.getZoom());

        g.drawString(airport.getAirportIcao(), (int) airportPos.getX() + 5, (int) airportPos.getY() + 5);
   


        g.drawOval((int) airportPos.getX() - (5 / 2), (int) airportPos.getY() - (5 / 2), 5, 5);

    }
    
    

    public double getMeterPerPixel(Point p, JXMapViewer map) {
        Point2D from = p;
        Point2D to = new Point((int) p.getX() + 1, (int) p.getY());

        double pDistance = to.distance(from);

        GeoPosition originCoord = map.getTileFactory().pixelToGeo(from, map.getZoom());
        GeoPosition centerCoord = map.getTileFactory().pixelToGeo(to, map.getZoom());
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

    public SupportedFirStation getSetupFir() {
        return setupFir;
    }

    public void setSetupFir(SupportedFirStation setupFir) {
        this.setupFir = setupFir;
    }

    public SupportedAirportStation getSetupAirport() {
        return setupAirport;
    }

    public void setSetupAirport(SupportedAirportStation setupAirport) {
        this.setupAirport = setupAirport;
    }

    public List<SupportedFirStation> getActiveFirs() {
        return activeFirs;
    }

    public void setActiveFirs(List<SupportedFirStation> activeFirs) {
        this.activeFirs = activeFirs;
    }

    public List<SupportedAirportStation> getActiveAirports() {
        return activeAirports;
    }

    public void setActiveAirports(List<SupportedAirportStation> activeAirports) {
        this.activeAirports = activeAirports;
    }

    

    public double getDefaultAiportRange() {
        return this.defaultAiportRange;
    }

    public void setDefaultAiportRange(double aiportRange) {
        this.defaultAiportRange = aiportRange;
    }

    public Fir getSelectedFir() {
        return selectedFir;
    }

    public void setSelectedFir(Fir selectedFir) {
        this.selectedFir = selectedFir;
    }

    public PlainAirport getSelectedAirport() {
        return selectedAirport;
    }

    public void setSelectedAirport(PlainAirport selectedAirport) {
        this.selectedAirport = selectedAirport;
    }

    public List<PlainAirport> getIncludedAirportsInFir() {
        return includedAirportsInFir;
    }

    public void setIncludedAirportsInFir(List<PlainAirport> includedAirportsInFir) {
        this.includedAirportsInFir = includedAirportsInFir;
    }

    public double getSelectedAirportRange() {
        return selectedAirportRange;
    }

    public void setSelectedAirportRange(double selectedAirportRange) {
        this.selectedAirportRange = selectedAirportRange;
    }
    
    
    

}
