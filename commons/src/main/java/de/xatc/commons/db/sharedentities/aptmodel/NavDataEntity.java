
package de.xatc.commons.db.sharedentities.aptmodel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 * This database contains the navdata e.g. ils freqs information for an airport
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "navdataentity")
public class NavDataEntity implements Serializable {

    /**
     * aiport icao
     */
    @Id
    @Index(name = "navicaoIndex")
    private String icao;

    /**
     * runway
     */
    @Id
    @Index(name = "runwayIndex")
    private String runway;

    /**
     * frequencies
     */
    private String freq;

    /**
     * ils type
     */
    private String type;
    /**
     * lineType
     */
    private int lineTypeNumber;

    /**
     * latitude
     */
    @Id
    @Index(name = "latIndex")
    private String lat;

    /**
     * longitude
     */
    @Id
    @Index(name = "lonIndex")
    private String lon;

    /**
     * runway heading
     */
    private String heading;

    /**
     * getter
     *
     * @return
     */
    public String getIcao() {
        return icao;
    }

    /**
     * setter
     *
     * @param icao
     */
    public void setIcao(String icao) {
        this.icao = icao;
    }

    /**
     * getter
     *
     * @return
     */
    public String getRunway() {
        return runway;
    }

    /**
     * setter
     *
     * @param runway
     */
    public void setRunway(String runway) {
        this.runway = runway;
    }

    /**
     * getter
     *
     * @return
     */
    public String getFreq() {
        return freq;
    }

    /**
     * setter
     *
     * @param ilsFreq
     */
    public void setFreq(String ilsFreq) {
        this.freq = ilsFreq;
    }

    /**
     * getter
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * setter
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter
     *
     * @return
     */
    public int getLineTypeNumber() {
        return lineTypeNumber;
    }

    /**
     * setter
     *
     * @param lineTypeNumber
     */
    public void setLineTypeNumber(int lineTypeNumber) {
        this.lineTypeNumber = lineTypeNumber;
    }

    /**
     * getter
     *
     * @return
     */
    public String getHeading() {
        return heading;
    }

    /**
     * setter
     *
     * @param heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * getter
     *
     * @return
     */
    public String getLat() {
        return lat;
    }

    /**
     * setter
     *
     * @param lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * getter
     *
     * @return
     */
    public String getLon() {
        return lon;
    }

    /**
     * setter
     *
     * @param lon
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

}
