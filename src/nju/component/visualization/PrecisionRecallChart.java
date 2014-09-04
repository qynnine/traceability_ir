package nju.component.visualization;


import nju.component.metrics.Result;
import nju.type.PrecisionRecallCurve;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by niejia on 14-9-2.
 */
public class PrecisionRecallChart extends JFrame {

    List<PrecisionRecallCurve> curveList = new ArrayList<>();

    public PrecisionRecallChart() {
    }

    public PrecisionRecallChart(PrecisionRecallCurve precisionRecallCurve) {
        curveList.add(precisionRecallCurve);
    }

    public void showChart() {
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.setVisible(true);
    }

    private JPanel createChartPanel() {
        String chartTitle = "Precision-Recall Curve";
        String xAxisLabel = "Recall";
        String yAxisLabel = "Precision";

        XYDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        customizeChart(chart);

        return new ChartPanel(chart);
    }

    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        Color[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.green, Color.orange};
        Random rand = new Random();

        for (int i = 0; i < curveList.size(); i++) {
            int index = rand.nextInt(colors.length);
            renderer.setSeriesPaint(0, colors[index]);
        }
    }

    private XYDataset createDataset() {

        XYSeriesCollection dataSet = new XYSeriesCollection();

        for (PrecisionRecallCurve curve : curveList) {
//            System.out.println(" curve = " + curve.size() );
            List<String> pointId = new ArrayList<>();
            for (String key : curve.keySet()) {
                String id = key.split("_")[0];
                if (!pointId.contains(id)) {
                    pointId.add(id);
                }
            }

            String label = curve.getName() + " at " + curve.getArgumemt();
            XYSeries series = new XYSeries(label);
            for (String id : pointId) {
                double p = curve.get(id + "_Precision");
                double r = curve.get(id + "_Recall");
                series.add(r, p);
            }
            dataSet.addSeries(series);
       }
        return dataSet;
    }

    public void addLine(Result result) {
        curveList.add(result.getPrecisionRecallCurve());
    }
}
