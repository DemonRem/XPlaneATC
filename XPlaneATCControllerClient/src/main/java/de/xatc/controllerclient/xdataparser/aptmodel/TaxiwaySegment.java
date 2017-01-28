/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.xdataparser.aptmodel;

/**
 * A segment of a taxiway
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class TaxiwaySegment {

    /**
     * fromnode
     */
    private TaxiNetworkNode fromNode;
    /**
     * to node
     */
    private TaxiNetworkNode toNode;
    /**
     * type
     */
    private String type;
    /**
     * direction
     */
    private String direction;
    /**
     * name of segment
     */
    private String name;
    /**
     * id of frompoint
     */
    private int fromPointID;
    /**
     * id of to point
     */
    private int toPointID;

    /**
     * getter
     *
     * @return
     */
    public TaxiNetworkNode getFromNode() {
        return fromNode;
    }

    /**
     * setter
     *
     * @param fromNode
     */
    public void setFromNode(TaxiNetworkNode fromNode) {
        this.fromNode = fromNode;
    }

    /**
     * getter
     *
     * @return
     */
    public TaxiNetworkNode getToNode() {
        return toNode;
    }

    /**
     * setter
     *
     * @param toNode
     */
    public void setToNode(TaxiNetworkNode toNode) {
        this.toNode = toNode;
    }

    /**
     * getter
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * setter
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
    public String getDirection() {
        return direction;
    }

    /**
     * setter
     *
     * @param direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * getter
     *
     * @return
     */
    public int getFromPointID() {
        return fromPointID;
    }

    /**
     * setter
     *
     * @param fromPointID
     */
    public void setFromPointID(int fromPointID) {
        this.fromPointID = fromPointID;
    }

    /**
     * getter
     *
     * @return
     */
    public int getToPointID() {
        return toPointID;
    }

    /**
     * setter
     *
     * @param toPointID
     */
    public void setToPointID(int toPointID) {
        this.toPointID = toPointID;
    }

}
