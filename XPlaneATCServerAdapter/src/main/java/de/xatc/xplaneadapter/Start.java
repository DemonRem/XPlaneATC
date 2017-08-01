/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter;

import de.annodatatracker.annotations.ItemType;
import de.annodatatracker.annotations.TrackDataInNB;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.gui.MainFrame;
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
                 AdapterConfig.setMainFrame(new MainFrame());
            }
        });

    }
    
    
    private void annotrackerTest() {
        
        System.out.println("MEthod with DataTracker");
        
    }

}
