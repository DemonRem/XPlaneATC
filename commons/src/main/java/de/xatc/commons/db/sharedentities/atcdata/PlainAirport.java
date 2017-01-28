/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.db.sharedentities.atcdata;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Mirko
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "runtimeData")
public class PlainAirport implements Serializable {
    
    @Id
    private String airportIcao;
    
    private String airportName;
    private String cityName;
    private String countryCode;
    
    @Lob
    @Column(length = 65535)
    private ArrayList<AirportStation> airportStations = new ArrayList<>();
    
    @Lob
    private PlainNavPoint position;

    public String getAirportIcao() {
        return airportIcao;
    }

    public void setAirportIcao(String airportIcao) {
        this.airportIcao = airportIcao;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public PlainNavPoint getPosition() {
        return position;
    }

    public void setPosition(PlainNavPoint position) {
        this.position = position;
    }

    public ArrayList<AirportStation> getAirportStations() {
        return airportStations;
    }

    public void setAirportStations(ArrayList<AirportStation> airportStations) {
        this.airportStations = airportStations;
    }
    
    
    
    @Override
    public String toString() {
        
        return this.airportIcao + " - " + this.airportName;
    }
    
    
}
