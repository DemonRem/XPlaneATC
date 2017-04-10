/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui;

import de.mytools.tools.swing.IconPainter;
import de.xatc.xplaneadapter.audio.AudioTest;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.HorizontalLayout;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ConnectionStatusPanel extends JPanel implements MouseListener {
    
    private AudioTest audioTest;
    private JPanel listenToXPlanePanel;
    private JPanel connectedToXPlaneATCServerDataPanel;
    private JPanel connectedToXPlaneATCServerVoicePanel;
    private JLabel listenToXPlaneLabel;
    private JLabel connectedToXPlaneATCServerDataLabel;
    private JLabel connectedToXPlaneATCServerVoiceLabel;
    
    private IconPainter listenToXPlaneIcon;
    private IconPainter connectedToXPlaneATCServerDataIcon;
    private IconPainter connectedToXPlaneATCServerVoiceIcon;
    private JButton recordButton;
    
    public ConnectionStatusPanel() {
        super();
        initComponents();
    }
    
    private void initComponents() {
        
        
        this.setLayout(new HorizontalLayout());
        this.listenToXPlanePanel = new JPanel();
        this.connectedToXPlaneATCServerDataPanel = new JPanel();
        this.connectedToXPlaneATCServerVoicePanel = new JPanel();
        
        this.listenToXPlaneLabel = new JLabel("Listen to XPlane");
        this.listenToXPlaneIcon = new IconPainter(0,0,20,20,Color.RED);
        this.listenToXPlaneLabel.setIcon(listenToXPlaneIcon);
        
        
        this.connectedToXPlaneATCServerDataLabel = new JLabel("Data");
        this.connectedToXPlaneATCServerDataIcon = new IconPainter(0,0,20,20,Color.RED);
        this.connectedToXPlaneATCServerDataLabel.setIcon(connectedToXPlaneATCServerDataIcon);
        
        this.connectedToXPlaneATCServerVoiceLabel = new JLabel("Voice");
        this.connectedToXPlaneATCServerVoiceIcon = new IconPainter(0,0,20,20,Color.RED);
        this.connectedToXPlaneATCServerVoiceLabel.setIcon(connectedToXPlaneATCServerVoiceIcon);
        
        this.listenToXPlanePanel.add(listenToXPlaneLabel);
        this.connectedToXPlaneATCServerDataPanel.add(connectedToXPlaneATCServerDataLabel);
        this.connectedToXPlaneATCServerVoicePanel.add(connectedToXPlaneATCServerVoiceLabel);
        
        
        this.add(listenToXPlanePanel);
        
        recordButton = new JButton("record");
        recordButton.addMouseListener(this);
        
        this.add(recordButton);
        this.add(connectedToXPlaneATCServerDataPanel);
        this.add(connectedToXPlaneATCServerVoicePanel);
        
        
        
    }

    public JPanel getListenToXPlanePanel() {
        return listenToXPlanePanel;
    }

    public void setListenToXPlanePanel(JPanel listenToXPlanePanel) {
        this.listenToXPlanePanel = listenToXPlanePanel;
    }

    public JPanel getConnectedToXPlaneATCServerDataPanel() {
        return connectedToXPlaneATCServerDataPanel;
    }

    public void setConnectedToXPlaneATCServerDataPanel(JPanel connectedToXPlaneATCServerDataPanel) {
        this.connectedToXPlaneATCServerDataPanel = connectedToXPlaneATCServerDataPanel;
    }

    public JPanel getConnectedToXPlaneATCServerVoicePanel() {
        return connectedToXPlaneATCServerVoicePanel;
    }

    public void setConnectedToXPlaneATCServerVoicePanel(JPanel connectedToXPlaneATCServerVoicePanel) {
        this.connectedToXPlaneATCServerVoicePanel = connectedToXPlaneATCServerVoicePanel;
    }

    public JLabel getListenToXPlaneLabel() {
        return listenToXPlaneLabel;
    }

    public void setListenToXPlaneLabel(JLabel listenToXPlaneLabel) {
        this.listenToXPlaneLabel = listenToXPlaneLabel;
    }

    public JLabel getConnectedToXPlaneATCServerDataLabel() {
        return connectedToXPlaneATCServerDataLabel;
    }

    public void setConnectedToXPlaneATCServerDataLabel(JLabel connectedToXPlaneATCServerDataLabel) {
        this.connectedToXPlaneATCServerDataLabel = connectedToXPlaneATCServerDataLabel;
    }

    public JLabel getConnectedToXPlaneATCServerVoiceLabel() {
        return connectedToXPlaneATCServerVoiceLabel;
    }

    public void setConnectedToXPlaneATCServerVoiceLabel(JLabel connectedToXPlaneATCServerVoiceLabel) {
        this.connectedToXPlaneATCServerVoiceLabel = connectedToXPlaneATCServerVoiceLabel;
    }

    public IconPainter getListenToXPlaneIcon() {
        return listenToXPlaneIcon;
    }

    public void setListenToXPlaneIcon(IconPainter listenToXPlaneIcon) {
        this.listenToXPlaneIcon = listenToXPlaneIcon;
    }

    public IconPainter getConnectedToXPlaneATCServerDataIcon() {
        return connectedToXPlaneATCServerDataIcon;
    }

    public void setConnectedToXPlaneATCServerDataIcon(IconPainter connectedToXPlaneATCServerDataIcon) {
        this.connectedToXPlaneATCServerDataIcon = connectedToXPlaneATCServerDataIcon;
    }

    public IconPainter getConnectedToXPlaneATCServerVoiceIcon() {
        return connectedToXPlaneATCServerVoiceIcon;
    }

    public void setConnectedToXPlaneATCServerVoiceIcon(IconPainter connectedToXPlaneATCServerVoiceIcon) {
        this.connectedToXPlaneATCServerVoiceIcon = connectedToXPlaneATCServerVoiceIcon;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed");
        try {
        audioTest = new AudioTest();
        audioTest.setRecording(true);
        audioTest.record();
        }
        catch (LineUnavailableException ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        audioTest.setRecording(false);
        try {
            audioTest.playAudio(audioTest.getAudioData());
        } catch (LineUnavailableException ex) {
            Logger.getLogger(ConnectionStatusPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited");
    }
 
    
    
    
}
