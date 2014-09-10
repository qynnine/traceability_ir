package nju;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niejia on 14-9-9.
 */
public class LineFile {
    private int num;
    private List<String> file;

    public LineFile(String path) {
        file = new ArrayList<>();
        String input = _.readFile(path);
        String lines[] = input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            file.add(lines[i]);
        }
        num = lines.length;
    }

    public String getLine(int lineNum) {
        if (lineNum <= num) {
            return file.get(lineNum - 1);
        }
        return "";
    }

    public String getLine(String segment) {
        return null;
    }

    public static void main(String[] args) {
        String path = "data/itrust/origin/code/java/MonitorAdverseEventAction.java";
        LineFile file = new LineFile(path);
        System.out.println(file.getLine(130));
    }
}
