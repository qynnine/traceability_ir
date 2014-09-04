package nju.data;

import nju.Constants;
import nju._;
import nju.data.model.OracleMatrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by niejia on 14-8-26.
 * Extract files mentioned in answer matrix from the whole itrust project
 */

public class FileFilter {
    private OracleMatrix ansrMatrix;

    public FileFilter(OracleMatrix ansrMatrix) {
        this.ansrMatrix = ansrMatrix;
    }

    public void filterJavaFiles(String sourcePath, String targetPath) {
        List<File> files = filterFiles(sourcePath, ansrMatrix.getKlass(), "java");
//        System.out.println(files.size());
        exportFiles(sourcePath, targetPath, files);
    }

    public void filterJspFiles(String sourcePath, String targetPath) {
        List<File> files = filterFiles(sourcePath, ansrMatrix.getJsp(), "jsp");
//        System.out.println(files.size());
        exportFiles(sourcePath, targetPath, files);
    }

    public void filterUsecases(String sourcePath, String targetPath) {
        List<File> files = filterFiles(sourcePath, ansrMatrix.getUsecase(), "txt");
//        System.out.println(files.size());
        exportFiles(sourcePath, targetPath, files);
    }

    private List<File> filterFiles(String sourcePath, Set<String> involvedFiles, String extension) {
        List<File> currFiles = _.filesInFolder(new File(sourcePath));

        // find all files mentioned in answer matrix
        List<File> targetFiles = new ArrayList<>();
        for (File f : currFiles) {
            if (f.getPath().endsWith("." + extension)) {
                String name = f.getName().substring(0, f.getName().indexOf("."));
                if (involvedFiles.contains(name)) {
                    targetFiles.add(f);
                }
            }
        }

        return targetFiles;
    }

    private void exportFiles(String sourcePath, String targetPath, List<File> files) {
        for (File f : files) {
            String exportPath = targetPath + f.getName();
            _.writeFile(_.readFile(f.getPath()), exportPath);
        }
    }

    public static void main(String[] args) {
        OracleMatrix ansrMatrix = new OracleMatrix(Constants.REQS_PATH);
        FileFilter fileFilter = new FileFilter(ansrMatrix);
        fileFilter.filterJavaFiles(Constants.JAVA_PATH, Constants.INVOLVED_JAVA_PATH);
        fileFilter.filterJspFiles(Constants.JSP_PATH, Constants.INVOLVED_JSP_PATH);
        fileFilter.filterUsecases(Constants.REQUIREMENT_PATH, Constants.INVOLVED_REQUIREMENT_PATH);
    }
}