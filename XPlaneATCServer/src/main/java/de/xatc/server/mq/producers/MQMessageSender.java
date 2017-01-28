/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.mq.producers;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class MQMessageSender {

    private ConnectionFactory factory = null;

    private Connection connection = null;

    private Session session = null;

    private Destination destination = null;

    private MessageProducer producer = null;
    private String queueName;

    public MQMessageSender(String queueName) {

        System.out.println("Setting up Message Sender: " + queueName);
        this.queueName = queueName;

    }

    public void startProducer() {

        System.out.println("Starting Message Sender: " + this.queueName);
        try {
            factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_BROKER_URL);
            

            connection = factory.createConnection();

            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(this.queueName);

            producer = session.createProducer(destination);
        } catch (JMSException ex) {
            ex.printStackTrace(System.err);
        }

    }

    public void sendTextMessage(String message) {

        try {
            TextMessage textMessage = session.createTextMessage();

            textMessage.setText(message);

            producer.send(textMessage);
        } catch (JMSException ex) {
            Logger.getLogger(MQMessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendObjectMessage(Serializable object) {
        
        try {
            ObjectMessage o = session.createObjectMessage();
            o.setObject(object);
            
            producer.send(o);
        } catch (JMSException ex) {
            Logger.getLogger(MQMessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void shutdownProducer() {

        try {
            this.producer.close();
            this.session.close();
            this.connection.close();
        } catch (JMSException ex) {
            ex.printStackTrace(System.err);
        }

    }

    public ConnectionFactory getFactory() {
        return factory;
    }

    public void setFactory(ConnectionFactory factory) {
        this.factory = factory;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public MessageProducer getProducer() {
        return producer;
    }

    public void setProducer(MessageProducer producer) {
        this.producer = producer;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    
    
    
}
