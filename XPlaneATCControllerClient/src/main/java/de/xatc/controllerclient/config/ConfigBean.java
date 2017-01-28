/**
 * This file is part of the XPlane Home Server License.
 * You may edit and use this file as you like. But there is no warranty at all and no license condition.
 * XPlane Home Server tries to build up a simple network for flying in small local networks or via internet.
 * Have fun!
 *
 * @Author Mirko Bubel (mirko_bubel@hotmail.com)
 * @Created 02.06.2016
 */
package de.xatc.controllerclient.config;

/**
 * this bean represents the application properties configuration
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ConfigBean {

    /**
     * xplane main folder
     */
    private String folder_xplaneFolder;

    private String atcServerIP;
    private String atcServerPort;
    private String atcVoiceIP;
    private String atcVoicePort;
    
    
  
    /**
     *
     * @return
     */
    public String getFolder_xplaneFolder() {
        return folder_xplaneFolder;
    }

    /**
     *
     * @param folder_explaneFolder
     */
    public void setFolder_xplaneFolder(String folder_explaneFolder) {
        this.folder_xplaneFolder = folder_explaneFolder;
    }

    public String getAtcServerIP() {
        return atcServerIP;
    }

    public void setAtcServerIP(String atcServerIP) {
        this.atcServerIP = atcServerIP;
    }

    public String getAtcServerPort() {
        return atcServerPort;
    }

    public void setAtcServerPort(String atcServerPort) {
        this.atcServerPort = atcServerPort;
    }

    public String getAtcVoiceIP() {
        return atcVoiceIP;
    }

    public void setAtcVoiceIP(String atcVoiceIP) {
        this.atcVoiceIP = atcVoiceIP;
    }

    public String getAtcVoicePort() {
        return atcVoicePort;
    }

    public void setAtcVoicePort(String atcVoicePort) {
        this.atcVoicePort = atcVoicePort;
    }

    
    
    
   
}
