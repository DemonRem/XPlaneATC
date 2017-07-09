/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.gui.FlightPlanStrips;

import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlan;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.datastructures.DataStructureSilo;
import de.xatc.controllerclient.datastructures.LocalAtcDataStructure;
import de.xatc.controllerclient.datastructures.LocalPilotDataStructure;
import de.xatc.controllerclient.network.handlers.SubmittedFlightPlanActionHandler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author C047
 */
public class SubmittedFlightPlansFrame extends JFrame implements ActionListener {

    private static final Logger LOG = Logger.getLogger(SubmittedFlightPlansFrame.class.getName());
   
    
    private JPanel headPanel;
    private JPanel centerPanel;
    private JButton refreshButton;
    private JScrollPane scrollPane;
    private boolean loadControllersStrips;

    public SubmittedFlightPlansFrame(boolean myStrips) {
        super();
        this.loadControllersStrips = myStrips;
        initComponents();
    }

    private void initComponents() {

        this.setSize(new Dimension(400, 400));
        this.setLocation(40, 40);
        this.setLayout(new BorderLayout());
        headPanel = new JPanel();
        refreshButton = new JButton("refresh");
        refreshButton.addActionListener(this);
        headPanel.add(refreshButton);
        this.add(headPanel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setLayout(new VerticalLayout());

        this.scrollPane = new JScrollPane(centerPanel);

        this.add(scrollPane, BorderLayout.CENTER);

        this.loadStrips();
        this.pack();
        this.revalidate();
        this.setSize(new Dimension(this.getWidth() + 40, 400));
        this.setVisible(true);

    }

    private void loadStrips() {

        LOG.info("READING Flightstrips");

        this.centerPanel.removeAll();

        this.revalidate();
        this.repaint();

        if (this.loadControllersStrips) {
            
            LOG.info("Listing my flightPlans!");
            for (Entry<String,LocalPilotDataStructure> entry : DataStructureSilo.getLocalPilotStructure().entrySet()) {
              
                LOG.info(entry.getValue().getPilotServerStructure().getStructureSessionID());
                SubmittedFlightPlan plan = entry.getValue().getPilotServerStructure().getSubmittedFlightPlan();
                if (plan == null) {
                    continue;
                }
                if (plan.isRevoked() == true) {
                    continue;
                }
                if (plan.getAssingedControllerSessionID().equals(XHSConfig.getCurrentSessionID())) {
                    centerPanel.add(mapSubmittedFlightPlanToStrip(plan));
                }
                
            }
                
            
        }
        else {
            LOG.info("Listing all flightplans.");
            for (Entry<String,LocalPilotDataStructure> entry : DataStructureSilo.getLocalPilotStructure().entrySet()) {
                LOG.info(entry.getValue().getPilotServerStructure().getStructureSessionID());
                SubmittedFlightPlan plan = entry.getValue().getPilotServerStructure().getSubmittedFlightPlan();
                if (plan == null) {
                    continue;
                }
                if (plan.getAssingedControllerSessionID() == null) {
                    centerPanel.add(mapSubmittedFlightPlanToStrip(plan));
                }
                
            }
        }
        
        this.revalidate();
        this.repaint();
     

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();
        if (cmd.equals("refresh")) {
            this.centerPanel.removeAll();
            this.revalidate();
            this.repaint();
            SubmittedFlightPlanActionHandler.deleteLocalFlightPlans();
            SubmittedFlightPlanActionHandler.sendFlightPlansSyncRequest();
            loadStrips();
        }
    }

    public FligtPlanStripsPanel mapSubmittedFlightPlanToStrip(SubmittedFlightPlan p) {
        FligtPlanStripsPanel panel = new FligtPlanStripsPanel();
       // panel.setServersID(p.getServersID());
        panel.setVisible(true);
       // panel.getUserNameLabel().setText(p.getFlightPlanOwner().getRegisteredUserName());
        panel.getFlightNumberLabel().setText(p.getFlightNumber());
        panel.getAircraftTypeLabel().setText(p.getAircraftType());
        panel.getAirlineLabel().setText(p.getAirline());
        panel.getCurrentSpeedLabel().setText("-");
        panel.getCurrentAltLabel().setText("-");
        panel.getCurrentHeadingLabel().setText("-");
        panel.getFromIcaoLabel().setText(p.getIcaoFrom());
        panel.getToIcaoLabel().setText(p.getIcaoTo());
        panel.getRemarkLabel().setText(p.getRemark());
        panel.getRouteLabel().setText(p.getRemark());
        
        if (p.isActive()) {
            panel.getStatusLabel().setText("active");
            
        }
        else if (p.isRevoked()) {
            panel.getStatusLabel().setText("revoked");
        }
        else if (p.isAccepted()) {
            panel.getStatusLabel().setText("accepted");
        }
        if (p.getAssingedControllerSessionID() != null) {
            if (p.getAssingedControllerSessionID() != null) {
            
                LocalAtcDataStructure atc = DataStructureSilo.getLocalATCStructures().get(p.getAssingedControllerSessionID());
                if (atc != null) {
                    panel.getAssingedControllerLabel().setText(atc.getServerATCStructure().getUserName());
                }
            }
            
        }
        
        return panel;
    }

    public JPanel getHeadPanel() {
        return headPanel;
    }

    public void setHeadPanel(JPanel headPanel) {
        this.headPanel = headPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public void setRefreshButton(JButton refreshButton) {
        this.refreshButton = refreshButton;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public boolean isLoadControllersStrips() {
        return loadControllersStrips;
    }

    public void setLoadControllersStrips(boolean loadControllersStrips) {
        this.loadControllersStrips = loadControllersStrips;
    }

    
    
}
