package nju.data;

import nju.data.model.Method;

import java.util.List;
import java.util.Set;

/**
 * Created by niejia on 14-8-26.
 */
public class TraceLab {

    public static void sourceReqXMLFormat(Set<String> usecaseSet) {
    }

    public static void targetCodeXMLFormat(List<Method> methods, List<String> jsp) {
        for (Method m : methods) {
            String targetCodeItem = "<artifact><id>" + m.getId() + "</id><content>to_be_traced_source_code/java/" + m.getKlass() + "_" + m.getFileName() + "</content><parent_id/></artifact>";
            System.out.println(targetCodeItem);
        }

        for (String j : jsp) {
            String id = j + "_jsp::_jspService";
            String fileName = j + ".jsp";
            String targetCodeItem = "<artifact><id>" + id + "</id><content>to_be_traced_source_code/jsp/" + fileName + "</content><parent_id/></artifact>";
            System.out.println(targetCodeItem);
        }
        System.out.println(methods.size() + jsp.size());
    }
}
