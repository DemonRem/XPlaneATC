
package de.xatc.controllerclient.automation;

import de.xatc.controllerclient.config.ConfigBean;
import de.xatc.controllerclient.config.XHSConfig;
import java.io.IOException;

/**
 *
 * @author Mirko
 */


public class ConfigureProperties {
    
    public static void main(String[] arg) throws IOException {
        
        XHSConfig.setConfigBean(new ConfigBean());
        XHSConfig.getConfigBean().setFolder_xplaneFolder("D:\\games\\X-Plane 10");
        XHSConfig.getConfigBean().setAtcServerIP("192.168.56.1");
        XHSConfig.getConfigBean().setAtcServerPort("9092");
        XHSConfig.getConfigBean().setAtcVoiceIP("192.168.56.1");
        XHSConfig.getConfigBean().setAtcVoicePort("9091");
        XHSConfig.savePropsFile();
        
        
        
        
        
        
        
    }
    
    
}
