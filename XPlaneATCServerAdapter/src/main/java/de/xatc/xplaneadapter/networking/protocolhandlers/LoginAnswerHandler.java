/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.networking.protocolhandlers;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.xplaneadapter.config.AdapterConfig;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class LoginAnswerHandler {

    public static void handleLoginAnswer(Object packet) {

        LoginPacket l = (LoginPacket) packet;
        if (!l.isSuccessful()) {

            SwingTools.alertWindow("Login in was not successful. Server said: " + l.getServerMessage(), AdapterConfig.getMainFrame());
            AdapterConfig.getClientBootstrap().shutdownClient();
            return;

        }

        AdapterConfig.setCurrentSessionID(l.getSessionID());
        AdapterConfig.setCurrentChannelID(l.getChannelID());
        System.out.println("Login successful");
        
    }

}
