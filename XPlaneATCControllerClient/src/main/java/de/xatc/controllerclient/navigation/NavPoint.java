/**
 * This file is part of the XPlane Home Server License.
 * You may edit and use this file as you like. But there is no warranty at all and no license condition.
 * XPlane Home Server tries to build up a simple network for flying in small local networks or via internet.
 * Have fun!
 *
 * @Author Mirko Bubel (mirko_bubel@hotmail.com)
 * @Created 31.05.2016
 */
package de.xatc.controllerclient.navigation;

import java.awt.geom.Point2D;
import java.io.Serializable;
import org.jdesktop.swingx.mapviewer.GeoPosition;


/**
 * this bean represents a navpoint of the map
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class NavPoint implements Serializable {

    /**
     * the longitude as String
     */
    private String longitudeSTring = null;

    /**
     * the latitude as string
     */
    private String latitudeSTring = null;

    /**
     * the longitude as float
     */
    private float longitudeDouble = 0;

    /**
     * the latitude as float
     */
    private float latitudedouble = 0;

    /**
     * x value of pixels
     */
    private float x = 0;

    /**
     * y value of pixel
     */
    private float y = 0;

    /**
     * width of map for point translation.
     */
    private int mapWidth = 0;

    /**
     * height of map for point translation
     */
    private int mapHeight = 0;

    /**
     * the name of the navpoint
     */
    private String name = null;

    /**
     * the openstreetmap compatible geoposition representation of the mavigation
     * point
     */
    private GeoPosition geoPos;

    /**
     * 2d point used to translate lat lon to pixels
     */
    private Point2D point2D;

    /**
     * bearing of point as string
     */
    private String headingString;

    /**
     * bearing of point as double
     */
    private double headingDouble;

    private boolean bezierPoint = false;

    private NavPoint bezierNavPoint;

    /**
     *
     * @return
     */
    public String getLongitudeSTring() {
        return longitudeSTring;
    }

    /**
     *
     * @param longitudeSTring
     */
    public void setLongitudeSTring(String longitudeSTring) {
        this.longitudeSTring = longitudeSTring;
    }

    /**
     *
     * @return
     */
    public String getLatitudeSTring() {
        return latitudeSTring;
    }

    /**
     *
     * @param latitudeSTring
     */
    public void setLatitudeSTring(String latitudeSTring) {
        this.latitudeSTring = latitudeSTring;
    }

    /**
     *
     * @return
     */
    public double getLongitudeDouble() {
        return longitudeDouble;
    }

    /**
     *
     * @param longitudeDouble
     */
    public void setLongitudeDouble(float longitudeDouble) {
        this.longitudeDouble = longitudeDouble;
    }

    /**
     *
     * @return
     */
    public double getLatitudedouble() {
        return latitudedouble;
    }

    /**
     *
     * @param latitudedouble
     */
    public void setLatitudedouble(float latitudedouble) {
        this.latitudedouble = latitudedouble;
    }

    /**
     *
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public GeoPosition getGeoPos() {
        return geoPos;
    }

    /**
     *
     * @param geoPos
     */
    public void setGeoPos(GeoPosition geoPos) {
        this.geoPos = geoPos;
    }

    /**
     *
     * @return
     */
    public Point2D getPoint2D() {
        return point2D;
    }

    /**
     *
     * @param point2D
     */
    public void setPoint2D(Point2D point2D) {
        this.point2D = point2D;
    }

    /**
     *
     * @return
     */
    public String getHeadingString() {
        return headingString;
    }

    /**
     *
     * @param headingString
     */
    public void setHeadingString(String headingString) {
        this.headingString = headingString;
    }

    /**
     *
     * @return
     */
    public double getHeadingDouble() {
        return headingDouble;
    }

    /**
     *
     * @param headingDouble
     */
    public void setHeadingDouble(double headingDouble) {
        this.headingDouble = headingDouble;
    }

    /**
     *
     * @return
     */
    public boolean isBezierPoint() {
        return bezierPoint;
    }

    /**
     *
     * @param bezierPoint
     */
    public void setBezierPoint(boolean bezierPoint) {
        this.bezierPoint = bezierPoint;
    }

    /**
     *
     * @return
     */
    public NavPoint getBezierNavPoint() {
        return bezierNavPoint;
    }

    /**
     *
     * @param bezierNavPoint
     */
    public void setBezierNavPoint(NavPoint bezierNavPoint) {
        this.bezierNavPoint = bezierNavPoint;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

}
