
package de.xatc.controllerclient.gui.setupatc;

import de.xatc.commons.networkpackets.atc.stations.SupportedFirStation;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Mirko
 */


public class SelectedStationListCellRenderer implements ListCellRenderer<Object> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        
        
        
        if (value instanceof SupportedFirStation) {
            SupportedFirStation fir = (SupportedFirStation) value;
            return this.createFirRenderPanel(fir);
        }
        return null;
        
        
    }

    private JPanel createFirRenderPanel(SupportedFirStation fir) {
        
        
        JPanel p = new JPanel();
        
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel freqLabel = new JLabel(fir.getFrequency());
        JLabel nameLabel = new JLabel(fir.getFir().getFirNameIcao() + " - " + fir.getFir().getFirName());
        JTextArea a = new JTextArea();
        a.setLineWrap(true);
        a.setText(fir.getFirMessage());
        System.out.println(fir.getFirMessage());
        a.setMaximumSize(new Dimension(400,50));
        a.setPreferredSize(new Dimension(400,50));
        a.setForeground(Color.BLACK);
        a.setBackground(Color.LIGHT_GRAY);
        a.setEditable(false);
        
        p.add(freqLabel);
        p.add(nameLabel);
        p.add(a);
        return p;
        
        
        
    }
    
}
