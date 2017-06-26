/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.playerthread.protocolhandler;

import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.flightplayer.playerthread.PlayerControlThread;


/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class RegisterAnwerHandler {
    
    public static void handlePacket(Object packet, PlayerControlThread controlThread) {
        
        
        
        System.out.println("RegisteredAnserHandler");
        System.out.println("msg = " + packet);
        RegisterPacket p = (RegisterPacket) packet;
        if (p.isSuccess()) {
            
            
            System.out.println("Registering was successful");
            controlThread.getBootstrap().shutdownClient();
        }
        else {
            System.out.println("Could not register your Account! Server said: ");
            controlThread.getBootstrap().shutdownClient();
        }
        
        
        
    }
    
    
}
