/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.client.LoginPacket;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.config.ConfigBean;
import de.xatc.xplaneadapter.networking.DataClientBootstrap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ConnectFrame extends JFrame implements ActionListener, WindowListener {

    private JPanel connectPanel;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JLabel nameLabel;
    private JButton connectButton;
    private JButton cancelButton;

    public ConnectFrame() {

        super("Connect to server...");
        initComponents();

    }

    private void initComponents() {

        AdapterConfig.setConnectFrame(this);
        this.setLocation(400, 400);
        this.setSize(400, 200);
        this.addWindowListener(this);
        this.connectPanel = new JPanel();

        connectPanel.setLayout(new VerticalLayout());
        nameField = new JTextField();
        nameLabel = new JLabel("Please enter your connection name:");
        connectPanel.add(nameLabel);
        connectPanel.add(nameField);
        
        this.passwordField = new JPasswordField();
        this.passwordLabel = new JLabel("Enter password:");
        
        
        connectPanel.add(passwordField);
        connectPanel.add(passwordLabel);
        connectButton = new JButton("connect");
        cancelButton = new JButton("cancel");
        
        
        
        ConfigBean config = AdapterConfig.getConfigBean();
        if (config != null) {

            nameField.setText(config.getConnectionName());

        }

        connectButton.addActionListener(this);
        connectPanel.add(connectButton);

        this.add(connectPanel);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("connect")) {

            if (StringUtils.isEmpty(this.nameField.getText())) {
                
                SwingTools.alertWindow("Username may not be empty!", this);
                return;
                
            }
            if (StringUtils.isEmpty(String.valueOf(this.passwordField.getPassword()))) {
                
                SwingTools.alertWindow("Password may not be empty!", this);
                return;
            }
            
            DataClientBootstrap clientStrap = new DataClientBootstrap();
            
            
            
            AdapterConfig.setClientBootstrap(clientStrap);
            
            LoginPacket p = new LoginPacket();
            p.setUserName(this.nameField.getText());
            p.setPassword(String.valueOf(this.passwordField.getPassword()));
            
            AdapterConfig.getDataClient().writeMessage(p);
            AdapterConfig.setCurrentConnectionName(nameField.getText());
            
            
            AdapterConfig.setConnectFrame(null);
            this.dispose();

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        AdapterConfig.setConnectFrame(null);
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

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getConnectButton() {
        return connectButton;
    }

    public void setConnectButton(JButton connectButton) {
        this.connectButton = connectButton;
    }
    
    

}
