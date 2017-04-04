/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.automation;

/**
 *
 * @author C047
 */
public class SendFlightPlan {


    public static void main(String[] arg) {
        
        AutomationTools tools = new AutomationTools();
        tools.startUp();
        tools.login();
        tools.createAndSendFlightPlan();
        
        
        
    }
    
}
