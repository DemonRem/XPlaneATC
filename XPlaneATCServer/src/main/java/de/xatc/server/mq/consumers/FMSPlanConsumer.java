/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author Mirko
 */
public class FMSPlanConsumer extends MQAbstractConsumer {

    public FMSPlanConsumer(String queueName) {
        super(queueName);
    }

    @Override
    public void onObjectMessage(ObjectMessage message) {
        System.out.println("FMSPlanConsumer.... recived ObejctMessage");
    }

    @Override
    public void onTextMessage(TextMessage message) {
        System.out.println("FMSPlanConsumer.... recived TextMessage");
    }
    
}
