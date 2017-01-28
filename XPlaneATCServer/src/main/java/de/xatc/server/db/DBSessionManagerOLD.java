/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: May/2015
 *Have fun!
 */
package de.xatc.server.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;



/**
 * This Method handles all embedded database stuff
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DBSessionManagerOLD {

    /**
     * session singleton
     */
    private static Session session = null;
    /**
     * session factory
     */
    private static SessionFactory fct = getSessionFactory();

    /**
     * open a session if not null
     */
    private static void openSession() {

        if (session == null) {

            session = fct.openSession();

        }

    }

    /**
     * return an opened database session object
     *
     * @return
     */
    public static Session getSession() {

        if (session == null) {
            openSession();
        }

        return session;

    }

    /**
     * close a session and flush before
     */
    public static void closeSession() {

        session.flush();
        session.close();
        session = null;

    }

    /**
     * shut down database, usally called by shutdown hook. The database shutdown
     * always throws an exception with code 50000 Dont panic, seems to be normal
     * that the derby database communicates its status by exceptions
     */
    public static void shutdownDB() {

        try {
            if (session != null) {
                session.flush();
                session.clear();
                session.disconnect();
            }
            fct.close();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException ex) {

            System.out.println("Database Exit Status " + ex.getErrorCode());
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
                System.out.println("DATABASE ERROR. Could not open Database. " + ex.getLocalizedMessage());

            }
        } else {
            return fct;
        }
        return null;
    }

    public static List<String> getAllTables() {
        List<String> tableNames = new ArrayList<>();
        Session localsession = getSession();
        SessionFactory sessionFactory = localsession.getSessionFactory();
        Map<String, ClassMetadata> map = (Map<String, ClassMetadata>) sessionFactory.getAllClassMetadata();
        for (String entityName : map.keySet()) {
            SessionFactoryImpl sfImpl = (SessionFactoryImpl) sessionFactory;
            String tableName = ((AbstractEntityPersister) sfImpl.getEntityPersister(entityName)).getTableName();
            tableNames.add(tableName);
        }
        closeSession();
        return tableNames;
    }
}
