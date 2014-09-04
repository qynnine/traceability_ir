package nju.test;

import nju.Path;
import nju.component.TXTPaser;
import nju.component.metrics.MetricComputation;
import nju.component.metrics.Result;
import nju.type.RankStrategy;
import nju.type.SimilarityMatrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by niejia on 14-8-31.
 */
public class ResultTest {

    private String method_1 = "AccessDAO::setSessionTimeoutMins";
    private String method_2 = "AuthDAO::recordResetPasswordFailure";

    private SimilarityMatrix similarity = TXTPaser.createSimilarityMatrix(Path.SIMILARITY_TXT);
    private SimilarityMatrix ansrMatrix = TXTPaser.createSimilarityMatrix(Path.ORACLE_TXT);

    private MetricComputation metricComputation = new MetricComputation(similarity, ansrMatrix);
    private Result constCutResult = metricComputation.compute(RankStrategy.CONSTANT_CUT);
    private Result variableCutResult = metricComputation.compute(RankStrategy.VARIABLE_CUT);
    private Result constThresholdResult = metricComputation.compute(RankStrategy.CONSTANT_THRESHOLD);
    private Result variableThresholdResult = metricComputation.compute(RankStrategy.VARIABLE_THRESHOLD);
    private Result scaleThresholdResult = metricComputation.compute(RankStrategy.SCALE_THRESHOLD);

    @Test
    public void testGetPrecisionByQuery() throws Exception {
        constThresholdResult.setArgument(0.0);
//        assertEquals(0.0413324420677362, constThresholdResult.getPrecisionByRanklist(), 0.001);
//        assertEquals(0.89830508, constThresholdResult.getRecallByRanklist(), 0.001);
//        assertEquals(0.320, constThresholdResult.getAveragePrecisionByRanklist(), 0.001);

//        constThresholdResult.showPrecisionRecallCurve();
        double precision1 = constThresholdResult.getPrecisionByQuery().get(method_1);
        double precision2 = constThresholdResult.getPrecisionByQuery().get(method_2);
//        System.out.println(constCutResult.matrix.getThreshold());
        assertEquals(1 / 7.0, precision1, 0.0001);
        assertEquals(1 / 34.0, precision2, 0.0001);

        constThresholdResult.setArgument(0.1);
        double precision3 = constThresholdResult.getPrecisionByQuery().get(method_1);
        double precision4 = constThresholdResult.getPrecisionByQuery().get(method_2);
        assertEquals(1 / 2.0, precision3, 0.0001);
        assertEquals(1 / 4.0, precision4, 0.0001);

        constThresholdResult.setArgument(0.2);
        double precision5 = constThresholdResult.getPrecisionByQuery().get(method_1);
        double precision6 = constThresholdResult.getPrecisionByQuery().get(method_2);
        assertEquals(0.0, precision5, 0.0001);
        assertEquals(Double.NaN, precision6, 0.0001);

        constCutResult.setArgument(2.0);
        double precision7 = constCutResult.getPrecisionByQuery().get(method_1);
        double precision8 = constCutResult.getPrecisionByQuery().get(method_2);
        assertEquals(1 / 2.0, precision7, 0.0001);
        assertEquals(0.0, precision8, 0.0001);

        constCutResult.setArgument(4.0);
        double precision9 = constCutResult.getPrecisionByQuery().get(method_1);
        double precision10 = constCutResult.getPrecisionByQuery().get(method_2);
        assertEquals(1 / 4.0, precision9, 0.0001);
        assertEquals(1 / 4.0, precision10, 0.0001);

        variableCutResult.setArgument(0.1);
        double precision11 = variableCutResult.getPrecisionByQuery().get(method_1);
        double precision12 = variableCutResult.getPrecisionByQuery().get(method_2);
        assertEquals(Double.NaN, precision11, 0.0001);
        assertEquals(1 / 3.0, precision12, 0.0001);

        variableCutResult.setArgument(0.2);
        double precision15 = variableCutResult.getPrecisionByQuery().get(method_1);
        double precision16 = variableCutResult.getPrecisionByQuery().get(method_2);
        assertEquals(0 / 1.0, precision15, 0.0001);
        assertEquals(1 / 6.0, precision16, 0.0001);

        variableThresholdResult.setArgument(0.1);
        double precision17 = variableThresholdResult.getPrecisionByQuery().get(method_1);
        double precision18 = variableThresholdResult.getPrecisionByQuery().get(method_2);

        assertEquals(1 / 4.0, precision17, 0.0001);
        assertEquals(1 / 7.0, precision18, 0.0001);

        variableThresholdResult.setArgument(0.5);
        double precision19 = variableThresholdResult.getPrecisionByQuery().get(method_1);
        double precision20 = variableThresholdResult.getPrecisionByQuery().get(method_2);
        assertEquals(1 / 2.0, precision19, 0.0001);
        assertEquals(1 / 5.0, precision20, 0.0001);

        scaleThresholdResult.setArgument(0.3);
        double precision21 = scaleThresholdResult.getPrecisionByQuery().get(method_1);
        double precision22 = scaleThresholdResult.getPrecisionByQuery().get(method_2);
        assertEquals(1 / 2.0, precision21, 0.0001);
        assertEquals(1 / 6.0, precision22, 0.0001);

        scaleThresholdResult.setArgument(0.7);
        double precision23 = scaleThresholdResult.getPrecisionByQuery().get(method_1);
        double precision24 = scaleThresholdResult.getPrecisionByQuery().get(method_2);
        assertEquals(0 / 1.0, precision23, 0.0001);
        assertEquals(1 / 5.0, precision24, 0.0001);
    }

