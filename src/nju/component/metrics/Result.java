package nju.component.metrics;

import nju.component.visualization.PrecisionRecallChart;
import nju.type.LinksList;
import nju.type.PrecisionRecallCurve;
import nju.type.SimilarityMatrix;
import nju.type.SingleLink;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by niejia on 14-8-29.
 */
public abstract class Result {
    public SimilarityMatrix matrix;
    protected SimilarityMatrix oracle;
    protected SimilarityMatrix originMatrix;
    protected String name;
    protected double argument;

    public abstract void setArgument(double val);
    public abstract void showMetrics();

    public double getArgument() {
        return argument;
    }

    public SimilarityMatrix getMatrix() {
        return matrix;
    }

    public Result(SimilarityMatrix matrix, SimilarityMatrix oracle) {
        this.matrix = matrix;
        this.oracle = oracle;
        originMatrix = matrix;
    }

    public void showMatrix() {
        System.out.println("matrix " + "at " + name + " " + argument + " = \n" + matrix);
    }

    public void showOracle() {
        System.out.println("oracle = \n" + oracle);
    }

    public void showPrecisionByQuery() {
        HashMap<String, Double> sourcePrecisions = getPrecisionByQuery();

        System.out.println("Precision by each query at " + name + " " + argument + "\n");
        for (String sourceArtifact : sourcePrecisions.keySet()) {
            System.out.println(sourceArtifact + ": " + sourcePrecisions.get(sourceArtifact));
        }
    }

    public HashMap<String, Double> getPrecisionByQuery() {
        HashMap<String, Double> sourcePrecisions = new LinkedHashMap<>();
        oracle.setThreshold(0.0);
        for (String sourceArtifact : oracle.sourceArtifactsIds()) {
            LinksList links = matrix.getLinksAboveThresholdForSourceArtifact(sourceArtifact);
            int correct = 0;
            for (SingleLink link : links) {
                if (oracle.isLinkAboveThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                    correct++;
                }
            }
            sourcePrecisions.put(sourceArtifact, correct / (double) links.size());
        }
        return sourcePrecisions;
    }

    public void showRecallByQuery() {
        HashMap<String, Double> sourceRecall = getRecallByQuery();
        System.out.println("Recall by each query at " + name + " " + argument + "\n");
        for (String sourceArtifact : sourceRecall.keySet()) {
            System.out.println(sourceArtifact + ": " + sourceRecall.get(sourceArtifact));
        }
    }

    public HashMap<String, Double> getRecallByQuery() {
        HashMap<String, Double> sourceRecall = new LinkedHashMap<>();
        oracle.setThreshold(0.0);

        for (String sourceArtifact : oracle.sourceArtifactsIds()) {
            LinksList links = matrix.getLinksAboveThresholdForSourceArtifact(sourceArtifact);
            int correct = 0;
            for (SingleLink link : links) {
                if (oracle.isLinkAboveThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                    correct++;
                }
            }
            sourceRecall.put(sourceArtifact, correct / (double) oracle.getCountOfLinksAboveThresholdForSourceArtifact(sourceArtifact));
        }
        return sourceRecall;
    }

    public HashMap<String, Double> getAveragePrecisionByQuery() {
        HashMap<String, Double> sourceAveragePrecision = new LinkedHashMap<>();

        for (String sourceID : oracle.sourceArtifactsIds()) {
            double sumOfPrecisions = 0.0;
            int currentLink = 0;
            int correctSoFar = 0;
            LinksList links = matrix.getLinksAboveThresholdForSourceArtifact(sourceID);
            Collections.sort(links, Collections.reverseOrder());
            for (SingleLink link : links) {
                currentLink++;
                if (oracle.isLinkAboveThreshold(sourceID, link.getTargetArtifactId())) {
                    correctSoFar++;
                    sumOfPrecisions += correctSoFar / (double) currentLink;
                }
            }
            sourceAveragePrecision.put(sourceID, sumOfPrecisions / oracle.getCountOfLinksAboveThresholdForSourceArtifact(sourceID));
        }
        return sourceAveragePrecision;
    }

