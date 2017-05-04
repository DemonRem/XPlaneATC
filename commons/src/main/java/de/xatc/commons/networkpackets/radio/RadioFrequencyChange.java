/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.radio;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author C047
 */
public class RadioFrequencyChange extends NetworkPacket {
    
    
    private String oldFrequency;
    private String cuurentFrequency;

    public String getOldFrequency() {
        return oldFrequency;
    }

    public void setOldFrequency(String oldFrequency) {
        this.oldFrequency = oldFrequency;
    }

    public String getCuurentFrequency() {
        return cuurentFrequency;
    }

    public void setCuurentFrequency(String cuurentFrequency) {
        this.cuurentFrequency = cuurentFrequency;
    }

    
    
    
    
    
}
