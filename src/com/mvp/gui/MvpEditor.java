package com.mvp.gui;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.ui.table.JBTable;
import com.mvp.utils.PrintUtils;
import com.mvp.utils.TableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 */
public class MvpEditor extends JFrame {
    private PsiFile currentFile;
    private PsiClass currentClass;

    private JPanel rootPanel;

    private JLabel lbPackage;

    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnCancel;
    private JButton btnOk;

    private int deleteRowIndex = -1;

    // Presenter Panel
    private JPanel presenterPanel;
    private JTable presenterTable;

    // View Panel
    private JPanel viewPanel;
    private JTable viewTable;

    // Print
    private PrintUtils printUtils;

    public MvpEditor(PsiFile file, PsiClass psiClass) throws HeadlessException {
        this.currentFile = file;
        this.currentClass = psiClass;
        initForm();
        initGeneratePanel();
        initTable();
        initAction();
        printUtils = new PrintUtils(currentFile, currentClass);
    }

    private void initForm() {
        setContentPane(rootPanel);
        setTitle("Mvp Generate Editor");

    }

    private void initGeneratePanel() {
        lbPackage.setText(((PsiJavaFileImpl) currentFile).getPackageName() + "." + currentFile.getName().split("\\.")[0]);
    }

    private void initAction() {
        btnAdd.addActionListener(event -> {
                    TableUtils.addEmptyRow(presenterTable, "");
                    TableUtils.addEmptyRow(viewTable, "(boolean success)");
                }
        );

        btnDelete.addActionListener(event -> {

            final int index = presenterTable.getSelectedRow();

            if (index >= 0) {
                TableUtils.deleteRow(presenterTable, index);
                TableUtils.deleteRow(viewTable, index);
            }
        });

        btnOk.addActionListener(e -> print(presenterTable, viewTable));

        btnCancel.addActionListener(event -> cancelEditor());

    }

    private void initTable() {
        presenterTable = new JBTable();
        viewTable = new JBTable();
        TableUtils.buildTable(presenterPanel, presenterTable, 1, "");
        TableUtils.buildTable(viewPanel, viewTable, 1, "(boolean success)");
    }

    private void cancelEditor() {
        setVisible(false);
        dispose();
    }

    // Output
    private void print(JTable presenter, JTable view) {

        printUtils.print(((DefaultTableModel) presenter.getModel()).getDataVector(), ((DefaultTableModel) view.getModel()).getDataVector());
    }

    private void println(Object msg) {
        System.out.println(msg);
    }

}
