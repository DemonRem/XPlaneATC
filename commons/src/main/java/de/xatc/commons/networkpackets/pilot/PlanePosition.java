/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.pilot;

import de.mytools.tools.dateandtime.SQLDateTimeTools;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import java.sql.Timestamp;
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
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "PlanePosition")
public class PlanePosition extends NetworkPacket {

            /**
     * the airport icao
     */
    @Id
    @Index(name = "id")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(nullable = false)
    private int id;
    
    @Id
    @Index(name="planePosistionUserName")
    private String userName;
    
    private String latitude;
    private String longitude;
    private String altitude;
    private String gpsSpeed;
    private String weigth;
    private String groundSpeed;
    private String transponder;
    private String transponderMode;
    private String heading;
    private String truePhi;
    private String trueTheta;
    private String com1Freq;
    private Timestamp timestamp = SQLDateTimeTools.getTimeStampOfNow();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(String gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public String getWeigth() {
        return weigth;
    }

    public void setWeigth(String weigth) {
        this.weigth = weigth;
    }

    public String getGroundSpeed() {
        return groundSpeed;
    }

    public void setGroundSpeed(String groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    public String getTransponder() {
        return transponder;
    }

    public void setTransponder(String transponder) {
        this.transponder = transponder;
    }

    public String getTransponderMode() {
        return transponderMode;
    }

    public void setTransponderMode(String transponderMode) {
        this.transponderMode = transponderMode;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTruePhi() {
        return truePhi;
    }

    public void setTruePhi(String truePhi) {
        this.truePhi = truePhi;
    }

    public String getTrueTheta() {
        return trueTheta;
    }

    public void setTrueTheta(String trueTheta) {
        this.trueTheta = trueTheta;
    }

    public String getCom1Freq() {
        return com1Freq;
    }

    public void setCom1Freq(String com1Freq) {
        this.com1Freq = com1Freq;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
    
    
    
           
    
    
    
}
