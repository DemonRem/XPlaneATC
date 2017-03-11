
package de.xatc.controllerclient.automation;

import de.xatc.controllerclient.automation.tools.AutomationTools;
import de.xatc.controllerclient.config.ConfigBean;
import de.xatc.controllerclient.config.XHSConfig;
import java.io.IOException;

/**
 *
 * @author Mirko
 */


public class ConfigureProperties {
    
    public static void main(String[] arg) throws IOException {
        
        AutomationTools a = new AutomationTools();
        a.configureProperties();
        
    }
    
    
}
