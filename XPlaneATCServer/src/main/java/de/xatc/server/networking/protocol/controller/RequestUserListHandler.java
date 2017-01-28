/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.atc.usermgt.UserListResponse;
import de.xatc.server.db.DBSessionManager;
import io.netty.channel.Channel;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author C047
 */
public class RequestUserListHandler {

    public static void handleGetUserListRequest(Channel n) {
        
        Session session = DBSessionManager.getSession();
        
        List<RegisteredUser> l = session.createCriteria(RegisteredUser.class).list();
        
        UserListResponse u = new UserListResponse();
        u.setUserList(l);
        n.writeAndFlush(u);
        
 
    }



    
}
