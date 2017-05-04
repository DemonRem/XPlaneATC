/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.mytools.encoding.UUIDCreator;
import de.mytools.tools.dateandtime.SQLDateTimeTools;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.db.entities.XATCUserSession;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ATCLoginHandler {

    public static void handleLogin(Channel n, Object msg) {

        System.out.println("handleLogin Method");
        Session session = DBSessionManager.getSession();

        LoginPacket p = (LoginPacket) msg;

        LoginPacket returnPacket = new LoginPacket();

        System.out.println("searching for user!");
        RegisteredUser u = (RegisteredUser) session.createCriteria(RegisteredUser.class).add(Restrictions.eq("registeredUserName", p.getUserName())).uniqueResult();
        
        
        System.out.println("USER FOUND.");
        System.out.println("USER: " + u.getUserRole());
        
        
        
        if (u == null) {
            System.out.println("User not found.....");
            returnPacket.setSuccessful(false);
            returnPacket.setServerMessage("Could not find User");
            n.writeAndFlush(returnPacket);
            return;
        }
        
        if (u.getUserRole() != UserRole.CONTROLLER && u.getUserRole() != UserRole.ADMINISTRATOR) {
            
            returnPacket.setSuccessful(false);
            returnPacket.setServerMessage("You must be an Controller or an Administrator to log in here!");
            n.writeAndFlush(returnPacket);
            return;
        }

        List<XATCUserSession> sessionList = session.createCriteria(XATCUserSession.class).add(Restrictions.eq("registeredUser", u)).list();
        if (sessionList.size() > 0) {
            Transaction t = session.beginTransaction();
            Query deleteUserSessions = session.createQuery("delete from XATCUserSession where sessionUserName = '" + u.getRegisteredUserName() + "'");
            System.out.println("execute Delete Query");
            deleteUserSessions.executeUpdate();
            System.out.println("commit delete user session");
            t.commit();
        }

        String givenPassword = p.getPassword();
        String savedPassword = u.getPassword();
        if (!givenPassword.equals(savedPassword)) {
            System.out.println("Passwords do not match: " + givenPassword + " vs " + savedPassword);
            returnPacket.setSuccessful(false);
            returnPacket.setServerMessage("Password is not correct!");
            n.writeAndFlush(returnPacket);
            return;
        }

        String sessionID = UUIDCreator.createUUID();
        returnPacket.setSessionID(sessionID);
        returnPacket.setSuccessful(true);

        returnPacket.setChannelID(n.id().asLongText());
        

        XATCUserSession userSession = new XATCUserSession();
      
        userSession.setChannelID(n.id().asLongText());
        userSession.setSessionID(sessionID);
        userSession.setRegisteredUser(u);
        userSession.setStartSession(SQLDateTimeTools.getTimeStampOfNow());

        userSession.setLastAction(SQLDateTimeTools.getTimeStampOfNow());

        session.save(userSession);
        DBSessionManager.closeSession(session);
        
        
        SessionManagement.addATCSession(userSession);
        SessionManagement.addATCChannel(n);
        
        System.out.println("client LOGGED IN SUCCESSFULLY with long ChannelID: " + n.id().asLongText());
        System.out.println("client LOGGED IN SUCCESSFULLY with short CHANNELID:  " + n.id().asShortText());
        n.writeAndFlush(returnPacket);
        //TODO here noch in die Queue fuer die Datenbank, um die Session in der DB zu halten!

    }

}
