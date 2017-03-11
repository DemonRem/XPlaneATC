
package de.xatc.controllerclient.automation.tools;

import de.xatc.commons.networkpackets.atc.datasync.RequestSyncPacket;
import de.xatc.controllerclient.Start;
import de.xatc.controllerclient.config.ConfigBean;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.gui.datasync.ServerSyncFrame;
import de.xatc.controllerclient.log.DebugMessageLevel;
import de.xatc.controllerclient.xdataparser.AptFileIndexer;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko
 */


public class AutomationTools {
    
    public void startApp() {
        
        try {
            Start.main(new String[]{});
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

         try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
   public void login() {
       
       ActionEvent filePress = new ActionEvent(XHSConfig.getMainFrame().getConnectItem(), ActionEvent.ACTION_PERFORMED, "Connect...");
        XHSConfig.getMainFrame().actionPerformed(filePress);

        XHSConfig.getConnectFrame().getNameField().setText("micko");
        XHSConfig.getConnectFrame().getPasswordField().setText("doof");

        ActionEvent connectPress = new ActionEvent(XHSConfig.getConnectFrame().getConnectButton(), ActionEvent.ACTION_PERFORMED, "connect");
        XHSConfig.getConnectFrame().actionPerformed(connectPress);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

   } 
   
   public void configureProperties() {
       
        XHSConfig.setConfigBean(new ConfigBean());
        XHSConfig.getConfigBean().setFolder_xplaneFolder("D:\\games\\X-Plane 10");
        XHSConfig.getConfigBean().setAtcServerIP("192.168.56.1");
        XHSConfig.getConfigBean().setAtcServerPort("9092");
        XHSConfig.getConfigBean().setAtcVoiceIP("192.168.56.1");
        XHSConfig.getConfigBean().setAtcVoicePort("9091");
        XHSConfig.savePropsFile();
       
       
       
   }
    
   
   public void setupWithExistingProperties() {
       
       System.out.println("Deleting old data");
        Session s = DBSessionManager.getSession();

        Transaction tx = s.beginTransaction();
        Query q = s.createQuery("delete from PlainAirport");
        q.executeUpdate();
        tx.commit();

        Transaction tx1 = s.beginTransaction();
        Query q1 = s.createQuery("delete from PlainAirport");
        q1.executeUpdate();
        tx1.commit();

        Transaction tx2 = s.beginTransaction();
        Query q2 = s.createQuery("delete from Fir");
        q2.executeUpdate();
        tx2.commit();

        DBSessionManager.closeSession(s);
        XHSConfig.setServerSyncFrame(new ServerSyncFrame());
        XHSConfig.getServerSyncFrame().setVisible(true);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        
        RequestSyncPacket sync = new RequestSyncPacket();
        sync.setDataSetToSync("airport");
        XHSConfig.getDataClient().writeMessage(sync);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

        RequestSyncPacket sync1 = new RequestSyncPacket();
        sync1.setDataSetToSync("country");
        XHSConfig.getDataClient().writeMessage(sync1);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

        RequestSyncPacket sync2 = new RequestSyncPacket();
        sync2.setDataSetToSync("fir");
        XHSConfig.getDataClient().writeMessage(sync2);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

        
        XHSConfig.getServerSyncFrame().dispose();
        XHSConfig.setServerSyncFrame(null);
        
        ActionEvent indexPress = new ActionEvent(XHSConfig.getMainFrame().getxPlaneFileIndexerItem(), ActionEvent.ACTION_PERFORMED, "XPlane File Indexer");
        XHSConfig.getMainFrame().actionPerformed(indexPress);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        AptFileIndexer indexer = new AptFileIndexer();
        XHSConfig.debugMessage("STARTING INDEX", DebugMessageLevel.DEBUG);
        indexer.start();
       
       
       
   }
   
   
}
