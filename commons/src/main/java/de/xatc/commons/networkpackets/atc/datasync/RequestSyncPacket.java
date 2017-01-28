/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.datasync;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author Mirko
 */
public class RequestSyncPacket extends NetworkPacket {
    
    
    private String dataSetToSync;

    public String getDataSetToSync() {
        return dataSetToSync;
    }

    public void setDataSetToSync(String dataSetToSync) {
        this.dataSetToSync = dataSetToSync;
    }
    
    
    
    
}
