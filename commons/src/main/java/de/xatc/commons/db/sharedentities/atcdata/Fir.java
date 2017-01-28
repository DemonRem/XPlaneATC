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
public class Fir implements Serializable {
    
    @Id
    private int id;
   
    private String firNameIcao;
    
    private String firName;
    
    
    private String countryCode;
    
    @Lob
    private PlainNavPoint position;
    
   
    @Lob
    @Column(length = 65535)
    private ArrayList<PlainNavPoint> poligonList = new ArrayList<>();
    
    
    @Lob
    @Column(length = 9999999)
    private ArrayList<PlainAirport> includedAirports = new ArrayList();

    public String getFirNameIcao() {
        return firNameIcao;
    }

    public void setFirNameIcao(String firNameIcao) {
        this.firNameIcao = firNameIcao;
    }

    public String getFirName() {
        return firName;
    }

    public void setFirName(String firName) {
        this.firName = firName;
    }

   

    public ArrayList<PlainNavPoint> getPoligonList() {
        return poligonList;
    }

    public void setPoligonList(ArrayList<PlainNavPoint> poligonList) {
        this.poligonList = poligonList;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<PlainAirport> getIncludedAirports() {
        return includedAirports;
    }

    public void setIncludedAirports(ArrayList<PlainAirport> includedAirports) {
        this.includedAirports = includedAirports;
    }

    public PlainNavPoint getPosition() {
        return position;
    }

    public void setPosition(PlainNavPoint position) {
        this.position = position;
    }

    
    
    
    
    @Override
    public String toString() {
        
     return countryCode + " - " + firNameIcao + " - " + this.firName;
            
   
  
    }
    

    
}
