package nju.component.metrics;

import nju.type.SimilarityMatrix;
import nju.type.SingleLink;

/**
 * Created by niejia on 14-8-29.
 */
public class ScaleThresholdResult extends Result{

    public ScaleThresholdResult(SimilarityMatrix matrix, SimilarityMatrix oracle) {
        super(matrix, oracle);
        name = "scale threshold";
        argument = originMatrix.getScaleThreshold();
    }

    @Override
    public void showMetrics() {
    }

    public void setScaleThreshold(double val) {
        setArgument(val);
    }

    @Override
    public void setArgument(double val) {

        SimilarityMatrix sims = new SimilarityMatrix();
        originMatrix.setScaleThreshold(val);
        argument = val;
        for (SingleLink link : originMatrix.allLinks()) {
            if (originMatrix.isLinkAboveScaleThreshold(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), link.getScore());
            } else {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), 0.0);
            }
        }
        matrix = sims;
    }
}
