/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.server.networking.protocol.controller.SubmittedFlightPlanActionHandler;
import de.xatc.server.networking.protocol.controller.SubmittedFlightPlanHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author Mirko
 */
public class SubmittedFlighPlanActionsConsumer extends MQAbstractConsumer {

    public SubmittedFlighPlanActionsConsumer(String queueName) {
        super(queueName);
    }

    @Override
    public void onObjectMessage(ObjectMessage message) {
        
        try {
            SubmittedFlightPlansActionPacket p = (SubmittedFlightPlansActionPacket) message.getObject();
            if (p.getAction().equals("revoke")) {
                SubmittedFlightPlanActionHandler.revokeSubmittedFlightPlan(p);
            }
        } catch (JMSException ex) {
            ex.printStackTrace(System.err);
        }
        
        
        
    }

    @Override
    public void onTextMessage(TextMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
