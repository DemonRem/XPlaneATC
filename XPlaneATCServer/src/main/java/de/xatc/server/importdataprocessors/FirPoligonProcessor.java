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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */
public class FirPoligonProcessor extends Thread {
    
    private static final Logger LOG = Logger.getLogger(FirPoligonProcessor.class.getName());
    private final File firDetail = new File(ServerConfig.getFirDetailFile());
    
    
    @Override
    public void run() {
    
        
         if (!firDetail.exists()) {
            LOG.error("FirDetailList not found.... returning");
            return;
        }
        
        
        Session s = DBSessionManager.getSession();
        
        List<Fir> firList = s.createCriteria(Fir.class).list();
        
        for (Fir fir : firList) {
            
           
            ArrayList<PlainNavPoint> poligonPoints = this.getFirPoligon(fir.getId());
            
            LOG.trace("FOUND POLIGONPOINTS: " + poligonPoints.size());
            fir.setPoligonList(poligonPoints);
            
            LOG.trace(fir.toString());
            s.saveOrUpdate(fir);
            s.flush();
            s.clear();
            
        }
        
        DBSessionManager.closeSession(s);
        
        
    }
    
    private void controlPoligon() {
        
        Session s = DBSessionManager.getSession();
        
        List<Fir> firList = s.createCriteria(Fir.class).list();
        
        for (Fir fir : firList) {
            
             LOG.trace(fir.getFirName() + " " + fir.getFirNameIcao() + " " + fir.getPoligonList().size());
            
        }
        DBSessionManager.closeSession(s);
    }
    
    
    
     private ArrayList<PlainNavPoint> getFirPoligon(int id) {

        LOG.trace("Getting FIR Poligon: " + id);
        ArrayList<PlainNavPoint> returnList = new ArrayList<>();

        boolean sectorStarted = false;

        try {

            BufferedReader br = new BufferedReader(new FileReader(firDetail));
            String line;
            while ((line = br.readLine()) != null) {

                if (StringUtils.isEmpty(line)) {
                    LOG.debug("Line Empty! Continueing...");
                    continue;
                }
              
                if (line.equals("DISPLAY_LIST_" + id)) {
                    LOG.trace("ID Found");
                    if (!sectorStarted) {
                        LOG.trace("Sector started!");
                        sectorStarted = true;
                        continue;
                    }

                }
                if (sectorStarted) {

                    if (line.matches("DISPLAY_LIST_.*")) {
                        return returnList;

                    }
                    String[] splitted = line.split(":");
                    if (splitted.length != 2) {
                        continue;
                    }
                    PlainNavPoint p = new PlainNavPoint();
                    p.setLat(Double.parseDouble(splitted[0]));
                    p.setLon(Double.parseDouble(splitted[1]));
                    LOG.trace("Adding FIR Poligon navpoint");

                    returnList.add(p);

                }

            }
            LOG.trace("Closing FileReader");
            br.close();
        } catch (IOException ex) {
            LOG.error(ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
        }

        return returnList;

    }
     
     
     public static void main(String[] arg) {
         
         
         new FirPoligonProcessor().start();
         
         
     }
    
    
}
