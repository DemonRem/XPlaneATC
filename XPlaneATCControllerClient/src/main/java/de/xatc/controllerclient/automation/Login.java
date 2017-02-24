package de.xatc.controllerclient.automation;

import de.xatc.controllerclient.Start;
import de.xatc.controllerclient.config.XHSConfig;
import java.awt.event.ActionEvent;
import java.io.IOException;


/**
 *
 * @author Mirko
 */
public class Login {

    public static void main(String[] arg) throws IOException {

        Start.main(new String[]{});

         try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        ActionEvent filePress = new ActionEvent(XHSConfig.getMainFrame().getConnectItem(), ActionEvent.ACTION_PERFORMED, "Connect...");
        XHSConfig.getMainFrame().actionPerformed(filePress);

        XHSConfig.getConnectFrame().getNameField().setText("micko");
        XHSConfig.getConnectFrame().getPasswordField().setText("doof");

        ActionEvent connectPress = new ActionEvent(XHSConfig.getConnectFrame().getConnectButton(), ActionEvent.ACTION_PERFORMED, "connect");
        XHSConfig.getConnectFrame().actionPerformed(connectPress);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

    }

}
