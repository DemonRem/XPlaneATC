/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.gui.FlightPlanStrips;

import de.xatc.commons.networkpackets.client.SubmittedFlightPlan;
import de.xatc.controllerclient.db.DBSessionManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.hibernate.Session;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author C047
 */
public class SubmittedFlightPlansFrame extends JFrame {
    
    private JPanel headPanel;
    private JPanel centerPanel;
    private JButton refreshButton;
    private boolean loadControllersStrips;

    public SubmittedFlightPlansFrame(boolean myStrips) {
        super();
        this.loadControllersStrips = myStrips;
        initComponents();
    }
    
    private void initComponents() {
        
        this.setSize(new Dimension(400,400));
        this.setLocation(40,40);
        this.setLayout(new BorderLayout());
        headPanel = new JPanel();
        refreshButton = new JButton("refresh");
        headPanel.add(refreshButton);
        this.add(headPanel,BorderLayout.NORTH);
        
        
        
        centerPanel = new JPanel();
        centerPanel.setLayout(new VerticalLayout());
        
        this.add(centerPanel,BorderLayout.CENTER);
      
        this.loadStrips();
        this.pack();
        this.setVisible(true);
        
    }

    
     private void loadStrips() {
        
        
        System.out.println("READING Flightstrips");
        
        this.centerPanel.removeAll();
        
      
        this.revalidate();
        this.repaint();
        
       
        Session session = DBSessionManager.getSession();
        List<SubmittedFlightPlan> list = session.createCriteria(SubmittedFlightPlan.class).list();
        
        System.out.println("FOUND STRIPS: " + list.size());
        
        for (SubmittedFlightPlan p : list) {
            
            hier fehlen noch felder und es sieht noch scheisse aus.
            FligtPlanStripsPanel panel = new FligtPlanStripsPanel();
            panel.setVisible(true);
            panel.getUserNameLabel().setText(p.getFlightPlanOwner().getRegisteredUserName());
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
            centerPanel.add(panel);
            
        }
        this.revalidate();
        this.repaint();
        DBSessionManager.closeSession(session);
        
        
        
        
        
    }


}
