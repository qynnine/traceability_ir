package nju.data;

import nju.Path;
import nju.data.model.OracleMatrix;

/**
 * Created by niejia on 14-8-25.
 */
public class Preprocess {
    public static void main(String[] args) {

        OracleMatrix oracle = new OracleMatrix(Path.ORACLE_LITE_TXT);
        MethodExtractor methodExtractor = new MethodExtractor(Path.INVOLVED_JAVA, oracle.getMethod());


//        System.out.println(ansrMatrix.getUsecase());

//        JspExtractor jspExtractor = new JspExtractor(Constants.INVOLVED_JSP, ansrMatrix.getJsp());
//
//        TraceLab.sourceReqXMLFormat(ansrMatrix.getUsecase());
//        TraceLab.targetCodeXMLFormat(methodExtractor.getMethods(), jspExtractor.getJsp());
    }
}
