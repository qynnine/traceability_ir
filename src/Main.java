import nju.Path;
import nju.component.VSM;
import nju.component.XMLParser;
import nju.type.ArtifactsCollection;
import nju.type.SimilarityMatrix;

import java.io.FileNotFoundException;

/**
 * Created by niejia on 14-9-9.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        ArtifactsCollection sourceCollection = XMLParser.createArtifacts(Path.REQ_BYWORDS_XML);
        ArtifactsCollection targetCollection = XMLParser.createArtifacts(Path.CODE_BYWORDS_XML);

//        TermDocumentMatrix req_td = new TermDocumentMatrix(sourceCollection);
//        System.out.println(req_td);

////        TermDocumentMatrix code_td = new TermDocumentMatrix(targetCollection);
//        System.out.println(" code_td = " + code_td );

//        System.out.println(req_td.getMatrix().length);
//        System.out.println(req_td.getMatrix()[0].length);

        SimilarityMatrix sm = VSM.Compute(targetCollection, sourceCollection);
//        System.out.println(" sm = " + sm );


//        String path = "/Users/niejia/Documents/IdeaProjects/traceability_ir/data/itrust/origin/code/java/MonitorAdverseEventAction.java";
//        File file = new File(path);
//        FileReader fr = new FileReader(file);
//        LineNumberReader reader = new LineNumberReader(fr);
//        System.out.println(reader.getLineNumber());
    }
}
