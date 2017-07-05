/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: May/2015
 *Have fun!
 */
package de.xatc.controllerclient.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 * This Method handles all embedded database stuff
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DBSessionManager {

    private static final Logger LOG = Logger.getLogger(DBSessionManager.class.getName());
  
    /**
     * session factory
     */
    private static SessionFactory fct = getSessionFactory();



    /**
     * return an opened database session object
     *
     * @return
     */
    public static Session getSession() {

        

        return  fct.openSession();

    }

    /**
     * close a session and flush before
     * @param session
     */
    public static void closeSession(Session session) {

        session.flush();
        session.close();
     

    }

    /**
     * shut down database, usally called by shutdown hook. The database shutdown
     * always throws an exception with code 50000 Dont panic, seems to be normal
     * that the derby database communicates its status by exceptions
     */
    public static void shutdownDB() {

        try {
           
            fct.close();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException ex) {

            LOG.info("Database Exit Status " + ex.getErrorCode());
        }

    }

    /**
     * create a session factory
     *
     * @return
     */
    private static synchronized SessionFactory getSessionFactory() {

        if (fct == null) {

            try {
                return new AnnotationConfiguration()
                        .configure()
                        .buildSessionFactory();
            } catch (Exception ex) {
                LOG.error("DATABASE ERROR. Could not open Database. " + ex.getLocalizedMessage());

            }
        } else {
            return fct;
        }
        return null;
    }

}
