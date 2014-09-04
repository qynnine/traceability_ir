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
 */
public class JspExtractor {
    private List<File> filesList;
    private List<String> jsp;
    private String sourcePath;

    public JspExtractor(String sourcePath, Set<String> jspInOracle) {
        this.sourcePath = sourcePath;
        jsp = new ArrayList<>();
        filesList = _.filterFile(sourcePath, "jsp");

        for (File f : filesList) {
            String name = f.getName().replace(".jsp", "");
            if (jspInOracle.contains(name)){
                jsp.add(name);
            }
        }
//        System.out.println(jsp.size());
    }

    public void exportJsp(String path) {
        for (String j : jsp) {
            String fileName = j + ".jsp";
            String inputPath = sourcePath + fileName;
            String outputPath = path + fileName;
            _.writeFile(_.readFile(inputPath), outputPath);
        }
    }

    public List<String> getJsp() {
        return jsp;
    }

    public static void main(String[] args) {
        OracleMatrix ansrMatrix = new OracleMatrix(Path.ORACLE_TXT);
        JspExtractor jspExtractor = new JspExtractor(Path.INVOLVED_JSP, ansrMatrix.getJsp());
        jspExtractor.exportJsp(Path.TRACELAB_JSP);
    }
}
