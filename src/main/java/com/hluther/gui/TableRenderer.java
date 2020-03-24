package com.hluther.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author helmuth
 */
public class TableRenderer extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value instanceof JLabel){
            JLabel label = (JLabel)value;
            switch(row){
                case 0: 
                    label.setBackground(Color.blue);
                break;
                case 1:
                    label.setBackground(Color.yellow);
                break;
                case 2:
                    label.setBackground(Color.green);
                break;
                case 3:
                    label.setBackground(Color.red);
                break;
                case 4:
                    label.setBackground(Color.orange);
                break;
                case 5:
                    label.setBackground(Color.lightGray);
                break;
                case 6:
                    label.setBackground(Color.cyan);
                break;
                case 7:
                    label.setBackground(Color.darkGray);
                break;
                case 8:
                    label.setBackground(Color.white);
                break;
                case 9:
                    label.setBackground(Color.magenta);
                break;      
            }
            return label;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
