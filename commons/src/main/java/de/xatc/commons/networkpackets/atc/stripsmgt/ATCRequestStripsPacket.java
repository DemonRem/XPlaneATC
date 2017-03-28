/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.stripsmgt;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author Mirko
 */
public class ATCRequestStripsPacket extends NetworkPacket {
    
    private boolean requestATCStrips;

    public boolean isRequestATCStrips() {
        return requestATCStrips;
    }

    public void setRequestATCStrips(boolean requestATCStrips) {
        this.requestATCStrips = requestATCStrips;
    }
    
    
    
}