    public double getMeanAveragePrecisionByQuery() {
        HashMap<String, Double> sourceAveragePrecision = getAveragePrecisionByQuery();
        double sum = 0.0;
        for (String sourceArtifact : sourceAveragePrecision.keySet()) {
            sum += sourceAveragePrecision.get(sourceArtifact);
        }
        return (sum / sourceAveragePrecision.size());
    }

    public void showAveragePrecisionByQuery() {
        HashMap<String, Double> sourceAveragePrecision = getAveragePrecisionByQuery();
        System.out.println("AveragePrecision by each query at " + name + " " + argument + "\n");
        for (String sourceArtifact : sourceAveragePrecision.keySet()) {
            System.out.println(sourceArtifact + ": " + sourceAveragePrecision.get(sourceArtifact));
        }
    }

    public void showMeanAveragePrecisionByQuery() {
        double meanAveragePrecision = getMeanAveragePrecisionByQuery();
        System.out.println("MeanAverage at " + name + " " + argument + "\n");
    }

    public void showPrecisionByRanklist() {
        double precision = getPrecisionByRanklist();
        String metric = "Precision";
        System.out.println(metric + " = " + precision + " at " + name + " " + argument);
    }

    public void showRecallByRanklist() {
        double recall = getRecallByRanklist();
        String metric = "Recall";
        System.out.println(metric + " = " + recall + " at " + name + " " + argument);
    }

    public void showAveragePrecisionByRanklist() {
        double averagePrecision = getAveragePrecisionByRanklist();
        String metric = "AveragePrecision";
        System.out.println(metric + " = " + averagePrecision + " at " + name + " " + argument);
    }

    public void showPrecisionRecallCurve() {
        PrecisionRecallCurve precisionRecallCurve = getPrecisionRecallCurve();
        PrecisionRecallChart chart = new PrecisionRecallChart(precisionRecallCurve);
        chart.showChart();
//        System.out.println("PrecisionRecallCurve at " + name + " " + argument + "\n");
//        for (String curve : precisionRecallCurve.keySet()) {
//            System.out.println(curve + " " + precisionRecallCurve.get(curve));
//        }

    }

    public double getPrecisionByRanklist() {
        oracle.setThreshold(0.0);
        int correct = 0;

        for (SingleLink link : matrix.getLinksAboveThreshold()) {
            if (oracle.isLinkAboveThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                correct++;
            }
        }
        double precision = correct / (double) matrix.getLinksAboveThreshold().size();

        return precision;
    }

    public double getRecallByRanklist() {
        oracle.setThreshold(0.0);
        int correct = 0;
        for (SingleLink link : matrix.getLinksAboveThreshold()) {
            if (oracle.isLinkAboveThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                correct++;
            }
        }
        double recall = correct / (double) oracle.count();
        return recall;
    }

    public double getAveragePrecisionByRanklist() {
        double sumOfPrecisions = 0.0;
        int currentLink = 0;
        int correctSoFar = 0;
        LinksList links = matrix.getLinksAboveThreshold();
        Collections.sort(links, Collections.reverseOrder());
        for (SingleLink link : links) {
            currentLink++;
            if (oracle.isLinkAboveThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                correctSoFar++;
                sumOfPrecisions += correctSoFar / (double) currentLink;
            }
        }
        double averagePrecision = sumOfPrecisions / oracle.allLinks().size();
        return averagePrecision;
    }

    public PrecisionRecallCurve getPrecisionRecallCurve() {
        PrecisionRecallCurve precisionRecallCurve = new PrecisionRecallCurve();
        precisionRecallCurve.setName(name);
        precisionRecallCurve.setArgumemt(argument);

        oracle.setThreshold(0.0);
        int correct = 0;

        LinksList links = matrix.getLinksAboveThreshold();
        Collections.sort(links, Collections.reverseOrder());

        for (int linkNumber = 1; linkNumber <= links.size(); linkNumber++) {
            if (oracle.isLinkAboveThreshold(links.get(linkNumber - 1).getSourceArtifactId(), links.get(linkNumber - 1).getTargetArtifactId())) {

                correct++;
            }
            precisionRecallCurve.put(String.format("%03d_Precision", linkNumber), correct / (double) linkNumber);
            precisionRecallCurve.put(String.format("%03d_Recall", linkNumber), correct / (double) oracle.count());
        }
        return precisionRecallCurve;
    }

}
