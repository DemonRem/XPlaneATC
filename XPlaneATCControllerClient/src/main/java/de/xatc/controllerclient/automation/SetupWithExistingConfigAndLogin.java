package de.xatc.controllerclient.automation;

import de.xatc.controllerclient.automation.tools.AutomationTools;
import java.io.IOException;

/**
 *
 * @author Mirko
 */
public class SetupWithExistingConfigAndLogin {

    public static void main(String[] arg) throws IOException {

        AutomationTools a = new AutomationTools();
        a.startApp();
        a.login();
        a.setupWithExistingProperties();

        
    }

}
