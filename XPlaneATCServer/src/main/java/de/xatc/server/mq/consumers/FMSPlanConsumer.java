/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko
 */
public class FMSPlanConsumer extends MQAbstractConsumer {

    //TODO - what is this?
    private static final Logger LOG = Logger.getLogger(FMSPlanConsumer.class.getName());
    public FMSPlanConsumer(String queueName) {
        super(queueName);
    }

    @Override
    public void onObjectMessage(ObjectMessage message) {
        LOG.info("FMSPlanConsumer.... recived ObejctMessage");
    }

    @Override
    public void onTextMessage(TextMessage message) {
        LOG.info("FMSPlanConsumer.... recived TextMessage");
    }
    
}
