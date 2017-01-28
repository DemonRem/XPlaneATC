/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.importdataprocessors;

import de.xatc.commons.db.sharedentities.atcdata.Country;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.db.DBSessionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko
 */
public class CountryProcessor extends Thread {

   
    

    @Override
    public void run() {

        File countryCodes = new File(ServerConfig.getCountryCodesFile());
        if (!countryCodes.exists()) {
            System.out.println("CountryCodes File does not exist!");
            return;
        }

        Session s = DBSessionManager.getSession();
        
        Transaction tx = s.beginTransaction();
        Query q = s.createQuery("delete from Country");
        q.executeUpdate();
        tx.commit();
        DBSessionManager.closeSession(s);
        
        s = DBSessionManager.getSession();
        
        try (BufferedReader br = new BufferedReader(new FileReader(countryCodes))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                Country c = new Country();
                String[] splitted = line.split(":");
                if (splitted.length == 2) {
                    
                    System.out.println(line + "... imported");
                    c.setCountryCode(splitted[0]);
                    c.setCountryName(splitted[1]);
                    s.saveOrUpdate(c);
                    s.flush();
                    s.clear();
                    
                }
                
                
            }
            DBSessionManager.closeSession(s);
            br.close();
            System.out.println("Returning....");
           
        }
        catch(IOException e) {
            e.printStackTrace(System.err);
        }

    }
    
    
    public static void main(String[] arg) {
        
        new CountryProcessor().start();
        
        
    }

}
