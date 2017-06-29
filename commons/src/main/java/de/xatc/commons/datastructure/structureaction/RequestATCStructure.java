/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.datastructure.structureaction;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author C047
 */
public class RequestATCStructure extends NetworkPacket {
    
    private String structureSessionID;

    public String getStructureSessionID() {
        return structureSessionID;
    }

    public void setStructureSessionID(String structureSessionID) {
        this.structureSessionID = structureSessionID;
    }
    
    
    
}
