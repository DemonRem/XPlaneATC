/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.beans.sharedgui;

import de.xatc.commons.networkpackets.client.TextMessagePacket;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author C047
 */
public class ChatMessageRenderer implements ListCellRenderer<TextMessagePacket> {

    @Override
    public Component getListCellRendererComponent(JList<? extends TextMessagePacket> list, TextMessagePacket value, int index, boolean isSelected, boolean cellHasFocus) {
        
         list.revalidate();
        int width = list.getWidth();
        System.out.println("WINDOW WIDTH: " + width);
        
        
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder(value.getFromFAlias()));
        
        
        String labelText = labelText = String.format("<html><div WIDTH=%d>%s</div><html>", width-20, value.getMessage());
        
        
        JLabel l = new JLabel();
        l.setBorder(new EmptyBorder(0,7,0,10));
        l.setText(labelText);
        p.add(l);
        return p;
        
        
    }

    
}
