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

import de.xatc.commons.networkpackets.pilot.FMSPlan;
import de.xatc.commons.networkpackets.pilot.FMSWayPoint;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.datastructures.DataStructureSilo;
import de.xatc.controllerclient.log.DebugMessageLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

/**
 * This painter is an overlay to the map and paints the current position of the
 * aircraft in xplane
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AircraftPainter implements Painter<JXMapViewer> {

    private PlanePosition p;
    /**
     * do we want AntiAliasing, of course
     */
    private boolean antiAlias = true;

    /**
     * the image of the aircraft drawn
     */
    private BufferedImage image;
    private BufferedImage originalImage;

    /**
     * to show the heading of the plane we need to rotate the image. This is
     * done by the AffineTransform class
     */
    private AffineTransform at = new AffineTransform();

    /**
     * constructor,
     *
     * @param p - navpoint of the aircraft position
     */
    public AircraftPainter() {

        XHSConfig.debugMessage("PATH: " + Paths.get(".").toAbsolutePath().normalize().toString(), DebugMessageLevel.DEBUG);

        try {
            File imageFile = new File("Resources/plugins/PythonScripts/followMeCarScripts/plane.png");
            if (!imageFile.exists()) {
                imageFile = new File("src/main/resources/planeicons/plane.png");
            }
            this.image = ImageIO.read(imageFile);
            this.originalImage = ImageIO.read(imageFile);

        } catch (IOException ex) {
            XHSConfig.debugMessage("could not load Image: " + ex.getLocalizedMessage(), DebugMessageLevel.EXCEPTION);
        }

    }

    /**
     * paint the image
     *
     * @param g
     * @param map
     * @param w
     * @param h
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
        if (this.p == null) {
            return;
        }
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        drawPlane(g, map);
        drawPlaneFlownPath(g, map);
        drawFMSPlan(g, map);
        g.dispose();
    }

    private void drawFMSPlan(Graphics2D g, JXMapViewer map) {

        FMSPlan fmsPlan = DataStructureSilo.getLocalPilotStructure().get(p.getSessionID()).getPilotServerStructure().getFmsPlan();
        if (fmsPlan == null) {
            return;
        }

        Point2D fromPoint = null;
        Point2D toPoint = null;
        boolean firstLine = true;
        g.setColor(Color.BLUE);
        if (fmsPlan.getWayPointList().size() > 0) {
            for (Entry<String, FMSWayPoint> entry : fmsPlan.getWayPointList().entrySet()) {

                if (firstLine) {
                    fromPoint = map.getTileFactory().geoToPixel(new GeoPosition(Double.parseDouble(entry.getValue().getLatitude()), Double.parseDouble(entry.getValue().getLongitude())), map.getZoom());
                    firstLine = false;
                    continue;
                }

                toPoint = map.getTileFactory().geoToPixel(new GeoPosition(Double.parseDouble(entry.getValue().getLatitude()), Double.parseDouble(entry.getValue().getLongitude())), map.getZoom());
                g.drawLine((int) fromPoint.getX(), (int) fromPoint.getY(), (int) toPoint.getX(), (int) toPoint.getY());
                g.fillOval((int)fromPoint.getX()-5, (int)fromPoint.getY()-5, 10, 10);
                g.drawString(entry.getKey(), (int)toPoint.getX()+10, (int) toPoint.getY());
                fromPoint = toPoint;

            }
        }
    }

    private void drawPlaneFlownPath(Graphics2D g, JXMapViewer map) {

        List<PlanePosition> posList = DataStructureSilo.getLocalPilotStructure().get(p.getSessionID()).getPilotServerStructure().getPlanePositionList();

        g.setColor(Color.BLACK);
        Point2D fromPoint = null;
        Point2D toPoint = null;
        boolean firstLine = true;
        if (posList.size() > 2) {
            for (PlanePosition pos : posList) {

                if (firstLine) {
                    fromPoint = map.getTileFactory().geoToPixel(new GeoPosition(Double.parseDouble(pos.getLatitude()), Double.parseDouble(pos.getLongitude())), map.getZoom());
                    firstLine = false;
                    continue;
                }
                toPoint = map.getTileFactory().geoToPixel(new GeoPosition(Double.parseDouble(pos.getLatitude()), Double.parseDouble(pos.getLongitude())), map.getZoom());
                g.drawLine((int) fromPoint.getX(), (int) fromPoint.getY(), (int) toPoint.getX(), (int) toPoint.getY());
                fromPoint = toPoint;

            }
        }
    }

    /**
     * draw the plane
     *
     * @param g
     * @param map
     */
    private void drawPlane(Graphics2D g, JXMapViewer map) {

        Point2D plane2d = map.getTileFactory().geoToPixel(new GeoPosition(Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude())), map.getZoom());

        this.image = this.originalImage;
        this.image = this.rotateImage(image, Double.parseDouble(p.getHeading()));

        int x = (int) plane2d.getX() - (this.image.getWidth() / 2);
        int y = (int) (int) plane2d.getY() - (this.image.getHeight() / 2);
        g.setColor(Color.BLACK);
        g.drawString("xPr<b> " + p.getTransponder() + " " + p.getTransponderMode(), x, y - 30);
        g.drawString("iac: " + p.getGroundSpeed(), x, y - 20);
        g.drawString("alt: " + p.getAltitude(), x, y - 10);
        g.drawImage(image, x, y, null);

    }

    /**
     * load and rotate image
     *
     * @param src
     * @param degrees
     * @return
     */
    private BufferedImage rotateImage(BufferedImage src, double degrees) {
        AffineTransform affineTransform = AffineTransform.getRotateInstance(
                Math.toRadians(degrees),
                src.getWidth() / 2,
                src.getHeight() / 2);
        BufferedImage rotatedImage = new BufferedImage(src.getWidth(), src
                .getHeight(), src.getType());
        Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
        g.setTransform(affineTransform);
        g.drawImage(src, 0, 0, null);
        return rotatedImage;
    }

    public PlanePosition getP() {
        return p;
    }

    public void setP(PlanePosition p) {
        this.p = p;
    }

}
