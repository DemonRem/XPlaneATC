/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.networkpackets.atc.stripsmgt.ATCRequestStripsPacket;
import de.xatc.commons.networkpackets.client.SubmittedFlightPlan;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author C047
 */
public class SubmittedFlightPlansHandler {
    
    public static void saveFlightSubmittedFlightPlans(List<SubmittedFlightPlan> list) {
        
        Session s = DBSessionManager.getSession();
        for (SubmittedFlightPlan p : list) {
            System.out.println("Saving flightPlan: " + p.getFlightPlanOwner().getRegisteredUserName());
            p.setServersID(p.getId());
            p.setId(0);
            s.saveOrUpdate(p);
            
            if (XHSConfig.getSubmittedFlightPlansPoolFrame() != null) {
                System.out.println("Adding new FlightPlan to Panel");
                XHSConfig.getSubmittedFlightPlansPoolFrame().getCenterPanel().add(XHSConfig.getSubmittedFlightPlansPoolFrame().mapSubmittedFlightPlanToStrip(p));
                XHSConfig.getSubmittedFlightPlansPoolFrame().revalidate();
                XHSConfig.getSubmittedFlightPlansPoolFrame().repaint();
//TODO                
//ok, hier muss noch etwas getan werden, dass zwischen unassigned and my strips unterschieden wird.
            }
            
            
        }
        DBSessionManager.closeSession(s);
        
        
    }
    public static void deleteLocalFlightPlans() {
        
        
        Session s = DBSessionManager.getSession();

        Transaction tx = s.beginTransaction();
        Query q = s.createQuery("delete from SubmittedFlightPlan");
        q.executeUpdate();
        tx.commit();

        DBSessionManager.closeSession(s);
        
        
        
        
    }
    public static void sendFlightPlansSyncRequest() {
        
        if (XHSConfig.getDataClient() == null) {
            System.out.println("could not send sync Request for SubmittedFlightPlans. Not Connected!");
            return;
        }
        ATCRequestStripsPacket p = new ATCRequestStripsPacket();
        XHSConfig.getDataClient().writeMessage(p);
        
    }
    
    
    
    
}
