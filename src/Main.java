import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;

/**
 * Created by niejia on 14-9-9.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "/Users/niejia/Documents/IdeaProjects/traceability_ir/data/itrust/origin/code/java/MonitorAdverseEventAction.java";
        File file = new File(path);
        FileReader fr = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(fr);
        System.out.println(reader.getLineNumber());

    }
}
