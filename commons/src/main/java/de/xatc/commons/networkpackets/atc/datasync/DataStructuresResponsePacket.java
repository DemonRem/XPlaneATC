/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.datasync;

import de.xatc.commons.datastructure.atc.ATCStructure;
import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author c047
 */
public class DataStructuresResponsePacket extends NetworkPacket {

    private String structureSsessionID;

    private PilotStructure pilotStructure;
    private ATCStructure atcStructure;

    public String getStructureSsessionID() {
        return structureSsessionID;
    }

    public void setStructureSsessionID(String structureSsessionID) {
        this.structureSsessionID = structureSsessionID;
    }

    public PilotStructure getPilotStructure() {
        return pilotStructure;
    }

    public void setPilotStructure(PilotStructure pilotStructure) {
        this.pilotStructure = pilotStructure;
    }

    public ATCStructure getAtcStructure() {
        return atcStructure;
    }

    public void setAtcStructure(ATCStructure atcStructure) {
        this.atcStructure = atcStructure;
    }
    
    
    
}
