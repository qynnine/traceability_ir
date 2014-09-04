package nju.test;

import nju.Path;
import nju.component.TXTPaser;
import nju.type.SimilarityMatrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by niejia on 14-8-27.
 */
public class SimilarityMatrixTest {

    public SimilarityMatrix similarity = TXTPaser.createSimilarityMatrix(Path.SIMILARITY_TXT);
    private String method_1 = "AccessDAO::setSessionTimeoutMins";
    private String method_2 = "AuthDAO::recordResetPasswordFailure";

    @Test
    public void testIsLinkAboveThreshold() {
        similarity.setThreshold(0.1);
//        System.out.println(" similarity = \n" + similarity.getLinksAboveThreshold() );
        assertEquals(true, similarity.isLinkAboveThreshold(method_1, "UC3"));
        assertEquals(false, similarity.isLinkAboveThreshold(method_1, "UC5"));
        assertEquals(true, similarity.isLinkAboveThreshold(method_2, "UC3"));
        assertEquals(false, similarity.isLinkAboveThreshold(method_2, "UC4"));

        similarity.setThreshold(0.0);
//        System.out.println(" similarity = \n" + similarity.getLinksAboveThreshold() );
        assertEquals(true, similarity.isLinkAboveThreshold(method_1, "UC3"));
        assertEquals(false, similarity.isLinkAboveThreshold(method_1, "UC25"));
        assertEquals(true, similarity.isLinkAboveThreshold(method_2, "UC3"));
        assertEquals(true, similarity.isLinkAboveThreshold(method_2, "UC4"));

    }

    @Test
    public void testIsLinkInCutN() throws Exception {
        similarity.setCutN(4);
        assertEquals(true, similarity.isLinkInCutN(method_1, "UC12"));
        assertEquals(false, similarity.isLinkInCutN(method_1, "UC9"));
        assertEquals(true, similarity.isLinkInCutN(method_2, "UC3"));
        assertEquals(false, similarity.isLinkInCutN(method_2, "UC4"));
//        System.out.println(" similarity = \n" + similarity.getLinksInCutN() );

        similarity.setCutN(1);
        assertEquals(true,similarity.isLinkInCutN(method_1, "UC8"));
        assertEquals(false, similarity.isLinkInCutN(method_1, "UC3"));
        assertEquals(true, similarity.isLinkInCutN(method_2, "UC1"));
        assertEquals(false, similarity.isLinkInCutN(method_2, "UC27"));
//        System.out.println(" similarity = \n" + similarity.getLinksInCutN() );

        similarity.setCutN(0);
        assertEquals(false, similarity.isLinkInCutN(method_1, "UC8"));
        assertEquals(false, similarity.isLinkInCutN(method_1, "UC3") );
        assertEquals(false, similarity.isLinkInCutN(method_2, "UC1"));
        assertEquals(false, similarity.isLinkInCutN(method_2, "UC4"));
//        System.out.println(" similarity = \n" + similarity.getLinksInCutN() );
    }

    @Test
    public void testIsLinkInVariableCut() throws Exception {
        similarity.setVariableCut(0.5);
        assertEquals(false,similarity.isLinkInVariableCut(method_1, "UC12"));
        assertEquals(true, similarity.isLinkInVariableCut(method_1, "UC5"));
        assertEquals(true,similarity.isLinkInVariableCut(method_2, "UC24"));
        assertEquals(false, similarity.isLinkInVariableCut(method_2, "UC16"));
//        System.out.println(" similarity = \n" + similarity.getLinksInVariableCut());

        similarity.setVariableCut(1.0);
        assertEquals(true, similarity.isLinkInVariableCut(method_1, "UC15") );
        assertEquals(false, similarity.isLinkInVariableCut(method_1, "UC26"));
        assertEquals(true, similarity.isLinkInVariableCut(method_2, "UC6"));
        assertEquals(true, similarity.isLinkInVariableCut(method_2, "UC19"));
//        System.out.println(" similarity = \n" + similarity.getLinksInVariableCut());

        similarity.setVariableCut(0.1);
        assertEquals(false,similarity.isLinkInVariableCut(method_1, "UC12"));
        assertEquals(false, similarity.isLinkInVariableCut(method_1, "UC9") );
        assertEquals(true, similarity.isLinkInVariableCut(method_2, "UC3"));
        assertEquals(false, similarity.isLinkInVariableCut(method_2, "UC5"));
//        System.out.println(" similarity = \n" + similarity.getLinksInVariableCut());
    }

