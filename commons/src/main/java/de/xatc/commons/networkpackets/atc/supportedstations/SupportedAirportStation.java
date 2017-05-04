/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.supportedstations;

import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

/**
 *
 * @author Mirko
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "stationData")
public class SupportedAirportStation extends NetworkPacket {
    
    @Index(name = "id")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(nullable = false)
    @Id
    private int id;
    
    @Lob
    @Column(length = 65535)
    private PlainAirport airport;
    
    private String stationName;
    private String stationFrequency;
    private int visibility;
    private boolean active = false;
    
    private String saveName;
    
    
    
    @Lob
    @Column(length = 65535)
    private RegisteredUser regjsteredUser;
    
    @Lob
    @Column(length = 65535)
    private SupportedStationStatistics statistics;
    
    @Lob
    @Column(length = 65535)
    private SupportedATISStation atis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlainAirport getAirport() {
        return airport;
    }

    public void setAirport(PlainAirport airport) {
        this.airport = airport;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationFrequency() {
        return stationFrequency;
    }

    public void setStationFrequency(String stationFrequency) {
        this.stationFrequency = stationFrequency;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RegisteredUser getUser() {
        return regjsteredUser;
    }

    public void setUser(RegisteredUser user) {
        this.regjsteredUser = user;
    }

    public SupportedStationStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(SupportedStationStatistics statistics) {
        this.statistics = statistics;
    }

    public SupportedATISStation getAtis() {
        return atis;
    }

    public void setAtis(SupportedATISStation atis) {
        this.atis = atis;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public RegisteredUser getRegjsteredUser() {
        return regjsteredUser;
    }

    public void setRegjsteredUser(RegisteredUser regjsteredUser) {
        this.regjsteredUser = regjsteredUser;
    }
    
    
    
    
    
    
    
    
 
    
    
}
