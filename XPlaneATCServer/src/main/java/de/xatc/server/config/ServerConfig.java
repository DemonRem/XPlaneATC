/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.config;

import de.xatc.server.mq.MQBrokerManager;
import de.xatc.server.mq.consumers.MQAbstractConsumer;
import de.xatc.server.mq.producers.MQMessageSender;
import de.xatc.server.networking.clients.DataServerBootstrap;
import de.xatc.server.networking.controller.ATCServerBootstrap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ServerConfig {

    private static final String appName = "XPlane ATC Server";
    private static final String version = "0.1-alpha";

    private static final int dataPort = 9090;
    private static final int voicePort = 9091;

    private static final String dataIP = "192.168.56.1";
    private static final String voiceIP = "192.168.56.1";

    private static final String atcIP = "192.168.56.1";
    private static final int atcPort = 9092;

    private static final int maxConnectionsAllowed = 128;

    private static final boolean keepConnectionsAlive = true;

    

    private static DataServerBootstrap dataServerBootStrap;

    private static ATCServerBootstrap atcServerBootStrap;

    private static MQBrokerManager mqBrokerManager;
    private static final String mqBrokerConnectorAddress = "tcp://localhost:61616";

    private static Map<String, MQMessageSender> messageSenders = new HashMap<>();
    private static Map<String, MQAbstractConsumer> messageReceivers = new HashMap<>();
    
    private static final String countryCodesFile = "src/main/resources/atcData/countrycodes.dat";
    private static final String countriesFile = "src/main/resources/atcData/countries.dat";
    private static final String firListFile = "src/main/resources/atcData/firlist.dat";
    private static final String firDetailFile = "src/main/resources/atcData/firdisplay.dat";
    private static final String airportFile = "src/main/resources/atcData/airports.dat";
    
    
    

    public static String getAppName() {
        return appName;
    }

    public static String getVersion() {
        return version;
    }

    public static int getDataPort() {
        return dataPort;
    }

    public static int getVoicePort() {
        return voicePort;
    }

    public static String getDataIP() {
        return dataIP;
    }

    public static String getVoiceIP() {
        return voiceIP;
    }

    public static int getMaxConnectionsAllowed() {
        return maxConnectionsAllowed;
    }

    public static boolean isKeepConnectionsAlive() {
        return keepConnectionsAlive;
    }

   

    public static DataServerBootstrap getDataServerBootStrap() {
        return dataServerBootStrap;
    }

    public static void setDataServerBootStrap(DataServerBootstrap dataServerBootStrap) {
        ServerConfig.dataServerBootStrap = dataServerBootStrap;
    }

    public static String getMqBrokerConnectorAddress() {
        return mqBrokerConnectorAddress;
    }

    public static MQBrokerManager getMqBrokerManager() {
        return mqBrokerManager;
    }

    public static void setMqBrokerManager(MQBrokerManager mqBrokerManager) {
        ServerConfig.mqBrokerManager = mqBrokerManager;
    }

    public static Map<String, MQMessageSender> getMessageSenders() {
        return messageSenders;
    }

    public static void setMessageSenders(Map<String, MQMessageSender> m) {
        messageSenders = m;
    }

    public static Map<String, MQAbstractConsumer> getMessageReceivers() {
        return messageReceivers;
    }

    public static void setMessageReceivers(Map<String, MQAbstractConsumer> messageReceivers) {
        ServerConfig.messageReceivers = messageReceivers;
    }

    public static String getAtcIP() {
        return atcIP;
    }

    public static int getAtcPort() {
        return atcPort;
    }

    public static ATCServerBootstrap getAtcServerBootStrap() {
        return atcServerBootStrap;
    }

    public static void setAtcServerBootStrap(ATCServerBootstrap atcServerBootStrap) {
        ServerConfig.atcServerBootStrap = atcServerBootStrap;
    }

    public static String getCountryCodesFile() {
        return countryCodesFile;
    }

    public static String getCountriesFile() {
        return countriesFile;
    }

    public static String getFirListFile() {
        return firListFile;
    }

    public static String getFirDetailFile() {
        return firDetailFile;
    }

    public static String getAirportFile() {
        return airportFile;
    }
    
    

}
