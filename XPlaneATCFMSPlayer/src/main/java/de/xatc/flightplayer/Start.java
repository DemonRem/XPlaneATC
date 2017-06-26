/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer;

import de.xatc.flightplayer.config.PlayerConfig;
import de.xatc.flightplayer.gui.MainFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class Start {

    public static void main(String[] arg) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 ShutdownHook.attachShutDownHook();
                 PlayerConfig.setMainFrame(new MainFrame());
            }
        });

    }

}
