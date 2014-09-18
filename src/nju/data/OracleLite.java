package nju.data;

import nju.Path;
import nju._;
import nju.data.model.OracleMatrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niejia on 14-9-4.
 */
public class OracleLite {

    private String oraclePath;
    private String javaPath;
    private String jspPath;
    private OracleMatrix oracleMatrix;



    public OracleLite(String oraclePath, String javaPath, String jspPath) {
        this.oracleMatrix = new OracleMatrix(oraclePath);
        this.oraclePath = oraclePath;
        this.jspPath = jspPath;
        this.javaPath = javaPath;
    }

    public void export(String outputPath) {
        List<String> jspName = new ArrayList<>();
        List<String> methodName = new ArrayList<>();

        File java_folder = new File(javaPath);
        File jsp_folder = new File(jspPath);

        for (File jsp : _.filesInFolder(jsp_folder)) {
            String fileName = jsp.getName();
            if (fileName.endsWith(".jsp")) {
                String name = fileName.split("\\.")[0];
                String jsp_format = name + "_jsp::_jspService";
                jspName.add(jsp_format);
            }
        }

//        System.out.println(" jspName = " + jspName);

        for (File method : _.filesInFolder(java_folder)) {
            String fileName = method.getName();
            if (fileName.endsWith(".txt")) {
                String name = fileName.split("\\.")[0];
                methodName.add(name.replace("_", "::"));
            }
        }

//        System.out.println(" methodName = " + methodName);


        String oracle = _.readFile(oraclePath);

        List<String> oracleLite = new ArrayList<>();
        String[] lines = oracle.split("\n");

        for (String line : lines) {
            String sourceId = line.split(" ")[0];
            if (jspName.contains(sourceId) || methodName.contains(sourceId)) {
                oracleLite.add(line);
            }
        }

        System.out.println("OracleLite Size = "+oracleLite.size());

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < oracleLite.size(); i++) {
            sb.append(oracleLite.get(i));
            sb.append("\n");
        }

        _.writeFile(sb.toString(), outputPath);

    }


    public static void main(String[] args) {

        OracleLite oracleLite = new OracleLite(Path.ORACLE_TXT, Path.TRACELAB_JAVA, Path.TRACELAB_JSP);
        oracleLite.export(Path.ORACLE_LITE_TXT);

//        export(Path.ORACLE_LITE_TXT);
//        OracleMatrix oracleMatrix = new OracleMatrix(Constants.REQS);

//        SimilarityMatrix sm = TXTPaser.createSimilarityMatrix(Path.ORACLE_LITE_TXT);
//        Sqlite3.writeSimilarityMatrixToDB(sm, Path.ORACLE_LITE_DB);
    }
}
