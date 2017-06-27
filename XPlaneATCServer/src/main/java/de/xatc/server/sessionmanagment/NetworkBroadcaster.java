/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.sessionmanagment;

import de.xatc.commons.datastructure.atc.ATCStructure;
import io.netty.channel.Channel;
import java.util.Map.Entry;

/**
 *
 * @author c047
 */
public class NetworkBroadcaster {

    public static void broadcastPilots(Object o) {

        if (!SessionManagement.getPilotDataStructures().isEmpty()) {
            for (Entry<String, Channel> entry : SessionManagement.getPilotChannels().entrySet()) {

                entry.getValue().writeAndFlush(o);

            }
        }
    }

    public static void broadcastATC(Object o) {

        if (!SessionManagement.getAtcDataStructures().isEmpty()) {
            for (Entry<String, Channel> entry : SessionManagement.getAtcChannels().entrySet()) {

                entry.getValue().writeAndFlush(o);

            }
        }
    }

    public static void broadcastAll(Object o) {

        if (!SessionManagement.getPilotDataStructures().isEmpty()) {
            for (Entry<String, Channel> entry : SessionManagement.getPilotChannels().entrySet()) {

                entry.getValue().writeAndFlush(o);

            }
        }
        if (!SessionManagement.getAtcDataStructures().isEmpty()) {
            for (Entry<String, Channel> entry : SessionManagement.getAtcChannels().entrySet()) {

                entry.getValue().writeAndFlush(o);

            }
        }
    }

}
