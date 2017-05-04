/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui;

import de.mytools.tools.screen.MyScreenProperties;
import de.xatc.commons.networktools.NetworkingTools;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.config.ConfigBean;
import de.xatc.xplaneadapter.nettyclient.XPlaneUDPListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.UnknownHostException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class MainFrame extends JFrame implements ActionListener, WindowListener, ItemListener {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;

    private JMenuItem propertiesMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem connectMenuItem;
    private JMenuItem disconnectMenuItem;
    private JMenuItem sendFlightPlanMenuItem;
    private JCheckBoxMenuItem listenToXPlaneItem;
    private JCheckBoxMenuItem recordFligtItem;
    private MainPanel mainPanel;
    private JMenuItem registerMenuItem;
    private JTextField recordFileName;

    private boolean isConnected = false;

    public MainFrame() {
        super(AdapterConfig.getAppName() + " " + AdapterConfig.getVersion());
        initComponents();
    }

    private void initComponents() {

        this.addWindowListener(this);
        this.setAlwaysOnTop(true);
        this.setLocation(5, 10);
        this.recordFileName = new JTextField();
        this.setSize(new Dimension(300, 300));
        createMenu();
        this.setVisible(true);
        MyScreenProperties.setWindowSize(this);

    }

    /**
     * create the top menu
     */
    private void createMenu() {

        //create the bar
        this.menuBar = new JMenuBar();

        //then create the menus
        this.fileMenu = new JMenu("File");
        this.editMenu = new JMenu("Edit");
        this.helpMenu = new JMenu("Help");

        //create items
        propertiesMenuItem = new JMenuItem("Properties");
        exitMenuItem = new JMenuItem("Exit");
        connectMenuItem = new JMenuItem("Connect to ATC-Server");
        disconnectMenuItem = new JMenuItem("Disconnect");
        this.listenToXPlaneItem = new JCheckBoxMenuItem("Listen to XPlane");
        this.recordFligtItem = new JCheckBoxMenuItem("Record Flight");
        this.listenToXPlaneItem.addItemListener(this);

        this.registerMenuItem = new JMenuItem("Create Account");
        this.registerMenuItem.addActionListener(this);

        this.connectMenuItem.setEnabled(true);
        this.disconnectMenuItem.setEnabled(false);

        sendFlightPlanMenuItem = new JMenuItem("Send FlightPlan");

        //Actoion Listeners
        this.propertiesMenuItem.addActionListener(this);

        this.exitMenuItem.addActionListener(this);
        this.connectMenuItem.addActionListener(this);
        this.disconnectMenuItem.addActionListener(this);
        this.sendFlightPlanMenuItem.addActionListener(this);
        //end now add all the stuff
        this.fileMenu.add(this.connectMenuItem);
        this.fileMenu.add(this.disconnectMenuItem);
        this.fileMenu.add(new JSeparator());
        this.fileMenu.add(listenToXPlaneItem);
        this.fileMenu.add(new JSeparator());
        this.fileMenu.add(this.recordFligtItem);
        this.fileMenu.add(this.recordFileName);
        this.fileMenu.add(new JSeparator());
        this.fileMenu.add(registerMenuItem);
        this.fileMenu.add(new JSeparator());

        this.fileMenu.add(this.exitMenuItem);

        this.editMenu.add(this.sendFlightPlanMenuItem);
        
        this.editMenu.add(new JSeparator());
        this.editMenu.add(this.propertiesMenuItem);

        this.menuBar.add(fileMenu);
        this.menuBar.add(editMenu);
        this.menuBar.add(helpMenu);

        this.setJMenuBar(this.menuBar);
        ConfigBean configBean = AdapterConfig.getConfigBean();
        if (configBean == null) {
            new ConfigFrame();
        }

        this.mainPanel = new MainPanel();
        this.add(mainPanel);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //nothing to do
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Connect to ATC-Server")) {
            ConnectFrame c = new ConnectFrame();
        } else if (cmd.equals("Properties")) {
            ConfigFrame configFrame = new ConfigFrame();
        } else if (cmd.equals("Disconnect")) {

            AdapterConfig.getClientBootstrap().shutdownClient();
            AdapterConfig.setCurrentChannelID(null);
            AdapterConfig.setCurrentSessionID(null);
            AdapterConfig.setClientBootstrap(null);

        } else if (cmd.equals("Create Account")) {
            if (AdapterConfig.getRegisterFrame() == null) {
                RegisterFrame regFrame = new RegisterFrame();
                AdapterConfig.setRegisterFrame(regFrame);
            }
        }
        else if (cmd.equals("Send FlightPlan")) {
            
            if (AdapterConfig.getFlightPlanFrame() == null) {
                AdapterConfig.setFlightPlanFrame(new FlightPlanFrame());
                AdapterConfig.getFlightPlanFrame().setVisible(true);
            }
            
            
        }
        else if (cmd.equals("Exit")) {
            System.exit(0);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        
        System.out.println("Item State Changed");
        System.out.println("ID: " + e.getID());
        System.out.println("Statechanged: " + e.getStateChange());
        System.out.println("ITEM: " + e.getItem());
        
        System.out.println("SELECTED: " +ItemEvent.SELECTED);
        
        
        if (this.listenToXPlaneItem.isSelected()) {
            System.out.println("CheckBox Listener selected");
            XPlaneUDPListener l = new XPlaneUDPListener();
            AdapterConfig.setXplaneUDPListener(l);
            l.start();
        } else if (!this.listenToXPlaneItem.isSelected()) {

            try {
                NetworkingTools.sendUPDStringToServerSocket(AdapterConfig.getConfigBean().getXplaneListnerIP(), Integer.parseInt(AdapterConfig.getConfigBean().getXplaneListenerPort()), "SHUTDOWN");
            } catch (UnknownHostException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public JMenuBar getMainFrameMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public void setFileMenu(JMenu fileMenu) {
        this.fileMenu = fileMenu;
    }

    public JMenu getEditMenu() {
        return editMenu;
    }

    public void setEditMenu(JMenu editMenu) {
        this.editMenu = editMenu;
    }

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public void setHelpMenu(JMenu helpMenu) {
        this.helpMenu = helpMenu;
    }

    public JMenuItem getPropertiesMenuItem() {
        return propertiesMenuItem;
    }

    public void setPropertiesMenuItem(JMenuItem propertiesMenuItem) {
        this.propertiesMenuItem = propertiesMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public void setExitMenuItem(JMenuItem exitMenuItem) {
        this.exitMenuItem = exitMenuItem;
    }

    public JMenuItem getConnectMenuItem() {
        return connectMenuItem;
    }

    public void setConnectMenuItem(JMenuItem connectMenuItem) {
        this.connectMenuItem = connectMenuItem;
    }

    public JMenuItem getDisconnectMenuItem() {
        return disconnectMenuItem;
    }

    public void setDisconnectMenuItem(JMenuItem disconnectMenuItem) {
        this.disconnectMenuItem = disconnectMenuItem;
    }

    public JMenuItem getSendFlightPlanMenuItem() {
        return sendFlightPlanMenuItem;
    }

    public void setSendFlightPlanMenuItem(JMenuItem sendFlightPlanMenuItem) {
        this.sendFlightPlanMenuItem = sendFlightPlanMenuItem;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public boolean isIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public JCheckBoxMenuItem getListenToXPlaneItem() {
        return listenToXPlaneItem;
    }

    public void setListenToXPlaneItem(JCheckBoxMenuItem listenToXPlaneItem) {
        this.listenToXPlaneItem = listenToXPlaneItem;
    }

    public JTextField getRecordFileName() {
        return recordFileName;
    }

    public void setRecordFileName(JTextField recordFileName) {
        this.recordFileName = recordFileName;
    }

    public JCheckBoxMenuItem getRecordFligtItem() {
        return recordFligtItem;
    }

    public void setRecordFligtItem(JCheckBoxMenuItem recordFligtItem) {
        this.recordFligtItem = recordFligtItem;
    }
    

}
