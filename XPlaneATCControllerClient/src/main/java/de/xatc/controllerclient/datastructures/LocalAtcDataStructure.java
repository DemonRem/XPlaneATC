/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.datastructures;

import de.xatc.commons.datastructure.atc.ATCStructure;

/**
 *
 * @author c047
 */
public class LocalAtcDataStructure {
    
    private ATCStructure serverATCStructure;
    private String sessionID;

    public ATCStructure getServerATCStructure() {
        return serverATCStructure;
    }

    public void setServerATCStructure(ATCStructure serverATCStructure) {
        this.serverATCStructure = serverATCStructure;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
    
    
    
}
