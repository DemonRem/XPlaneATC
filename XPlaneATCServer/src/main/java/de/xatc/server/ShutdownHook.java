/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server;

import de.mytools.tools.dateandtime.SQLDateTimeTools;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.LastRun;
import de.xatc.server.mq.consumers.MQAbstractConsumer;
import de.xatc.server.mq.producers.MQMessageSender;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ShutdownHook {

    public static void attachShutDownHook() {

        System.out.println("Attaching ShutDownHook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("ShutDownHook Running");
                Session session = DBSessionManager.getSession();

                DetachedCriteria maxId = DetachedCriteria.forClass(LastRun.class)
                        .setProjection(Projections.max("id"));
                List<LastRun> lastRunList = session.createCriteria(LastRun.class)
                        .add(Property.forName("id").eq(maxId))
                        .list();

                LastRun lr = lastRunList.get(0);
                System.out.println("SHUTDOWN HOOK: LastRun StartDate: " + lr.getStartDate());
                lr.setEndDate(SQLDateTimeTools.getTimeStampOfNow());
                session.saveOrUpdate(lr);
                DBSessionManager.closeSession(session);

                //shutdown all mq producers
                System.out.println("Shutting Down all Message Producers for MQ");
                for (Map.Entry<String, MQMessageSender> entry : ServerConfig.getMessageSenders().entrySet()) {

                    System.out.println("Shutting down messageSende: " + entry.getValue().getQueueName());
                    entry.getValue().shutdownProducer();

                }

                //shutdown all Consumers
                for (Map.Entry<String, MQAbstractConsumer> entry : ServerConfig.getMessageReceivers().entrySet()) {

                    System.out.println("Shutting down messageReceiver: " + entry.getValue().getQueueName());
                    entry.getValue().shutdownConsumer();

                }
                
                
                
                //shutdown MQBroker
                if (ServerConfig.getMqBrokerManager() != null) {
                    try {
                        ServerConfig.getMqBrokerManager().shutdownBroker();
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                    }
                }
                
                DBSessionManager.shutdownDB();

            }
        });
        System.out.println("Shut Down Hook Attached.");

    }

}
