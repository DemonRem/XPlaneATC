/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.config;

import de.xatc.flightplayer.gui.MainFrame;
import de.xatc.flightplayer.playerthread.PlayerControlThread;
import de.xatc.xplaneadapter.tools.DebugMessageLevel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class PlayerConfig {

    private final static String appName = "XPlane ATC Flight Player";
    private final static String version = "0.1-alpha";


    private static List<PlayerControlThread> threadList = new ArrayList<>();

    private static final boolean doDebug = true;
    private static DebugMessageLevel debugLevel = DebugMessageLevel.INFO;
   
    private static boolean connectedToATCServer = false;
    private static boolean connectedToLocalXPlane = false;
    private static boolean listeningToXPlane = false;


    private static final String recordingDirectory = "recordings";
    
    private static final String usernamePrefix = "doof";

    
    private static String currentSessionID;
    private static String currentChannelID;

    private static MainFrame mainFrame;
    

    public static String getAppName() {
        return appName;
    }

    public static String getVersion() {
        return version;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        PlayerConfig.mainFrame = mainFrame;
    }

  

    public static DebugMessageLevel getDebugLevel() {
        return debugLevel;
    }

    public static void setDebugLevel(DebugMessageLevel debugLevel) {
        PlayerConfig.debugLevel = debugLevel;
    }

    
    
    
    /**
     *
     * @param s
     */
    public static void debugMessage(String s, DebugMessageLevel level) {

        if (!doDebug) {
            return;
        }

        if (level.getLevel() <= PlayerConfig.getDebugLevel().getLevel()) {

            System.out.println(s);

        }

    }

  
    public static boolean isConnectedToATCServer() {
        return connectedToATCServer;
    }

    public static void setConnectedToATCServer(boolean connectedToATCServer) {
        PlayerConfig.connectedToATCServer = connectedToATCServer;
    }

    public static boolean isConnectedToLocalXPlane() {
        return connectedToLocalXPlane;
    }

    public static void setConnectedToLocalXPlane(boolean connectedToLocalXPlane) {
        PlayerConfig.connectedToLocalXPlane = connectedToLocalXPlane;
    }

    public static boolean isListeningToXPlane() {
        return listeningToXPlane;
    }

    public static void setListeningToXPlane(boolean listeningToXPlane) {
        PlayerConfig.listeningToXPlane = listeningToXPlane;
    }

  
   
  
    public static String getCurrentSessionID() {
        return currentSessionID;
    }

    public static void setCurrentSessionID(String currentSessionID) {
        PlayerConfig.currentSessionID = currentSessionID;
    }

    public static String getCurrentChannelID() {
        return currentChannelID;
    }

    public static void setCurrentChannelID(String currentChannelID) {
        PlayerConfig.currentChannelID = currentChannelID;
    }

  
    public static boolean isDoDebug() {
        return doDebug;
    }

    public static String getRecordingDirectory() {
        return recordingDirectory;
    }   

    public static String getUsernamePrefix() {
        return usernamePrefix;
    }

    public static List<PlayerControlThread> getThreadList() {
        return threadList;
    }

    public static void setThreadList(List<PlayerControlThread> threadList) {
        PlayerConfig.threadList = threadList;
    }
    
    
}
