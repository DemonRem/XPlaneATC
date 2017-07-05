/**
 * This file is part of the XPlane Home Server License.
 * You may edit and use this file as you like. But there is no warranty at all and no license condition.
 * XPlane Home Server tries to build up a simple network for flying in small local networks or via internet.
 * Have fun!
 *
 * @Author Mirko Bubel (mirko_bubel@hotmail.com)
 * @Created 31.05.2016
 */
package de.xatc.controllerclient;

import de.mytools.tools.swing.SwingTools;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.gui.main.MainFrame;
import java.awt.Toolkit;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class Start extends JFrame  {

    
    private static final Logger LOG = Logger.getLogger(Start.class.getName());
    /**
     * the main method
     *
     * @param arg
     * @throws IOException
     */
    public static void main(String[] arg) throws IOException {

        
         
        System.out.println("Starting UP");
        System.out.println(XHSConfig.getAPPNAME());
        System.out.println(XHSConfig.getVERSION());
        System.out.println(XHSConfig.getLastUpdated());

        LOG.info("Setting LogLevel....");

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.WARNING);
        java.util.logging.Logger.getLogger("net.sf.ehcache").setLevel(java.util.logging.Level.WARNING);

        String myProcessID = ManagementFactory.getRuntimeMXBean().getName();

        
        
        
        if (XHSConfig.getLockFile().exists()) {
            JFrame existsFrame = new JFrame();

            existsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            existsFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 300);
            existsFrame.setSize(300, 300);

            String s = "<HTML>Lockfile exists! There is propably another instance of FollowMeCar Util running. Could not start!<p>";
            s += "This could cause damage to indexed airports inside the Follow Me Car database.<p><br>";
            s += "Press OK to proceed and start Follow Me Car Util.<p>Otherwise press NO and terminate the other instance manually.";

            s += "</HTML>";

            int returnValue = SwingTools.showYesNoDialogBox("LockFile Exists", s, existsFrame);
            LOG.trace(returnValue);
            if (returnValue == 0) {
                XHSConfig.getLockFile().delete();

            } else {
                System.exit(0);
            }

        }

        XHSConfig.getLockFile().createNewFile();
        FileUtils.writeStringToFile(XHSConfig.getLockFile(), myProcessID);
        XHSConfig.getLockFile().deleteOnExit();
        
        Session s = DBSessionManager.getSession();
        DBSessionManager.closeSession(s);
        
        attachShutDownHook();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                MainFrame mainFrame = new MainFrame();

            }
        });

    }

    /**
     * attach a shutdown hook to the runtime in order to delete the lock file
     * from filesystem
     */
    public static void attachShutDownHook() {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (XHSConfig.getLockFile().exists()) {
                    XHSConfig.getLockFile().delete();
                }
            }
        });
        LOG.info("Shut Down Hook Attached.");
        LOG.info("Connecting to database");
    }

}
