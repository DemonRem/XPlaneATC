/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.server.networking.protocol.controller.SubmittedFlightPlanActionHandlerATC;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko
 */
public class SubmittedFlighPlanActionsConsumerATC extends MQAbstractConsumer {

    
    private static final Logger LOG = Logger.getLogger(SubmittedFlighPlanActionsConsumerATC.class.getName());
    public SubmittedFlighPlanActionsConsumerATC(String queueName) {
        super(queueName);
    }

    @Override
    public void onObjectMessage(ObjectMessage message) {
        
        try {
            SubmittedFlightPlansActionPacket p = (SubmittedFlightPlansActionPacket) message.getObject();
            if (p.getAction().equals("revoke")) {
                SubmittedFlightPlanActionHandlerATC.revokeFlightPlan(p);
            }
            else if (p.getAction().equals("accept")) {
                
                
                
            }
            else if (p.getAction().equals("syncAll")) {
                SubmittedFlightPlanActionHandlerATC.sendSubmittedFlightPlansToATC(p);
            }
            
            
        } catch (JMSException ex) {
            LOG.error(ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
        }
        
        
        
    }

    @Override
    public void onTextMessage(TextMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
