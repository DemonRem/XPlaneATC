/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer;

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

             

            

            }
        });
        System.out.println("Shut Down Hook Attached.");

    }

}
