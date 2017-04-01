/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter;

import de.xatc.xplaneadapter.config.AdapterConfig;

/**
 *
 * @author Mirko
 */
public class ShutdownHook {

    public static void attachShutDownHook() {

        System.out.println("Attaching ShutDownHook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("ShutDownHook Running");

                if (AdapterConfig.isListeningToXPlane()) {
                    System.out.println("Closing X-Plane Listener Socket");
                    AdapterConfig.getXplaneUDPListener().closeSocket();
                }

                if (AdapterConfig.isConnectedToATCServer()) {
                    if (AdapterConfig.getClientBootstrap() != null) {
                        System.out.println("Logging off from Server");
                        AdapterConfig.getClientBootstrap().shutdownClient();
                    }

                }

            }
        });
        System.out.println("Shut Down Hook Attached.");

    }

}
