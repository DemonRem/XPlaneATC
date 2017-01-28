/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.xdataparser.aptmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a taxiway consisting of taxinetwork segments
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class Taxiway {

    /**
     * name of taxiway
     */
    private String name;
    /**
     * list of segments
     */
    private List<TaxiwaySegment> segments = new ArrayList<>();

    /**
     * getter
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setter
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter
     *
     * @return
     */
    public List<TaxiwaySegment> getSegments() {
        return segments;
    }

    /**
     * setter
     *
     * @param segments
     */
    public void setSegments(List<TaxiwaySegment> segments) {
        this.segments = segments;
    }

    /**
     * get all nodes of this taxiway
     *
     * @return
     */
    public List<TaxiNetworkNode> getAllNodes() {

        List<TaxiNetworkNode> returnList = new ArrayList<>();

        for (TaxiwaySegment s : this.segments) {

            returnList.add(s.getFromNode());
            returnList.add(s.getToNode());

        }

        return returnList;

    }

}
