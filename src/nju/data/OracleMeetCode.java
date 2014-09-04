package nju.data;

import nju.Path;
import nju.Sqlite3;
import nju.component.TXTPaser;
import nju.type.SimilarityMatrix;

/**
 * Created by niejia on 14-9-4.
 */
public class OracleMeetCode {

    public void OracleMeetCode() {
    }

    public static void main(String[] args) {
//        OracleMatrix oracleMatrix = new OracleMatrix(Constants.REQS);
//
//        List<String> jspName = new ArrayList<>();
//        List<String> methodName = new ArrayList<>();
//
//        File jsp_folder = new File(Constants.TRACELAB_JSP_PATH);
//        File java_folder = new File(Constants.TRACELAB_JAVA_PATH);
//
//        for (File jsp : _.filesInFolder(jsp_folder)) {
//            String fileName = jsp.getName();
//            if (fileName.endsWith(".jsp")) {
//                String name = fileName.split("\\.")[0];
//                String jsp_format = name + "_jsp::_jspService";
//                jspName.add(jsp_format);
//            }
//        }
////        System.out.println(" jspName = " + jspName );
//
//
//        for (File method : _.filesInFolder(java_folder)) {
//            String fileName = method.getName();
//            if (fileName.endsWith(".txt")) {
//                String name = fileName.split("\\.")[0];
//                methodName.add(name.replace("_","::"));
//            }
//        }
//
//        System.out.println(" methodName = " + methodName );
//
//
//        String oracle = _.readFile(Constants.Oracle);
//
//        List<String> oracleLite = new ArrayList<>();
//        String[] lines = oracle.split("\n");
//
//        for (String line : lines) {
//            String sourceId = line.split(" ")[0];
//            if (jspName.contains(sourceId) || methodName.contains(sourceId)) {
//                oracleLite.add(line);
//            }
//        }
//
//        System.out.println(oracleLite.size());
//
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < oracleLite.size(); i++) {
//            sb.append(oracleLite.get(i));
//            sb.append("\n");
//        }
//
//        _.writeFile(sb.toString(), Constants.ORACLE_LITE);

        SimilarityMatrix sm = TXTPaser.createSimilarityMatrix(Path.ORACLE_LITE_TXT);
        Sqlite3.writeSimilarityMatrixToDB(sm, Path.ORACLE_LITE_DB);
    }
}
