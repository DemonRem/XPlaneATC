/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.db.sharedentities.user;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "xatcusersession")
public class XATCUserSession implements Serializable {

    @Id
    @Index(name = "sessionID")
    private String sessionID;

    private boolean active = false;
    
    @OneToOne
    private RegisteredUser registeredUser;
    
    @Index(name = "channelId")
    private String channelID;
    
    @Index(name = "sessionUserName")
    private String sessionUserName;
    
    
    private Timestamp startSession;
    private Timestamp endSession;

    private Timestamp lastAction;

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

   

    public Timestamp getStartSession() {
        return startSession;
    }

    public void setStartSession(Timestamp startSession) {
        this.startSession = startSession;
    }

    public Timestamp getEndSession() {
        return endSession;
    }

    public void setEndSession(Timestamp endSession) {
        this.endSession = endSession;
    }

    public Timestamp getLastAction() {
        return lastAction;
    }

    public void setLastAction(Timestamp lastAction) {
        this.lastAction = lastAction;
    }

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

    public String getSessionUserName() {
        return sessionUserName;
    }

    public void setSessionUserName(String sessionUserName) {
        this.sessionUserName = sessionUserName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    

}
