/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.log;

/**
 * This ENUM represents possible log level if debug mode is on (set in
 * FMCConfig)
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public enum DebugMessageLevel {

    /**
     * log all
     */
    ALL(10),
    /**
     * log debug
     */
    DEBUG(9),
    /**
     * log error
     */
    ERROR(8),
    /**
     * log warn
     */
    WARN(7),
    /**
     * log info
     */
    INFO(6),
    /**
     * log messages
     */
    MESSAGE(5),
    /**
     * log exceptoin
     */
    EXCEPTION(4);

    /**
     * level
     */
    private int level;

    /**
     * constructor
     *
     * @param t
     */
    private DebugMessageLevel(int t) {
        this.level = t;

    }

    /**
     * getter
     *
     * @return
     */
    public int getLevel() {
        return this.level;
    }

}
