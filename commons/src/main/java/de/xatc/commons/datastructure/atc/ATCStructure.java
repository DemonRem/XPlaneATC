/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.datastructure.atc;

import de.xatc.commons.datastructure.AbstractDataStructure;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.XATCUserSession;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import io.netty.channel.Channel;

/**
 *
 * @author c047
 */
public class ATCStructure extends NetworkPacket implements AbstractDataStructure {
    
    private String userName;
    private RegisteredUser user;
    private boolean active = false;
    private String structureSessionID;
    private String channelID;
    private XATCUserSession userSession;
    


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public XATCUserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(XATCUserSession userSession) {
        this.userSession = userSession;
    }

    public String getStructureSessionID() {
        return structureSessionID;
    }

    public void setStructureSessionID(String structureSessionID) {
        this.structureSessionID = structureSessionID;
    }
    
    
    
    
    
    
    
    
}
