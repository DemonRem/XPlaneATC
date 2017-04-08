/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.client;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author Mirko
 */
public class SubmittedFlightPlansActionPacket extends NetworkPacket {
    
    
    private int serversID;
    private String action;

    public int getServersID() {
        return serversID;
    }

    public void setServersID(int serversID) {
        this.serversID = serversID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    
    
}
