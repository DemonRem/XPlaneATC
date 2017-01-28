/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.client;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author C047
 */
public class SubmittedFlightPlan extends NetworkPacket {

    private String icaoFrom;
    private String icaoTo;
    private String aircraftType;
    private String flightLevel;
    private String flightNumber;
    private String takeOffTime;
    private String arrivalTime;
    private String ifrOrVfr;
    private String airline;
    private String route;
    private String remark;

    public String getIcaoFrom() {
        return icaoFrom;
    }

    public void setIcaoFrom(String icaoFrom) {
        this.icaoFrom = icaoFrom;
    }

    public String getIcaoTo() {
        return icaoTo;
    }

    public void setIcaoTo(String icaoTo) {
        this.icaoTo = icaoTo;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getFlightLevel() {
        return flightLevel;
    }

    public void setFlightLevel(String flightLevel) {
        this.flightLevel = flightLevel;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(String takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getIfrOrVfr() {
        return ifrOrVfr;
    }

    public void setIfrOrVfr(String ifrOrVfr) {
        this.ifrOrVfr = ifrOrVfr;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    
}
