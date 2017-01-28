/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.parent;

import java.io.Serializable;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public abstract class NetworkPacket implements Serializable {

    private String sessionID;
    private String channelID;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }
    

}
