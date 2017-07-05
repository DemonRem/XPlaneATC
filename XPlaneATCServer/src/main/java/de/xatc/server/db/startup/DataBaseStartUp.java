/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.db.startup;

import de.mytools.tools.dateandtime.SQLDateTimeTools;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.LastRun;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataBaseStartUp {

    private static final Logger LOG = Logger.getLogger(DataBaseStartUp.class.getName());
    public void prepareForStartup() {

        LOG.info("Running prepare Startup Database");

        LOG.info("opening session");
        Session session = DBSessionManager.getSession();

        LOG.debug("new LastRun");
        LastRun lastRun = new LastRun();
        lastRun.setStartDate(SQLDateTimeTools.getTimeStampOfNow());

        LOG.debug("saving last run");
        session.save(lastRun);

        LOG.debug("New Transaction to delte usersessions");

        Transaction t = session.beginTransaction();
        Query deleteAirportsQ = session.createQuery("delete from XATCUserSession");
       LOG.debug("execute Delete Query");
        deleteAirportsQ.executeUpdate();
        LOG.debug("commit delete user session");
        t.commit();

        List<String> deleteList = new ArrayList<>();
        deleteList.add("SupportedFirStation");
        deleteList.add("SupportedATISStation");
        deleteList.add("SupportedAirportStation");
        deleteList.add("SupportedStationStatistics");

        deleteList.forEach((table) -> {
            Transaction t1 = session.beginTransaction();
            Query deleteStatement = session.createQuery("delete from " + table);
            LOG.debug("execute Delete Query " + table);
            deleteStatement.executeUpdate();
            LOG.debug("commit delete " + table);
            t1.commit();
        });

        LOG.debug("close DB Session");
        DBSessionManager.closeSession(session);

    }

}
