/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.networkpackets.atc.servercontrol.ShutdownServer;
import de.xatc.commons.networkpackets.atc.servercontrol.StartClientConnector;
import de.xatc.commons.networkpackets.atc.servercontrol.StartMQBroker;
import de.xatc.commons.networkpackets.atc.servercontrol.StartMessagingConsumers;
import de.xatc.commons.networkpackets.atc.servercontrol.StartMessagingProducers;
import de.xatc.commons.networkpackets.atc.servercontrol.StopClientConnector;
import de.xatc.commons.networkpackets.atc.servercontrol.StopMQBroker;
import de.xatc.commons.networkpackets.atc.servercontrol.StopMessagingConsumers;
import de.xatc.commons.networkpackets.atc.servercontrol.StopMessagingProducers;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.mq.MQBrokerManager;
import de.xatc.server.mq.consumers.MQAbstractConsumer;
import de.xatc.server.mq.consumers.TextMessageBroadCastConsumer;
import de.xatc.server.mq.producers.MQMessageSender;
import de.xatc.server.nettybootstrap.pilot.DataServerBootstrap;
import de.xatc.server.sessionmanagment.SessionManagement;
import java.util.Map;

/**
 *
 * @author Mirko
 */
public class ServerControlHandler {

    public static void handleStartClientConnections(Object msg) {

        System.out.println("Starting Client Connections");
        StartClientConnector s = (StartClientConnector) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        if (ServerConfig.getDataServerBootStrap() != null) {
            System.out.println("Client Connector is already running. returning!");
            return;

        }
        DataServerBootstrap d = new DataServerBootstrap();
        ServerConfig.setDataServerBootStrap(d);
        System.out.println("Client Connections started");

    }

    public static void handleStopClientConnections(Object msg) {

        System.out.println("Stopping Client Connections");
        StopClientConnector s = (StopClientConnector) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }
        if (ServerConfig.getDataServerBootStrap() == null) {
            System.out.println("No Client connector found");
            return;
        }

        ServerConfig.getDataServerBootStrap().shutdownServer();
        ServerConfig.setDataServerBootStrap(null);
        SessionManagement.getDataChannelGroup().clear();
        SessionManagement.getUserSessionList().clear();
        System.out.println("Client Connections stopped");

    }

    public static void handleShutdownServer(Object msg) {

        System.out.println("shutting down Server");
        ShutdownServer s = (ShutdownServer) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        System.exit(0);

    }

    public static void handleStartMessagingProducers(Object msg) {

        System.out.println("Starting Messaging Producers");
        StartMessagingProducers s = (StartMessagingProducers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        if (ServerConfig.getMessageSenders().size() > 0) {
            System.out.println("Producers already running.");
            return;
        }

        //setup all Message Producers
        MQMessageSender writeToAll = new MQMessageSender("broadcastTextMessages");
        writeToAll.startProducer();
        ServerConfig.getMessageSenders().put("broadcastTextMessages", writeToAll);

        System.out.println("Messaging producers started");
    }

    public static void handleStopMessagingProducers(Object msg) {

        System.out.println("Stopping Messaging producers");
        StopMessagingProducers s = (StopMessagingProducers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        //shutdown all mq producers
        System.out.println("Shutting Down all Message Producers for MQ");
        for (Map.Entry<String, MQMessageSender> entry : ServerConfig.getMessageSenders().entrySet()) {

            System.out.println("Shutting down messageSende: " + entry.getValue().getQueueName());
            entry.getValue().shutdownProducer();

        }

        ServerConfig.getMessageSenders().clear();
        System.out.println("Messaging Producers stopped");
    }

    public static void handleStartMessagingConsumers(Object msg) {

        System.out.println("Starting Messaging Consumers");
        StartMessagingConsumers s = (StartMessagingConsumers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        if (ServerConfig.getMessageReceivers().size() > 0) {
            System.out.println("Message receivers already running");
            return;
        }

        //Setup all Message Consumers
        
        
        TextMessageBroadCastConsumer b = new TextMessageBroadCastConsumer("broadcastTextMessages");
        ServerConfig.getMessageReceivers().put("broadcastTextMessages", b);

        System.out.println("Messaging Consumers started");
    }

    public static void handleStopMessagingConsumers(Object msg) {

        System.out.println("Stopping Messaging Consumers");
        StopMessagingConsumers s = (StopMessagingConsumers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        //shutdown all Consumers
        for (Map.Entry<String, MQAbstractConsumer> entry : ServerConfig.getMessageReceivers().entrySet()) {

            System.out.println("Shutting down messageReceiver: " + entry.getValue().getQueueName());
            entry.getValue().shutdownConsumer();

        }

        ServerConfig.getMessageReceivers().clear();
        System.out.println("MessagingConsumers stopped");
    }

    public static void handleStartMQBroker(Object msg) {

        System.out.println("Starting MQ Broker");
        StartMQBroker s = (StartMQBroker) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        if (ServerConfig.getMqBrokerManager() != null) {
            System.out.println("Borker already running.....");
            return;
        }
        
        MQBrokerManager manager = new MQBrokerManager();
        try {
            manager.startBroker();
        } catch (Exception ex) {
            System.exit(-1);
        }

        ServerConfig.setMqBrokerManager(manager);
        System.out.println("MQ Broker started");
    }

    public static void handleStopMQBroker(Object msg) {
        
        System.out.println("Stopping MQ Broker");
        StopMQBroker s = (StopMQBroker) msg;
        if (!SessionManagement.isAdmin(s.getSessionID(), SessionManagement.getAtcSessionList())) {
            System.out.println("Not admin.... returning");
            return;
        }

        //shutdown MQBroker
        if (ServerConfig.getMqBrokerManager() != null) {
            try {
                ServerConfig.getMqBrokerManager().shutdownBroker();
                ServerConfig.setMqBrokerManager(null);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        System.out.println("MQ BRoker stopped");
    }

}
