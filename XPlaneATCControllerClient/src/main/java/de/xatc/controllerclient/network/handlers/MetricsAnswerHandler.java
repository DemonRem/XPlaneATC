
package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.networkpackets.atc.servercontrol.ServerMetrics;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.datastructures.DataStructureSilo;
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko
 */


public class MetricsAnswerHandler {
    
    private static final Logger LOG = Logger.getLogger(MetricsAnswerHandler.class.getName());
    
    public static void handleMetricsAnswer(Object msg) {
        
        ServerMetrics m = (ServerMetrics) msg;
        LOG.debug("MetricsAnswerHandler: " + m.getMessage());
        
        StringBuilder b = new StringBuilder();
        b.append("<HTML><BODY>\n");
        
        b.append(m.getMessage());
        
        b.append("<hr/>");
        b.append("LocalMetrix: <br/>");
        
        b.append("PilotStrucutres: ").append(DataStructureSilo.getLocalPilotStructure().size()).append("<br>");
        b.append("ATCStrucutres: ").append(DataStructureSilo.getLocalATCStructures().size()).append("<br>");
        
        b.append("</BODY></HTML>");
        
        XHSConfig.getMetricsFrame().getMetricsLabel().setText(b.toString());
        XHSConfig.getMetricsFrame().revalidate();
        XHSConfig.getMetricsFrame().repaint();
        
        
        
    }
    
    
    
    
}
