package de.xatc.controllerclient.network.handlers;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlan;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.datastructures.DataStructureSilo;
import de.xatc.controllerclient.datastructures.LocalPilotDataStructure;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.gui.FlightPlanStrips.FligtPlanStripsPanel;
import java.awt.Component;
import javax.swing.JPanel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko
 */
public class SubmittedFlightPlanActionHandler {
    
    
    
    public static void handleNewSubmittedFlightPlan(SubmittedFlightPlan p) {
        
        LocalPilotDataStructure s = DataStructureSilo.getLocalPilotStructure().get(p.getSessionID());
        if (s == null) {
            System.out.println("No session found for submitted flight plan. returning");
            SwingTools.alertWindow("A Flightplan was Subbmitted of a user which could not be found in SessionManagement!", XHSConfig.getMainFrame());
            return;
        }
        Session session = DBSessionManager.getSession();
        session.saveOrUpdate(s);
        DBSessionManager.closeSession(session);
        
        Also das mit den Subbmitted Flight Plans muss nochmal komplett überdacht werden, vor allem die Actions mit der ServierID::::
        Whatever this is.
                
        
        if (XHSConfig.getSubmittedFlightPlansPoolFrame().)
    }
    
    
    public static void handleActionPacket(SubmittedFlightPlansActionPacket p) {
        
        if (p.getAction().equals("revoke")) {
            
            revokeSubmittedFlightPlan(p);
            
        }
        
    }
    
    public static void revokeSubmittedFlightPlan(SubmittedFlightPlansActionPacket p) {
        
        
        Session session = DBSessionManager.getSession();
        Transaction a = session.beginTransaction();
        Query q = session.createQuery("delete from SubmittedFlightPlan where serversID = " + p.getServersID());
        q.executeUpdate();
        a.commit();
        DBSessionManager.closeSession(session);
        if (XHSConfig.getSubmittedFlightPlansPoolFrame() != null) {
            
            JPanel centerPanel = XHSConfig.getSubmittedFlightPlansPoolFrame().getCenterPanel();
            for (Component c : centerPanel.getComponents()) {
                
                if (c instanceof FligtPlanStripsPanel) {
                    FligtPlanStripsPanel panel = (FligtPlanStripsPanel) c;
                    if (panel.getServersID() == p.getServersID()) {
                        centerPanel.remove(c);
                    }
                }
                
            }
            
        }
        XHSConfig.getSubmittedFlightPlansPoolFrame().revalidate();
        XHSConfig.getSubmittedFlightPlansPoolFrame().repaint();
        
    }
    
}
