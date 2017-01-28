/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2015
 * Have fun!
 *
 */
package de.twyhelper.tools;

/**
 * this enum represents all different markings. this is needed to know what
 * drawing and saving mode the application is currently using. depending on
 * this, a specific key listener will be loaded and a specific file format will
 * be used.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public enum AirportCharterEnum {

    /**
     * a label drawn on map
     */
    LABEL("label"),
    /**
     * a runway drawn on map
     */
    RUNWAY("runway"),
    /**
     * a pparking position drawn on map
     */
    PARKING("parking"),
    /**
     * a sqaure drawn on map
     */
    SQUARE("square"),
    /**
     * a taxyway drawn on map
     */
    TAXIWAY("taxiway");

    /**
     *
     */
    private String type;

    /**
     *
     * @param t
     */
    private AirportCharterEnum(String t) {
        this.type = t;

    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

}
