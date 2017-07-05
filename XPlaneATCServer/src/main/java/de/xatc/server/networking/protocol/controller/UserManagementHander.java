/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.protocol.controller;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.atc.usermgt.NewUser;
import de.xatc.commons.networkpackets.atc.usermgt.UpdateUser;
import de.xatc.server.db.DBSessionManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mirko
 */
public class UserManagementHander {

    private static final Logger LOG = Logger.getLogger(UserManagementHander.class.getName());

    public static void deleteUser(String username) {

        Session session = DBSessionManager.getSession();
        RegisteredUser u = (RegisteredUser) session.createCriteria(RegisteredUser.class).add(Restrictions.eq("registeredUserName", username)).list().get(0);
        if (u == null) {
            return;
        }
        session.delete(u);
        DBSessionManager.closeSession(session);

    }

    public static void newUser(NewUser newUser) {

        Session session = DBSessionManager.getSession();
        RegisteredUser u = (RegisteredUser) session.createCriteria(RegisteredUser.class).add(Restrictions.eq("registeredUserName", newUser.getUser().getRegisteredUserName())).list().get(0);
        if (u != null) {
            LOG.warn("User already present...");
            return;
        }
        session.save(newUser.getUser());
        DBSessionManager.closeSession(session);

    }

    public static void updateUser(UpdateUser updateUser) {

        Session session = DBSessionManager.getSession();
        RegisteredUser u = (RegisteredUser) session.createCriteria(RegisteredUser.class).add(Restrictions.eq("registeredUserName", updateUser.getUser().getRegisteredUserName())).list().get(0);
        if (u == null) {
            LOG.warn("User to update not found!...");
            return;
        }
        if (updateUser.getUser().getPassword() != null) {

            u.setPassword(updateUser.getUser().getPassword());

        }
        u.setLocked(updateUser.getUser().isLocked());
        u.setUserRole(updateUser.getUser().getUserRole());
        session.saveOrUpdate(u);
        DBSessionManager.closeSession(session);

        //TODO
        //ok, das hier funktioniert noch nicht so richtig!
    }

}
