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
import de.twyhelper.tools.debug.DebugMessageLevel;
import de.xatc.controllerclient.navigation.NavPoint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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

    /**
     * do we want AntiAliasing, of course
     */
    private boolean antiAlias = true;

    /**
     * the image of the aircraft drawn
     */
    private BufferedImage image;

    /**
     * the navpoint where the aircraft is drawn to
     */
    private NavPoint p;

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
    public AircraftPainter(NavPoint p) {

        FMCConfig.debugMessage("PATH: " + Paths.get(".").toAbsolutePath().normalize().toString(), DebugMessageLevel.DEBUG);
        this.p = p;
        try {
            File imageFile = new File("Resources/plugins/PythonScripts/followMeCarScripts/plane.png");
            if (!imageFile.exists()) {
                imageFile = new File("src/main/resources/pythonfiles/followMeCarScripts/plane.png");
            }
            this.image = ImageIO.read(imageFile);

            this.image = this.rotateImage(image, p.getHeadingDouble());

        } catch (IOException ex) {
            FMCConfig.debugMessage("could not load Image: " + ex.getLocalizedMessage(), DebugMessageLevel.EXCEPTION);
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
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        drawPlane(g, map);

        g.dispose();
    }

    /**
     * draw the plane
     *
     * @param g
     * @param map
     */
    private void drawPlane(Graphics2D g, JXMapViewer map) {

        Point2D plane2d = map.getTileFactory().geoToPixel(new GeoPosition(p.getLatitudedouble(), p.getLongitudeDouble()), map.getZoom());

        g.drawImage(image, (int) plane2d.getX(), (int) plane2d.getY(), null);

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

}
