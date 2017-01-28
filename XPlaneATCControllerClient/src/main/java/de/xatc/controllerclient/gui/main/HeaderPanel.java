/*
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2015
 * Have fun!
 *
 */
package de.xatc.controllerclient.gui.main;

import de.xatc.controllerclient.config.XHSConfig;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * this is the header panel with logo and Appname
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class HeaderPanel extends JPanel {

    /**
     * constructor
     */
    public HeaderPanel() {

        super();
        initComponents();
    }

    /**
     * init all guicomponents
     */
    private void initComponents() {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        JLabel label = new JLabel();

        File logoFile = new File("Resources/plugins/PythonScripts/followMeCarScripts/logo.jpg");
        if (!logoFile.exists()) {

            logoFile = new File("src/main/resources/pythonfiles/followMeCarScripts/logo.jpg");

        }

        label.setIcon(new ImageIcon(logoFile.getAbsolutePath()));

        this.add(label, BorderLayout.WEST);

        JLabel appNameLabel = new JLabel(XHSConfig.getAPPNAME());
        JLabel versionLabel = new JLabel("Version: " + XHSConfig.getVERSION());

        this.add(appNameLabel, BorderLayout.EAST);
        this.add(versionLabel, BorderLayout.EAST);

    }

}
