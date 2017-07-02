/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.pilot;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author Mirko
 */
public class SubmittedFlightPlansActionPacket extends NetworkPacket {
    
    

    /**
     * action can be "new", "revoke","update","sync","syncAll","accept","release","handover"
     */
    private String action;
    private SubmittedFlightPlan SubmittedFlightPlan;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public SubmittedFlightPlan getSubmittedFlightPlan() {
        return SubmittedFlightPlan;
    }

    public void setSubmittedFlightPlan(SubmittedFlightPlan SubmittedFlightPlan) {
        this.SubmittedFlightPlan = SubmittedFlightPlan;
    }
    
    
    
    
}
