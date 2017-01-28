
package de.xatc.commons.db.sharedentities.aptmodel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 * This is a apt airport bean used to be marshealled as xml for later
 * unmarshalling and displaying when apt airport is selected
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "aptairport")
public class AptAirport implements Serializable {

    /**
     * the airport icao
     */
    @Id
    @Index(name = "icaoindex")
    private String icao;

    /**
     * airport name
     */
    @Index(name = "nameIndex")
    private String aiportName;

    
  
    
    /**
     * the aiport type. 1=LAND 2=SEA 3=HELIPAD
     */
    private int airportType = 1;

    /**
     * apt.dat filename containing this airport
     */
    private String airportFileName;

    /**
     * full filename of the file containing this airport
     */
    private String fullFileName;

    /**
     * the short filename of the apt.dat file containing this airport
     */
    private String shortFileName;

    /**
     * the line number where the aiport declaration starts
     */
    @Id
    @Index(name = "lineNumberStartIndex")
    private int lineNumberStart;

    /**
     * does this airport has a taxinetwork?
     */
    private boolean taxiNetworkPresent = false;

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
    public String getAirportFileName() {
        return airportFileName;
    }

    /**
     * setter
     *
     * @param airportFileName
     */
    public void setAirportFileName(String airportFileName) {
        this.airportFileName = airportFileName;
    }

    /**
     * getter
     *
     * @return
     */
    public String getAiportName() {
        return aiportName;
    }

    /**
     * setter
     *
     * @param aiportName
     */
    public void setAiportName(String aiportName) {
        this.aiportName = aiportName;
    }

    /**
     * getter
     *
     * @return
     */
    public String getFullFileName() {
        return fullFileName;
    }

    /**
     * setter
     *
     * @param fullFileName
     */
    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    /**
     * getter
     *
     * @return
     */
    public int getAirportType() {
        return airportType;
    }

    /**
     * setter
     *
     * @param airportType
     */
    public void setAirportType(int airportType) {
        this.airportType = airportType;
    }

    /**
     * getter
     *
     * @return
     */
    public int getLineNumberStart() {
        return lineNumberStart;
    }

    /**
     * setter
     *
     * @param lineNumberStart
     */
    public void setLineNumberStart(int lineNumberStart) {
        this.lineNumberStart = lineNumberStart;
    }

    /**
     * getter
     *
     * @return
     */
    public String getShortFileName() {
        return shortFileName;
    }

    /**
     * setter
     *
     * @param shortFileName
     */
    public void setShortFileName(String shortFileName) {
        this.shortFileName = shortFileName;
    }

    /**
     * getter
     *
     * @return
     */
    public boolean isTaxiNetworkPresent() {
        return taxiNetworkPresent;
    }

    /**
     * setter
     *
     * @param taxiNetworkPresent
     */
    public void setTaxiNetworkPresent(boolean taxiNetworkPresent) {
        this.taxiNetworkPresent = taxiNetworkPresent;
    }

    /**
     * overwritten toString method to be shown up inside selection List
     *
     * @return
     */
    @Override
    public String toString() {

        String returnString = "";
        returnString += this.getIcao() + " - " + this.getAiportName();

        return returnString;
    }



    
    
    
    
}
