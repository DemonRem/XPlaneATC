/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui.tools;

import de.xatc.commons.beans.sharedgui.ChatFrame;
import de.xatc.xplaneadapter.config.AdapterConfig;

/**
 *
 * @author Mirko
 */
public class XPlaneAdapterGuiTools {
    
    public static void showChatFrame() {
        
        if (AdapterConfig.getChatFrame() == null) {
            AdapterConfig.setChatFrame(new ChatFrame());
            AdapterConfig.getChatFrame().setVisible(true);
        } else if (!AdapterConfig.getChatFrame().isVisible()) {
            AdapterConfig.getChatFrame().setVisible(true);
            
        }

    }

}
