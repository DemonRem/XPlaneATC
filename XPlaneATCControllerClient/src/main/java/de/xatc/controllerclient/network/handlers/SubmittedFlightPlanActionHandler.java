package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.networkpackets.client.SubmittedFlightPlansActionPacket;
import de.xatc.controllerclient.config.XHSConfig;
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
