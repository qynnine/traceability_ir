package nju.test;

import nju.Path;
import nju.Sqlite3;
import nju.type.SimilarityMatrix;
import org.junit.Test;

/**
 * Created by niejia on 14-8-28.
 */
public class Sqlite3Test {
    @Test
    public void testReadSimilarityMatrix() throws Exception {
        SimilarityMatrix similarityMatrix = Sqlite3.readSimilarityMatrixFromDB(Path.MATRIX_SCORE_DB);
    }

    @Test
    public void testWriteSimilarityMatrix() throws Exception {
//        SimilarityMatrix similarity = TXTPaser.createSimilarityMatrix(Constants.SIMILARITY_PATH);
//        Sqlite3.writeSimilarityMatrixToDB(similarity, Constants.ITRUST_DB_SCORE);
    }
}
