/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.pilot;

import de.xatc.commons.networkpackets.parent.NetworkPacket;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author c047
 */
public class FMSPlan extends NetworkPacket {

    private String username;
    private boolean active = true;

    private double distanceToDestination;
    private double distanceToNextWayPoint;

    private Map<String,FMSWayPoint> wayPointList = new LinkedHashMap<>();

    public Map<String, FMSWayPoint> getWayPointList() {
        return wayPointList;
    }

    public void setWayPointList(Map<String, FMSWayPoint> wayPointList) {
        this.wayPointList = wayPointList;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getDistanceToDestination() {
        return distanceToDestination;
    }

    public void setDistanceToDestination(double distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
    }

    public double getDistanceToNextWayPoint() {
        return distanceToNextWayPoint;
    }

    public void setDistanceToNextWayPoint(double distanceToNextWayPoint) {
        this.distanceToNextWayPoint = distanceToNextWayPoint;
    }

    

}
