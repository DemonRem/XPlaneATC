/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server;

import de.xatc.server.config.ServerConfig;
import de.xatc.server.mq.MQBrokerManager;
import de.xatc.server.mq.consumers.FMSPlanConsumer;
import de.xatc.server.mq.consumers.LoginConsumer;
import de.xatc.server.mq.consumers.PlanePositionConsumer;
import de.xatc.server.mq.consumers.SubmittedFlighPlanActionsConsumerATC;
import de.xatc.server.mq.consumers.SubmittedFlighPlanActionsConsumerPilot;
import de.xatc.server.mq.consumers.TextMessageBroadCastConsumer;
import de.xatc.server.mq.producers.MQMessageSender;
import de.xatc.server.nettybootstrap.pilot.DataServerBootstrap;
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko
 */
public class StartupHook {

    private static final Logger LOG = Logger.getLogger(StartupHook.class.getName());
    public static void startupHook() {

        DataServerBootstrap d = new DataServerBootstrap();
        ServerConfig.setDataServerBootStrap(d);
        LOG.info("Client Connections started");

        MQBrokerManager manager = new MQBrokerManager();
        try {
            manager.startBroker();
        } catch (Exception ex) {
            System.exit(-1);
        }

        ServerConfig.setMqBrokerManager(manager);
        LOG.info("MQ Broker started");

        /////////////////////MQ SENDERS
        MQMessageSender writeToAll = new MQMessageSender("broadcastTextMessages");
        writeToAll.startProducer();
        ServerConfig.getMessageSenders().put("broadcastTextMessages", writeToAll);
        
        MQMessageSender planePositionSender = new MQMessageSender("planePosition");
        planePositionSender.startProducer();
        ServerConfig.getMessageSenders().put("planePosition", planePositionSender);
        
        MQMessageSender fmsPlanSender = new MQMessageSender("fmsPlan");
        fmsPlanSender.startProducer();
        ServerConfig.getMessageSenders().put("fmsPlan", fmsPlanSender);
        
        MQMessageSender loginSender = new MQMessageSender("login");
        loginSender.startProducer();
        ServerConfig.getMessageSenders().put("login", loginSender);
        
 
        MQMessageSender submittedFlightPlanActionsSenderATC = new MQMessageSender("submittedFlightPlanActionsATC");
        submittedFlightPlanActionsSenderATC.startProducer();
        ServerConfig.getMessageSenders().put("submittedFlightPlanActionsATC", submittedFlightPlanActionsSenderATC);
        
        MQMessageSender submittedFlightPlanActionsSenderPilot = new MQMessageSender("submittedFlightPlanActionsPilot");
        submittedFlightPlanActionsSenderPilot.startProducer();
        ServerConfig.getMessageSenders().put("submittedFlightPlanActionsPilot", submittedFlightPlanActionsSenderPilot);
        
        
        //MQRECEIVERS

        LOG.info("Messaging producers started");

        TextMessageBroadCastConsumer b = new TextMessageBroadCastConsumer("broadcastTextMessages");
        ServerConfig.getMessageReceivers().put("broadcastTextMessages", b);
        
        PlanePositionConsumer ppc = new PlanePositionConsumer("planePosition");
        ServerConfig.getMessageReceivers().put("planePosition", ppc);

        FMSPlanConsumer fmsp = new FMSPlanConsumer("fmsPlan");
        ServerConfig.getMessageReceivers().put("fmsPlan", fmsp);
        
        LoginConsumer loginConsumer = new LoginConsumer("login");
        ServerConfig.getMessageReceivers().put("login", loginConsumer);
        
       
        
        SubmittedFlighPlanActionsConsumerATC submittedFlightPlansActionsConsumerATC = new SubmittedFlighPlanActionsConsumerATC("submittedFlightPlanActionsATC");
        ServerConfig.getMessageReceivers().put("submittedFlightPlanActionsATC", submittedFlightPlansActionsConsumerATC);
        
        SubmittedFlighPlanActionsConsumerPilot submittedFlightPlansActionsConsumerPilot = new SubmittedFlighPlanActionsConsumerPilot("submittedFlightPlanActionsPilot");
        ServerConfig.getMessageReceivers().put("submittedFlightPlanActionsPilot", submittedFlightPlansActionsConsumerPilot);
        
        
        LOG.info("Messaging Consumers started");

    }

}
