package nju.component.metrics;

import nju.type.SimilarityMatrix;
import nju.type.SingleLink;

/**
 * Created by niejia on 14-8-29.
 */
public class ConstantThresholdResult extends Result{
    public ConstantThresholdResult(SimilarityMatrix matrix, SimilarityMatrix oracle) {
        super(matrix, oracle);
        name = "constant threshold";
        argument = originMatrix.getThreshold();
    }

    @Override
    public void showMetrics() {
    }

    public void setThreshold(double val) {
        setArgument(val);
    }

    @Override
    public void setArgument(double val) {

        SimilarityMatrix sims = new SimilarityMatrix();
        originMatrix.setThreshold(val);
        argument = val;

        for (SingleLink link : originMatrix.allLinks()) {
            if (originMatrix.isLinkAboveThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), link.getScore());
            } else {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), 0.0);
            }
        }
        matrix = sims;
    }
}
