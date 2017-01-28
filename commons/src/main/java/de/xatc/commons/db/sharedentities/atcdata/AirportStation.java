/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.db.sharedentities.atcdata;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
        region = "runtimeData")
public class AirportStation implements Serializable {

    
    @Index(name = "id")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(nullable = false)
    @Id
    private int id;
    
    private String airportIcao;
    private String airportName;
    
  
   
    private String frequency;
    private boolean atis = false;
    private String stationName;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isAtis() {
        return atis;
    }

    public void setAtis(boolean atis) {
        this.atis = atis;
    }

   

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

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
    
  
    
    @Override
    public String toString() {
       
        return this.frequency + " - " + this.stationName;
        
    }
    
    
    
    
    
    
}
