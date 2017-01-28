/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networktools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class NetworkingTools {

    public static void sendUPDStringToServerSocket(String host, int port, String message) throws UnknownHostException {

        InetAddress address = InetAddress.getByName(host);
        System.out.println("NetworkTools UPD Sender....");
        System.out.println("add is :" + host);
        byte[] msg = message.getBytes();
        try {

            DatagramPacket packet = new DatagramPacket(msg, msg.length,
                    address, port);
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(packet);
            System.out.println("NetworkTools: Sending UDP to ..." + address.getHostAddress());
            dsocket.close();
        } catch (UnknownHostException ex) {
            ex.printStackTrace(System.err);
        } catch (SocketException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

    }

}
