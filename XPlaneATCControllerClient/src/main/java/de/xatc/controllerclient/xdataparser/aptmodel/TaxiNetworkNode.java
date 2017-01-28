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
 * This class represents a Node inside a taxiway network
 *
 * @author Mirko
 */
public class TaxiNetworkNode {

    /**
     * navpoint
     */
    private NavPoint navPoint;
    /**
     * id - extracted from apt.dat file
     */
    private int id;
    /**
     * node name (usally the taxiway name)
     */
    private String name;
    /**
     * usage - oneway or both directions possible
     */
    private String usage;
    /**
     * names of connected taxiways
     */
    private List<String> connNames = new ArrayList<>();
    
    /**
     * List of nodes to which this node is connected to
     */
    private List<TaxiNetworkNode> connectionPoints = new ArrayList<>();

    /**
     * getter
     *
     * @return
     */
    public NavPoint getNavPoint() {
        return navPoint;
    }

    /**
     * setter
     *
     * @param navPoint
     */
    public void setNavPoint(NavPoint navPoint) {
        this.navPoint = navPoint;
    }

    /**
     * getter
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * setter
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

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
    public String getUsage() {
        return usage;
    }

    /**
     * setter
     *
     * @param usage
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    /**
     * getter
     *
     * @return
     */
    public List<String> getConnNames() {
        return connNames;
    }

    /**
     * setter
     *
     * @param connNames
     */
    public void setConnNames(List<String> connNames) {
        this.connNames = connNames;
    }

    /**
     * getter
     * @return 
     */
    public List<TaxiNetworkNode> getConnectionPoints() {
        return connectionPoints;
    }

    /**
     * setter
     * @param connectionPoints 
     */
    public void setConnectionPoints(List<TaxiNetworkNode> connectionPoints) {
        this.connectionPoints = connectionPoints;
    }
    

}
