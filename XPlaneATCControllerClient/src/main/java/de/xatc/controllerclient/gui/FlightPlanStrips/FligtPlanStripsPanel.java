 
package de.xatc.controllerclient.gui.FlightPlanStrips;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.client.SubmittedFlightPlansActionPacket;
import de.xatc.controllerclient.config.XHSConfig;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

/**
 *
 * @author Mirko
 */


public class FligtPlanStripsPanel extends javax.swing.JPanel {

    
    private int serversID;
    /**
     * Creates new form NewJPanel
     */
    public FligtPlanStripsPanel() {
        initComponents();
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
        userNameLabel = new javax.swing.JLabel();
        flightNumberLabel = new javax.swing.JLabel();
        aircraftTypeLabel = new javax.swing.JLabel();
        flightLevelLabel = new javax.swing.JLabel();
        ifrVfrLabel = new javax.swing.JLabel();
        airlineLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        currentSpeedLabel = new javax.swing.JLabel();
        currentAltLabel = new javax.swing.JLabel();
        currentHeadingLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        fromIcaoLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        toIcaoLabel = new javax.swing.JLabel();
        departureTimeLabel = new javax.swing.JLabel();
        arrivalTimeLabel = new javax.swing.JLabel();
        routeLabel = new javax.swing.JLabel();
        remarkLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentsTextArea = new javax.swing.JTextArea();
        revokeButton = new javax.swing.JButton();
        assignButton = new javax.swing.JButton();
        sendMessageButton = new javax.swing.JButton();
        contactMeButton = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        userNameLabel.setText("userName");

        flightNumberLabel.setText("flightnumber");

        aircraftTypeLabel.setText("aircraftType");

        flightLevelLabel.setText("flightlevel");

        ifrVfrLabel.setText("IFR");

        airlineLabel.setText("airline");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(flightNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aircraftTypeLabel)
                            .addComponent(flightLevelLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(airlineLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ifrVfrLabel))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(userNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flightNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aircraftTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(flightLevelLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ifrVfrLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(airlineLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        currentSpeedLabel.setText("currentSpeed");

        currentAltLabel.setText("currentAlt");

        currentHeadingLabel.setText("currentHeading");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(currentAltLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(currentHeadingLabel)
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(currentSpeedLabel)
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentAltLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(currentHeadingLabel)
                .addGap(23, 23, 23))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        fromIcaoLabel.setText("fromICAO");

        jLabel2.setText("->");

        toIcaoLabel.setText("toICAO");

        departureTimeLabel.setText("depTime");

        arrivalTimeLabel.setText("arrTime");

        routeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        routeLabel.setText("Route");
        routeLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        routeLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        remarkLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        remarkLabel.setText("remark");
        remarkLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(fromIcaoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toIcaoLabel)
                .addGap(18, 18, 18)
                .addComponent(departureTimeLabel)
                .addGap(18, 18, 18)
                .addComponent(arrivalTimeLabel)
                .addGap(0, 44, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(routeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(remarkLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fromIcaoLabel)
                    .addComponent(jLabel2)
                    .addComponent(toIcaoLabel)
                    .addComponent(departureTimeLabel)
                    .addComponent(arrivalTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(routeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remarkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        commentsTextArea.setColumns(20);
        commentsTextArea.setRows(5);
        jScrollPane1.setViewportView(commentsTextArea);

        revokeButton.setText("revoke");
        revokeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revokeButtonActionPerformed(evt);
            }
        });

        assignButton.setText("assign");

        sendMessageButton.setText("send msg");

        contactMeButton.setText("cnt me");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(assignButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendMessageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactMeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(revokeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(revokeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assignButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendMessageButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(contactMeButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void revokeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revokeButtonActionPerformed
        SubmittedFlightPlansActionPacket action = new SubmittedFlightPlansActionPacket();
        action.setServersID(this.serversID);
        action.setAction("revoke");
        if (XHSConfig.getDataClient() == null) {
            SwingTools.alertWindow("Not Connected!", XHSConfig.getSubmittedFlightPlansPoolFrame());
            return;
        }
        XHSConfig.getDataClient().writeMessage(action);
    }//GEN-LAST:event_revokeButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aircraftTypeLabel;
    private javax.swing.JLabel airlineLabel;
    private javax.swing.JLabel arrivalTimeLabel;
    private javax.swing.JButton assignButton;
    private javax.swing.JTextArea commentsTextArea;
    private javax.swing.JButton contactMeButton;
    private javax.swing.JLabel currentAltLabel;
    private javax.swing.JLabel currentHeadingLabel;
    private javax.swing.JLabel currentSpeedLabel;
    private javax.swing.JLabel departureTimeLabel;
    private javax.swing.JLabel flightLevelLabel;
    private javax.swing.JLabel flightNumberLabel;
    private javax.swing.JLabel fromIcaoLabel;
    private javax.swing.JLabel ifrVfrLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel remarkLabel;
    private javax.swing.JButton revokeButton;
    private javax.swing.JLabel routeLabel;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JLabel toIcaoLabel;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables

    public JLabel getAircraftTypeLabel() {
        return aircraftTypeLabel;
    }

    public void setAircraftTypeLabel(JLabel aircraftTypeLabel) {
        this.aircraftTypeLabel = aircraftTypeLabel;
    }

    public JLabel getAirlineLabel() {
        return airlineLabel;
    }

    public void setAirlineLabel(JLabel airlineLabel) {
        this.airlineLabel = airlineLabel;
    }

    public JLabel getArrivalTimeLabel() {
        return arrivalTimeLabel;
    }

    public void setArrivalTimeLabel(JLabel arrivalTimeLabel) {
        this.arrivalTimeLabel = arrivalTimeLabel;
    }

    public JButton getAssignButton() {
        return assignButton;
    }

    public void setAssignButton(JButton assignButton) {
        this.assignButton = assignButton;
    }

    public JTextArea getCommentsTextArea() {
        return commentsTextArea;
    }

    public void setCommentsTextArea(JTextArea commentsTextArea) {
        this.commentsTextArea = commentsTextArea;
    }

    public JButton getContactMeButton() {
        return contactMeButton;
    }

    public void setContactMeButton(JButton contactMeButton) {
        this.contactMeButton = contactMeButton;
    }

    public JLabel getCurrentAltLabel() {
        return currentAltLabel;
    }

    public void setCurrentAltLabel(JLabel currentAltLabel) {
        this.currentAltLabel = currentAltLabel;
    }

    public JLabel getCurrentHeadingLabel() {
        return currentHeadingLabel;
    }

    public void setCurrentHeadingLabel(JLabel currentHeadingLabel) {
        this.currentHeadingLabel = currentHeadingLabel;
    }

    public JLabel getCurrentSpeedLabel() {
        return currentSpeedLabel;
    }

    public void setCurrentSpeedLabel(JLabel currentSpeedLabel) {
        this.currentSpeedLabel = currentSpeedLabel;
    }

    public JLabel getDepartureTimeLabel() {
        return departureTimeLabel;
    }

    public void setDepartureTimeLabel(JLabel departureTimeLabel) {
        this.departureTimeLabel = departureTimeLabel;
    }

    public JLabel getFlightLevelLabel() {
        return flightLevelLabel;
    }

    public void setFlightLevelLabel(JLabel flightLevelLabel) {
        this.flightLevelLabel = flightLevelLabel;
    }

    public JLabel getFlightNumberLabel() {
        return flightNumberLabel;
    }

    public void setFlightNumberLabel(JLabel flightNumberLabel) {
        this.flightNumberLabel = flightNumberLabel;
    }

    public JLabel getFromIcaoLabel() {
        return fromIcaoLabel;
    }

    public void setFromIcaoLabel(JLabel fromIcaoLabel) {
        this.fromIcaoLabel = fromIcaoLabel;
    }

    public JLabel getIfrVfrLabel() {
        return ifrVfrLabel;
    }

    public void setIfrVfrLabel(JLabel ifrVfrLabel) {
        this.ifrVfrLabel = ifrVfrLabel;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
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

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JSeparator getjSeparator1() {
        return jSeparator1;
    }

    public void setjSeparator1(JSeparator jSeparator1) {
        this.jSeparator1 = jSeparator1;
    }

    public JSeparator getjSeparator2() {
        return jSeparator2;
    }

    public void setjSeparator2(JSeparator jSeparator2) {
        this.jSeparator2 = jSeparator2;
    }

    public JLabel getRemarkLabel() {
        return remarkLabel;
    }

    public void setRemarkLabel(JLabel remarkLabel) {
        this.remarkLabel = remarkLabel;
    }

    public JButton getRevokeButton() {
        return revokeButton;
    }

    public void setRevokeButton(JButton revokeButton) {
        this.revokeButton = revokeButton;
    }

    public JLabel getRouteLabel() {
        return routeLabel;
    }

    public void setRouteLabel(JLabel routeLabel) {
        this.routeLabel = routeLabel;
    }

    public JButton getSendMessageButton() {
        return sendMessageButton;
    }

    public void setSendMessageButton(JButton sendMessageButton) {
        this.sendMessageButton = sendMessageButton;
    }

    public JLabel getToIcaoLabel() {
        return toIcaoLabel;
    }

    public void setToIcaoLabel(JLabel toIcaoLabel) {
        this.toIcaoLabel = toIcaoLabel;
    }

    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

    public void setUserNameLabel(JLabel userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    public int getServersID() {
        return serversID;
    }

    public void setServersID(int serversID) {
        this.serversID = serversID;
    }

}
