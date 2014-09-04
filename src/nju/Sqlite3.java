package nju;

import nju.component.TXTPaser;
import nju.type.SimilarityMatrix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niejia on 14-8-28.
 */
public class Sqlite3 {
    private static List<String> usecaseList;

    static {
        // guarantee existied usecase
        usecaseList = new ArrayList<>();
        for (int i = 1; i <= 38; i++) {
            usecaseList.add("UC" + i);
        }
        usecaseList.remove("UC7");
        usecaseList.remove("UC14");
        usecaseList.remove("UC20");
        usecaseList.remove("UC22");
    }

    public static SimilarityMatrix readSimilarityMatrixFromDB(String inputPath) {
        Connection c = null;
        Statement stmt = null;
        StringBuffer sb = new StringBuffer();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + inputPath);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reqs;");

            while (rs.next()) {
                String method = rs.getString("class");
                for (String usecase : usecaseList) {
                    String uc = rs.getString(usecase).trim();

                    if (!uc.equals("")) {
                        sb.append(method);
                        sb.append(" ");
                        sb.append(usecase);
                        sb.append(" ");
                        if (uc.equals("x")) {
                            sb.append("1");
                        } else {
                            sb.append(uc);
                        }
                        sb.append("\n");
                    }
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        _.writeFile(sb.toString(), Constants.REQS_PATH);
        SimilarityMatrix similarityMatrix = new SimilarityMatrix();
        return TXTPaser.createSimilarityMatrix(Constants.REQS_PATH);
    }

    public static void writeSimilarityMatrixToDB(SimilarityMatrix matrix, String outputPath) {
        Connection c = null;
        Statement stmt = null;
        try {
            // create table
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + outputPath);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            StringBuffer sb_uc = new StringBuffer();
            for (String uc : usecaseList) {
                sb_uc.append(",");
                sb_uc.append(uc);
                sb_uc.append(" ");
            }

            System.out.println(sb_uc);
            String sql = "CREATE TABLE reqs " +
                    "(class" +
                    sb_uc.toString() +
                    ")";
            stmt.executeUpdate(sql);

            // insert scores
            for (String sourceId : matrix.sourceArtifactsIds()) {

                StringBuffer sb_score = new StringBuffer();
                for (String usecase : usecaseList) {
                    Double score = matrix.getScoreForLink(sourceId, usecase);
                    sb_score.append(", ");
                    sb_score.append("\'");
                    sb_score.append(score);
                    sb_score.append("\'");
                }

                sql = "INSERT INTO reqs (class" + sb_uc.toString() + ") " +
                        "VALUES (\'" + sourceId + "\'" + sb_score.toString()+ ");";
//                System.out.println(" sql = " + sql );
                stmt.executeUpdate(sql);
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
