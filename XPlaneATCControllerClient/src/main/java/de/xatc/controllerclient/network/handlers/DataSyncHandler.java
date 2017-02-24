
package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.db.sharedentities.atcdata.Country;
import de.xatc.commons.db.sharedentities.atcdata.Fir;
import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.networkpackets.atc.datasync.DataSyncPacket;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;
import org.hibernate.Session;

/**
 *
 * @author Mirko
 */


public class DataSyncHandler {
    
    public static void handleIncomingDataSyncPacket(DataSyncPacket p) {
        
        if (p.getDataSetToSync().equals("fir")) {
            
            if (p.isFinished()) {
                
                if (XHSConfig.getServerSyncFrame() != null) {
                    XHSConfig.getServerSyncFrame().getFirProgress().setValue(0);
                    XHSConfig.getServerSyncFrame().getFirProgress().setMinimum(0);
                    XHSConfig.getServerSyncFrame().revalidate();
                    return;
                }
                
            }
            
            Fir fir = (Fir) p.getTransferObject();
            Session s = DBSessionManager.getSession();
            System.out.println("Saving new FIR: " + fir.getFirNameIcao());
            s.saveOrUpdate(fir);
            DBSessionManager.closeSession(s);
            if (XHSConfig.getServerSyncFrame() != null) {
                    XHSConfig.getServerSyncFrame().getFirProgress().setValue(p.getCurrentDataSet());
                    XHSConfig.getServerSyncFrame().getFirProgress().setMinimum(0);
                    XHSConfig.getServerSyncFrame().getFirProgress().setMaximum(p.getMaxDataSets());
                    XHSConfig.getServerSyncFrame().revalidate();
                    return;
                }
            
        }
        
        else if (p.getDataSetToSync().equals("airport")) {
            
            if (p.isFinished()) {
                
                if (XHSConfig.getServerSyncFrame() != null) {
                    XHSConfig.getServerSyncFrame().getAirportProgress().setValue(0);
                    XHSConfig.getServerSyncFrame().getAirportProgress().setMinimum(0);
                    XHSConfig.getServerSyncFrame().revalidate();
                    return;
                }
                
            }
            
            PlainAirport airport = (PlainAirport) p.getTransferObject();

            Session s = DBSessionManager.getSession();
            System.out.println("Saving new Airport: " + airport.getAirportIcao());
            s.saveOrUpdate(airport);
            DBSessionManager.closeSession(s);
            if (XHSConfig.getServerSyncFrame() != null) {
                    XHSConfig.getServerSyncFrame().getAirportProgress().setValue(p.getCurrentDataSet());
                    XHSConfig.getServerSyncFrame().getAirportProgress().setMinimum(0);
                    XHSConfig.getServerSyncFrame().getAirportProgress().setMaximum(p.getMaxDataSets());
                    XHSConfig.getServerSyncFrame().revalidate();
                    return;
                }
            
        }
        else if (p.getDataSetToSync().equals("country")) {
            
            if (p.isFinished()) {
                
                if (XHSConfig.getServerSyncFrame() != null) {
                    XHSConfig.getServerSyncFrame().getCountryProgress().setValue(0);
                    XHSConfig.getServerSyncFrame().getCountryProgress().setMinimum(0);
                    XHSConfig.getServerSyncFrame().revalidate();
                    return;
                }
                
            }
            
            Country country = (Country) p.getTransferObject();
            Session s = DBSessionManager.getSession();
            System.out.println("Saving new Country: " + country.getCountryCode());
            s.saveOrUpdate(country);
            DBSessionManager.closeSession(s);
            if (XHSConfig.getServerSyncFrame() != null) {
                    XHSConfig.getServerSyncFrame().getCountryProgress().setValue(p.getCurrentDataSet());
                    XHSConfig.getServerSyncFrame().getCountryProgress().setMaximum(p.getMaxDataSets());
                    XHSConfig.getServerSyncFrame().revalidate();
                    return;
                }
            
        }
        
        
        
    }
    
    
}
