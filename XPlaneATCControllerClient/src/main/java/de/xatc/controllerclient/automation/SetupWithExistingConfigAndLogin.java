package de.xatc.controllerclient.automation;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.atc.datasync.RequestSyncPacket;
import de.xatc.controllerclient.Start;
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
public class SetupWithExistingConfigAndLogin {

    public static void main(String[] arg) throws IOException {

        Start.main(new String[]{});

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        ActionEvent filePress = new ActionEvent(XHSConfig.getMainFrame().getConnectItem(), ActionEvent.ACTION_PERFORMED, "Connect...");
        XHSConfig.getMainFrame().actionPerformed(filePress);

        XHSConfig.getConnectFrame().getNameField().setText("micko");
        XHSConfig.getConnectFrame().getPasswordField().setText("doof");

        ActionEvent connectPress = new ActionEvent(XHSConfig.getConnectFrame().getConnectButton(), ActionEvent.ACTION_PERFORMED, "connect");
        XHSConfig.getConnectFrame().actionPerformed(connectPress);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

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
