/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.datastructure.structureaction;

import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.commons.networkpackets.pilot.PlanePosition;

/**
 *
 * @author C047
 */
public class PlanePositoinSyncResponse extends NetworkPacket {

    private String structureSessionID;
    private PlanePosition p;

    public String getStructureSessionID() {
        return structureSessionID;
    }

    public void setStructureSessionID(String structureSessionID) {
        this.structureSessionID = structureSessionID;
    }

    public PlanePosition getP() {
        return p;
    }

    public void setP(PlanePosition p) {
        this.p = p;
    }
}
