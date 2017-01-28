
package de.xatc.controllerclient.xdataparser.aptmodel;

import de.xatc.commons.db.sharedentities.aptmodel.NavDataEntity;
import de.xatc.controllerclient.navigation.NavPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains the full description of an airport with taxiways,
 * pavements, runways and so on. The data is parsed from apt.dat files
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */

public class AptAirportModel {

   // jetzt müssen wir das Ding hier noch anzeigen!

    /**
     * runways
     */
    private List<AptRunwayModel> runwayList = new ArrayList<>();
    /**
     * navdata, fequencies
     */
    private List<NavDataEntity> navData = new ArrayList<>();
    /**
     * pavement model
     */
    private List<AptPavementModel> pavement = new ArrayList<>();
    /**
     * icao code of airport
     */

    private String icao;
    /**
     * name of airport
     */
    private String airportName;
    /**
     * altitude of airport
     */
    private String altitude;
    /**
     * most northern navpoint of airport to paint airport name
     */
    private NavPoint mostNorthernPoint = null;
    /**
     * List of parking position (spawned by xplane)
     */
    private ArrayList<NavPoint> parkings = new ArrayList<>();
    /**
     * list of taxi network nodes which do connect multiple taxiways
     */
    private ArrayList<TaxiNetworkNode> connPoints = new ArrayList<>();

    /**
     * list of all taxinetwork nodes
     */
    private HashMap<Integer, TaxiNetworkNode> taxiNetworkNodes = new HashMap<>();

    /**
     * taxiways
     */
    private HashMap<String, Taxiway> taxiways = new HashMap<>();

    
    /**
     * airport Frequencies
     */
    private Map<String,String> airportFrequencies = new HashMap();
    
  
    
    /**
     * new attributes introduced with xplane 10.5
     */
    Map<String, String> attributeMap = new HashMap<>();
    
    
    /**
     * getter
     *
     * @return
     */
    public List<AptRunwayModel> getRunwayList() {
        return runwayList;
    }

    /**
     * setter
     *
     * @param runwayList
     */
    public void setRunwayList(ArrayList<AptRunwayModel> runwayList) {
        this.runwayList = runwayList;
    }

    /**
     * getter
     *
     * @return
     */
    public List<AptPavementModel> getPavement() {
        return pavement;
    }

    /**
     * setter
     *
     * @param pavement
     */
    public void setPavement(ArrayList<AptPavementModel> pavement) {
        this.pavement = pavement;
    }

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
    public String getAirportName() {
        return airportName;
    }

    /**
     * setter
     *
     * @param airportName
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * getter
     *
     * @return
     */
    public String getAltitude() {
        return altitude;
    }

    /**
     * setter
     *
     * @param altitude
     */
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    /**
     * getter
     *
     * @return
     */
    public NavPoint getMostNorthernPoint() {
        return mostNorthernPoint;
    }

    /**
     * setter
     *
     * @param mostNorthernPoint
     */
    public void setMostNorthernPoint(NavPoint mostNorthernPoint) {
        this.mostNorthernPoint = mostNorthernPoint;
    }

    /**
     * setter
     *
     * @return
     */
    public List<NavDataEntity> getNavData() {
        return navData;
    }

    /**
     * getter
     *
     * @param navData
     */
    public void setNavData(List<NavDataEntity> navData) {
        this.navData = navData;
    }

    /**
     * setter
     *
     * @return
     */
    public List<NavPoint> getParkings() {
        return parkings;
    }

    /**
     * getter
     *
     * @param parkings
     */
    public void setParkings(ArrayList<NavPoint> parkings) {
        this.parkings = parkings;
    }

    /**
     * getter
     *
     * @return
     */
    public Map<String, Taxiway> getTaxiways() {
        return taxiways;
    }

    /**
     * setter
     *
     * @param taxiways
     */
    public void setTaxiways(HashMap<String, Taxiway> taxiways) {
        this.taxiways = taxiways;
    }

    /**
     * getter
     *
     * @return
     */
    public Map<Integer, TaxiNetworkNode> getTaxiNetworkNodes() {
        return taxiNetworkNodes;
    }

    /**
     * setter
     *
     * @param taxiNetworkNodes
     */
    public void setTaxiNetworkNodes(HashMap<Integer, TaxiNetworkNode> taxiNetworkNodes) {
        this.taxiNetworkNodes = taxiNetworkNodes;
    }

    /**
     * getter
     *
     * @return
     */
    public List<TaxiNetworkNode> getConnPoints() {
        return connPoints;
    }

    /**
     * setter
     *
     * @param connPoints
     */
    public void setConnPoints(ArrayList<TaxiNetworkNode> connPoints) {
        this.connPoints = connPoints;
    }

    public Map<String, String> getAirportFrequencies() {
        return airportFrequencies;
    }

    public void setAirportFrequencies(Map<String, String> airportFrequencies) {
        this.airportFrequencies = airportFrequencies;
    }

    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    
    
    
    
}