    @Test
    public void testIsLinkAboveVariableThreshold() {
        similarity.setVariableThreshold(0.5);
        assertEquals(true,similarity.isLinkAboveVariableThreshold(method_1, "UC3"));
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_1, "UC5"));
        assertEquals(true,similarity.isLinkAboveVariableThreshold(method_2, "UC4"));
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_2, "UC2"));
//        System.out.println(" similarity = \n" + similarity.getLinksAboveVariableThreshold() );

        similarity.setVariableThreshold(1.0);
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_1, "UC8"));
        assertEquals(false, similarity.isLinkAboveVariableThreshold(method_1, "UC3") );
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_2, "UC1") );
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_2, "UC27"));
//        System.out.println(" similarity = \n" + similarity.getLinksAboveVariableThreshold() );

        similarity.setVariableThreshold(0.99);
        assertEquals(true,similarity.isLinkAboveVariableThreshold(method_1, "UC8"));
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_1, "UC3"));
        assertEquals(true,similarity.isLinkAboveVariableThreshold(method_2, "UC1"));
        assertEquals(true,similarity.isLinkAboveVariableThreshold(method_2, "UC27"));
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_2, "UC3"));
//        System.out.println(" similarity = \n" + similarity.getLinksAboveVariableThreshold() );

        similarity.setVariableThreshold(0.0);
        assertEquals(true,similarity.isLinkAboveVariableThreshold(method_1, "UC30"));
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_1, "UC26"));
        assertEquals(true,similarity.isLinkAboveVariableThreshold(method_2, "UC6"));
        assertEquals(false,similarity.isLinkAboveVariableThreshold(method_2, "UC17"));

        // when minSimilarity > 0.0, should this minSimilarity links be returned when VariableThreshold = 0.0 ?
        // > or >= question
        assertEquals(similarity.isLinkAboveVariableThreshold("visitReminders_jsp::_jspService", "UC16"), false);
//        System.out.println(" similarity = \n" + similarity.getLinksAboveVariableThreshold() );
    }

    @Test
    public void testIsLinkAboveScaleThreshold() {
        similarity.setScaleThreshold(0.15);
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_1, "UC12"));
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_1, "UC5"));
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_2, "UC12"));
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_2, "UC13"));
//        System.out.println(" similarity = \n" + similarity.getLinksAboveScaleThreshold());

        similarity.setScaleThreshold(1.0);
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_1, "UC3"));
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_1, "UC8"));
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_2, "UC1") );
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_2, "UC27"));
//        System.out.println(" similarity = \n" + similarity.getLinksAboveScaleThreshold());

        similarity.setScaleThreshold(0.99);
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_1, "UC3"));
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_1, "UC8"));
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_2, "UC1"));
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_2, "UC27"));
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_2, "UC3"));
//        System.out.println(" similarity = \n" + similarity.getLinksAboveScaleThreshold());

        similarity.setScaleThreshold(0.0);
        assertEquals(false,similarity.isLinkAboveScaleThreshold(method_1, "UC26") );
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_1, "UC30"));
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_2, "UC6"));
        assertEquals(true,similarity.isLinkAboveScaleThreshold(method_2, "UC34"));
//        System.out.println(" similarity = \n" + similarity.getLinksAboveScaleThreshold());
    }

    @Test
    public void testGetCountOfLinksAboveThresholdForSourceArtifact() {
        similarity.setThreshold(0.0);
        int num = similarity.getCountOfLinksAboveThresholdForSourceArtifact(method_1);
        assertEquals(7,num);
    }

    @Test
    public void testGetCountOfLinksInCutNForSourceArtifact() {
        similarity.setCutN(4);
        int num = similarity.getCountOfLinksInCutNForSourceArtifact(method_1);
        assertEquals(4, num);
        num = similarity.getCountOfLinksInCutNForSourceArtifact(method_2);
        assertEquals(4, num);
    }

    @Test
    public void testGetCountOfLinksInVariableCutForSourceArtifact() {
        similarity.setVariableCut(0.5);
        int num = similarity.getCountOfLinksInVariableCutForSourceArtifact(method_1);
        assertEquals(3, num);
        num = similarity.getCountOfLinksInVariableCutForSourceArtifact(method_2);
        assertEquals(17, num);
    }

    @Test
    public void testGetCountOfLinksAboveVariableThresholdForSourceArtifact() {
        similarity.setVariableThreshold(0.5);
        int num = similarity.getCountOfLinksAboveVariableThresholdForSourceArtifact(method_1);
        assertEquals(2, num);
        num = similarity.getCountOfLinksAboveVariableThresholdForSourceArtifact(method_2);
        assertEquals(5 ,num);
    }

    @Test
    public void testGetCountOfLinksAboveScaleThresholdForSourceArtifact() {
        similarity.setScaleThreshold(0.1);
        int num = similarity.getCountOfLinksAboveScaleThresholdForSourceArtifact(method_1);
        assertEquals(4,num);
        num = similarity.getCountOfLinksAboveScaleThresholdForSourceArtifact(method_2);
        assertEquals(7, num);
    }

}
