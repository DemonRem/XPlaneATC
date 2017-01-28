
package de.xatc.controllerclient.gui.metrics;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.atc.servercontrol.RequestServerMetrics;
import de.xatc.controllerclient.config.XHSConfig;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author Mirko
 */


public class MetricsFrame extends JFrame implements WindowListener, ActionListener {
    
    
    
    private JLabel metricsLabel;
    private JButton refreshButton;
    
    public MetricsFrame() {
        super("Metrics Frame");
        initComponents();
    }
    
    private void initComponents() {
        
        this.setSize(300, 300);
        this.setLayout(new VerticalLayout());
        this.addWindowListener(this);
        this.setAlwaysOnTop(true);
        
        metricsLabel = new JLabel("");
        this.refreshButton = new JButton("refresh");
        refreshButton.addActionListener(this);
        
        this.add(metricsLabel);
        this.add(refreshButton);
        this.setVisible(true);
        
        
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        RequestServerMetrics m = new RequestServerMetrics();
        if (XHSConfig.getDataClient() == null) {
            SwingTools.alertWindow("Not connected", this);
            return;
        }
        XHSConfig.getDataClient().writeMessage(m);
        
        
        
    }
    
    
    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        XHSConfig.setMetricsFrame(null);
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
       
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
       
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

    public JLabel getMetricsLabel() {
        return metricsLabel;
    }

    public void setMetricsLabel(JLabel metricsLabel) {
        this.metricsLabel = metricsLabel;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public void setRefreshButton(JButton refreshButton) {
        this.refreshButton = refreshButton;
    }


   
    
    
    
    
}
