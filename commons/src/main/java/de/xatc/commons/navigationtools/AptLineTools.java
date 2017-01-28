/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.navigationtools;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Mirko
 */
public class AptLineTools {
    
    public static boolean lineContainsNumberOfElements(String[] testString, int expectedCount) {
        
        for (String string : testString) {
            
            if (StringUtils.isEmpty(string)) {
                return false;
            }
            
        }
        
        return true;
        
    }
    
    
}
