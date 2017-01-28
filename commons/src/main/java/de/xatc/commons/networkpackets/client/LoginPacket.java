/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.client;

import de.xatc.commons.networkpackets.parent.NetworkPacket;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class LoginPacket extends NetworkPacket {
    
    
    private String userName;
    private String password;
    private boolean successful;
    private String serverMessage;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    
    
   
    
    
    
}
