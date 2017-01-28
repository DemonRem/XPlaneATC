/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.mytools.tools.dateandtime.SQLDateTimeTools;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.client.TextMessagePacket;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.TextMessageEntity;
import de.xatc.server.sessionmanagment.SessionManagement;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.hibernate.Session;

/**
 *
 * @author c047
 */
public class TextMessageBroadCastConsumer extends MQAbstractConsumer {

    public TextMessageBroadCastConsumer(String queueName) {
        super(queueName);
    }

    
    
    
    
    @Override
    public void onObjectMessage(ObjectMessage message) {
       
        System.out.println("Message Consumer receiving ObjectMessage.");
        try {
            TextMessagePacket p = (TextMessagePacket) message.getObject();
            
            if (SessionManagement.getAtcChannelGroup().size() > 0) {
                System.out.println("Writing to ATCGroup!");
                SessionManagement.getAtcChannelGroup().writeAndFlush(p);
            }
            if (SessionManagement.getDataChannelGroup().size() > 0) {
                System.out.println("Writing to UserGroup");
                SessionManagement.getDataChannelGroup().writeAndFlush(p);
            }
            
            Session session = DBSessionManager.getSession();
            TextMessageEntity dbMsg = new TextMessageEntity();
            
            RegisteredUser u = SessionManagement.findRegisteredUserByUserName(p.getFromUserName(), SessionManagement.getUserSessionList());
            if (u != null) {
                dbMsg.setFromUser(u);
            }
            dbMsg.setTextMessage(p.getMessage());
            dbMsg.setSentTime(SQLDateTimeTools.getTimeStampOfNow());
            session.save(dbMsg);
            DBSessionManager.closeSession(session);
            
            
            
            
        } catch (JMSException ex) {
            ex.printStackTrace(System.err);
        }
        
        
    }

    @Override
    public void onTextMessage(TextMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
