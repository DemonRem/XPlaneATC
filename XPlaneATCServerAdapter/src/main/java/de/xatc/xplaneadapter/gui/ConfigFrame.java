/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui;

import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.config.ConfigBean;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ConfigFrame extends JFrame implements ActionListener, WindowListener {
 
    private JPanel configPanel = new JPanel();
    private JTextField atcServerField = new JTextField();
    private JTextField atcPortField = new JTextField();
    private JTextField localAdapterPortField = new JTextField();
    private JTextField localAdapterIPField = new JTextField();
    private JTextField defaultConnectionNameField = new JTextField();
    private JTextField atcServerVoiceField = new JTextField();
    private JTextField atcServerVoicePortField = new JTextField();
    
    private JLabel atcServerLabel = new JLabel("ATC Servername or IP-Address");
    private JLabel atcPortLabel = new JLabel("ATC Server Port");
    private JLabel localPortLabel = new JLabel("Listen to XPlane on local Port");
    private JLabel localIPLabel = new JLabel("Listen to XPlane on local IP");
    private JLabel nameLabel = new JLabel("Your default connection name");
    private JLabel atcServerVoiceLabel = new JLabel("ATC Server for Voice");
    private JLabel atcServerVoicePortLabel = new JLabel("ATC Server Voice Port");
    
    private JButton saveButton = new JButton("save");
    private JButton cancelButton = new JButton("cancel");
    
    
    
    public ConfigFrame() {
        
        super("Adpater Configuration....");
        initComponents();
        
        
    }
    private void initComponents() {
        
        this.addWindowListener(this);
        AdapterConfig.setConfigFrame(this);
        this.setLocation(400, 400);
        this.setSize(new Dimension(400,400));
        this.configPanel.setLayout(new VerticalLayout());
        
        this.configPanel.add(nameLabel);
        this.configPanel.add(defaultConnectionNameField);
        this.configPanel.add(atcServerLabel);
        this.configPanel.add(atcServerField);
        this.configPanel.add(atcPortLabel);
        this.configPanel.add(atcPortField);
        this.configPanel.add(atcServerVoiceLabel);
        this.configPanel.add(atcServerVoiceField);
        this.configPanel.add(atcServerVoicePortLabel);
        this.configPanel.add(atcServerVoicePortField);
       
        this.configPanel.add(localIPLabel);
        this.configPanel.add(localAdapterIPField);
        this.configPanel.add(localPortLabel);
        this.configPanel.add(localAdapterPortField);
        
        
        
        this.saveButton.addActionListener(this);
        this.cancelButton.addActionListener(this);
        
        this.configPanel.add(saveButton);
        this.configPanel.add(cancelButton);
        
        ConfigBean configBean = AdapterConfig.getConfigBean();
        if (configBean != null) {
            
            defaultConnectionNameField.setText(configBean.getConnectionName());
            atcServerField.setText(configBean.getAtcServerName());
            atcPortField.setText(configBean.getAtcServerPort());
            localAdapterIPField.setText(configBean.getXplaneListnerIP());
            localAdapterPortField.setText(configBean.getXplaneListenerPort());
            atcServerVoiceField.setText(configBean.getAtcVoiceServerName());
            atcServerVoicePortField.setText(configBean.getAtcVoiceServerPort());
            
            
        }
        
        this.add(configPanel);
        this.setVisible(true);
        
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
       
        String cmd = e.getActionCommand();
        if (cmd.equals("save")) {
            
            ConfigBean configBean = new ConfigBean();
            configBean.setAtcServerName(this.atcServerField.getText());
            configBean.setAtcServerPort(this.atcPortField.getText());
            configBean.setXplaneListnerIP(this.localAdapterIPField.getText());
            configBean.setXplaneListenerPort(this.localAdapterPortField.getText());
            configBean.setConnectionName(this.defaultConnectionNameField.getText());
            configBean.setAtcVoiceServerName(this.atcServerVoiceField.getText());
            configBean.setAtcVoiceServerPort(this.atcServerVoicePortField.getText());
            AdapterConfig.setConfigBean(configBean);
            AdapterConfig.savePropsFile();
            AdapterConfig.setConfigFrame(null);
            this.dispose();
            
            
        }
        else if (cmd.equals("cancel")) {
            AdapterConfig.setConfigFrame(null);
            this.dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
       
    }

    @Override
    public void windowClosing(WindowEvent e) {
        AdapterConfig.setConfigFrame(null);
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
    
    
    
    
    
    
    
}
