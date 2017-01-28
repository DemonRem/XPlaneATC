/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.stations;

import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.db.sharedentities.atcdata.PlainNavPoint;
import java.util.ArrayList;
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
public class SupportedAirport {
    
    @Index(name = "id")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(nullable = false)
    @Id
    private int id;
    
    private PlainAirport airport;
    
    private String atisMessage;
    private String atisFrequency;
    
    @Lob
    @Column(length = 65535)
    private ArrayList<SupportedAirportStation> airportStations = new ArrayList<>();

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

    public String getAtisMessage() {
        return atisMessage;
    }

    public void setAtisMessage(String atisMessage) {
        this.atisMessage = atisMessage;
    }

    public String getAtisFrequency() {
        return atisFrequency;
    }

    public void setAtisFrequency(String atisFrequency) {
        this.atisFrequency = atisFrequency;
    }

    public ArrayList<SupportedAirportStation> getAirportStations() {
        return airportStations;
    }

    public void setAirportStations(ArrayList<SupportedAirportStation> airportStations) {
        this.airportStations = airportStations;
    }
    
    
    
}
