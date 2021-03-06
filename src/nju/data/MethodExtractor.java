package nju.data;

import nju.Path;
import nju._;
import nju.data.model.Method;
import nju.data.model.OracleMatrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by niejia on 14-8-26.
 */
public class MethodExtractor {

    private List<File> filesList;
    private List<Method> methods;
    private String sourcePath;

    public MethodExtractor(String sourcePath, Set<String> methodInOracle) {
        this.sourcePath = sourcePath;
        methods = new ArrayList<>();
        filesList = _.filterFile(sourcePath, "java");

        for (File f : filesList) {
            List<Method> methodsInKlass = JavaParser.extractMethods(f.getPath());
            for (Method m : methodsInKlass) {
                if (methodInOracle.contains(m.getId())) {
                    methods.add(m);
                }
            }
        }
    }

    public void exportMethods(String path) {
        for (Method m : methods) {
            String outputPath = path + m.getKlass() + "_"+m.getFileName();
            _.writeFile(m.toString(), outputPath);
        }
    }

    public List<Method> getMethods() {
        return methods;
    }

    public static void main(String[] args) {
        OracleMatrix ansrMatrix = new OracleMatrix(Path.ORACLE_LITE_TXT);
        MethodExtractor methodExtractor = new MethodExtractor(Path.INVOLVED_JAVA, ansrMatrix.getMethod());
        methodExtractor.exportMethods(Path.TRACELAB_JAVA);
    }
}