/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.db.sharedentities.user;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "registeredUsers")
public class RegisteredUser implements Serializable {
    
        /**
     * the airport icao
     */
    @Id    
    @Index(name = "registeredUserName")
    private String registeredUserName;
    
    private String sourceIP;
    
    private String sourcePort;
    
    private UserRole userRole;
    
    private String password;
    
    private boolean locked = false;

    public String getRegisteredUserName() {
        return registeredUserName;
    }

    public void setRegisteredUserName(String registeredUserName) {
        this.registeredUserName = registeredUserName;
    }

   
    

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    
    
    
    
    
    
    
}
