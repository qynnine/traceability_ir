package nju.component.visualization;

import com.googlecode.charts4j.*;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Shape;
import nju.component.metrics.Result;
import nju.type.PrecisionRecallCurve;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.googlecode.charts4j.Color.*;

/**
 * Created by niejia on 14-9-1.
 */
public class GracefulCurve {
    private String url;

    private PrecisionRecallCurve curve;

    public GracefulCurve(Result result) {
        this.curve = result.getPrecisionRecallCurve();
        initialize();
        addLine(curve);
    }

    public void addLine(HashMap<String, Double> precisionRecallCurve) {
        List<String> pointId = new ArrayList<>();

        for (String key : precisionRecallCurve.keySet()) {
            String id = key.split("_")[0];
            if (!pointId.contains(id)) {
                pointId.add(id);
            }
        }
    }

    private void initialize() {

        List<String> pointId = new ArrayList<>();

        for (String key : curve.keySet()) {
            String id = key.split("_")[0];
            if (!pointId.contains(id)) {
                pointId.add(id);
            }
        }

        List<Double> precisionList = new ArrayList<>();
        List<Double> recallList = new ArrayList<>();

        for (String id : pointId) {
            double p = curve.get(id + "_Precision");
            double r = curve.get(id + "_Recall");
            BigDecimal bd_p = new BigDecimal(p);
            BigDecimal bd_r = new BigDecimal(r);
            p = bd_p.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            r = bd_r.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            precisionList.add(p);
            recallList.add(r);
        }

        int size = 130;
        int gap = precisionList.size() /size ;
        double[] precision = new double[size];
        double[] recall = new double[size];

        int j=0;
        for (int i = 0; i < precisionList.size(); i = i + gap) {
            if (j <size ) {
                precision[j] = precisionList.get(i)*100;
                recall[j] = recallList.get(i)*100;
                j++;
            }
        }
        
        Data d1 = Data.newData(recall);
        Data d2 = Data.newData(precision);

        ScatterPlotData data = Plots.newScatterPlotData(d1, d2);

        String label = curve.getName() + " at " + curve.getArgumemt();
        data.setLegend("Threshold at 0.0");
        Color diamondColor = Color.newColor("FF471A");

        data.addShapeMarkers(Shape.DIAMOND, diamondColor, 3);
        data.setColor(diamondColor);
        XYLineChart chart = GCharts.newXYLineChart(data);

        chart.setSize(600, 450);
        chart.setGrid(20, 20, 3, 2);

        chart.setSize(600, 500);
        chart.setTitle("Precision Recall Curve|(Experiment result)", WHITE, 14);
        chart.addHorizontalRangeMarker(40, 60, Color.newColor(RED, 30));
        chart.addVerticalRangeMarker(70, 90, Color.newColor(GREEN, 30));

        chart.setGrid(30, 10, 3, 2);

        AxisStyle axisStyle = AxisStyle.newAxisStyle(WHITE, 12, AxisTextAlignment.CENTER);
        AxisLabels xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, 1);
        xAxis.setAxisStyle(axisStyle);

        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, 1);

        yAxis.setAxisStyle(axisStyle);

        chart.addXAxisLabels(xAxis);
        chart.addYAxisLabels(yAxis);

        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
        
        url = chart.toURLString();
    }

    public void show() {
        System.out.println(url);

        if (Desktop.isDesktopSupported()) {
            try {
                url = url.replaceAll("\\|", "%7C");
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
