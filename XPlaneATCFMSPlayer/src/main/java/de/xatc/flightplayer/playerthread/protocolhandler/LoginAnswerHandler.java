/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.playerthread.protocolhandler;

import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.flightplayer.playerthread.PlayerControlThread;



/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class LoginAnswerHandler {

    public static void handleLoginAnswer(Object packet, PlayerControlThread controlThread) {

        LoginPacket l = (LoginPacket) packet;
        if (!l.isSuccessful()) {

            controlThread.getBootstrap().shutdownClient();
            return;

        }

        controlThread.setChannelID(l.getChannelID());
        controlThread.setSessionID(l.getSessionID());
        System.out.println("Login successful");
        
    }

}
