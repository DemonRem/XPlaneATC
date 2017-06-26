/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.datastructures;

import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.controllerclient.gui.painters.AircraftPainter;

/**
 *
 * @author c047
 */
public class LocalPilotDataStructure {
    
    
    private PilotStructure pilotServerStructure;
    private AircraftPainter aircraftPainter;
    private String sessionID;

    public PilotStructure getPilotServerStructure() {
        return pilotServerStructure;
    }

    public void setPilotServerStructure(PilotStructure pilotServerStructure) {
        this.pilotServerStructure = pilotServerStructure;
    }

    public AircraftPainter getAircraftPainter() {
        return aircraftPainter;
    }

    public void setAircraftPainter(AircraftPainter aircraftPainter) {
        this.aircraftPainter = aircraftPainter;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
    
    
    
    
    
}
