/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.automation;

import de.xatc.xplaneadapter.Start;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.config.ConfigBean;
import de.xatc.xplaneadapter.networking.XPlaneUDPListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Mirko
 */
public class Login {

    public static void main(String[] arg) {

        
        AdapterConfig.setConfigBean(new ConfigBean());
        AdapterConfig.getConfigBean().setAtcServerName("192.168.56.1");
        AdapterConfig.getConfigBean().setAtcServerPort("9090");
        AdapterConfig.getConfigBean().setXplaneListnerIP("192.168.56.1");
        AdapterConfig.getConfigBean().setXplaneListenerPort("8181");
        AdapterConfig.getConfigBean().setConnectionName("doof");
        AdapterConfig.getConfigBean().setAtcVoiceServerName("192.168.56.1");
        AdapterConfig.getConfigBean().setAtcVoiceServerPort("8282");
        AdapterConfig.savePropsFile();
        
        
        Start.main(new String[]{});

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        ActionEvent filePress = new ActionEvent(AdapterConfig.getMainFrame().getConnectMenuItem(), ActionEvent.ACTION_PERFORMED, "Connect to ATC-Server");
        AdapterConfig.getMainFrame().actionPerformed(filePress);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        AdapterConfig.getConnectFrame().getNameField().setText("doof");
        AdapterConfig.getConnectFrame().getPasswordField().setText("doof");

        ActionEvent connPress = new ActionEvent(AdapterConfig.getConnectFrame().getConnectButton(), ActionEvent.ACTION_PERFORMED, "connect");
        AdapterConfig.getConnectFrame().actionPerformed(connPress);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

        XPlaneUDPListener l = new XPlaneUDPListener();
        AdapterConfig.setXplaneUDPListener(l);
        l.start();

    }

}
