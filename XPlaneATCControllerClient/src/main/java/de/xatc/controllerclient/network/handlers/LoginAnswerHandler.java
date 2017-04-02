/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.network.handlers;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.atc.stripsmgt.ATCRequestStripsPacket;
import de.xatc.commons.networkpackets.client.LoginPacket;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;
import java.awt.Color;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class LoginAnswerHandler {
    
    public static void handleLoginAnswer(Object packet) {
        
        LoginPacket l = (LoginPacket) packet;
        if (!l.isSuccessful()) {
            
            SwingTools.alertWindow("Login in was not successful. Server said: " + l.getServerMessage(), XHSConfig.getMainFrame());
            XHSConfig.getDataClientBootstrap().shutdownClient();
            return;
            
        }
        
        XHSConfig.getMainFrame().getMainPanel().getStatusPanel().getConnectedToATCDataServer().setColor(Color.GREEN);
        XHSConfig.getMainFrame().getMainPanel().getStatusPanel().revalidate();
        XHSConfig.getMainFrame().getMainPanel().getStatusPanel().repaint();
        XHSConfig.getMainFrame().getConnectItem().setEnabled(false);
        XHSConfig.getMainFrame().getDisconnectItem().setEnabled(true);
        
        XHSConfig.setCurrentSessionID(l.getSessionID());
        XHSConfig.setCurrentChannelID(l.getChannelID());
        System.out.println("Login successful");
        
        Session s = DBSessionManager.getSession();

        Transaction tx = s.beginTransaction();
        Query q = s.createQuery("delete from SubmittedFlightPlan");
        q.executeUpdate();
        tx.commit();

        DBSessionManager.closeSession(s);
        ATCRequestStripsPacket p = new ATCRequestStripsPacket();
        XHSConfig.getDataClient().writeMessage(p);
        
    }
    
}
