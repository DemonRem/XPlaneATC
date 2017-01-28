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
import java.util.ArrayList;
import java.util.List;

/**
 * This is the chunkmodel of an airport
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptChunkModel {

    /**
     * navpoint list
     */
    private List<NavPoint> navPointList = new ArrayList<>();

    /**
     * getter
     *
     * @return
     */
    public List<NavPoint> getNavPointList() {
        return navPointList;
    }

    /**
     * setter
     *
     * @param navPointList
     */
    public void setNavPointList(List<NavPoint> navPointList) {
        this.navPointList = navPointList;
    }

}
