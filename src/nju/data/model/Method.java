package nju.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niejia on 14-6-27.
 */
public class Method {
    private String klass;
    private String name;
    private List<String> identifiers;

    public Method() {
        identifiers = new ArrayList<>();
    }
    public String getKlass() {
        return klass;
    }

    public String getId() {
        return klass + "::" + name;
    }

    public String getFileName() {
        return name + ".txt";
    }

    public void setKlass(String klass) {
        this.klass = klass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(klass);
        sb.append(" ");
        sb.append(name);
        sb.append(" ");
        for (String s : identifiers) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString();
    }
}
