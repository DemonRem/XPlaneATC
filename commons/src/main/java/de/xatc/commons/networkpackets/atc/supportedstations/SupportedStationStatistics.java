/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.supportedstations;

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
 * @author Mirko
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "stationData")
public class SupportedStationStatistics extends NetworkPacket {

    @Index(name = "id")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(nullable = false)
    @Id
    private int id;

    private Timestamp startStation;
    private Timestamp endStation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartStation() {
        return startStation;
    }

    public void setStartStation(Timestamp startStation) {
        this.startStation = startStation;
    }

    public Timestamp getEndStation() {
        return endStation;
    }

    public void setEndStation(Timestamp endStation) {
        this.endStation = endStation;
    }

}
