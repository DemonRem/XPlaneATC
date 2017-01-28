/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.db.startup;

import de.mytools.tools.dateandtime.SQLDateTimeTools;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.LastRun;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataBaseStartUp {
    
    public void prepareForStartup() {
        
        System.out.println("Running prepare Startup Database");
        
        System.out.println("opening session");
        Session session = DBSessionManager.getSession();

        System.out.println("new LastRun");
        LastRun lastRun = new LastRun();
        lastRun.setStartDate(SQLDateTimeTools.getTimeStampOfNow());
        
        
        
        System.out.println("saving last run");
        session.save(lastRun);
    
        
        System.out.println("New Transaction to delte usersessions");
        
        Transaction t = session.beginTransaction();
        Query deleteAirportsQ = session.createQuery("delete from XATCUserSession");
        System.out.println("execute Delete Query");
        deleteAirportsQ.executeUpdate();
        System.out.println("commit delete user session");
        t.commit();
       

        System.out.println("close DB Session");
        DBSessionManager.closeSession(session);
        
        
        
        
    }
    
    
}
