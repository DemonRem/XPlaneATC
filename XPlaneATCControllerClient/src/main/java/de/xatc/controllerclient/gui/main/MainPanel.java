/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2015
 * Have fun!
 *
 */
package de.xatc.controllerclient.gui.main;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 * this is the main panel, containing all subpanels
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class MainPanel extends JPanel {

    private static final Logger LOG = Logger.getLogger(MainPanel.class.getName());
    /**
     * headerpanel
     */
    private HeaderPanel headerPanel = null;

    /**
     * the center map panel
     */
    private ATCMapPanel mapPanel = null;

    /**
     * aboutpanel
     */
    private AboutWindow aboutPanel = null;
    
    private StatusPanel statusPanel;

    /**
     * constructor
     */
    public MainPanel() {

        super();
        LOG.info("MainPanel Constructor");
        initComponents();
    }

    /**
     * init gui components
     */
    private void initComponents() {

        LOG.info("MainPanel init components");
        this.setLayout(new BorderLayout());

        this.mapPanel = new ATCMapPanel();

        this.headerPanel = new HeaderPanel();
        this.statusPanel = new StatusPanel();

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(this.mapPanel, BorderLayout.CENTER);
        this.add(this.statusPanel, BorderLayout.SOUTH);

    }

    /**
     * remove all toolpanels like charter or properties
     */
    public void removeAllWorkingPanels() {

        System.gc();
    }

    /**
     *
     * @return
     */
    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    /**
     *
     * @param headerPanel
     */
    public void setHeaderPanel(HeaderPanel headerPanel) {
        this.headerPanel = headerPanel;
    }

    /**
     *
     * @return
     */
    public ATCMapPanel getMapPanel() {
        return mapPanel;
    }

    /**
     *
     * @param mapPanel
     */
    public void setMapPanel(ATCMapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    /**
     *
     * @return
     */
    public AboutWindow getAboutPanel() {
        return aboutPanel;
    }

    /**
     *
     * @param aboutPanel
     */
    public void setAboutPanel(AboutWindow aboutPanel) {
        this.aboutPanel = aboutPanel;
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public void setStatusPanel(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;
    }

    
    
}
