package com.mvp.utils;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.intellij.util.IncorrectOperationException;

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

    private String packageName;
    private String className;
    private PsiDirectory directory;

    //
    private String contractPackageName;
    private String presenterPackageName;


    public PrintUtils(String packageName, String className, PsiDirectory directory) {
        this.packageName = packageName;
        this.className = className;
        this.directory = directory;
        init();
    }

    private void init() {
        createFolder(directory, PACKAGE_PRESENTER);
        createFolder(directory, PACKAGE_CONTRACT);

        presenterPackageName = getPackageByFolderName(PACKAGE_PRESENTER);
        contractPackageName = getPackageByFolderName(PACKAGE_CONTRACT);

    }

    public void print(Vector presenter, Vector view, OnPrintListener listener) {

        System.out.println("===============Contract=============");
        String contract = buildContract(presenter, view);
        System.out.println(contract);
        saveAsFileWriter(getPathByFolderName(PACKAGE_CONTRACT), "I" + className + "Contract.java", contract);

        System.out.println("===============Presenter=============");
        String str = buildPresenter(presenter, view);
        System.out.println(str);
        saveAsFileWriter(getPathByFolderName(PACKAGE_PRESENTER), className + "Presenter.java", str);

        listener.onComplete();

    }

    private String buildContract(Vector presenter, Vector view) {

        String str = "package " + contractPackageName + ";" + newLine + newLine +
                "public interface I" + className + "Contract { " + newLine;
        str += buildMethod("Presenter", presenter) + newLine + newLine;
        str += buildMethod("View", view) + newLine;
        str += "}";

        return str;
    }

    private String buildPresenter(Vector presenter, Vector view) {

        String contract = "I" + className + "Contract";

        String str = "package " + presenterPackageName + ";" + newLine + newLine +
                "public class " + className + "Presenter extends BasePresenter implements " + contract + ".Presenter {" + newLine + newLine +
                tabLine + "private " + contract + ".View view;" + newLine + newLine;
        for (Object aPresenter : presenter) {
            str += buildPresenterMethod((Vector) aPresenter) + newLine + newLine;
        }
        str += "}";

        return str;

    }

    private String buildMethod(String parentName, Vector view) {

        String str = tabLine + "interface " + parentName + (parentName.equals("Presenter") ? " extends IBaseContract.IBasePresenter " : "") + " { " + newLine;

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

    private void createFolder(PsiDirectory directory, String folderName) {
        if (!directory.isValid()) {
            directory.createSubdirectory(folderName);
        }
    }

    //
    private String getPathByFolderName(String folderName) {
        String path = "";

        for (int i = 0; i < directory.getChildren().length; i++) {
            if (((PsiJavaDirectoryImpl) directory.getChildren()[i]).getName().equals(folderName)) {
                path = ((PsiJavaDirectoryImpl) directory.getChildren()[i]).getVirtualFile().getPath();
                break;
            }
        }

        return path;
    }

    private String getPackageByFolderName(String folderName) {

        return packageName.substring(0, packageName.lastIndexOf(".")) + "." + folderName;
    }

    private void saveAsFileWriter(String saveFile, String saveName, String content) {

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(saveFile + "/" + saveName);
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

    // call back
    public interface OnPrintListener {
        void onComplete();

        void onFail();
    }

}
