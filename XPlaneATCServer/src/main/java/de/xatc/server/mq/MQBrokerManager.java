/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq;

import de.xatc.server.config.ServerConfig;
import java.net.URI;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class MQBrokerManager {

    private BrokerService brokerService;
    private TransportConnector transportConnector;

    public MQBrokerManager() {

    }

    public void startBroker() throws Exception {

        brokerService = new BrokerService();
        
        
        
        transportConnector = new TransportConnector();
        transportConnector.setUri(new URI(ServerConfig.getMqBrokerConnectorAddress()));
        
        brokerService.addConnector(transportConnector);
        brokerService.start();

    }

    public void shutdownBroker() throws Exception {
        brokerService.removeConnector(transportConnector);
        brokerService.stop();
    }

}
