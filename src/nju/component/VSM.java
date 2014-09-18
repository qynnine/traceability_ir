package nju.component;

import nju.type.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by niejia on 14-7-4.
 */
public class VSM {

    public static SimilarityMatrix Compute(ArtifactsCollection source, ArtifactsCollection target) {
        return Compute(new TermDocumentMatrix(source), new TermDocumentMatrix(target));
    }

    private static SimilarityMatrix Compute(TermDocumentMatrix source, TermDocumentMatrix target) {
//        System.out.println(" source = \n" + source );
//        System.out.println(" target = \n" + target );
        TermDocumentMatrix IDs = ComputeIdentities(source);

//        System.out.println(" source = " + source );
        TermDocumentMatrix TF = ComputeTF(target);
//        System.out.println(" target TF = \n" + TF );
        double[] IDF = ComputeIDF(ComputeDF(target), target.NumDocs());
//        System.out.println(" target IDF = \n" + Arrays.toString(IDF) );
        TermDocumentMatrix TFIDF = ComputeTFIDF(TF, IDF);

//        System.out.println(" target TFIDF = \n" + TFIDF );

        return ComputeSimilarities(IDs, TFIDF);
    }

    private static TermDocumentMatrix ComputeTFIDF(TermDocumentMatrix tf, double[] idf) {
        for (int i = 0; i < tf.NumDocs(); i++)
        {
            for (int j = 0; j < tf.NumTerms(); j++)
            {
                tf.setValue(i,j, tf.getValue(i,j) * idf[j]);
            }
        }
        return tf;
    }

    private static double[] ComputeIDF(double[] df, int numDocs) {
        double[] idf = new double[df.length];
        for (int i = 0; i < df.length; i++)
        {
            if (df[i] <= 0.0)
            {
                idf[i] = 0.0;
            }
            else
            {
                idf[i] = Math.log(numDocs / df[i]);
            }
        }
        return idf;
    }

    private static double[] ComputeDF(TermDocumentMatrix matrix) {
        double[] df = new double[matrix.NumTerms()];
        for (int j = 0; j < matrix.NumTerms(); j++)
        {
            df[j] = 0.0;
            for (int i = 0; i < matrix.NumDocs(); i++)
            {
                df[j] += (matrix.getValue(i,j) > 0.0) ? 1.0 : 0.0;
            }
        }
        return df;
    }

    private static TermDocumentMatrix ComputeTF(TermDocumentMatrix matrix) {
        for (int i = 0; i < matrix.NumDocs(); i++)
        {
            double max = 0.0;
            for (int k = 0; k < matrix.NumTerms(); k++) {
                max += matrix.getValue(i, k);
            }

            for (int j = 0; j < matrix.NumTerms(); j++) {
                matrix.setValue(i, j, (matrix.getValue(i, j) / max));
            }
        }
        return matrix;
    }

    private static TermDocumentMatrix ComputeIdentities(TermDocumentMatrix matrix) {
        for (int i = 0; i < matrix.NumDocs(); i++)
        {
            for (int j = 0; j < matrix.NumTerms(); j++) {
                matrix.setValue(i, j, ((matrix.getValue(i, j) > 0.0) ? 1.0 : 0.0));
            }
        }
        return matrix;
    }

    private static SimilarityMatrix ComputeSimilarities(TermDocumentMatrix ids, TermDocumentMatrix tfidf) {
        SimilarityMatrix sims = new SimilarityMatrix();
        List<TermDocumentMatrix> matrices = TermDocumentMatrix.Equalize(ids, tfidf);

        for (int i = 0; i < ids.NumDocs(); i++) {
            LinksList links = new LinksList();
            for (int j = 0; j < tfidf.NumDocs(); j++) {
                double product = 0.0;
                double asquared = 0.0;
                double bsquared = 0.0;
                for (int k = 0; k < matrices.get(0).NumTerms(); k++) {
                    double a = matrices.get(0).getValue(i, k);
                    double b = matrices.get(1).getValue(j, k);
                    product += (a * b);
                    asquared += Math.pow(a, 2);
                    bsquared += Math.pow(b, 2);
                }
                double cross = Math.sqrt(asquared) * Math.sqrt(bsquared);
                if (cross == 0.0) {
                    links.add(new SingleLink(ids.getDocumentName(i).trim(), tfidf.getDocumentName(j).trim(), 0.0));
                }
                else {
                    links.add(new SingleLink(ids.getDocumentName(i), tfidf.getDocumentName(j), product / cross));
                }
            }

            Collections.sort(links, Collections.reverseOrder());

            for (SingleLink link : links) {
                sims.addLink(link.getSourceArtifactId(), link.getTargetArtifactId(), link.getScore());
            }
        }
        return sims;
    }

    public static void main(String[] args) {

    }
}
