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
import de.xatc.server.mq.consumers.TextMessageBroadCastConsumer;
import de.xatc.server.mq.producers.MQMessageSender;
import de.xatc.server.networking.clients.DataServerBootstrap;

/**
 *
 * @author Mirko
 */
public class StartupHook {

    public static void startupHook() {

        DataServerBootstrap d = new DataServerBootstrap();
        ServerConfig.setDataServerBootStrap(d);
        System.out.println("Client Connections started");

        MQBrokerManager manager = new MQBrokerManager();
        try {
            manager.startBroker();
        } catch (Exception ex) {
            System.exit(-1);
        }

        ServerConfig.setMqBrokerManager(manager);
        System.out.println("MQ Broker started");

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
        
        
        //MQRECEIVERS

        System.out.println("Messaging producers started");

        TextMessageBroadCastConsumer b = new TextMessageBroadCastConsumer("broadcastTextMessages");
        ServerConfig.getMessageReceivers().put("broadcastTextMessages", b);
        
        PlanePositionConsumer ppc = new PlanePositionConsumer("planePosition");
        ServerConfig.getMessageReceivers().put("planePosition", ppc);

        FMSPlanConsumer fmsp = new FMSPlanConsumer("fmsPlan");
        ServerConfig.getMessageReceivers().put("fmsPlan", fmsp);
        
        LoginConsumer loginConsumer = new LoginConsumer("login");
        ServerConfig.getMessageReceivers().put("login", loginConsumer);
        
        
        System.out.println("Messaging Consumers started");

    }

}
