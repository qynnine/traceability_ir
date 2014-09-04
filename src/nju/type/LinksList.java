package nju.type;

import java.util.ArrayList;

/**
 * Created by niejia on 14-7-9.
 */
public class LinksList extends ArrayList<SingleLink> {

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (SingleLink link : this) {
            sb.append(link);
            sb.append("\n");
        }
        return sb.toString();
    }
}
