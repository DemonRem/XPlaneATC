/**
 * This file is part of the XPlane Home Server License.
 * You may edit and use this file as you like. But there is no warranty at all and no license condition.
 * XPlane Home Server tries to build up a simple network for flying in small local networks or via internet.
 * Have fun!
 *
 * @Author Mirko Bubel (mirko_bubel@hotmail.com)
 * @Created 03.07.2016
 */
package de.xatc.controllerclient.gui.main;

import de.mytools.tools.swing.IconPainter;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class StatusPanel extends JPanel {
    
    
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private IconPainter connectedToATCDataServer;
    private IconPainter connectedToATCVoiceServer;
    private JLabel connectedToATCDAtaServerLabel;
    private JLabel connectedToATCVoiceServerLabel;
    
    
    private JPanel connectionStatusPanel;
    private JPanel progressBarPanel;
    private JPanel statusPanel;
    
    
    
    public StatusPanel() {
        
        super();
        initComponents();
        
        
    }
    
    private void initComponents() {
        
        this.setSize(100,300);
        this.setLayout(new VerticalLayout());
        connectionStatusPanel = new JPanel(); 
        connectionStatusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        connectedToATCDAtaServerLabel = new JLabel("ATC Data Server connected");
        connectedToATCVoiceServerLabel = new JLabel("ATC Voice Server connected");
        connectedToATCDataServer = new IconPainter(0, 0, 10, 10, Color.RED);
        connectedToATCVoiceServer = new IconPainter(0, 0, 10, 10, Color.RED);
        
        connectedToATCDAtaServerLabel.setIcon(connectedToATCDataServer);
        connectedToATCVoiceServerLabel.setIcon(connectedToATCVoiceServer);
        
        connectionStatusPanel.add(connectedToATCDAtaServerLabel);
        connectionStatusPanel.add(connectedToATCVoiceServerLabel);
        
 
        progressBarPanel = new JPanel();
        this.progressBar = new JProgressBar();
        progressBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        progressBar.setSize(400,15);
        progressBarPanel.add(progressBar);
        
        
        statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Ready.....");
        statusPanel.add(statusLabel);
        
        this.add(statusPanel);
        this.add(progressBarPanel);
        this.add(connectionStatusPanel);
        
        
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public IconPainter getConnectedToATCDataServer() {
        return connectedToATCDataServer;
    }

    public void setConnectedToATCDataServer(IconPainter connectedToATCDataServer) {
        this.connectedToATCDataServer = connectedToATCDataServer;
    }

    public IconPainter getConnectedToATCVoiceServer() {
        return connectedToATCVoiceServer;
    }

    public void setConnectedToATCVoiceServer(IconPainter connectedToATCVoiceServer) {
        this.connectedToATCVoiceServer = connectedToATCVoiceServer;
    }
    
    
    
    
    
}