    @Test
    public void testGetRecallByQuery() throws Exception {
        constThresholdResult.setArgument(0.0);
        double recall1 = constThresholdResult.getRecallByQuery().get(method_1);
        double recall2 = constThresholdResult.getRecallByQuery().get(method_2);
        assertEquals(1.0, recall1, 0.0);
        assertEquals(1.0, recall2, 0.0);

        constThresholdResult.setArgument(0.1);
        double recall3 = constThresholdResult.getRecallByQuery().get(method_1);
        double recall4 = constThresholdResult.getRecallByQuery().get(method_2);
        assertEquals(1.0, recall3, 0.0);
        assertEquals(1.0, recall4, 0.0);

        constThresholdResult.setArgument(0.2);
        double recall5 = constThresholdResult.getRecallByQuery().get(method_1);
        double recall6 = constThresholdResult.getRecallByQuery().get(method_2);
        assertEquals(0.0, recall5, 0.0);
        assertEquals(0.0, recall6, 0.0);

        constCutResult.setArgument(2.0);
        double recall7 = constCutResult.getRecallByQuery().get(method_1);
        double recall8 = constCutResult.getRecallByQuery().get(method_2);
        assertEquals(1.0, recall7, 0.0);
        assertEquals(0.0, recall8, 0.0);

        constCutResult.setArgument(4.0);
        double recall9 = constCutResult.getRecallByQuery().get(method_1);
        double recall10 = constCutResult.getRecallByQuery().get(method_2);
        assertEquals(1.0, recall9, 0.0);
        assertEquals(1.0, recall10, 0.0);

        variableCutResult.setArgument(0.1);
        double recall11 = variableCutResult.getRecallByQuery().get(method_1);
        double recall12 = variableCutResult.getRecallByQuery().get(method_2);
        assertEquals(0.0, recall11, 0.0);
        assertEquals(1.0, recall12, 0.0);

        variableCutResult.setArgument(0.2);
        double recall13 = variableCutResult.getRecallByQuery().get(method_1);
        double recall14 = variableCutResult.getRecallByQuery().get(method_2);
        assertEquals(0.0, recall13, 0.0);
        assertEquals(1.0, recall14, 0.0);

        variableThresholdResult.setArgument(0.1);
        double recall15 = variableThresholdResult.getRecallByQuery().get(method_1);
        double recall16 = variableThresholdResult.getRecallByQuery().get(method_2);
        assertEquals(1.0, recall15, 0.0);
        assertEquals(1.0, recall16, 0.0);

        variableThresholdResult.setArgument(0.5);
        double recall17 = variableThresholdResult.getRecallByQuery().get(method_1);
        double recall18 = variableThresholdResult.getRecallByQuery().get(method_2);
        assertEquals(1.0, recall17, 0.0);
        assertEquals(1.0, recall18, 0.0);

        scaleThresholdResult.setArgument(0.3);
        double recall19 = scaleThresholdResult.getRecallByQuery().get(method_1);
        double recall20 = scaleThresholdResult.getRecallByQuery().get(method_2);
        assertEquals(1.0, recall19, 0.0);
        assertEquals(1.0, recall20, 0.0);

        scaleThresholdResult.setArgument(0.7);
        double recall21 = scaleThresholdResult.getRecallByQuery().get(method_1);
        double recall22 = scaleThresholdResult.getRecallByQuery().get(method_2);
        assertEquals(0 / 1.0, recall21, 0.0);
        assertEquals(1.0, recall22, 0.0);
    }

    @Test
    public void testGetAveragePrecisionByQuery() throws Exception {
        constThresholdResult.setArgument(0.0);
        double averagePrecision1 = constThresholdResult.getAveragePrecisionByQuery().get(method_1);
        double averagePrecision2 = constThresholdResult.getAveragePrecisionByQuery().get(method_2);

        assertEquals(0.5, averagePrecision1, 0.0001);
        assertEquals(1 / 3.0, averagePrecision2, 0.0001);
    }

