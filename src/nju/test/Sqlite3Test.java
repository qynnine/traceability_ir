package nju.test;

import nju.Constants;
import nju.Sqlite3;
import nju.type.SimilarityMatrix;
import org.junit.Test;

/**
 * Created by niejia on 14-8-28.
 */
public class Sqlite3Test {
    @Test
    public void testReadSimilarityMatrix() throws Exception {
        SimilarityMatrix similarityMatrix = Sqlite3.readSimilarityMatrixFromDB(Constants.MATRIX_DB_PATH);
    }

    @Test
    public void testWriteSimilarityMatrix() throws Exception {
//        SimilarityMatrix similarity = TXTPaser.createSimilarityMatrix(Constants.SIMILARITY_PATH);
//        Sqlite3.writeSimilarityMatrixToDB(similarity, Constants.ITRUST_DB_SCORE);
    }
}
