/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlan;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.xplaneadapter.config.AdapterConfig;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author c047
 */
public class FlightPlanFrame extends javax.swing.JFrame {

    /**
     * Creates new form FlightPlanFrame
     */
    public FlightPlanFrame() {
        this.setAlwaysOnTop(true);
        initComponents();
        this.fillInCurrentFlightPlanValues();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        icaoFromField = new javax.swing.JTextField();
        icaoToField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        flightLevelField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        flightNumberField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        takeOffTimeField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        arrivalTimeField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        aircraftTypeField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        airlineField = new javax.swing.JTextField();
        ifrRadio = new javax.swing.JRadioButton();
        vfrRadio = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        routeField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        remarkField = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        deactivateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Flight Plan");

        jLabel2.setText("From (Icao)*");

        jLabel3.setText("Dest (Icao)*");

        jLabel4.setText("Flightlevel");

        jLabel5.setText("FlightNumber*");

        jLabel12.setText("e.g. 090");

        jLabel13.setText("e.g. LH1807");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(icaoFromField, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(flightNumberField)
                    .addComponent(icaoToField))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(flightLevelField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(flightNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(icaoFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(flightLevelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(icaoToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel6.setText("Take Off Time (UTC)");

        jLabel7.setText("Arrival Time (UTC)");

        jLabel10.setText("Aircraft-Type*");

        jLabel11.setText("Airline");

        ifrRadio.setText("IFR");
        ifrRadio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ifrRadioStateChanged(evt);
            }
        });

        vfrRadio.setText("VFR");
        vfrRadio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                vfrRadioStateChanged(evt);
            }
        });

        jLabel14.setText("e.g. 13:12:00");

        jLabel15.setText("e.g. 18:12:00");

        jLabel16.setText("e.g. FA7X");

