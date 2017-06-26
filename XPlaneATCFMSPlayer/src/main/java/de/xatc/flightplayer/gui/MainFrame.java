/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author c047
 */
public class MainFrame extends JFrame implements WindowListener {
    
    private PlayerPanel playerPanel;
    
    
    public MainFrame() {
        
        super();
        initComponents();
    }
    private void initComponents() {
        
        
        this.setSize(new Dimension(500,500));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        
        playerPanel = new PlayerPanel();
        this.add(playerPanel);
        
        this.setVisible(true);
        
        
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
       //nothing to do here
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
       //nothing to do here
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //nothing to do here
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //nothing to do here
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //nothing to do here
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //nothing to do here
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }
    
    
    
}
