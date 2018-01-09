/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server;

import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.LastRun;
import de.xatc.server.db.startup.DataBaseStartUp;
import de.xatc.server.db.startup.DataInitializer;
import de.xatc.server.nettybootstrap.atc.ATCServerBootstrap;
import java.net.BindException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class Start {

    public static void main(String[] arg) {

        
       
        
        //open a session once to generate database
        Session session = DBSessionManager.getSession();
        DBSessionManager.closeSession(session);

        session = DBSessionManager.getSession();
        List<LastRun> lastRunList = session.createCriteria(LastRun.class).list();
        DBSessionManager.closeSession(session);
        if (lastRunList.size() <= 0) {

            DataInitializer initializer = new DataInitializer();
            initializer.initDataInDataBase();

        }

        DataBaseStartUp startup = new DataBaseStartUp();
        startup.prepareForStartup();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //go around ServerControl
        StartupHook.startupHook();
        
        
        
        
        
        
        attachShutDownHook();
        
        
        try {
        new ATCServerBootstrap();
        }
        catch (BindException ex) {
            
            System.err.println("Bind exception detected: " + ex.getLocalizedMessage());
            System.exit(-1);
            
        }

    }

    /**
     * attach a shutdown hook to the runtime in order to delete the lock file
     * from filesystem
     */
    public static void attachShutDownHook() {

        ShutdownHook.attachShutDownHook();
    }

}
