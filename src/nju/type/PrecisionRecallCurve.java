package nju.type;

import java.util.LinkedHashMap;

/**
 * Created by niejia on 14-9-2.
 */
public class PrecisionRecallCurve extends LinkedHashMap<String, Double> {
    private String name;
    private double argumemt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArgumemt() {
        return argumemt;
    }

    public void setArgumemt(double argumemt) {
        this.argumemt = argumemt;
    }
}
