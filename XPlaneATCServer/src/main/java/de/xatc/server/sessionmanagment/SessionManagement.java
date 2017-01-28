/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.sessionmanagment;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.server.db.entities.XATCUserSession;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class SessionManagement {

    private static ChannelGroup dataChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ChannelGroup atcChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    
    private static Map<String,XATCUserSession> userSessionList = new HashMap<>();

    private static Map<String,XATCUserSession> atcSessionList = new HashMap<>();
    
    private static Map<String,DefaultChannelGroup> frequencyList = new HashMap<>();
    
    
    
    
    
    
    public static synchronized void removeChannelFromFrequencyChannelGroup(String freq, Channel n) {
        
        if (frequencyList.containsKey(freq)) {
            frequencyList.get(freq).remove(n);
        }
        
        
    }
    
    public static synchronized void addChannelToFrequencyChannelGroup(String freq,Channel n) {
        
        if (frequencyList.containsKey(freq)) {
            
            frequencyList.get(freq).add(n);
            
            
        }
        
        
    }
    
    
    public static synchronized void removeFrequencyChannelGroup(String freq) {
        
        frequencyList.remove(freq);
        
    }
    
    public static synchronized void addFrequencyChannelGroup(String freq) {
        
        DefaultChannelGroup g = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        frequencyList.put(freq,g);
        
        
    }
    
    
    
    public static RegisteredUser findRegisteredUserByUserName(String userName, Map<String,XATCUserSession> sessionList) {
        
        for (Entry<String,XATCUserSession> entry : sessionList.entrySet()) {
            
            RegisteredUser u = entry.getValue().getRegisteredUser();
            if (u.getRegisteredUserName().equals(userName)) {
                return u;
            }
            
            
        }
        return null;
        
    }
    
    
    
    
    public static synchronized UserRole getUserRoleBySessionID(String sessionID, Map<String,XATCUserSession> sessionList) {
        System.out.println("Finding UserRole by SessionID");
        XATCUserSession s = findUserSessionBySessionID(sessionID,sessionList);
        if (s == null) {
            System.out.println("Session is null. Could not be found");
            return null;
        }
        return s.getRegisteredUser().getUserRole();
        
        
        
    }
    
    public static boolean isAdmin(String sessionID, Map<String,XATCUserSession> sessionList) {
        
        UserRole r = getUserRoleBySessionID(sessionID, sessionList);
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
    
    
    
    public static synchronized void addUserSession(XATCUserSession session) {
        
        userSessionList.put(session.getSessionID(),session);
        
    }
    
    public static synchronized void removeUserSession(XATCUserSession session) {
        if (session == null) {
            return;
        }
        userSessionList.remove(session);
        
    }
    
    public static synchronized void addATCSession(XATCUserSession session) {
        
        atcSessionList.put(session.getSessionID(),session);
        
    }
    
    public static synchronized void removeATCSession(String sessionID) {
        if (sessionID == null) {
            return;
        }
        atcSessionList.remove(sessionID);
        
    }
    
    
    public static XATCUserSession findUserSessionByUsername(String userName) {

        for (Entry<String,XATCUserSession> entry  : userSessionList.entrySet()) {

            if (entry.getValue().getSessionUserName().equals(userName)) {
                return entry.getValue();
            }

        }
        return null;

    }
    
    
     public static XATCUserSession findUserSessionByChannelID(String channelID, Map<String,XATCUserSession> sessionList) {
        
        for (Entry<String,XATCUserSession> entry : sessionList.entrySet()) {

            if (entry.getValue().getChannelID().equals(channelID)) {
                return entry.getValue();
            }

        }
        return null;
        
        
    }
    
    
    public static XATCUserSession findUserSessionBySessionID(String sessionID, Map<String,XATCUserSession> sessionList) {
        
        return sessionList.get(sessionID);
    }
    
    public static synchronized void addATCChannel(Channel c) {
        atcChannelGroup.add(c);
    }
    public static synchronized void removeATCChannel(Channel c) {
        atcChannelGroup.remove(c);
    }

    public static synchronized void addDataChannel(Channel c) {
        dataChannelGroup.add(c);
    }

    public static synchronized void removeDataChannel(Channel c) {
        dataChannelGroup.remove(c);

    }

    public static ChannelGroup getDataChannelGroup() {
        return dataChannelGroup;
    }

    public static void setDataChannelGroup(ChannelGroup dataChannelGroup) {
        SessionManagement.dataChannelGroup = dataChannelGroup;
    }

    public static ChannelGroup getAtcChannelGroup() {
        return atcChannelGroup;
    }

    public static void setAtcChannelGroup(ChannelGroup atcChannelGroup) {
        SessionManagement.atcChannelGroup = atcChannelGroup;
    }

    public static Map<String, XATCUserSession> getUserSessionList() {
        return userSessionList;
    }

    public static void setUserSessionList(Map<String, XATCUserSession> userSessionList) {
        SessionManagement.userSessionList = userSessionList;
    }

    public static Map<String, XATCUserSession> getAtcSessionList() {
        return atcSessionList;
    }

    public static void setAtcSessionList(Map<String, XATCUserSession> atcSessionList) {
        SessionManagement.atcSessionList = atcSessionList;
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
  
   

}
