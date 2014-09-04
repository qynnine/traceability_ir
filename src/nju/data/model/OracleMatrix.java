package nju.data.model;

import nju.Constants;
import nju._;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by niejia on 14-8-26.
 */
public class OracleMatrix {

    private Set<String> klassSet;
    private Set<String> methodSet;
    private Set<String> jspSet;
    private Set<String> usecaseSet;
    private Set<String> files;

    public OracleMatrix(String path) {
        klassSet = new LinkedHashSet<>();
        methodSet = new LinkedHashSet<>();
        jspSet = new LinkedHashSet<>();
        usecaseSet = new LinkedHashSet<>();
        files = new LinkedHashSet<>();
        parserMatrix(path);
    }

    private void parserMatrix(String path) {
        String input = _.readFile(path);
        String record[] = input.split("\n");
        for (String line : record) {
            String code = line.split(" ")[0];
            String usecase = line.split(" ")[1];

            if (isJavaFile(code)) {
                String klass = code.split("::")[0];
//                System.out.println(" klass = " + klass );
                klassSet.add(klass);
                methodSet.add(code);
            } else {
                String jsp = code.substring(0, code.indexOf("_"));
//                System.out.println(" jsp = " + jsp );
                jspSet.add(jsp);
            }
            files.add(code);
            usecaseSet.add(usecase);
        }

    }

    private boolean isJavaFile(String javaOrJsp) {
        return javaOrJsp.indexOf("_") < 0;
    }

    public int getKlassNum() {
        return klassSet.size();
    }

    public int getMethodNum() {
        return methodSet.size();
    }

    public int getJspNum() {
        return jspSet.size();
    }

    public int getUsecaseNum() {
        return usecaseSet.size();
    }

    public int getFilesNum() {
        return files.size();
    }

    public Set<String> getKlass() {
        return klassSet;
    }

    public Set<String> getMethod() {
        return methodSet;
    }

    public Set<String> getJsp() {
        return jspSet;
    }

    public Set<String> getUsecase() {
        return usecaseSet;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("oracle: ");
        sb.append("\n");
        sb.append(getFilesNum());
        sb.append(" files");
        sb.append("\n");
        sb.append(getKlassNum());
        sb.append(" java files");
        sb.append("\n");
        sb.append(getMethodNum());
        sb.append(" methods");
        sb.append("\n");
        sb.append(getJspNum());
        sb.append(" jsp files");
        sb.append("\n");
        sb.append(getUsecaseNum());
        sb.append(" usecases");

        return sb.toString();
    }

    public static void main(String[] args) {
        OracleMatrix ansrMatrix = new OracleMatrix(Constants.REQS_PATH);
        System.out.println(ansrMatrix);
    }
}
