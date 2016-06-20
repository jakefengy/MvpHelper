package com.mvp.utils;

import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 */
public class TableUtils {

    public static void buildTable(JPanel panel, JTable table, int defRowCount, String defValue) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Method");
        model.addColumn("Params");

        table.setModel(model);

        JScrollPane scrollPane = new JBScrollPane(table);

        panel.add(scrollPane);

        for (int i = 0; i < defRowCount; i++) {
            addEmptyRow(table, defValue);
        }

    }

    public static void addEmptyRow(JTable table, String defValue) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"", defValue});
    }

    public static void deleteRow(JTable table, int rowIndex) {
        ((DefaultTableModel) table.getModel()).removeRow(rowIndex);
    }

}
