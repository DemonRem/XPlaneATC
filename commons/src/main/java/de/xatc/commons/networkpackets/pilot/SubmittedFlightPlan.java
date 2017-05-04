/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.pilot;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

/**
 *
 * @author C047
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "flightplan")
public class SubmittedFlightPlan extends NetworkPacket {

    @Index(name = "id")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(nullable = false)
    @Id
    private int id;
    private int serversID;
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
    private boolean active = false;
    private String controllerComments;
    @Lob
    @Column(length = 65535)
    private RegisteredUser assignedController;
    
    @Lob
    @Column(length = 65535)
    private RegisteredUser flightPlanOwner;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getControllerComments() {
        return controllerComments;
    }

    public void setControllerComments(String controllerComments) {
        this.controllerComments = controllerComments;
    }

    public RegisteredUser getAssignedController() {
        return assignedController;
    }

    public void setAssignedController(RegisteredUser assignedController) {
        this.assignedController = assignedController;
    }

    public RegisteredUser getFlightPlanOwner() {
        return flightPlanOwner;
    }

    public void setFlightPlanOwner(RegisteredUser flightPlanOwner) {
        this.flightPlanOwner = flightPlanOwner;
    }

    public int getServersID() {
        return serversID;
    }

    public void setServersID(int serversID) {
        this.serversID = serversID;
    }
    
    
    
    
    
    
}
