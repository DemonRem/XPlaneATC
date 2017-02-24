
package de.xatc.controllerclient.gui.config;

import de.mytools.tools.swing.SwingTools;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.log.DebugMessageLevel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.VerticalLayout;

/**
 * this is the properties panel, where user may set directories
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class PropertiesPanel extends JPanel implements ActionListener {


    private JLabel xplaneFolderTextField;
    private String xplaneFolderValue;


    private JButton saveButton;
  
    private final int maxFolderStartDisplayLength = 10;

    /**
     * max size for the directory written in jlabel
     */
    private final int maxFolderEndDisplayLength = 10;

    /**
     * constructor
     */
    public PropertiesPanel() {

        super();
        initComponents();

    }

    /**
     * init components for gui
     */
    private void initComponents() {

        this.setBorder(BorderFactory.createTitledBorder("Properties"));
        this.setLayout(new VerticalLayout());

        JPanel xPlanePanel = new JPanel();
        xPlanePanel.setLayout(new GridLayout(1, 3));
        JLabel xPlaneLabel = new JLabel("Your X-Plane Folder");
        xplaneFolderTextField = new JLabel();
        xplaneFolderTextField.setForeground(Color.WHITE);
        xplaneFolderTextField.setBackground(Color.BLACK);

        if (!StringUtils.isEmpty(XHSConfig.getConfigBean().getFolder_xplaneFolder())) {
            xplaneFolderTextField.setText(stripLongFolderNames(stripLongFolderNames(XHSConfig.getConfigBean().getFolder_xplaneFolder())));
        }
        JButton xPlaneButton = new JButton("Browse for X-Plane Folder");
        xPlaneButton.addActionListener(this);

        xPlanePanel.add(xPlaneLabel);
        xPlanePanel.add(xplaneFolderTextField);
        xPlanePanel.add(xPlaneButton);
        this.add(xPlanePanel);

        this.add(new JLabel("This is the root installation folder of your X-Plane"));

        this.add(new JSeparator());


        JPanel applyPanel = new JPanel();

        saveButton = new JButton("apply");
        JButton cancelButton = new JButton("cancel");
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        applyPanel.add(saveButton);
        applyPanel.add(cancelButton);
        this.add(applyPanel);
    }

    /**
     * customer action listener for interaction
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        
        XHSConfig.debugMessage("Action peformed", DebugMessageLevel.DEBUG);
        String desc = ae.getActionCommand();
        XHSConfig.debugMessage("Action Command was: " + desc, DebugMessageLevel.DEBUG);

        String homeDir = null;

        if (StringUtils.isEmpty(XHSConfig.getConfigBean().getFolder_xplaneFolder())) {
            homeDir = System.getProperty("user.home");

        } else {
            homeDir = XHSConfig.getConfigBean().getFolder_xplaneFolder();
        }

        if (desc.equals("Browse for X-Plane Folder")) {

            String value = SwingTools.fileChooser(homeDir);
            if (!StringUtils.isEmpty(value)) {
                this.xplaneFolderTextField.setText(stripLongFolderNames(value));

                this.xplaneFolderValue = value;
                
                return;

            } else {
                SwingTools.alertWindow("Folder is Empty!", this);
                return;
            }
        }
        if (desc.equals("cancel")) {
            XHSConfig.getFolderPropertiesFrame().dispose();
            XHSConfig.setFolderPropertiesFrame(null);
            return;
        }
        if (desc.equals("apply")) {
            
            XHSConfig.getConfigBean().setFolder_xplaneFolder(xplaneFolderValue);
            XHSConfig.savePropsFile();
            XHSConfig.getFolderPropertiesFrame().dispose();
            XHSConfig.setFolderPropertiesFrame(null);
            
        }

    }



    /**
     * make long folder names short
     *
     * @param in
     * @return
     */
    private String stripLongFolderNames(String in) {

        int l = in.length();
        if (l <= (this.maxFolderStartDisplayLength + this.maxFolderEndDisplayLength + 3)) {
            return in;
        }
        String startString = in.substring(0, this.maxFolderStartDisplayLength);
        String endString = in.substring(l - this.maxFolderEndDisplayLength);
        return startString + "..." + endString;
    }

    public String getXplaneFolderValue() {
        return xplaneFolderValue;
    }

    public void setXplaneFolderValue(String xplaneFolderValue) {
        this.xplaneFolderValue = xplaneFolderValue;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    
    
    
}
