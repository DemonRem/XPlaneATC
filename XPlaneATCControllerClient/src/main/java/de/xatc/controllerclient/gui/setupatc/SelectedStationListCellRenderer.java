
package de.xatc.controllerclient.gui.setupatc;

import de.xatc.commons.networkpackets.atc.stations.SupportedAirportStation;
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
            System.out.println("FIRSTATION");
            return this.createFirRenderPanel(fir, isSelected, cellHasFocus);
        }
        else if (value instanceof SupportedAirportStation) {
            SupportedAirportStation airport = (SupportedAirportStation)value;
            System.out.println("Airportstation");
            return this.createAirportRenderPanel(airport, isSelected, cellHasFocus);
        }
        return null;
        
        
    }
    
    private JPanel createAirportRenderPanel(SupportedAirportStation airport, boolean selected, boolean focus) {
        
        JPanel p = new JPanel();
        if (selected) {
            p.setBackground(Color.RED);
        }
        
        if (focus) {
            p.setBackground(Color.LIGHT_GRAY);
        }
        else {
            p.setBackground(Color.DARK_GRAY);
        }
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel freqLabel = new JLabel(airport.getStationFrequency());
        JLabel nameLabel = new JLabel(airport.getAirport().getAirportName() + " - " + airport.getAirport().getAirportIcao());
        p.add(freqLabel);
        p.add(nameLabel);
        
        JLabel atisFreq = new JLabel(airport.getAtis().getAtisFrequency());
        p.add(atisFreq);
        
        JTextArea a = new JTextArea();
        a.setLineWrap(true);
        a.setText(airport.getAtis().getAtisMessage());
        
        a.setMaximumSize(new Dimension(400,50));
        a.setPreferredSize(new Dimension(400,50));
        a.setForeground(Color.BLACK);
        a.setBackground(Color.LIGHT_GRAY);
        a.setEditable(false);
        p.add(a);
        JLabel rangeLabel = new JLabel("RANGE: " + airport.getVisibility());
        p.add(rangeLabel);
        
        
        
        return p;
    }
    
    private JPanel createFirRenderPanel(SupportedFirStation fir, boolean isSelected, boolean hasFocus) {
        
        
        JPanel p = new JPanel();
        if (isSelected) {
            p.setBackground(Color.RED);
        }
        
        if (hasFocus) {
            p.setBackground(Color.LIGHT_GRAY);
        }
        else {
            p.setBackground(Color.DARK_GRAY);
        }
        
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel freqLabel = new JLabel(fir.getFrequency());
        JLabel nameLabel = new JLabel(fir.getFir().getFirNameIcao() + " - " + fir.getFir().getFirName());
        JTextArea a = new JTextArea();
        a.setLineWrap(true);
        a.setText(fir.getFirMessage());
        
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
