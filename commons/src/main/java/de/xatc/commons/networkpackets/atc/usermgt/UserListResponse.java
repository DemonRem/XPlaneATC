/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.networkpackets.atc.usermgt;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author C047
 */
public class UserListResponse extends NetworkPacket {
    
    private List<RegisteredUser> userList = new ArrayList<>();

    public List<RegisteredUser> getUserList() {
        return userList;
    }

    public void setUserList(List<RegisteredUser> userList) {
        this.userList = userList;
    }


    
    
    
    
    
}
