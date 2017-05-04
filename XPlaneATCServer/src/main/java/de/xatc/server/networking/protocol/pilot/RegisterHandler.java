/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.pilot;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.server.db.DBSessionManager;
import io.netty.channel.Channel;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class RegisterHandler {
    
    public static synchronized void doRegistering(Channel n, Object msg) {
        
        
        System.out.println("do Registering Handler");
        Session session = DBSessionManager.getSession();
        RegisterPacket p = (RegisterPacket) msg;
        RegisterPacket returnPacket = new RegisterPacket();
        List<RegisteredUser> userList = session.createCriteria(RegisteredUser.class).add(Restrictions.eq("registeredUserName", p.getUserName())).list();
        if (userList.size() > 0) {
            
            returnPacket.setSuccess(false);
            returnPacket.setServerMessage("A user with this name does allready exist! Please choose a different one!");
            n.writeAndFlush(returnPacket);
            
        }
        else {
            
            RegisteredUser newUser = new RegisteredUser();
            newUser.setRegisteredUserName(p.getUserName());
            newUser.setPassword(p.getPassword());
            newUser.setUserRole(UserRole.REGISTEREDUSER);
            
            session.save(newUser);
            DBSessionManager.closeSession(session);
            returnPacket.setSuccess(true);
            System.out.println("Registering was successfully performed!");
            n.writeAndFlush(returnPacket);
            
            
        }
        
        
    }
    
    
}
