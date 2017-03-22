
package de.xatc.controllerclient.gui.tools;

import de.xatc.commons.beans.sharedgui.ChatFrame;
import de.xatc.controllerclient.config.XHSConfig;

/**
 *
 * @author Mirko
 */


public class ControllerClientGuiTools {
    
    public static void showChatFrame() {
        
        if (XHSConfig.getChatFrame() == null) {
            XHSConfig.setChatFrame(new ChatFrame());
            XHSConfig.getChatFrame().setVisible(true);
        } else if (!XHSConfig.getChatFrame().isVisible()) {
            XHSConfig.getChatFrame().setVisible(true);
            
        }
        
        
    }
    
    
}
