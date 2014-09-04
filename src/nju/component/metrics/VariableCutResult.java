package nju.component.metrics;

import nju.type.SimilarityMatrix;
import nju.type.SingleLink;

/**
 * Created by niejia on 14-8-29.
 */
public class VariableCutResult extends Result{
    public VariableCutResult(SimilarityMatrix matrix, SimilarityMatrix oracle) {
        super(matrix, oracle);
        name = "variable cut";
        argument = originMatrix.getVariableCut();
    }

    @Override
    public void showMetrics() {
    }

    public void setVariableCut(double val) {
        setArgument(val);
    }

    @Override
    public void setArgument(double val) {

        SimilarityMatrix sims = new SimilarityMatrix();
        originMatrix.setVariableCut(val);
        argument = val;
        for (SingleLink link : originMatrix.allLinks()) {
            if (originMatrix.isLinkInVariableCut(link.getSourceArtifactId(), link.getTargetArtifactId())) {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), link.getScore());
            } else {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), 0.0);
            }
        }
        matrix = sims;
    }
}
