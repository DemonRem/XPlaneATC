/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.commons.networkpackets.client.PlanePosition;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.XATCUserSession;
import de.xatc.server.sessionmanagment.SessionManagement;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class PlanePositionConsumer extends MQAbstractConsumer {

    public PlanePositionConsumer(String queueName) {
        super(queueName);
    }

   @Override
    public void onObjectMessage(ObjectMessage message) {
        
        System.out.println("Message Consumer PlanePosistion ObjectMessage");
        
        PlanePosition p = null;
        try {
            p = (PlanePosition) message.getObject();
        } catch (JMSException ex) {
            
            ex.printStackTrace(System.err);
            return;
        }
        System.out.println(p.getSessionID());
        
        XATCUserSession userSession = SessionManagement.findUserSessionBySessionID(p.getSessionID(), SessionManagement.getUserSessionList());
        if (userSession == null) {
            return;
        }
        p.setUserName(userSession.getRegisteredUser().getRegisteredUserName());
        
        System.out.println("PlanePositionComsumer.... recived ObejctMessage");
        if (SessionManagement.getAtcChannelGroup().size() > 0) {
            SessionManagement.getAtcChannelGroup().writeAndFlush(p);
        }
        Session s = DBSessionManager.getSession();
        s.saveOrUpdate(p);
        DBSessionManager.closeSession(s);
        
        
    }

    @Override
    public void onTextMessage(TextMessage message) {
        System.out.println("PlanePositionConsumer.... recived TextMessage");
    }
    
}
