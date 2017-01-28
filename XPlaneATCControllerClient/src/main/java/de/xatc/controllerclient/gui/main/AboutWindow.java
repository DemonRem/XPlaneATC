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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.VerticalLayout;

/**
 * this paints the about Window
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AboutWindow extends JPanel implements ActionListener {

    /**
     * the close button
     */
    private JButton closeButton;

    /**
     * show me and init Components
     */
    public AboutWindow() {
        super();
        initComponents();
    }

    /**
     * init all gui components
     */
    private void initComponents() {

        JPanel internalPanel = new JPanel();
        this.setLayout(new VerticalLayout());
        internalPanel.setLayout(new VerticalLayout());
        internalPanel.setBorder(BorderFactory.createTitledBorder("About"));
        this.add(internalPanel);
        this.setPreferredSize(new Dimension(400, 400));
      

        JLabel title = new JLabel("<HTML><center><font size=+2><b>" + XHSConfig.getAPPNAME() + " " + XHSConfig.getVERSION() + "</b></font></center></HTML>", SwingConstants.CENTER);
        internalPanel.add(title);

        internalPanel.add(new JSeparator());

        JLabel authorLabel = new JLabel("<HTML><center><b>AUTHOR: " + XHSConfig.getAuthorString() + "</b></center></HTML>", SwingConstants.CENTER);
        internalPanel.add(authorLabel);
        internalPanel.add(new JSeparator());
        JLabel version = new JLabel("<HTML><center><b>Current Version:" + XHSConfig.getVERSION() + "</b></center></HTML>", SwingConstants.CENTER);
        internalPanel.add(version);
        internalPanel.add(new JSeparator());
        JLabel updated = new JLabel("<HTML><center><b>Latest Update:" + XHSConfig.getLastUpdated() + "</b></center></HTML>", SwingConstants.CENTER);
        internalPanel.add(updated);
        internalPanel.add(new JSeparator());

        this.closeButton = new JButton("close");
        this.closeButton.addActionListener(this);
        this.add(closeButton);

        this.setVisible(true);

        XHSConfig.getMainFrame().revalidate();
        XHSConfig.getMainFrame().repaint();

    }

    /**
     * my action Listener for closing the window
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        XHSConfig.getMainFrame().getMainPanel().remove(XHSConfig.getMainFrame().getMainPanel().getAboutPanel());
        XHSConfig.getMainFrame().getMainPanel().setAboutPanel(null);
        XHSConfig.getMainFrame().revalidate();
        XHSConfig.getMainFrame().repaint();

    }

}
