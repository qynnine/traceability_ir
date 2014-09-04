package nju.component.metrics;

import nju.type.SimilarityMatrix;
import nju.type.SingleLink;

/**
 * Created by niejia on 14-8-29.
 */
public class VariableThresholdResult extends Result {
    public VariableThresholdResult(SimilarityMatrix matrix, SimilarityMatrix oracle) {
        super(matrix, oracle);
        name = "variable threshold";
        argument = originMatrix.getVariableThreshold();
    }

    @Override
    public void showMetrics() {
    }

    public void setVariableThreshold(double val) {
        setArgument(val);
    }

    @Override
    public void setArgument(double val) {

        SimilarityMatrix sims = new SimilarityMatrix();
        originMatrix.setVariableThreshold(val);
        argument = val;

        for (SingleLink link : originMatrix.allLinks()) {
            if (originMatrix.isLinkAboveVariableThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), link.getScore());
            } else {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), 0.0);
            }
        }
        matrix = sims;
    }
}
