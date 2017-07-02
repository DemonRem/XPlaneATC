/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.server.networking.protocol.pilot.SubmittedFlightPlanActionHandlerPilot;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author C047
 */
public class SubmittedFlighPlanActionsConsumerPilot extends MQAbstractConsumer {

    public SubmittedFlighPlanActionsConsumerPilot(String queueName) {
        super(queueName);
    }

    @Override
    public void onObjectMessage(ObjectMessage message) {
        try {
            SubmittedFlightPlansActionPacket f = (SubmittedFlightPlansActionPacket) message.getObject();
            if (f.getAction().equals("new")) {
                SubmittedFlightPlanActionHandlerPilot.handleNewIncomingSubmittedFlightPlan(f);
                return;
            }
            if (f.getAction().equals("revoke")) {
                
                SubmittedFlightPlanActionHandlerPilot.revokeSubmittedFlightPlan(f);
                
            }
            
            
        } catch (JMSException ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void onTextMessage(TextMessage message) {
       //nothing here to do yet
    }
    
}
