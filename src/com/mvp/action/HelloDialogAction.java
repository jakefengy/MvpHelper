package com.mvp.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.mvp.dialog.MvpDialog;

/**
 */
public class HelloDialogAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Application application = ApplicationManager.getApplication();
        MvpDialog dialog = application.getComponent(MvpDialog.class);
        dialog.pack();
        dialog.setVisible(true);
    }
}
