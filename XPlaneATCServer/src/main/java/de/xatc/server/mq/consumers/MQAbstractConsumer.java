/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.consumers;

import de.xatc.server.config.ServerConfig;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;



/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public abstract class MQAbstractConsumer implements MessageListener {
    
    private static final Logger LOG = Logger.getLogger(MQAbstractConsumer.class.getName());
    
    private Session session;
    private String queueName;
    private Connection connection;
    private ActiveMQConnectionFactory connectionFactory;
    private Destination destination;
    
    public MQAbstractConsumer(String queueName) {
        
        LOG.info("Starting MQConsumer: " + queueName);
        this.queueName = queueName;
        this.setupMessageQueueConsumer();
        
    }
    
    private void setupMessageQueueConsumer() {
        connectionFactory = new ActiveMQConnectionFactory(ServerConfig.getMqBrokerConnectorAddress());
        connectionFactory.setTrustAllPackages(true);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = this.session.createQueue(queueName);
 
            
            MessageConsumer consumer = this.session.createConsumer(destination);
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            LOG.error(e.getLocalizedMessage());
            e.printStackTrace(System.err);
        }
    }
    
    public void shutdownConsumer() {
        
        try {
            session.close();
            connection.close();
        } catch (JMSException ex) {
            LOG.error(ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
        }
        
        
        
    }
    
    
    
    
    
    
    @Override
    public void onMessage(Message message){
        
        if (message instanceof TextMessage) {
            LOG.trace("AbstractMQ Consumer, TEXT Message received");
            onTextMessage((TextMessage) message);
            
        }
        else if (message instanceof ObjectMessage) {
            LOG.trace("AbstractMQ Consumer, OBJECT Message received");
            onObjectMessage((ObjectMessage) message);
        }
        
    }
    
    
    public abstract void onObjectMessage(ObjectMessage message);
    
    public abstract void onTextMessage(TextMessage message);

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ActiveMQConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
  
    
    
    
    
}
