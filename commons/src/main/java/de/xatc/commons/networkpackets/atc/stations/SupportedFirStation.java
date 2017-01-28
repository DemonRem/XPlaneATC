/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.stations;

import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Mirko
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "stationData")
public class SupportedFirStation extends NetworkPacket {
    
    @Id
    private int id;
    
    private RegisteredUser user;
    private Fir fir;
    private String firMessage;
    private String frequency;
    private Timestamp firStart;
    private Timestamp firEnd;
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Fir getFir() {
        return fir;
    }

    public void setFir(Fir fir) {
        this.fir = fir;
    }

    public String getFirMessage() {
        return firMessage;
    }

    public void setFirMessage(String firMessage) {
        this.firMessage = firMessage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Timestamp getFirStart() {
        return firStart;
    }

    public void setFirStart(Timestamp firStart) {
        this.firStart = firStart;
    }

    public Timestamp getFirEnd() {
        return firEnd;
    }

    public void setFirEnd(Timestamp firEnd) {
        this.firEnd = firEnd;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        
        String returnString = "FIR: ";
        returnString += this.fir.getFirNameIcao() + " - " + this.fir.getFirName() + " - " + this.frequency;
        if (this.active) {
            returnString += " - active";
        }
        else {
            returnString += " - inactive";
        }
        
        returnString += " - " + this.firMessage;
        return returnString;
    }
    
    
    
    
}
