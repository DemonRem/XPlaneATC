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

import de.xatc.commons.beans.sharedgui.ChatFrame;
import de.xatc.commons.beans.sharedgui.ChatMessageRenderer;
import de.xatc.commons.networkpackets.client.TextMessagePacket;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.gui.config.ConnectionConfigFrame;
import de.xatc.controllerclient.gui.config.FolderPropertiesFrame;
import de.xatc.controllerclient.gui.connect.ConnectFrame;
import de.xatc.controllerclient.gui.datasync.ServerSyncFrame;
import de.xatc.controllerclient.gui.metrics.MetricsFrame;
import de.xatc.controllerclient.gui.servercontrol.FileIndexerFrame;
import de.xatc.controllerclient.gui.servercontrol.ServerControlFrame;
import de.xatc.controllerclient.gui.setupatc.ATCSetupFrame;
import de.xatc.controllerclient.gui.tools.ControllerClientGuiTools;
import de.xatc.controllerclient.gui.usercontrol.UserControlFrame;
import de.xatc.controllerclient.log.DebugMessageLevel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class MainFrame extends JFrame implements WindowListener, ActionListener, KeyListener {

    /**
     * the main panel
     */
    private MainPanel mainPanel;

    /**
     * the upper menu bar
     */
    private JMenuBar menuBar;

    /**
     * the file menu
     */
    private JMenu fileMenu;

    private JMenu windows;

    /**
     * the edit menu
     */
    private JMenu editMenu;

    /**
     * the helpmenu
     */
    private JMenu helpMenu;

    private JMenuItem connectItem;
    private JMenuItem disconnectItem;
    private JMenuItem configurtationItem;
    private JMenuItem serverControlItem;
    private JMenuItem flightPlansItem;
    private JMenuItem myStripesItem;
    private JMenuItem setUpATCArea;
    private JMenuItem exitItem;

    private JMenuItem helpItem;
    private JMenuItem aboutItem;
    private JMenuItem textMessageItem;
    private JMenuItem indexXplaneDataItem;
    private JMenuItem userManagementItem;
    private JMenuItem metricsItem;
    private JMenuItem serverSyncItem;
    private JMenuItem xPlaneFileIndexerItem;
    private JMenuItem folderOptionsItem;

    /**
     * the goto airport text field inside the file menu
     */
    private JTextField gotoAirportTextField;

    /**
     * my Window Name
     */
    private final String windowName = "mainWindow";

    public MainFrame() {
        super(XHSConfig.getAPPNAME() + " " + XHSConfig.getVERSION());
        initComponents();
    }

    /**
     * init all gui components
     *
     * @throws IOException
     */
    private void initComponents() {
        XHSConfig.setMainFrame(this);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

            UIManager.put("control", new Color(128, 128, 128));

            UIManager.put("info", new Color(128, 128, 128));

            UIManager.put("nimbusBase", new Color(18, 30, 49));

            UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));

            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));

            UIManager.put("nimbusFocus", new Color(115, 164, 209));

            UIManager.put("nimbusGreen", new Color(176, 179, 50));

            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));

            UIManager.put("nimbusLightBackground", new Color(18, 30, 49));

            UIManager.put("nimbusOrange", new Color(191, 98, 4));

            UIManager.put("nimbusRed", new Color(169, 46, 34));

            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));

            UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));

            UIManager.put("text", new Color(230, 230, 230));

            System.setProperty("nb.useSwingHtmlRendering", "true");

        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        this.setSize(800, 800);

        System.out.println("NEW MAIN PANEL");
        this.mainPanel = new MainPanel();

        this.createMenu();
        this.add(mainPanel);
        this.addWindowListener(this);
        this.setVisible(true);

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
        this.windows = new JMenu("Windows");
        this.helpMenu = new JMenu("Help");

        //init all Items
        connectItem = new JMenuItem("Connect...");
        connectItem.addActionListener(this);

        disconnectItem = new JMenuItem("Disconnect...");
        disconnectItem.setEnabled(false);
        disconnectItem.addActionListener(this);

        userManagementItem = new JMenuItem("User Management");
        userManagementItem.addActionListener(this);

        configurtationItem = new JMenuItem("Connection Options");
        configurtationItem.addActionListener(this);
        folderOptionsItem = new JMenuItem("Folder Options");
        folderOptionsItem.addActionListener(this);

        serverControlItem = new JMenuItem("ServerControl");
        serverControlItem.addActionListener(this);

        flightPlansItem = new JMenuItem("FlightPlan Pool");
        flightPlansItem.addActionListener(this);

        myStripesItem = new JMenuItem("my Stripes");
        myStripesItem.addActionListener(this);

        setUpATCArea = new JMenuItem("Setup ATC");
        setUpATCArea.addActionListener(this);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);

        helpItem = new JMenuItem("Help");
        helpItem.addActionListener(this);

        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);

        textMessageItem = new JMenuItem("Text Messages");
        textMessageItem.addActionListener(this);

        metricsItem = new JMenuItem("Server Metrics");
        metricsItem.addActionListener(this);

        serverSyncItem = new JMenuItem("Sync Runtime Data");
        serverSyncItem.addActionListener(this);

        xPlaneFileIndexerItem = new JMenuItem("XPlane File Indexer");
        xPlaneFileIndexerItem.addActionListener(this);

        //end now add all the stuff
        fileMenu.add(connectItem);
        fileMenu.add(disconnectItem);

        fileMenu.add(new JSeparator());

        this.gotoAirportTextField = new JTextField();
        this.gotoAirportTextField.addKeyListener(this);
        fileMenu.add(new JLabel("Go To Airport"));
        fileMenu.add(this.gotoAirportTextField);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitItem);

        //edit menu
        editMenu.add(configurtationItem);
        editMenu.add(new JSeparator());
        editMenu.add(folderOptionsItem);

        //windows
        windows.add(serverControlItem);
        windows.add(flightPlansItem);
        windows.add(myStripesItem);
        windows.add(userManagementItem);
        windows.add(new JSeparator());
        windows.add(setUpATCArea);
        windows.add(new JSeparator());
        windows.add(metricsItem);
        windows.add(serverSyncItem);
        windows.add(xPlaneFileIndexerItem);
        windows.add(this.textMessageItem);

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(windows);
        menuBar.add(helpMenu);

        this.setJMenuBar(this.menuBar);

    }

    /**
     * nothing to do with window opened
     *
     * @param we
     */
    @Override
    public void windowOpened(WindowEvent we) {
        //nothing to do here
    }

    /**
     * custom closing listener
     *
     * @param we
     */
    @Override
    public void windowClosing(WindowEvent we) {
        //todo
        DBSessionManager.shutdownDB();
        System.exit(0);
    }

    /**
     * custom window closed method
     *
     * @param we
     */
    @Override
    public void windowClosed(WindowEvent we) {
        //nothing to do
    }

    /**
     * default method
     *
     * @param we
     */
    @Override
    public void windowIconified(WindowEvent we) {
        //nothing to do
    }

    /**
     * method from windowlistener interface
     *
     * @param we
     */
    @Override
    public void windowDeiconified(WindowEvent we) {
        //nothing to do
    }

    /**
     * method from windowlistener interface
     *
     * @param we
     */
    @Override
    public void windowActivated(WindowEvent we) {
        //nothing to do
    }

    /**
     * method from windowlistener interface
     *
     * @param we
     */
    @Override
    public void windowDeactivated(WindowEvent we) {
        //nothing to do
    }

    /**
     * Action Listener for Menu items
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        XHSConfig.debugMessage("Action Menu clicked", DebugMessageLevel.DEBUG);
        if (ae.getSource() == helpItem) {

        } else if (ae.getSource() == configurtationItem) {

            XHSConfig.setConfigFrame(new ConnectionConfigFrame());
            XHSConfig.getConfigFrame().setVisible(true);
        } else if (ae.getSource() == connectItem) {

            XHSConfig.setConnectFrame(new ConnectFrame());

        } else if (ae.getSource() == disconnectItem) {

            System.out.println("Disconnecting");
            XHSConfig.getDataClientBootstrap().shutdownClient();

            System.out.println("disconnected!");

        } else if (ae.getSource() == metricsItem) {

            XHSConfig.setMetricsFrame(new MetricsFrame());

        } else if (ae.getSource() == serverControlItem) {
            XHSConfig.setServerControlFrame(new ServerControlFrame());
            XHSConfig.getServerControlFrame().setVisible(true);
        } else if (ae.getSource() == this.userManagementItem) {
            XHSConfig.setUserControlFrame(new UserControlFrame());
            XHSConfig.getUserControlFrame().setVisible(true);
        } else if (ae.getSource() == this.exitItem) {
            this.dispose();
            DBSessionManager.shutdownDB();
            System.exit(0);
        } else if (ae.getSource() == this.serverSyncItem) {

            XHSConfig.setServerSyncFrame(new ServerSyncFrame());
            XHSConfig.getServerSyncFrame().setVisible(true);

        } else if (ae.getSource() == xPlaneFileIndexerItem) {
            XHSConfig.setFileIndexerFrame(new FileIndexerFrame());
            XHSConfig.getFileIndexerFrame().setVisible(true);
        } else if (ae.getSource() == folderOptionsItem) {

            XHSConfig.setFolderPropertiesFrame(new FolderPropertiesFrame());

        } else if (ae.getSource() == textMessageItem) {

            System.out.println("CHATFRAME: " + XHSConfig.getChatFrame());

            ControllerClientGuiTools.showChatFrame();
          
        } else if (ae.getSource() == setUpATCArea) {

            XHSConfig.setAtcSetupFrame(new ATCSetupFrame());
            XHSConfig.getAtcSetupFrame().setVisible(true);

        }

    }

    /**
     * key event
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * key event
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * key event. if the user type enter the map will navigate to entered
     * airport
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

        }

    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
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

    public JTextField getGotoAirportTextField() {
        return gotoAirportTextField;
    }

    public void setGotoAirportTextField(JTextField gotoAirportTextField) {
        this.gotoAirportTextField = gotoAirportTextField;
    }

    public void setWindows(JMenu windows) {
        this.windows = windows;
    }

    public JMenuItem getConnectItem() {
        return connectItem;
    }

    public void setConnectItem(JMenuItem connectItem) {
        this.connectItem = connectItem;
    }

    public JMenuItem getDisconnectItem() {
        return disconnectItem;
    }

    public void setDisconnectItem(JMenuItem disconnectItem) {
        this.disconnectItem = disconnectItem;
    }

    public JMenuItem getConfigurtationItem() {
        return configurtationItem;
    }

    public void setConfigurtationItem(JMenuItem configurtationItem) {
        this.configurtationItem = configurtationItem;
    }

    public JMenuItem getServerControlItem() {
        return serverControlItem;
    }

    public void setServerControlItem(JMenuItem serverControlItem) {
        this.serverControlItem = serverControlItem;
    }

    public JMenuItem getFlightPlansItem() {
        return flightPlansItem;
    }

    public void setFlightPlansItem(JMenuItem flightPlansItem) {
        this.flightPlansItem = flightPlansItem;
    }

    public JMenuItem getMyStripesItem() {
        return myStripesItem;
    }

    public void setMyStripesItem(JMenuItem myStripesItem) {
        this.myStripesItem = myStripesItem;
    }

    public JMenuItem getSetUpATCArea() {
        return setUpATCArea;
    }

    public void setSetUpATCArea(JMenuItem setUpATCArea) {
        this.setUpATCArea = setUpATCArea;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    public void setExitItem(JMenuItem exitItem) {
        this.exitItem = exitItem;
    }

    public JMenuItem getHelpItem() {
        return helpItem;
    }

    public void setHelpItem(JMenuItem helpItem) {
        this.helpItem = helpItem;
    }

    public JMenuItem getAboutItem() {
        return aboutItem;
    }

    public void setAboutItem(JMenuItem aboutItem) {
        this.aboutItem = aboutItem;
    }

    public JMenuItem getTextMessageItem() {
        return textMessageItem;
    }

    public void setTextMessageItem(JMenuItem textMessageItem) {
        this.textMessageItem = textMessageItem;
    }

    public JMenuItem getIndexXplaneDataItem() {
        return indexXplaneDataItem;
    }

    public void setIndexXplaneDataItem(JMenuItem indexXplaneDataItem) {
        this.indexXplaneDataItem = indexXplaneDataItem;
    }

    public JMenuItem getUserManagementItem() {
        return userManagementItem;
    }

    public void setUserManagementItem(JMenuItem userManagementItem) {
        this.userManagementItem = userManagementItem;
    }

    public JMenuItem getServerSyncItem() {
        return serverSyncItem;
    }

    public void setServerSyncItem(JMenuItem serverSyncItem) {
        this.serverSyncItem = serverSyncItem;
    }

    public JMenuItem getxPlaneFileIndexerItem() {
        return xPlaneFileIndexerItem;
    }

    public void setxPlaneFileIndexerItem(JMenuItem xPlaneFileIndexerItem) {
        this.xPlaneFileIndexerItem = xPlaneFileIndexerItem;
    }

    public JMenuItem getFolderOptionsItem() {
        return folderOptionsItem;
    }

    public void setFolderOptionsItem(JMenuItem folderOptionsItem) {
        this.folderOptionsItem = folderOptionsItem;
    }

}
