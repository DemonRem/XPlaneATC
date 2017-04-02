/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.networkpackets.client.SubmittedFlightPlan;
import de.xatc.controllerclient.db.DBSessionManager;
import java.util.List;
import org.hibernate.Session;

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
            
            
        }
        DBSessionManager.closeSession(s);
        
        
    }
    
    
    
    
}
