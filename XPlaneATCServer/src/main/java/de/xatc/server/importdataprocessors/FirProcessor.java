/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.importdataprocessors;

import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.atcdata.PlainNavPoint;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.db.DBSessionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mirko
 */
public class FirProcessor extends Thread {

    private final File firList = new File(ServerConfig.getFirListFile());
    

    @Override
    public void run() {

        if (!firList.exists()) {
            System.out.println("Firlist not found.... returning");
            return;
        }
       

        Session s = DBSessionManager.getSession();

        Transaction tx = s.beginTransaction();
        Query q = s.createQuery("delete from Fir");
        q.executeUpdate();
        tx.commit();

        DBSessionManager.closeSession(s);

        s = DBSessionManager.getSession();
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(firList))) {

            String line;
            while ((line = br.readLine()) != null) {

                boolean continueDueToErrors = false;
                lineNumber++;

                if (StringUtils.isEmpty(line)) {
                    System.out.println("Line is empty.... continue");
                    continue;
                }
                Fir fir = new Fir();
                String[] splitted = line.split(":");

                for (String st : splitted) {
                    if (StringUtils.isEmpty(st)) {
                        System.out.println(line + " Values in Line are empty.... continue");
                        continueDueToErrors = true;
                        break;
                    }
                }
                if (continueDueToErrors) {
                    continue;
                }

                if (splitted.length == 6) {

                    fir.setCountryCode(splitted[2]);
                    fir.setFirName(splitted[1]);
                    fir.setFirNameIcao(splitted[0]);
                    PlainNavPoint position = new PlainNavPoint();
                    position.setLat(Double.parseDouble(splitted[3]));
                    position.setLon(Double.parseDouble(splitted[4]));
                    fir.setPosition(position);
                    
                    
                    fir.setId(Integer.parseInt(splitted[5]));

                    
                    s.saveOrUpdate(fir);
                    s.flush();
                    s.clear();
                    System.out.println(line + "... imported");

                }

            }
            DBSessionManager.closeSession(s);
            br.close();
            System.out.println("Returning....");

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

    }

    

   

    

    public static void main(String[] arg) {

       new FirProcessor().start();

//        Session s = DBSessionManager.getSession();
//        List<Fir> firList = s.createCriteria(Fir.class).list();
//        for (Fir fir : firList) {
//            
//            System.out.println(fir.getFirNameIcao());
//            System.out.println(fir.getIncludedAirports());
//            
//            
//            
//        }
//           
        
    }

}
