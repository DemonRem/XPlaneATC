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
        region = "users")
public enum UserRole implements Serializable {
    
    
    
    REGISTEREDUSER("registeredUser"),
    ADMINISTRATOR("administrator"),
    CONTROLLER("controller"),
    VISITOR("visitor");
    
    @Id
    @Index(name = "userRole")
    private String userRole;
    
    private UserRole(String role) {
        this.userRole = role;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    
    
}
