/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.mytools.encoding.UUIDCreator;
import de.mytools.tools.dateandtime.SQLDateTimeTools;
import de.xatc.commons.datastructure.atc.ATCStructure;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.commons.db.sharedentities.user.XATCUserSession;
import de.xatc.commons.networkpackets.atc.datasync.DataStructuresResponsePacket;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.sessionmanagment.NetworkBroadcaster;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ATCLoginHandler {
    
    private static final Logger LOG = Logger.getLogger(ATCLoginHandler.class.getName());

    public static void handleLogin(Channel n, Object msg) {

        LOG.debug("handleLogin Method");
        Session session = DBSessionManager.getSession();

        LoginPacket p = (LoginPacket) msg;

        LoginPacket returnPacket = new LoginPacket();

        LOG.debug("searching for user!");
        RegisteredUser u = (RegisteredUser) session.createCriteria(RegisteredUser.class).add(Restrictions.eq("registeredUserName", p.getUserName())).uniqueResult();

        LOG.debug("USER FOUND.");
        LOG.debug("USER: " + u.getUserRole());

        if (u == null) {
            LOG.warn("User not found.....");
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
            LOG.debug("execute Delete Query");
            deleteUserSessions.executeUpdate();
            LOG.debug("commit delete user session");
            t.commit();
        }

        String givenPassword = p.getPassword();
        String savedPassword = u.getPassword();
        if (!givenPassword.equals(savedPassword)) {
            LOG.debug("Passwords do not match: " + givenPassword + " vs " + savedPassword);
            returnPacket.setSuccessful(false);
            returnPacket.setServerMessage("Password is not correct!");
            n.writeAndFlush(returnPacket);
            return;
        }

        ATCStructure atcStructure = new ATCStructure();

        String sessionID = UUIDCreator.createUUID();
        SessionManagement.getAtcChannels().put(sessionID, n);

        returnPacket.setSessionID(sessionID);
        atcStructure.setStructureSessionID(sessionID);
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

        atcStructure.setUserSession(userSession);
        atcStructure.setUserName(u.getRegisteredUserName());
        atcStructure.setActive(true);

        SessionManagement.getAtcDataStructures().put(sessionID, atcStructure);
        LOG.debug("client LOGGED IN SUCCESSFULLY with long ChannelID: " + n.id().asLongText());
        LOG.debug("client LOGGED IN SUCCESSFULLY with short CHANNELID:  " + n.id().asShortText());
        n.writeAndFlush(returnPacket);
        //TODO here noch in die Queue fuer die Datenbank, um die Session in der DB zu halten!

        DataStructuresResponsePacket broadcatAtc = new DataStructuresResponsePacket();
        broadcatAtc.setStructureSsessionID(sessionID);
        broadcatAtc.setAtcStructure(atcStructure);
        NetworkBroadcaster.broadcastAll(broadcatAtc);
        
    }

}
