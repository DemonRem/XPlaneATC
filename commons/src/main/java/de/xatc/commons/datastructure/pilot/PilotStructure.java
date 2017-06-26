/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.datastructure.pilot;

import de.xatc.commons.datastructure.AbstractDataStructure;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.XATCUserSession;
import de.xatc.commons.networkpackets.atc.supportedstations.SupportedATISStation;
import de.xatc.commons.networkpackets.atc.supportedstations.SupportedAirportStation;
import de.xatc.commons.networkpackets.atc.supportedstations.SupportedFirStation;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.commons.networkpackets.pilot.FMSPlan;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlan;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author c047
 */
public class PilotStructure extends NetworkPacket implements AbstractDataStructure {
    
    private String channelID;

    private boolean sessionActive = false;
    private XATCUserSession userSession;
    private RegisteredUser user;
    private String userName;
    private List<PlanePosition> planePositionList = new ArrayList<>();
    private PlanePosition lastKnownPlanePosition;
    private FMSPlan fmsPlan;
    private SubmittedFlightPlan submittedFlightPlan;
    private String currentFrequency;
    private String structureSessionID;
    
    private List<SupportedATISStation> visibleATISStations = new ArrayList<>();
    private List<SupportedAirportStation> visibleAirportStations = new ArrayList<>();
    private List<SupportedFirStation> visibleFirStations = new ArrayList<>();

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public boolean isSessionActive() {
        return sessionActive;
    }

    public void setSessionActive(boolean sessionActive) {
        this.sessionActive = sessionActive;
    }

    public XATCUserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(XATCUserSession userSession) {
        this.userSession = userSession;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PlanePosition> getPlanePositionList() {
        return planePositionList;
    }

    public void setPlanePositionList(List<PlanePosition> planePositionList) {
        this.planePositionList = planePositionList;
    }

    public FMSPlan getFmsPlan() {
        return fmsPlan;
    }

    public void setFmsPlan(FMSPlan fmsPlan) {
        this.fmsPlan = fmsPlan;
    }

    public SubmittedFlightPlan getSubmittedFlightPlan() {
        return submittedFlightPlan;
    }

    public void setSubmittedFlightPlan(SubmittedFlightPlan submittedFlightPlan) {
        this.submittedFlightPlan = submittedFlightPlan;
    }

    public String getCurrentFrequency() {
        return currentFrequency;
    }

    public void setCurrentFrequency(String currentFrequency) {
        this.currentFrequency = currentFrequency;
    }

    public List<SupportedATISStation> getVisibleATISStations() {
        return visibleATISStations;
    }

    public void setVisibleATISStations(List<SupportedATISStation> visibleATISStations) {
        this.visibleATISStations = visibleATISStations;
    }

    public List<SupportedAirportStation> getVisibleAirportStations() {
        return visibleAirportStations;
    }

    public void setVisibleAirportStations(List<SupportedAirportStation> visibleAirportStations) {
        this.visibleAirportStations = visibleAirportStations;
    }

    public List<SupportedFirStation> getVisibleFirStations() {
        return visibleFirStations;
    }

    public void setVisibleFirStations(List<SupportedFirStation> visibleFirStations) {
        this.visibleFirStations = visibleFirStations;
    }

    public PlanePosition getLastKnownPlanePosition() {
        return lastKnownPlanePosition;
    }

    public void setLastKnownPlanePosition(PlanePosition lastKnownPlanePosition) {
        this.lastKnownPlanePosition = lastKnownPlanePosition;
    }

    public String getStructureSessionID() {
        return structureSessionID;
    }

    public void setStructureSessionID(String structureSessionID) {
        this.structureSessionID = structureSessionID;
    }

    

    
    
    
    
    
    
    
}
