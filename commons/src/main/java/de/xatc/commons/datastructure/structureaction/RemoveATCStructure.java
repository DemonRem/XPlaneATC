/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.datastructure.structureaction;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author c047
 */
public class RemoveATCStructure extends NetworkPacket {
    
    private String strucutureSessionID;

    public String getStrucutureSessionID() {
        return strucutureSessionID;
    }

    public void setStrucutureSessionID(String strucutureSessionID) {
        this.strucutureSessionID = strucutureSessionID;
    }
    
    
    
}
