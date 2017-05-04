/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.automation;

import de.xatc.xplaneadapter.Start;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.config.ConfigBean;
import de.xatc.xplaneadapter.nettyclient.XPlaneUDPListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Mirko
 */
public class Login {

    public static void main(String[] arg) {

        
        AutomationTools tools = new AutomationTools();
        tools.startUp();
        tools.login();
        
        
       

        

    }

}
