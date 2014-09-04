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
        OracleMatrix ansrMatrix = new OracleMatrix(Constants.REQS_PATH);
        JspExtractor jspExtractor = new JspExtractor(Constants.INVOLVED_JSP_PATH, ansrMatrix.getJsp());
        jspExtractor.exportJsp(Constants.TRACELAB_JSP_PATH);
    }
}
