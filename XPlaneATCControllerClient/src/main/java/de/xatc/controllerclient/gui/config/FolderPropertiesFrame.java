package de.xatc.controllerclient.gui.config;

import de.xatc.controllerclient.config.XHSConfig;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Mirko
 */
public class FolderPropertiesFrame extends JFrame implements WindowListener {

    private PropertiesPanel propertiesPanel;

    public FolderPropertiesFrame() {
        super();
        initComponents();
    }

    private void initComponents() {

        this.setAlwaysOnTop(true);
        this.addWindowListener(this);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocation(300, 300);
        this.setSize(800, 800);
        propertiesPanel = new PropertiesPanel();
        this.add(propertiesPanel);
        this.setVisible(true);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //nothing to Do
    }

    @Override
    public void windowClosing(WindowEvent e) {
        XHSConfig.setFolderPropertiesFrame(null);
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //nothing to Do
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //nothing to Do
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //nothing to Do
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //nothing to Do
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //nothing to Do
    }

    public PropertiesPanel getPropertiesPanel() {
        return propertiesPanel;
    }

    public void setPropertiesPanel(PropertiesPanel propertiesPanel) {
        this.propertiesPanel = propertiesPanel;
    }
    
    
}