        jLabel17.setText("e.g. Lufthansa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(arrivalTimeField, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                    .addComponent(aircraftTypeField)
                    .addComponent(takeOffTimeField))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ifrRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vfrRadio))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(airlineField, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(takeOffTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ifrRadio)
                    .addComponent(vfrRadio)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(arrivalTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(airlineField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(aircraftTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel8.setText("Route");

        jLabel9.setText("Remarks");

        remarkField.setColumns(20);
        remarkField.setRows(5);
        jScrollPane3.setViewportView(remarkField);

        sendButton.setText("send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("cancel");

        jLabel18.setText("e.g. BKD L619 BUMIL M748 RARUP");

        deactivateButton.setText("revoke Plan");
        deactivateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deactivateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(routeField)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(sendButton)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deactivateButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(routeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton)
                    .addComponent(cancelButton)
                    .addComponent(deactivateButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        AdapterConfig.setFlightPlanFrame(null);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing


    //TRACKDATAINNB_START
    //itemName="pilotSendNewFlightPlan"
    //comment="initially send new FlightPlan from XPlane Adapter"
    //step=1
    //itemType="Method"
    //methodName="sendButtonActionPerformed"
    //TRACKDATAINNB_STOP
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        if (StringUtils.isEmpty(this.icaoFromField.getText())){
            SwingTools.alertWindow("Icao From may not be empty", this);
            return;
        }
        if (StringUtils.isEmpty(this.icaoToField.getText())){
            SwingTools.alertWindow("Icao To may not be empty", this);
            return;
        }
        if (StringUtils.isEmpty(this.flightNumberField.getText())){
            SwingTools.alertWindow("FlightNumber may not be empty", this);
            return;
        }
        if (this.flightNumberField.getText().length() <=3) {
            SwingTools.alertWindow("Please enter a conform Flightnumber, e.g. LH1807", this);
            return;
        }
        if (StringUtils.isEmpty(this.flightLevelField.getText())){
            SwingTools.alertWindow("FlightLevel may not be empty", this);
            return;
        }
        if (this.flightLevelField.getText().length() != 3) {
            SwingTools.alertWindow("Please enter a conform Flightlevel, e.g. 080", this);
            return;
        }
        if (!this.ifrRadio.isSelected() && !this.vfrRadio.isSelected()) {
            SwingTools.alertWindow("Please select either IFR or VFR", this);
            return;
        }
        
        if (AdapterConfig.getDataClient() == null) {
            SwingTools.alertWindow("Not connected!", this);
            return;
        }
        
        SubmittedFlightPlan p = new SubmittedFlightPlan();
        
        p.setAircraftType(this.aircraftTypeField.getText());
        p.setAirline(this.airlineField.getText());
        p.setArrivalTime(this.arrivalTimeField.getText());
        p.setFlightLevel(this.flightLevelField.getText());
        p.setFlightNumber(this.flightNumberField.getText());
        p.setIcaoFrom(this.icaoFromField.getText());
        p.setIcaoTo(this.icaoToField.getText());
        p.setRemark(this.remarkField.getText());
        p.setRoute(this.routeField.getText());
        p.setTakeOffTime(this.takeOffTimeField.getText());
        
        if (this.ifrRadio.isSelected()) {
            p.setIfrOrVfr("IFR");
        }
        else {
            p.setIfrOrVfr("VFR");
        }
//        also das hier funzt alles noch nicht. Da muss ich nohcmal von vorne anfangen und den Weg
//                der Daten verfolgen.
        SubmittedFlightPlansActionPacket fpAction = new SubmittedFlightPlansActionPacket();
        fpAction.setSubmittedFlightPlan(p);
        if (AdapterConfig.getCurrentSubmittedFlightPlan() != null) {
            fpAction.setAction("update");
        }
        else {
            fpAction.setAction("new");
        }
        AdapterConfig.getDataClient().writeMessage(fpAction);
        AdapterConfig.setCurrentSubmittedFlightPlan(p);
        AdapterConfig.setFlightPlanFrame(null);
        this.dispose();
        
    }//GEN-LAST:event_sendButtonActionPerformed

    private void fillInCurrentFlightPlanValues() {
        
        if (AdapterConfig.getCurrentSubmittedFlightPlan() == null) {
            return;
        }
        this.aircraftTypeField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getAircraftType());
        this.airlineField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getAirline());
        this.arrivalTimeField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getArrivalTime());
        this.flightLevelField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getFlightLevel());
        this.flightNumberField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getFlightNumber());
        this.icaoFromField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getIcaoFrom());
        this.icaoToField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getIcaoTo());
        this.remarkField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getRemark());
        this.routeField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getRoute());
        this.takeOffTimeField.setText(AdapterConfig.getCurrentSubmittedFlightPlan().getTakeOffTime());

        if (AdapterConfig.getCurrentSubmittedFlightPlan().getIfrOrVfr().equals("IFR")) {
            this.ifrRadio.setSelected(true);
        }
        else {
            this.vfrRadio.setSelected(true);
        }
        
        
    }
    
    
    private void ifrRadioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ifrRadioStateChanged
        if (this.ifrRadio.isSelected()) {
            this.vfrRadio.setSelected(false);
        }
        else {
            this.vfrRadio.setSelected(true);
        }
    }//GEN-LAST:event_ifrRadioStateChanged

    private void vfrRadioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_vfrRadioStateChanged
        if (this.vfrRadio.isSelected()) {
            this.ifrRadio.setSelected(false);
        }
        else {
            this.ifrRadio.setSelected(true);
        }
    }//GEN-LAST:event_vfrRadioStateChanged

    private void deactivateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deactivateButtonActionPerformed
        
        if (AdapterConfig.getCurrentSubmittedFlightPlan() == null) {
            SwingTools.alertWindow("FlightPlan has not been sent yet!", AdapterConfig.getMainFrame());
            return;
        }
        else {
            
            SubmittedFlightPlansActionPacket fpAction = new SubmittedFlightPlansActionPacket();
            fpAction.setAction("revoke");
            fpAction.setSubmittedFlightPlan(AdapterConfig.getCurrentSubmittedFlightPlan());
            AdapterConfig.getDataClient().writeMessage(fpAction);
            AdapterConfig.setCurrentSubmittedFlightPlan(null);
            AdapterConfig.setFlightPlanFrame(null);
            SwingTools.alertWindow("FlightPlan has been revoked. Please send a new one!", AdapterConfig.getMainFrame());
            this.dispose();
            
        }
        
        
    }//GEN-LAST:event_deactivateButtonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aircraftTypeField;
    private javax.swing.JTextField airlineField;
    private javax.swing.JTextField arrivalTimeField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deactivateButton;
    private javax.swing.JTextField flightLevelField;
    private javax.swing.JTextField flightNumberField;
    private javax.swing.JTextField icaoFromField;
    private javax.swing.JTextField icaoToField;
    private javax.swing.JRadioButton ifrRadio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea remarkField;
    private javax.swing.JTextField routeField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField takeOffTimeField;
    private javax.swing.JRadioButton vfrRadio;
    // End of variables declaration//GEN-END:variables

    public void pressSendButtonFromAutomation(java.awt.event.ActionEvent e) {
        
        this.sendButtonActionPerformed(e);
        
    }
    
    
    public JTextField getAircraftTypeField() {
        return aircraftTypeField;
    }

    public void setAircraftTypeField(JTextField aircraftTypeField) {
        this.aircraftTypeField = aircraftTypeField;
    }

    public JTextField getAirlineField() {
        return airlineField;
    }

    public void setAirlineField(JTextField airlineField) {
        this.airlineField = airlineField;
    }

    public JTextField getArrivalTimeField() {
        return arrivalTimeField;
    }

    public void setArrivalTimeField(JTextField arrivalTimeField) {
        this.arrivalTimeField = arrivalTimeField;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JButton getDeactivateButton() {
        return deactivateButton;
    }

    public void setDeactivateButton(JButton deactivateButton) {
        this.deactivateButton = deactivateButton;
    }

    public JTextField getFlightLevelField() {
        return flightLevelField;
    }

    public void setFlightLevelField(JTextField flightLevelField) {
        this.flightLevelField = flightLevelField;
    }

    public JTextField getFlightNumberField() {
        return flightNumberField;
    }

    public void setFlightNumberField(JTextField flightNumberField) {
        this.flightNumberField = flightNumberField;
    }

    public JTextField getIcaoFromField() {
        return icaoFromField;
    }

    public void setIcaoFromField(JTextField icaoFromField) {
        this.icaoFromField = icaoFromField;
    }

    public JTextField getIcaoToField() {
        return icaoToField;
    }

    public void setIcaoToField(JTextField icaoToField) {
        this.icaoToField = icaoToField;
    }

    public JRadioButton getIfrRadio() {
        return ifrRadio;
    }

    public void setIfrRadio(JRadioButton ifrRadio) {
        this.ifrRadio = ifrRadio;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel10() {
        return jLabel10;
    }

    public void setjLabel10(JLabel jLabel10) {
        this.jLabel10 = jLabel10;
    }

    public JLabel getjLabel11() {
        return jLabel11;
    }

    public void setjLabel11(JLabel jLabel11) {
        this.jLabel11 = jLabel11;
    }

    public JLabel getjLabel12() {
        return jLabel12;
    }

    public void setjLabel12(JLabel jLabel12) {
        this.jLabel12 = jLabel12;
    }

    public JLabel getjLabel13() {
        return jLabel13;
    }

    public void setjLabel13(JLabel jLabel13) {
        this.jLabel13 = jLabel13;
    }

    public JLabel getjLabel14() {
        return jLabel14;
    }

    public void setjLabel14(JLabel jLabel14) {
        this.jLabel14 = jLabel14;
    }

    public JLabel getjLabel15() {
        return jLabel15;
    }

    public void setjLabel15(JLabel jLabel15) {
        this.jLabel15 = jLabel15;
    }

    public JLabel getjLabel16() {
        return jLabel16;
    }

    public void setjLabel16(JLabel jLabel16) {
        this.jLabel16 = jLabel16;
    }

    public JLabel getjLabel17() {
        return jLabel17;
    }

    public void setjLabel17(JLabel jLabel17) {
        this.jLabel17 = jLabel17;
    }

    public JLabel getjLabel18() {
        return jLabel18;
    }

    public void setjLabel18(JLabel jLabel18) {
        this.jLabel18 = jLabel18;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public JLabel getjLabel5() {
        return jLabel5;
    }

    public void setjLabel5(JLabel jLabel5) {
        this.jLabel5 = jLabel5;
    }

    public JLabel getjLabel6() {
        return jLabel6;
    }

    public void setjLabel6(JLabel jLabel6) {
        this.jLabel6 = jLabel6;
    }

    public JLabel getjLabel7() {
        return jLabel7;
    }

    public void setjLabel7(JLabel jLabel7) {
        this.jLabel7 = jLabel7;
    }

    public JLabel getjLabel8() {
        return jLabel8;
    }

    public void setjLabel8(JLabel jLabel8) {
        this.jLabel8 = jLabel8;
    }

    public JLabel getjLabel9() {
        return jLabel9;
    }

    public void setjLabel9(JLabel jLabel9) {
        this.jLabel9 = jLabel9;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public JPanel getjPanel2() {
        return jPanel2;
    }

    public void setjPanel2(JPanel jPanel2) {
        this.jPanel2 = jPanel2;
    }

    public JPanel getjPanel3() {
        return jPanel3;
    }

    public void setjPanel3(JPanel jPanel3) {
        this.jPanel3 = jPanel3;
    }

    public JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public void setjScrollPane3(JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    public JTextArea getRemarkField() {
        return remarkField;
    }

    public void setRemarkField(JTextArea remarkField) {
        this.remarkField = remarkField;
    }

    public JTextField getRouteField() {
        return routeField;
    }

    public void setRouteField(JTextField routeField) {
        this.routeField = routeField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    public JTextField getTakeOffTimeField() {
        return takeOffTimeField;
    }

    public void setTakeOffTimeField(JTextField takeOffTimeField) {
        this.takeOffTimeField = takeOffTimeField;
    }

    public JRadioButton getVfrRadio() {
        return vfrRadio;
    }

    public void setVfrRadio(JRadioButton vfrRadio) {
        this.vfrRadio = vfrRadio;
    }



}
