/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.db.sharedentities.user.XATCUserSession;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.sessionmanagment.NetworkBroadcaster;
import de.xatc.server.sessionmanagment.SessionManagement;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class PlanePositionConsumer extends MQAbstractConsumer {

    private static final Logger LOG = Logger.getLogger(PlanePositionConsumer.class.getName());
    
    public PlanePositionConsumer(String queueName) {
        super(queueName);
    }

   @Override
    public void onObjectMessage(ObjectMessage message) {
        
        LOG.debug("Message Consumer PlanePosistion ObjectMessage");
        
        PlanePosition p = null;
        try {
            p = (PlanePosition) message.getObject();
        } catch (JMSException ex) {
            LOG.error(ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
            return;
        }
        LOG.debug(p.getSessionID());
        
        PilotStructure pilotStructure = SessionManagement.getPilotDataStructures().get(p.getSessionID());
        
        
        XATCUserSession userSession = SessionManagement.findUserSessionBySessionID(p.getSessionID(), SessionManagement.getPilotDataStructures());
        if (userSession == null) {
            return;
        }
        p.setUserName(userSession.getRegisteredUser().getRegisteredUserName());
        
        LOG.trace("PlanePositionComsumer.... recived ObejctMessage");
        if (SessionManagement.getAtcDataStructures().size() > 0) {
            NetworkBroadcaster.broadcastATC(p);
            
        }
        Session s = DBSessionManager.getSession();
        s.saveOrUpdate(p);
        DBSessionManager.closeSession(s);
        pilotStructure.getPlanePositionList().add(p);
        pilotStructure.setLastKnownPlanePosition(p);
        
    }

    @Override
    public void onTextMessage(TextMessage message) {
        LOG.warn("PlanePositionConsumer.... recived TextMessage");
    }
    
}
