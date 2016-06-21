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
    private String packageName;
    private String currentPath;

    public MvpEditor(PsiFile file, PsiClass psiClass) throws HeadlessException {
        this.currentFile = file;
        this.currentClass = psiClass;
        initForm();
        initGeneratePanel();
        initTable();
        initAction();
    }

    private void initForm() {
        setContentPane(rootPanel);
        setTitle("Mvp Generate Editor");

    }

    private void initGeneratePanel() {
        packageName = ((PsiJavaFileImpl) currentFile).getPackageName();
        currentPath = "";
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

        printUtils = new PrintUtils(packageName, currentClass.getName(), currentFile.getParent().getParent());

        Vector p = ((DefaultTableModel) presenter.getModel()).getDataVector();
        Vector v = ((DefaultTableModel) view.getModel()).getDataVector();

        printUtils.print(p, v, new PrintUtils.OnPrintListener() {
            @Override
            public void onComplete() {
                cancelEditor();
            }

            @Override
            public void onFail() {

            }
        });
    }

}
