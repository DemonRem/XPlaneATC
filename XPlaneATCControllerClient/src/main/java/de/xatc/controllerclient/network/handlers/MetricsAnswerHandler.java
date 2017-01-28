
package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.networkpackets.atc.servercontrol.ServerMetrics;
import de.xatc.controllerclient.config.XHSConfig;

/**
 *
 * @author Mirko
 */


public class MetricsAnswerHandler {
    
    public static void handleMetricsAnswer(Object msg) {
        
        ServerMetrics m = (ServerMetrics) msg;
        System.out.println("MetricsAnswerHandler: " + m.getMessage());
        
        XHSConfig.getMetricsFrame().getMetricsLabel().setText(m.getMessage());
        XHSConfig.getMetricsFrame().revalidate();
        XHSConfig.getMetricsFrame().repaint();
        
        
        
    }
    
    
    
    
}
