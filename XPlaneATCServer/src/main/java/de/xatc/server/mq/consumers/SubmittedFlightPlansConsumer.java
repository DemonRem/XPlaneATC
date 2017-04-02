/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.commons.networkpackets.client.SubmittedFlightPlan;
import de.xatc.server.networking.protocol.controller.FligtPlanManagementHandler;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author C047
 */
public class SubmittedFlightPlansConsumer extends MQAbstractConsumer {

    public SubmittedFlightPlansConsumer(String queueName) {
        super(queueName);
    }

    @Override
    public void onObjectMessage(ObjectMessage message) {
        try {
            SubmittedFlightPlan f = (SubmittedFlightPlan) message.getObject();
            FligtPlanManagementHandler.handleNewIncomingSubmittedFlightPlan(f);
        } catch (JMSException ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void onTextMessage(TextMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