    @Test
    public void testGetMeanAveragePrecisionByQuery() throws Exception {
        constThresholdResult.setArgument(0.0);
        double meanAveragePrecision = constThresholdResult.getMeanAveragePrecisionByQuery();
        assertEquals(0.564660, meanAveragePrecision, 0.0001);

        scaleThresholdResult.setArgument(0.5);
        double meanAveragePrecision1 = scaleThresholdResult.getMeanAveragePrecisionByQuery();
//        System.out.println(" meanAveragePrecision1 = " + meanAveragePrecision1 );

        variableThresholdResult.setArgument(0.5);
        double meanAveragePrecision2 = variableThresholdResult.getMeanAveragePrecisionByQuery();
//        System.out.println(" meanAveragePrecision2 = " + meanAveragePrecision2 );

        variableCutResult.setArgument(0.4);
        double meanAveragePrecision3 = variableCutResult.getMeanAveragePrecisionByQuery();
//        System.out.println(" meanAveragePrecision3 = " + meanAveragePrecision3 );

        constCutResult.setArgument(3);
        double meanAveragePrecision4 = constCutResult.getMeanAveragePrecisionByQuery();
//        System.out.println(" meanAveragePrecision4 = " + meanAveragePrecision4 );

        constThresholdResult.setArgument(0.5);
        double meanAveragePrecision5 = constThresholdResult.getMeanAveragePrecisionByQuery();
//        System.out.println(" meanAveragePrecision5 = " + meanAveragePrecision5 );
    }

    @Test
    public void testGetPrecisionRecallCurve() throws Exception {
//        for (double i = 0.0; i < 1.0; i = i + 0.1) {
//            constThresholdResult.setArgument(i);
//            constThresholdResult.showPrecisionByRanklist();
//            constThresholdResult.showRecallByRanklist();
//            constThresholdResult.showAveragePrecisionByRanklist();
//        }
//
//        for (double i = 0.0; i < 1.0; i = i + 0.1) {
//            constThresholdResult.setArgument(i);
//            constThresholdResult.showPrecisionByRanklist();
//            constThresholdResult.showRecallByRanklist();
//            constThresholdResult.showAveragePrecisionByRanklist();
//        }


//        assertEquals(0.0413324420677362, constThresholdResult.getPrecisionByRanklist(), 0.001);
//        assertEquals(0.89830508, constThresholdResult.getRecallByRanklist(), 0.001);
//        assertEquals(0.320, constThresholdResult.getAveragePrecisionByRanklist(), 0.001);

//        constThresholdResult.showPrecisionRecallCurve();
    }

    @Test
    public void testGetPrecisionByRanklist() {
        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            constThresholdResult.setArgument(i);
//            constThresholdResult.showPrecisionByRanklist();
        }

        for (double i = 0.0; i < 10.0; i = i + 1) {
            constCutResult.setArgument(i);
//            constCutResult.showPrecisionByRanklist();
        }

        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            variableCutResult.setArgument(i);
//            variableCutResult.showPrecisionByRanklist();
        }

        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            variableThresholdResult.setArgument(i);
//            variableThresholdResult.showPrecisionByRanklist();
        }

        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            scaleThresholdResult.setArgument(i);
//            scaleThresholdResult.showPrecisionByRanklist();
        }
    }

    @Test
    public void testGetRecallByRanklist() {
        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            constThresholdResult.setArgument(i);
//            constThresholdResult.showRecallByRanklist();
        }

        for (double i = 0.0; i < 10.0; i = i + 1) {
            constCutResult.setArgument(i);
//            constCutResult.showRecallByRanklist();
        }

        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            variableCutResult.setArgument(i);
//            variableCutResult.showRecallByRanklist();
        }

        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            variableThresholdResult.setArgument(i);
//            variableThresholdResult.showRecallByRanklist();
        }

        for (double i = 0.0; i < 1.0; i = i + 0.1) {
            scaleThresholdResult.setArgument(i);
//            scaleThresholdResult.showRecallByRanklist();
        }
    }

    @Test
    public void testShowMatrix() throws Exception {

        constCutResult.setArgument(7.0);
//        constCutResult.showMatrix();

        constThresholdResult.setArgument(0.4);
//        constThresholdResult.showMatrix();

        variableCutResult.setArgument(0.8);
//        variableCutResult.showMatrix();

        variableThresholdResult.setArgument(0.5);
//        variableThresholdResult.showMatrix();

        scaleThresholdResult.setArgument(0.5);
//        scaleThresholdResult.showMatrix();
    }

    @Test
    public void testShowPrecisionRecallCurve() {
    }
}