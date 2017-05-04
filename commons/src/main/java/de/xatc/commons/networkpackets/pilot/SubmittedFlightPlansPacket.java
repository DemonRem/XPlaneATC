/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.pilot;

import de.xatc.commons.networkpackets.parent.NetworkPacket;
import java.util.List;

/**
 *
 * @author C047
 */
public class SubmittedFlightPlansPacket extends NetworkPacket {
    
    List<SubmittedFlightPlan> list;

    public List<SubmittedFlightPlan> getList() {
        return list;
    }

    public void setList(List<SubmittedFlightPlan> list) {
        this.list = list;
    }
    
    
    
    
}
