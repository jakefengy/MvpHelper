package com.mvp.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 */
public class PrintUtils {

    private final static String PACKAGE_PRESENTER = "presenter";
    private final static String PACKAGE_CONTRACT = "contract";

    private String newLine = System.getProperty("line.separator");
    private String tabLine = "\t";

    private String parentDir;
    private String packageName;
    private PsiFile currentFile;
    private PsiClass currentClass;

    //
    private String contractPackageName;
    private String presenterPackageName;


    public PrintUtils(PsiFile currentFile, PsiClass currentClass) {
//        this.parentDir = currentFile.getParent().getParent();
        this.packageName = ((PsiJavaFileImpl) currentFile.getParent()).getPackageName();
        this.currentFile = currentFile;
        this.currentClass = currentClass;
        init();
    }

    private void init() {
        presenterPackageName = currentFile.getParent().getParent().createSubdirectory(PACKAGE_PRESENTER).getName();
        contractPackageName = currentFile.getParent().getParent().createSubdirectory(PACKAGE_CONTRACT).getName();

    }

    public void print(Vector presenter, Vector view) {

        System.out.println("===============Contract=============");
        String contract = buildContract(presenter, view);
        System.out.println(contract);
        saveAsFileWriter("", contract);

        System.out.println("===============Presenter=============");
        String str = buildPresenter(presenter, view);
        System.out.println(str);
        saveAsFileWriter("", str);

    }

    private String buildContract(Vector presenter, Vector view) {

        String str = "package " + presenterPackageName + ";" + newLine + newLine +
                "public interface I" + currentClass.getName() + "Contract { " + newLine;
        str += buildMethod("Presenter", presenter) + newLine + newLine;
        str += buildMethod("View", view) + newLine;
        str += "}";

        return str;
    }

    private String buildPresenter(Vector presenter, Vector view) {

        String contract = "I" + currentClass.getName() + "Contract";

        String str = "package " + contractPackageName + ";" + newLine + newLine +
                "public class " + currentClass.getName() + "Presenter extends BasePresenter implements " + contract + ".Presenter {" + newLine + newLine +
                tabLine + "private " + contract + ".View view;" + newLine + newLine;
        for (Object aPresenter : presenter) {
            str += buildPresenterMethod((Vector) aPresenter) + newLine + newLine;
        }
        str += "}";

        return str;

    }

    private String buildMethod(String parentName, Vector view) {

        String str = tabLine + "interface " + parentName + " { " + newLine;

        for (Object aView : view) {
            str += tabLine + tabLine + buildMethod((Vector) aView) + newLine;
        }

        str += tabLine + "}";

        return str;

    }

    private String buildMethod(Vector data) {
        return "void " + data.get(0) + " " + data.get(1) + ";";
    }

    private String buildPresenterMethod(Vector data) {

        String str = tabLine + "@Override" + newLine;
        str += tabLine + "public void " + data.get(0) + " " + data.get(1) + "{" + newLine + tabLine + "}";

        return str;
    }

    //
    private void saveAsFileWriter(String saveFile, String content) {

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(saveFile);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
