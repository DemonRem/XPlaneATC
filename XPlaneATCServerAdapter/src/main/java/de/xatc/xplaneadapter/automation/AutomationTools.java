/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.automation;

import de.xatc.xplaneadapter.ShutdownHook;
import de.xatc.xplaneadapter.Start;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.config.ConfigBean;
import de.xatc.xplaneadapter.gui.MainFrame;
import de.xatc.xplaneadapter.networking.XPlaneUDPListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author C047
 */
public class AutomationTools {
    
    
    
    public void createAndSendFlightPlan() {
        
        ActionEvent filePress = new ActionEvent(AdapterConfig.getMainFrame().getSendFlightPlanMenuItem(), ActionEvent.ACTION_PERFORMED, "Send FlightPlan");
        AdapterConfig.getMainFrame().actionPerformed(filePress);
        this.waitFor(3000);
        
        AdapterConfig.getFlightPlanFrame().getFlightNumberField().setText("DLH1807");
        AdapterConfig.getFlightPlanFrame().getIcaoFromField().setText("EDDK");
        AdapterConfig.getFlightPlanFrame().getIcaoToField().setText("EDDH");
        AdapterConfig.getFlightPlanFrame().getFlightLevelField().setText("290");
        AdapterConfig.getFlightPlanFrame().getTakeOffTimeField().setText("13:12:00");
        AdapterConfig.getFlightPlanFrame().getArrivalTimeField().setText("14:45:00");
        AdapterConfig.getFlightPlanFrame().getAircraftTypeField().setText("FA7X");
        AdapterConfig.getFlightPlanFrame().getIfrRadio().setSelected(true);
        AdapterConfig.getFlightPlanFrame().getAirlineField().setText("Lufthansa");
        AdapterConfig.getFlightPlanFrame().getRouteField().setText("PODIP Y867 WRB UM864 TOLGI Z90 DLE UP605 NOLGO");
        AdapterConfig.getFlightPlanFrame().getRemarkField().setText("This flight plan remark was created automatically");
        
        ActionEvent connPress = new ActionEvent(AdapterConfig.getFlightPlanFrame().getSendButton(), ActionEvent.ACTION_PERFORMED, "send");
        AdapterConfig.getFlightPlanFrame().pressSendButtonFromAutomation(connPress);
        this.waitFor(3000);
        
    }
    
    
    
    public void startUPDListener() {
        
        XPlaneUDPListener l = new XPlaneUDPListener();
        AdapterConfig.setXplaneUDPListener(l);
        AdapterConfig.getMainFrame().getListenToXPlaneItem().setSelected(true);
        AdapterConfig.getMainFrame().revalidate();
        AdapterConfig.getMainFrame().repaint();
        l.start();
        
    }
    
    public void startUp() {
         SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 ShutdownHook.attachShutDownHook();
                 AdapterConfig.setMainFrame(new MainFrame());
            }
        });

        this.waitFor(3000);
        
    }
    
    public void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    
    public void setPropertyValues() {
        
        
        AdapterConfig.setConfigBean(new ConfigBean());
        AdapterConfig.getConfigBean().setAtcServerName("192.168.56.1");
        AdapterConfig.getConfigBean().setAtcServerPort("9090");
        AdapterConfig.getConfigBean().setXplaneListnerIP("192.168.56.1");
        AdapterConfig.getConfigBean().setXplaneListenerPort("8181");
        AdapterConfig.getConfigBean().setConnectionName("doof");
        AdapterConfig.getConfigBean().setAtcVoiceServerName("192.168.56.1");
        AdapterConfig.getConfigBean().setAtcVoiceServerPort("8282");
        AdapterConfig.savePropsFile();
    }
    
    
    public void login() {
        
        
         
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
    }
    
    
    
    
}
