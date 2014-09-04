package nju.data;

import nju.Path;
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
        OracleMatrix ansrMatrix = new OracleMatrix(Path.ORACLE_TXT);
        FileFilter fileFilter = new FileFilter(ansrMatrix);
        fileFilter.filterJavaFiles(Path.JAVA, Path.INVOLVED_JAVA);
        fileFilter.filterJspFiles(Path.JSP, Path.INVOLVED_JSP);
        fileFilter.filterUsecases(Path.ORIGIN_REQUIREMENT, Path.INVOLVED_REQUIREMENT);
    }
}