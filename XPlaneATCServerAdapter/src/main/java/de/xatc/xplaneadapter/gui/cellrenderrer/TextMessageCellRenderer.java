/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.gui.cellrenderrer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Mirko
 */
public class TextMessageCellRenderer
        extends DefaultListCellRenderer {

    public static final String HTML_1 = "<html><body style='width: ";
    public static final String HTML_2 = "px'>";
    public static final String HTML_3 = "</html>";
    private int width;

    public TextMessageCellRenderer(int width) {
        this.width = width;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        String text = HTML_1 + String.valueOf(width) + HTML_2 + value.toString()
                + HTML_3;
        return super.getListCellRendererComponent(list, text, index, isSelected,
                cellHasFocus);
    }

}
