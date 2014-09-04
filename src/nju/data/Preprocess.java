package nju.data;

import nju.Constants;
import nju.data.model.OracleMatrix;

/**
 * Created by niejia on 14-8-25.
 */
public class Preprocess {
    public static void main(String[] args) {

        OracleMatrix ansrMatrix = new OracleMatrix(Constants.REQS_PATH);
//        System.out.println(ansrMatrix.getUsecase());
//        MethodExtractor methodExtractor = new MethodExtractor(Constants.INVOLVED_JAVA_PATH, ansrMatrix.getMethod());
//        JspExtractor jspExtractor = new JspExtractor(Constants.INVOLVED_JSP_PATH, ansrMatrix.getJsp());
//
//        TraceLab.sourceReqXMLFormat(ansrMatrix.getUsecase());
//        TraceLab.targetCodeXMLFormat(methodExtractor.getMethods(), jspExtractor.getJsp());
    }
}
