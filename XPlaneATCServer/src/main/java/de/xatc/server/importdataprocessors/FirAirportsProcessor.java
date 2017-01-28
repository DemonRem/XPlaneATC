/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.importdataprocessors;

import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.navigationtools.FirNavigationalTools;
import de.xatc.server.db.DBSessionManager;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class FirAirportsProcessor extends Thread {

    @Override
    public void run() {

        Session s = DBSessionManager.getSession();

        List<Fir> firList = s.createCriteria(Fir.class).list();
        List<PlainAirport> airportList = s.createCriteria(PlainAirport.class).list();

        for (Fir fir : firList) {

            System.out.println(fir.getFirName() + " " + fir.getFirNameIcao() + " " + fir.getPoligonList().size());

            ArrayList<PlainAirport> airportListInFir = FirNavigationalTools.findAirportsInFir(airportList,fir.getPoligonList());

            System.out.println("Airports in FIR: " + airportListInFir.size());
            fir.setIncludedAirports(airportListInFir);
            s.saveOrUpdate(fir);
            s.flush();
            s.clear();
        }
        DBSessionManager.closeSession(s);

    }

    
    
   

    public static void main(String[] arg) {

        new FirAirportsProcessor().start();

    }

   

}
