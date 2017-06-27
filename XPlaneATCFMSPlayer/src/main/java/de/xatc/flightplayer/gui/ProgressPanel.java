/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.gui;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author c047
 */
public class ProgressPanel extends JPanel {
    
    public ProgressPanel() {
        super();
        initComponents();
    }
    
    private void initComponents() {
        
        
        this.setLayout(new VerticalLayout());
        
    }
    
    public JProgressBar addProgressBar(String title, int min, int max, int current) {
        
        JProgressBar bar = new JProgressBar();
        bar.setStringPainted(true);
        bar.setMinimum(min);
        bar.setMaximum(max);
        bar.setString(title);
        bar.setString(title);
        bar.setValue(current);
        this.add(bar);
        return bar;
        
    }
    
}
