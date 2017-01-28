/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2015
 * Have fun!
 *
 */
package de.xatc.controllerclient.gui.setupatc;

import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.gui.painters.AircraftPainter;
import de.xatc.controllerclient.gui.painters.AptPainter;
import de.xatc.controllerclient.gui.painters.AtcSetupMapFirPainter;
import de.xatc.controllerclient.gui.painters.RosePainter;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

/**
 * this class is the home of the openstreetmap panel.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ATCSetupMapPanel extends JPanel {

    /**
     * the openstreetmap jmap kit
     */
    private JXMapKit jkit;

    private RosePainter rosePainter;
    private AircraftPainter aircraftPainter;
    private AtcSetupMapFirPainter firPainter;
    private AptPainter aptPainter;

    /**
     * the container for all painters
     */
    private CompoundPainter<JXMapViewer> compoundPainters;

    /**
     * list combining all painters
     */
    private List<Painter<JXMapViewer>> paintersList;

    /**
     * constructor and init components
     *
     *
     */
    public ATCSetupMapPanel() {

        super();
        this.initComponents();

    }

    /**
     * init all gui stuff
     */
    private void initComponents() {

        this.jkit = new JXMapKit();

        jkit.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
        GeoPosition startPos = new GeoPosition(XHSConfig.getInitialPos().getLatitudedouble(), XHSConfig.getInitialPos().getLongitudeDouble());

        System.out.println(XHSConfig.getInitialPos());
        jkit.setAddressLocationShown(true);
        System.out.println("AIRPORT MAP Panel INIT COMPONENTS");

        this.setLayout(new BorderLayout());

        jkit.setCenterPosition(startPos);
        jkit.setZoom(17);
        jkit.repaint();

        rosePainter = new RosePainter();
        rosePainter.getRoseMap().put(50, XHSConfig.getInitialPos());

        firPainter = new AtcSetupMapFirPainter();
        aptPainter = new AptPainter();
        
        
        aircraftPainter = new AircraftPainter();
        
        
        this.paintersList = new ArrayList<>();

        paintersList.add(rosePainter);
        paintersList.add(aircraftPainter);
        paintersList.add(firPainter);
        paintersList.add(aptPainter);

        this.compoundPainters = new CompoundPainter<>(paintersList);
        this.jkit.getMainMap().setOverlayPainter(compoundPainters);

        this.jkit.setMiniMapVisible(false);
        this.jkit.setZoomSliderVisible(true);

        this.add(jkit, BorderLayout.CENTER);

    }

    /**
     * add a new painter from outside
     *
     * @param p
     */
    public void addPainter(Painter<JXMapViewer> p) {
        this.paintersList.add(p);
    }

    public JXMapKit getJkit() {
        return jkit;
    }

    public void setJkit(JXMapKit jkit) {
        this.jkit = jkit;
    }

    public CompoundPainter<JXMapViewer> getCompoundPainters() {
        return compoundPainters;
    }

    public void setCompoundPainters(CompoundPainter<JXMapViewer> compoundPainters) {
        this.compoundPainters = compoundPainters;
    }

    public List<Painter<JXMapViewer>> getPaintersList() {
        return paintersList;
    }

    public void setPaintersList(List<Painter<JXMapViewer>> paintersList) {
        this.paintersList = paintersList;
    }

    public RosePainter getRosePainter() {
        return rosePainter;
    }

    public void setRosePainter(RosePainter rosePainter) {
        this.rosePainter = rosePainter;
    }

    public AircraftPainter getAircraftPainter() {
        return aircraftPainter;
    }

    public void setAircraftPainter(AircraftPainter aircraftPainter) {
        this.aircraftPainter = aircraftPainter;
    }

    public AtcSetupMapFirPainter getFirPainter() {
        return firPainter;
    }

    public void setFirPainter(AtcSetupMapFirPainter firPainter) {
        this.firPainter = firPainter;
    }

    public AptPainter getAptPainter() {
        return aptPainter;
    }

    public void setAptPainter(AptPainter aptPainter) {
        this.aptPainter = aptPainter;
    }
    
   
    
    
    

}
