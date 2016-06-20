package com.mvp.action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.mvp.gui.MvpEditor;

/**
 */
public class MvpEditorAction extends BaseGenerateAction {

    public MvpEditorAction() {
        super(null);
    }

    public MvpEditorAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project mProject = event.getData(PlatformDataKeys.PROJECT);

        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        PsiFile mFile = PsiUtilBase.getPsiFileInEditor(editor, mProject);
        PsiClass mClass = getTargetClass(editor, mFile);

        MvpEditor mvp = new MvpEditor(mFile, mClass);
        mvp.setSize(600, 400);
        mvp.setLocationRelativeTo(null);
        mvp.setVisible(true);
    }
}
