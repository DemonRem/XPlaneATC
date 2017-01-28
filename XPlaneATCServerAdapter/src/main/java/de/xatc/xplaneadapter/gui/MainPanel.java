/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui;

import de.mytools.tools.screen.MyScreenProperties;
import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.client.TextMessagePacket;
import de.xatc.commons.networkpackets.generic.RadioFrequencyChange;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.gui.cellrenderrer.TextMessageCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class MainPanel extends JPanel implements KeyListener, ActionListener {

    private ConnectionStatusPanel connectionStatusPanel = new ConnectionStatusPanel();
    private JList textMessageList;
    private JPanel textMessagePanel;
    private DefaultListModel textMessageListModel;
    private JPanel audioPanel;
    private JTextField textMessageField;

    private JLabel audioLabel;
    private JLabel freqLabel;
    private JTextField freqField;
    private JButton freqButton;
    private JScrollPane textListScrollPane;

    private JPanel centralPanel;

    public MainPanel() {

        super();
        initComponents();

    }

    private void initComponents() {

        this.setLayout(new BorderLayout());

        centralPanel = new JPanel();
        centralPanel.setLayout(new VerticalLayout());

        audioPanel = new JPanel();
        audioPanel.setLayout(new VerticalLayout());
        audioLabel = new JLabel("Current Audio Frequency");
        audioLabel.setForeground(Color.WHITE);
        audioPanel.setBorder(BorderFactory.createCompoundBorder());

        audioPanel.add(audioLabel);

        freqLabel = new JLabel();
        freqLabel.setFont(new Font(freqLabel.getName(), Font.PLAIN, 14));
        freqLabel.setForeground(Color.GREEN);
        freqLabel.setBackground(Color.DARK_GRAY);
        audioPanel.setBackground(Color.DARK_GRAY);

        if (StringUtils.isEmpty(AdapterConfig.getCurrentRadioFrequency())) {
            freqLabel.setText("000.000");
            AdapterConfig.setCurrentRadioFrequency("000.000");
        } else {
            freqLabel.setText(AdapterConfig.getCurrentRadioFrequency());
        }

        audioPanel.add(freqLabel);

        centralPanel.add(audioPanel);
        if (AdapterConfig.isDoDebug()) {

            freqField = new JTextField();
            freqButton = new JButton("set Freq");
            freqButton.addActionListener(this);
            centralPanel.add(freqField);
            centralPanel.add(freqButton);

        }

        this.textMessageListModel = new DefaultListModel();
        textMessageListModel.add(0, "Ready....");
        this.textMessageList = new JList(textMessageListModel);
        
        
        
        //TODO - HIer noch ein linewrap fuer den ChatClient.
        
        
        this.textMessageList.setCellRenderer(new TextMessageCellRenderer(MyScreenProperties.getScreenWidth()));
        
        
       
        
        
        
        

        textMessagePanel = new JPanel();
        textMessagePanel.setLayout(new BorderLayout());

        this.textListScrollPane = new JScrollPane();
        this.textListScrollPane.setViewportView(textMessageList);

        textMessagePanel.add(textListScrollPane, BorderLayout.CENTER);

        // Hier müssen wir uns jetzt noch etwas einfallen lassen, die nachrichten gut darzustellen.darzustellen
        //         Außerdem muss noch was an den Session Handlings gemacht werden. Wird Zeit, die Frequencies einzuführen, da es sonst Schwierigkeiten bei der Auffindung der korrekten user oder atc session geben kann.Auffindung
        textMessageField = new JTextField();
        textMessageField.addKeyListener(this);
        textMessagePanel.add(textMessageField, BorderLayout.NORTH);

        centralPanel.add(textMessagePanel);

        this.add(centralPanel, BorderLayout.CENTER);
        this.add(connectionStatusPanel, BorderLayout.SOUTH);

    }

    public ConnectionStatusPanel getConnectionStatusPanel() {
        return connectionStatusPanel;
    }

    public void setConnectionStatusPanel(ConnectionStatusPanel connectionStatusPanel) {
        this.connectionStatusPanel = connectionStatusPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing to do
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //mothing to do
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (StringUtils.isEmpty(this.textMessageField.getText())) {
                this.textMessageField.setText("");
                return;
            }

           
            
            TextMessagePacket m = new TextMessagePacket();

            m.setFrequency(AdapterConfig.getCurrentRadioFrequency());
            m.setMessage(textMessageField.getText());
            if (!StringUtils.isEmpty(AdapterConfig.getCurrentConnectionName())) {
                m.setFromUserName(AdapterConfig.getCurrentConnectionName());
            } else {
                m.setFromUserName("UNKOWN USER FROM ADAPTERCLIENT");
            }

            m.setFrequency(AdapterConfig.getCurrentRadioFrequency());
            System.out.println("Frequency Set: " + m.getFrequency());
            System.out.println("userNameSet: " + m.getFromUserName());

            if (AdapterConfig.getDataClient() != null) {

                AdapterConfig.getDataClient().writeMessage(m);

            } else {
                SwingTools.alertWindow("not connected", this);
            }
            this.textMessageField.setText("");
        }
    }

    public JList getTextMessageList() {
        return textMessageList;
    }

    public void setTextMessageList(JList textMessageList) {
        this.textMessageList = textMessageList;
    }

    public JPanel getTextMessagePanel() {
        return textMessagePanel;
    }

    public void setTextMessagePanel(JPanel textMessagePanel) {
        this.textMessagePanel = textMessagePanel;
    }

    public DefaultListModel getTextMessageListModel() {
        return textMessageListModel;
    }

    public void setTextMessageListModel(DefaultListModel textMessageListModel) {
        this.textMessageListModel = textMessageListModel;
    }

    public JPanel getAudioPanel() {
        return audioPanel;
    }

    public void setAudioPanel(JPanel audioPanel) {
        this.audioPanel = audioPanel;
    }

    public JTextField getTextMessageField() {
        return textMessageField;
    }

    public void setTextMessageField(JTextField textMessageField) {
        this.textMessageField = textMessageField;
    }

    public JLabel getAudioLabel() {
        return audioLabel;
    }

    public void setAudioLabel(JLabel audioLabel) {
        this.audioLabel = audioLabel;
    }

    public JTextField getFreqField() {
        return freqField;
    }

    public void setFreqField(JTextField freqField) {
        this.freqField = freqField;
    }

    public JButton getFreqButton() {
        return freqButton;
    }

    public void setFreqButton(JButton freqButton) {
        this.freqButton = freqButton;
    }

    public JPanel getCentralPanel() {
        return centralPanel;
    }

    public void setCentralPanel(JPanel centralPanel) {
        this.centralPanel = centralPanel;
    }

    public JLabel getFreqLabel() {
        return freqLabel;
    }

    public void setFreqLabel(JLabel freqLabel) {
        this.freqLabel = freqLabel;
    }

    public void addTextMessageItem(String text) {

        this.textMessageListModel.add(0, text);
        this.textMessageList.revalidate();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ACtion Performend");
        System.out.println(e.getActionCommand());
        if (AdapterConfig.isDoDebug()) {

            String cmd = e.getActionCommand();
            if (cmd.equals("set Freq")) {
                if (AdapterConfig.getDataClient() != null) {

                    RadioFrequencyChange c = new RadioFrequencyChange();
                    c.setOldFrequency(AdapterConfig.getCurrentRadioFrequency());
                    c.setCuurentFrequency(this.freqField.getText());
                    AdapterConfig.getDataClient().writeMessage(c);
                    this.freqLabel.setText(freqField.getText());
                    AdapterConfig.setCurrentRadioFrequency(freqField.getText());
                    this.revalidate();
                    this.repaint();
                }
            }

        }
    }

   

}
