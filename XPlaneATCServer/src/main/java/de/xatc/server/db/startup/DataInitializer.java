/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.db.startup;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.server.db.DBSessionManager;
import de.xatc.server.importdataprocessors.AirportProcessor;
import de.xatc.server.importdataprocessors.CountryProcessor;
import de.xatc.server.importdataprocessors.FirAirportsProcessor;
import de.xatc.server.importdataprocessors.FirPoligonProcessor;
import de.xatc.server.importdataprocessors.FirProcessor;
import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataInitializer {
    
    private static final Logger LOG = Logger.getLogger(DataInitializer.class.getName());
    
    public void initDataInDataBase() {
        
        LOG.debug("Running Database Initializer.....");
        
        
        LOG.debug("Open Session");
        Session session = DBSessionManager.getSession();
        
        LOG.debug("new reg User");
        RegisteredUser user = new RegisteredUser();
        user.setUserRole(UserRole.ADMINISTRATOR);
        user.setRegisteredUserName("micko");
        user.setPassword("doof");
        
        LOG.debug("Save User");
        session.save(user);
        
        LOG.debug("Close Session");
        DBSessionManager.closeSession(session);
        
        
        AirportProcessor airportProcessor = new AirportProcessor();
        airportProcessor.run();
        
        CountryProcessor countryProcessor = new CountryProcessor();
        countryProcessor.run();
        
        FirProcessor firProcessor = new FirProcessor();
        firProcessor.run();
        
        FirPoligonProcessor firPoligonProcessor = new FirPoligonProcessor();
        firPoligonProcessor.run();
        
        FirAirportsProcessor firAirportsProcessor = new FirAirportsProcessor();
        firAirportsProcessor.run();
                
    }
    
    
}
