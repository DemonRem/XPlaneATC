 
package de.xatc.controllerclient.network.handlers;

import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.networkpackets.atc.usermgt.UserListResponse;
import de.xatc.controllerclient.config.XHSConfig;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;



public class UserListResponseHandler {
    
    public static void handleUserListResonse(UserListResponse u) {
        
      
        if (XHSConfig.getUserControlFrame() == null) {
            return;
        }
        DefaultTableModel m = new DefaultTableModel();
       
        m.addColumn("Username");
        m.addColumn("Role");
        m.addColumn("SourceIP");
        m.addColumn("locked");
        
        
 
        for (RegisteredUser user : u.getUserList()) {
            
            Vector v = new Vector();
            
            v.add(user.getRegisteredUserName());
            v.add(user.getUserRole().toString());
            System.out.println("filling table with vector IP" + user.getSourceIP());
            v.add(user.getSourceIP());
            v.add(user.isLocked());
          
            
            
            m.addRow(v);
           
        }
        
        XHSConfig.getUserControlFrame().getjTable2().setModel(m);
        XHSConfig.getUserControlFrame().revalidate();
        XHSConfig.getUserControlFrame().repaint();
        
        
        
    }
    
    
    
}
