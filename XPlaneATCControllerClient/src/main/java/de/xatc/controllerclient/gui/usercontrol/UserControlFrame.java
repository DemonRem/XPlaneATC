package de.xatc.controllerclient.gui.usercontrol;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import de.xatc.commons.db.sharedentities.user.UserRole;
import de.xatc.commons.networkpackets.atc.usermgt.DeleteUser;
import de.xatc.commons.networkpackets.atc.usermgt.NewUser;
import de.xatc.commons.networkpackets.atc.usermgt.RequestUserList;
import de.xatc.commons.networkpackets.atc.usermgt.UpdateUser;
import de.xatc.controllerclient.config.XHSConfig;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class UserControlFrame extends javax.swing.JFrame {

    private static final Logger LOG = Logger.getLogger(UserControlFrame.class.getName());
    /**
     * Creates new form UserControlFrame
     */
    public UserControlFrame() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tableScrollPane = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        refresButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        usernameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        roleComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        lockedCheckbox = new javax.swing.JCheckBox();
        newUserButton = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("ATC User Control");

        jTable2.setModel(getUserData());
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        tableScrollPane.setViewportView(jTable2);

        refresButton.setText("refresh");
        refresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresButtonActionPerformed(evt);
            }
        });

        closeButton.setText("close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        roleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "REGISTEREDUSER", "ADMINISTRATOR", "CONTROLLER", "VISITOR" }));
        roleComboBox.setSelectedIndex(1);
        roleComboBox.setAutoscrolls(true);

        jLabel4.setText("Role");

        saveButton.setText("save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        lockedCheckbox.setText("locked");

        newUserButton.setText("new");
        newUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(refresButton)
                .addGap(18, 18, 18)
                .addComponent(closeButton)
                .addContainerGap(532, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(usernameField)
                            .addComponent(jLabel3)
                            .addComponent(passwordField)
                            .addComponent(roleComboBox, 0, 148, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lockedCheckbox)
                            .addComponent(newUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lockedCheckbox)
                        .addGap(2, 2, 2)
                        .addComponent(newUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refresButton)
                    .addComponent(closeButton))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        XHSConfig.setUserControlFrame(null);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        XHSConfig.setUserControlFrame(null);
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void refresButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresButtonActionPerformed
        if (XHSConfig.getDataClient() == null) {
            SwingTools.alertWindow("Not connected!", this);
            return;
        }
        XHSConfig.getDataClient().writeMessage(new RequestUserList());
    }//GEN-LAST:event_refresButtonActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        LOG.debug(this.jTable2.getSelectedRow());
        LOG.debug(jTable2.getValueAt(jTable2.getSelectedRow(), 1));
        this.usernameField.setText((String) jTable2.getValueAt(jTable2.getSelectedRow(), 0));

        this.roleComboBox.setSelectedItem(jTable2.getValueAt(jTable2.getSelectedRow(), 1));

        this.lockedCheckbox.setSelected((Boolean) jTable2.getValueAt(jTable2.getSelectedRow(), 3));

        this.usernameField.setEnabled(false);
        this.newButtonPressed = false;
        
        

        this.revalidate();
        this.repaint();


    }//GEN-LAST:event_jTable2MouseClicked

    private void newUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newUserButtonActionPerformed
        this.newButtonPressed = true;
        this.usernameField.setEnabled(true);
        this.usernameField.setText("");
        this.passwordField.setText("");
        this.roleComboBox.setSelectedItem("REGISTEREDUSER");
        this.usernameField.requestFocus();
        
    }//GEN-LAST:event_newUserButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed

        if (this.jTable2.getSelectedRowCount() == 0) {
            SwingTools.alertWindow("No User selected", this);
            return;
        }
        String userName = (String) jTable2.getValueAt(jTable2.getSelectedRow(), 0);
        if (userName == null) {
            SwingTools.alertWindow("No Username found to delete", this);
            return;
        }
        DeleteUser d = new DeleteUser();
        d.setUserName(userName);
        if (XHSConfig.getDataClient() != null) {
            XHSConfig.getDataClient().writeMessage(d);
        } else {
            SwingTools.alertWindow("Not connected!", this);
        }
        
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

        if (XHSConfig.getDataClient() == null) {
            SwingTools.alertWindow("Not connected!", this);
            return;
        }
        if (StringUtils.isEmpty(this.usernameField.getText())) {
            SwingTools.alertWindow("Please enter a valid Username", this);
            return;
        }

        if (this.newButtonPressed) {

            if (StringUtils.isEmpty(String.valueOf(this.passwordField.getPassword()))) {
                SwingTools.alertWindow("Please enter a password", this);
                return;
            }

            RegisteredUser u = new RegisteredUser();
            u.setRegisteredUserName(this.usernameField.getText());
            u.setPassword(String.valueOf(this.passwordField.getPassword()));
            u.setUserRole(UserRole.valueOf((String) this.roleComboBox.getSelectedItem()));
            u.setLocked(this.lockedCheckbox.isSelected());
            NewUser n = new NewUser();
            n.setUser(u);

            XHSConfig.getDataClient().writeMessage(n);

        } else {

            RegisteredUser u = new RegisteredUser();
            u.setRegisteredUserName(this.usernameField.getText());
            if (StringUtils.isEmpty(String.valueOf(this.passwordField.getPassword()))) {
                u.setPassword(null);
                return;
            } else {

                u.setPassword(String.valueOf(this.passwordField.getPassword()));
            }
            u.setUserRole(UserRole.valueOf((String) this.roleComboBox.getSelectedItem()));
            u.setLocked(this.lockedCheckbox.isSelected());
            UpdateUser update = new UpdateUser();
            update.setUser(u);
            XHSConfig.getDataClient().writeMessage(update);
           
        }


    }//GEN-LAST:event_saveButtonActionPerformed

    public DefaultTableModel getUserData() {

        DefaultTableModel t = new DefaultTableModel();

        return t;

    }

    public JTable getjTable1() {
        return jTable1;
    }

    public void setjTable1(JTable jTable1) {
        this.jTable1 = jTable1;
    }

    public JTable getjTable2() {
        return jTable2;
    }

    public void setjTable2(JTable jTable2) {
        this.jTable2 = jTable2;
    }

    private boolean newButtonPressed = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JCheckBox lockedCheckbox;
    private javax.swing.JButton newUserButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton refresButton;
    private javax.swing.JComboBox<String> roleComboBox;
    private javax.swing.JButton saveButton;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
