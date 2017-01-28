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
public class DataSyncPacket extends NetworkPacket {
    
    private String dataSetToSync;
    
    private int maxDataSets;
    private int currentDataSet;
    
    private boolean finished = false;
    
    
    private Object transferObject;
    //this should be done with enum and type safety a bit later! TODO

    public String getDataSetToSync() {
        return dataSetToSync;
    }

    public void setDataSetToSync(String dataSetToSync) {
        this.dataSetToSync = dataSetToSync;
    }

    public int getMaxDataSets() {
        return maxDataSets;
    }

    public void setMaxDataSets(int maxDataSets) {
        this.maxDataSets = maxDataSets;
    }

    public int getCurrentDataSet() {
        return currentDataSet;
    }

    public void setCurrentDataSet(int currentDataSet) {
        this.currentDataSet = currentDataSet;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Object getTransferObject() {
        return transferObject;
    }

    public void setTransferObject(Object transferObject) {
        this.transferObject = transferObject;
    }
    
    
    
    
    
}
