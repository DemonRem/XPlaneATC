/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.xdataparser.aptmodel;

import de.xatc.controllerclient.navigation.NavPoint;



/**
 * This class describes a runway of an airport
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptRunwayModel  {

    /**
     * startpoint
     */
    private NavPoint p1;
    /**
     * endpoint
     */
    private NavPoint p2;
    /**
     * runwayy name
     */
    private String nameP1;
    /**
     * runwayname
     */
    private String nameP2;
    /**
     * runway width
     */
    private float width;
    /**
     * runway type 100=LAND 101=SEA 102=HELIPAD
     */
    private int type;

    /**
     * getter
     *
     * @return
     */
    public NavPoint getP1() {
        return p1;
    }

    /**
     * setter
     *
     * @param p1
     */
    public void setP1(NavPoint p1) {
        this.p1 = p1;
    }

    /**
     * getter
     *
     * @return
     */
    public NavPoint getP2() {
        return p2;
    }

    /**
     * setter
     *
     * @param p2
     */
    public void setP2(NavPoint p2) {
        this.p2 = p2;
    }

    /**
     * getter
     *
     * @return
     */
    public String getNameP1() {
        return nameP1;
    }

    /**
     * setter
     *
     * @param nameP1
     */
    public void setNameP1(String nameP1) {
        this.nameP1 = nameP1;
    }

    /**
     * getter
     *
     * @return
     */
    public String getNameP2() {
        return nameP2;
    }

    /**
     * setter
     *
     * @param nameP2
     */
    public void setNameP2(String nameP2) {
        this.nameP2 = nameP2;
    }

    /**
     * getter
     *
     * @return
     */
    public float getWidth() {
        return width;
    }

    /**
     * setter
     *
     * @param width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * getter
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * setter
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

}
