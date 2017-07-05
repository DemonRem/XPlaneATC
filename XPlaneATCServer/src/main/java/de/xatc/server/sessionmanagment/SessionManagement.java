/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.sessionmanagment;

import de.xatc.commons.datastructure.atc.ATCStructure;
import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.commons.db.sharedentities.user.XATCUserSession;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class SessionManagement {


    private static final Logger LOG = Logger.getLogger(SessionManagement.class.getName());
    
    private static Map<String,DefaultChannelGroup> frequencyList = new HashMap<>();
    
    private static ConcurrentHashMap<String,PilotStructure> pilotDataStructures = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, ATCStructure> atcDataStructures = new ConcurrentHashMap<>();
    
    private static ConcurrentHashMap<String,Channel> atcChannels = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,Channel> pilotChannels = new ConcurrentHashMap();
    
    
    
    
    
    public static RegisteredUser findOverallUserByUsername(String userName) {
        
        
        RegisteredUser user = findRegisteredPilotUserByUserName(userName);
        if (user != null) {
            return user;
        }
        user = findRegisteredATCUserByUserName(userName);
        if (user != null) {
            return user;
        }
        return null;
        
        
    }
    
 
    public static RegisteredUser findRegisteredPilotUserByUserName(String userName) {
        
        for (Entry<String,PilotStructure> entry : pilotDataStructures.entrySet()) {
            
            
            if (entry.getValue().getUserName().equals(userName)) {
                return entry.getValue().getUser();
            }
            
            
        }
        return null;
        
    }
    public static RegisteredUser findRegisteredATCUserByUserName(String userName) {
        
        for (Entry<String,ATCStructure> entry : atcDataStructures.entrySet()) {
            
            
            if (entry.getValue().getUserName().equals(userName)) {
                return entry.getValue().getUser();
            }
            
        }
        return null;
    }
    
    
    
    public static UserRole getATCUserRoleBySessionID(String sessionID) {
        LOG.debug("Finding UserRole by SessionID");
        XATCUserSession s = findATCUserSessionBySessionID(sessionID);
        if (s == null) {
            LOG.warn("Session is null. Could not be found");
            return null;
        }
        return s.getRegisteredUser().getUserRole();
        
    }
    
    public static ATCStructure findATCStructureByChannelID(String channelID) {
        
        for (Entry<String,ATCStructure> entry : atcDataStructures.entrySet()) {
            
            if (entry.getValue().getChannelID().equals(channelID)) {
                
                return entry.getValue();
            }
            
        }
        return null;
        
    }
    public static PilotStructure findPilotStructureByChannelID(String channelID) {
        
        for (Entry<String,PilotStructure> entry : pilotDataStructures.entrySet()) {
            
            if (entry.getValue().getChannelID().equals(channelID)) {
                
                return entry.getValue();
            }
            
        }
        return null;
        
    }
    
    
    public static void removeATCSessionByChannel(Channel c) {
        
        for (Entry<String,Channel> entry : atcChannels.entrySet()) {
            
            if (entry.getValue() == c) {
                LOG.info("Removing ATC Channel");
                atcDataStructures.remove(entry.getKey());
                
                return;
            }
            
            
        }
        LOG.warn("Could not remove atc channel after losing connection");
        
    }
    
    
    public static void removePilotSessionByChannel(Channel c) {
        
        for (Entry<String,Channel> entry : pilotChannels.entrySet()) {
            
            if (entry.getValue() == c) {
                atcDataStructures.remove(entry.getKey());
                return;
            }   
        }    
    }
    
    
    
    
    
    public static boolean isAdmin(String sessionID) {
        
    
        
        
        
        UserRole r = getATCUserRoleBySessionID(sessionID);
        if (r == null) {
            return false;
        }
        if (r == UserRole.ADMINISTRATOR) {
            return true;
        }
        else {
            return false;
        }
        
        
    }
    
    
    
  
    
    public static XATCUserSession findUserSessionByUsername(String userName) {

        for (Entry<String,PilotStructure> entry  : pilotDataStructures.entrySet()) {

            if (entry.getValue().getUserName().equals(userName)) {
                return entry.getValue().getUserSession();
            }

        }
        return null;

    }


    public static XATCUserSession findOverallUserSessionByChannelID(String channelID) {
        
        
        XATCUserSession u = findPilotUserSessionByChannelID(channelID);
        if (u != null) {
            return u;
        }
        u = findATCUserSessionByChannelID(channelID);
        if (u != null) {
            return u;
        }
        return null;
        
        
    }
    
     public static XATCUserSession findATCUserSessionByChannelID(String channelID) {
        
        for (Entry<String,ATCStructure> entry : atcDataStructures.entrySet()) {

            if (entry.getValue().getChannelID().equals(channelID)) {
                return entry.getValue().getUserSession();
            }

        }
        return null;
        
        
    }
    
    
     public static XATCUserSession findPilotUserSessionByChannelID(String channelID) {
        
        for (Entry<String,PilotStructure> entry : pilotDataStructures.entrySet()) {

            if (entry.getValue().getChannelID().equals(channelID)) {
                return entry.getValue().getUserSession();
            }

        }
        return null;
        
        
    }
    
     public static XATCUserSession findATCUserSessionBySessionID(String sessionID) {
         ATCStructure s = atcDataStructures.get(sessionID);
         if (s == null) {
             return null;
         }
         return s.getUserSession();
     }
    
    public static XATCUserSession findUserSessionBySessionID(String sessionID, Map<String,PilotStructure> sessionList) {
        
        return sessionList.get(sessionID).getUserSession();
    }
   


    public static void addProvidedFrequency(String freq) {
        
        frequencyList.put(freq, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        
    }

    public static void removeProvidedFrequency(String freq) {
        
        frequencyList.remove(freq);
        
    }
    public static void putChannelToProvidedFrequency(String freq, Channel n) {
        
        if (!frequencyList.containsKey(freq)) {
            return;
        }
        frequencyList.get(freq).add(n);
        
        
    }
    public static void removeChannelFromProvidedFrequency(String freq, Channel n) {
        
        if (!frequencyList.containsKey(freq)) {
            return;
        }
        frequencyList.get(freq).remove(n);
    }
    
    public static String findFrequencyBy(Channel n) {
        
        for (Entry<String,DefaultChannelGroup> entry : frequencyList.entrySet()) {
            
            if (entry.getValue().contains(n)) {
                return entry.getKey();
            }
            
        }
        return null;
        
    }
    
    public static DefaultChannelGroup getChannelGroupByFrequency(String freq) {
        
        return frequencyList.get(freq);
        
    }

    public static Map<String, DefaultChannelGroup> getFrequencyList() {
        return frequencyList;
    }

    public static void setFrequencyList(Map<String, DefaultChannelGroup> frequencyList) {
        SessionManagement.frequencyList = frequencyList;
    }

    public static ConcurrentHashMap<String, PilotStructure> getPilotDataStructures() {
        return pilotDataStructures;
    }

    public static void setPilotDataStructures(ConcurrentHashMap<String, PilotStructure> pilotDataStructures) {
        SessionManagement.pilotDataStructures = pilotDataStructures;
    }

    public static ConcurrentHashMap<String, ATCStructure> getAtcDataStructures() {
        return atcDataStructures;
    }

    public static void setAtcDataStructures(ConcurrentHashMap<String, ATCStructure> atcDataStructures) {
        SessionManagement.atcDataStructures = atcDataStructures;
    }

    public static ConcurrentHashMap<String, Channel> getAtcChannels() {
        return atcChannels;
    }

    public static void setAtcChannels(ConcurrentHashMap<String, Channel> atcChannels) {
        SessionManagement.atcChannels = atcChannels;
    }

    public static ConcurrentHashMap<String, Channel> getPilotChannels() {
        return pilotChannels;
    }

    public static void setPilotChannels(ConcurrentHashMap<String, Channel> pilotChannels) {
        SessionManagement.pilotChannels = pilotChannels;
    }
  
   
    

}
