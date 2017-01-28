/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.client.RegisterPacket;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.networking.DataClientBootstrap;
import java.awt.GridLayout;
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
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class RegisterFrame extends JFrame implements WindowListener, ActionListener {

    private JPanel registerPanel;
    private JTextField userNameField;
    private JLabel userNameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton submitButton;
    private JButton closeButton;

    public RegisterFrame() {
        super("Create new Account");
        initComponents();
    }

    private void initComponents() {

        this.setSize(400, 400);
        this.setLocation(AdapterConfig.getMainFrame().getLocation());
        this.registerPanel = new JPanel();

        this.registerPanel.setLayout(new GridLayout(3, 2));

        userNameField = new JTextField();
        userNameLabel = new JLabel("Enter your UserName:* ");

        passwordField = new JPasswordField();
        passwordLabel = new JLabel("Enter your Password:*");

        submitButton = new JButton("create Account");
        closeButton = new JButton("cancel");
        
        submitButton.addActionListener(this);
        closeButton.addActionListener(this);

 
                
        registerPanel.add(userNameLabel);
        registerPanel.add(userNameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(submitButton);
        registerPanel.add(closeButton);

        this.setAlwaysOnTop(true);
        this.add(registerPanel);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        if (cmd.equals("create Account")) {
            if (StringUtils.isEmpty(this.userNameField.getText())) {
                SwingTools.alertWindow("Please fill in your Username", this);
            }
            if (StringUtils.isEmpty(String.valueOf(this.passwordField.getPassword()))) {
                
                SwingTools.alertWindow("Password is empty! Please fill in a Password!", this);
            }
            
            
            DataClientBootstrap b = new DataClientBootstrap();
            AdapterConfig.setClientBootstrap(b);
            
            
      
            
            
            
            RegisterPacket p = new RegisterPacket();
            p.setUserName(this.userNameField.getText());
            p.setPassword(String.valueOf(this.passwordField.getPassword()));
            System.out.println("Sending packet!.....");
            System.out.println("Dataclient = " + AdapterConfig.getDataClient());
            AdapterConfig.getDataClient().writeMessage(p);
            System.out.println("Package sent!");
            System.out.println("Closing Window");
            AdapterConfig.setRegisterFrame(null);
            this.dispose();
        }
        else if (cmd.equals("cancel")) {
            AdapterConfig.setRegisterFrame(null);
            this.dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //nothing to do
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //nothing to do
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //nothing to do
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //nothing to do
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //nothing to do
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //nothing to do
    }

}
