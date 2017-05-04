/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.networking.protocolhandlers;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.xplaneadapter.config.AdapterConfig;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class RegisterAnwerHandler {
    
    public static void handlePacket(Object packet) {
        
        
        
        System.out.println("RegisteredAnserHandler");
        System.out.println("msg = " + packet);
        RegisterPacket p = (RegisterPacket) packet;
        if (p.isSuccess()) {
            SwingTools.alertWindow("Your account was registered successfully!",AdapterConfig.getMainFrame());
            
            
            AdapterConfig.getClientBootstrap().shutdownClient();
        }
        else {
            SwingTools.alertWindow("Could not register your Account! Server said: " + p.getServerMessage(),AdapterConfig.getMainFrame());
            AdapterConfig.getClientBootstrap().shutdownClient();
        }
        
        
        
    }
    
    
}