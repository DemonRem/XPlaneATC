/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.sessionmanagment.NetworkBroadcaster;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class LoginConsumer extends MQAbstractConsumer {

    public LoginConsumer(String queueName) {
        super(queueName);
    }

    @Override
    public void onObjectMessage(ObjectMessage message) {
        System.out.println("LoginComsumer received Message");
        try {
            if (message.getObject() == null) {
                System.out.println("Object is null");
                return;
            }
            
            if (message.getObject() instanceof RegisteredUser == false) {
                System.out.println("Object is not registeredUser");
                return;
            }         
            RegisteredUser u = (RegisteredUser) message.getObject();
            
            Session s = DBSessionManager.getSession();
            s.saveOrUpdate(u);
            DBSessionManager.closeSession(s);
            
            NetworkBroadcaster.broadcastATC(u);
            
            
            
        } catch (JMSException ex) {
            ex.printStackTrace(System.err);
        }
        
        
    }

    @Override
    public void onTextMessage(TextMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
