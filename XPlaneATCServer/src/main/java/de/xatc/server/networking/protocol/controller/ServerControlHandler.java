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
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko
 */
public class ServerControlHandler {

    private static final Logger LOG = Logger.getLogger(ServerControlHandler.class.getName());
    
    public static void handleStartClientConnections(Object msg) {

        LOG.trace("Starting Client Connections");
        StartClientConnector s = (StartClientConnector) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }

        if (ServerConfig.getDataServerBootStrap() != null) {
            LOG.warn("Client Connector is already running. returning!");
            return;

        }
        DataServerBootstrap d = new DataServerBootstrap();
        ServerConfig.setDataServerBootStrap(d);
        LOG.debug("Client Connections started");

    }

    public static void handleStopClientConnections(Object msg) {

        LOG.info("Stopping Client Connections");
        StopClientConnector s = (StopClientConnector) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }
        if (ServerConfig.getDataServerBootStrap() == null) {
            LOG.warn("No Client connector found");
            return;
        }

        ServerConfig.getDataServerBootStrap().shutdownServer();
        ServerConfig.setDataServerBootStrap(null);
        SessionManagement.getPilotDataStructures().clear();
        SessionManagement.getAtcDataStructures().clear();
        LOG.debug("Client Connections stopped");

    }

    public static void handleShutdownServer(Object msg) {

        LOG.info("shutting down Server");
        ShutdownServer s = (ShutdownServer) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }

        System.exit(0);

    }

    public static void handleStartMessagingProducers(Object msg) {

        LOG.debug("Starting Messaging Producers");
        StartMessagingProducers s = (StartMessagingProducers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }

        if (ServerConfig.getMessageSenders().size() > 0) {
            LOG.warn("Producers already running.");
            return;
        }

        //setup all Message Producers
        MQMessageSender writeToAll = new MQMessageSender("broadcastTextMessages");
        writeToAll.startProducer();
        ServerConfig.getMessageSenders().put("broadcastTextMessages", writeToAll);

        LOG.debug("Messaging producers started");
    }

    public static void handleStopMessagingProducers(Object msg) {

        LOG.debug("Stopping Messaging producers");
        StopMessagingProducers s = (StopMessagingProducers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }

        //shutdown all mq producers
        LOG.info("Shutting Down all Message Producers for MQ");
        for (Map.Entry<String, MQMessageSender> entry : ServerConfig.getMessageSenders().entrySet()) {

            LOG.info("Shutting down messageSende: " + entry.getValue().getQueueName());
            entry.getValue().shutdownProducer();

        }

        ServerConfig.getMessageSenders().clear();
        LOG.info("Messaging Producers stopped");
    }

    public static void handleStartMessagingConsumers(Object msg) {

        LOG.debug("Starting Messaging Consumers");
        StartMessagingConsumers s = (StartMessagingConsumers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.debug("Not admin.... returning");
            return;
        }

        if (ServerConfig.getMessageReceivers().size() > 0) {
            LOG.debug("Message receivers already running");
            return;
        }

        //Setup all Message Consumers
        
        
        TextMessageBroadCastConsumer b = new TextMessageBroadCastConsumer("broadcastTextMessages");
        ServerConfig.getMessageReceivers().put("broadcastTextMessages", b);

        LOG.info("Messaging Consumers started");
    }

    public static void handleStopMessagingConsumers(Object msg) {

        LOG.debug("Stopping Messaging Consumers");
        StopMessagingConsumers s = (StopMessagingConsumers) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }

        //shutdown all Consumers
        for (Map.Entry<String, MQAbstractConsumer> entry : ServerConfig.getMessageReceivers().entrySet()) {

            LOG.debug("Shutting down messageReceiver: " + entry.getValue().getQueueName());
            entry.getValue().shutdownConsumer();

        }

        ServerConfig.getMessageReceivers().clear();
        LOG.info("MessagingConsumers stopped");
    }

    public static void handleStartMQBroker(Object msg) {

        LOG.debug("Starting MQ Broker");
        StartMQBroker s = (StartMQBroker) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }

        if (ServerConfig.getMqBrokerManager() != null) {
            LOG.warn("Borker already running.....");
            return;
        }
        
        MQBrokerManager manager = new MQBrokerManager();
        try {
            manager.startBroker();
        } catch (Exception ex) {
            System.exit(-1);
        }

        ServerConfig.setMqBrokerManager(manager);
        LOG.info("MQ Broker started");
    }

    public static void handleStopMQBroker(Object msg) {
        
        LOG.debug("Stopping MQ Broker");
        StopMQBroker s = (StopMQBroker) msg;
        if (!SessionManagement.isAdmin(s.getSessionID())) {
            LOG.warn("Not admin.... returning");
            return;
        }

        //shutdown MQBroker
        if (ServerConfig.getMqBrokerManager() != null) {
            try {
                ServerConfig.getMqBrokerManager().shutdownBroker();
                ServerConfig.setMqBrokerManager(null);
            } catch (Exception ex) {
                LOG.error(ex.getLocalizedMessage());
                ex.printStackTrace(System.err);
            }
        }
        LOG.info("MQ BRoker stopped");
    }

}
