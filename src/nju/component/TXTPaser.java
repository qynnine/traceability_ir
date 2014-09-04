package nju.component;

import nju._;
import nju.type.SimilarityMatrix;
import nju.type.SingleLink;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by niejia on 14-7-10.
 */
public class TXTPaser {

    public static SimilarityMatrix createSimilarityMatrix(String path) {
        SimilarityMatrix sims = new SimilarityMatrix();

        if (!path.endsWith(".txt")) {
            try {
                throw new Exception("not a txt file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Set<String> labelSet = new LinkedHashSet<>();
        String contents = _.readFile(path);
        String[] records = contents.split("\n");
        for (int i = 0; i < records.length; i++) {
            String[] parts = records[i].split(" ");
            SingleLink link = new SingleLink(parts[0].trim(), parts[1].trim(), Double.valueOf(parts[2].trim()));
            sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), link.getScore());
            labelSet.add(parts[1]);
        }
        sims.setCutN(labelSet.size());

        return _.sort(sims);
    }
}
