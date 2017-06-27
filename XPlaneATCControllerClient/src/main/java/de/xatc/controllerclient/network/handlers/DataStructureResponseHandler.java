/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.datastructure.atc.ATCStructure;
import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.datastructures.DataStructureSilo;
import de.xatc.controllerclient.datastructures.LocalAtcDataStructure;
import de.xatc.controllerclient.datastructures.LocalPilotDataStructure;
import de.xatc.controllerclient.gui.painters.AircraftPainter;

/**
 *
 * @author c047
 */
public class DataStructureResponseHandler {
    
    public static void handleRemovePilotStructure(String strcutureSessionID) {
        
        LocalPilotDataStructure l = DataStructureSilo.getLocalPilotStructure().get(strcutureSessionID);
        if (l != null) {
            
            XHSConfig.getMainFrame().getMainPanel().getMapPanel().getPaintersList().remove(l.getAircraftPainter());
            DataStructureSilo.getLocalPilotStructure().remove(strcutureSessionID);
            XHSConfig.getMainFrame().getMainPanel().getMapPanel().reloadPainter();
            
        }
        
    }
    
    public static void removeATCStructure(String structureSessionID) {
        
        DataStructureSilo.getLocalATCStructures().remove(structureSessionID);
        
        
    }
    
    
    public static void handleNewATCStructure(ATCStructure s) {
        
        LocalAtcDataStructure l = new LocalAtcDataStructure();
        l.setServerATCStructure(s);
        l.setSessionID(s.getStructureSessionID());

        DataStructureSilo.getLocalATCStructures().put(l.getSessionID(),l);
        
    }
    public static void handleNewPilotStructure(PilotStructure p) {
        
        LocalPilotDataStructure l = new LocalPilotDataStructure();
        l.setPilotServerStructure(p);
        l.setSessionID(p.getStructureSessionID());
        AircraftPainter painter = new AircraftPainter();
        l.setAircraftPainter(painter);
        XHSConfig.getMainFrame().getMainPanel().getMapPanel().getPaintersList().add(painter);
        DataStructureSilo.getLocalPilotStructure().put(p.getStructureSessionID(), l);
        XHSConfig.getMainFrame().getMainPanel().getMapPanel().reloadPainter();
        
        
    }
    
    
}
