package nju;

import nju.component.metrics.MetricComputation;
import nju.component.metrics.Result;
import nju.component.visualization.GracefulCurve;
import nju.type.RankStrategy;
import nju.type.SimilarityMatrix;

/**
 * Created by niejia on 14-9-2.
 */
public class Demo {

    public static void main(String[] args) {

        SimilarityMatrix matrix = Sqlite3.readSimilarityMatrixFromDB(Path.MATRIX_SCORE_DB);
        SimilarityMatrix oracle = Sqlite3.readSimilarityMatrixFromDB(Path.ORACLE_LITE_DB);

        /*
        SimilarityMatrix can be printed in console.
        Also SimilarityMatrix contains numerous API which you may not concerned at the moment.
         */
//        System.out.println(" matrix = " + matrix );


        MetricComputation metricComput = new MetricComputation(matrix, oracle);

        /*
        MetricComputation supports five rank strategies computation.
        Alse you can use:
            Result result = metricComput.compute(RankStrategy.CONSTANT_THRESHOLD, 0.1);
         */
        Result result = metricComput.compute(RankStrategy.CONSTANT_THRESHOLD);

        /*
        For ConstantThresholdResult, setArgument means setThreshold.
        Equals ((ConstantThresholdResult) result).setThreshold(0.1);
        */
        result.setArgument(0.0);


//        use show to print metric, use get to catch metric values.
        result.showPrecisionByRanklist();
//        result.showPrecisionRecallCurve();

        /*
        Use google API to draw curve and we abandon this solution
        because the limit on URL length .
         */
        GracefulCurve curve = new GracefulCurve(result);
        curve.show();

//        right way to draw curve
//        PrecisionRecallChart chart = new PrecisionRecallChart();
//        chart.addLine(result);
//
//        Result scaleThresholdResult = metricComput.compute(RankStrategy.SCALE_THRESHOLD, 0.5);
//        chart.addLine(scaleThresholdResult);
//        chart.showChart();

//        Sqlite3.writeSimilarityMatrixToDB(result.getMatrix(), Path.OUTPUT_DB);
    }
}