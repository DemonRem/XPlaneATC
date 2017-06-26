/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.datastructures;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author c047
 */
public class DataStructureSilo {
    
    private static ConcurrentHashMap<String, LocalAtcDataStructure> localATCStructures = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, LocalPilotDataStructure> localPilotStructure = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, LocalAtcDataStructure> getLocalATCStructures() {
        return localATCStructures;
    }

    public static void setLocalATCStructures(ConcurrentHashMap<String, LocalAtcDataStructure> localATCStructures) {
        DataStructureSilo.localATCStructures = localATCStructures;
    }

    public static ConcurrentHashMap<String, LocalPilotDataStructure> getLocalPilotStructure() {
        return localPilotStructure;
    }

    public static void setLocalPilotStructure(ConcurrentHashMap<String, LocalPilotDataStructure> localPilotStructure) {
        DataStructureSilo.localPilotStructure = localPilotStructure;
    }
    
    
    
    
    
    
}
