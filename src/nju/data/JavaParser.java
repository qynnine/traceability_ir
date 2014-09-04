package nju.data;

import nju._;
import nju.data.model.Method;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niejia on 14-8-25.
 */
public class JavaParser {

    public static List<Method> extractMethods(String path) {

        String sourceCode = _.readFile(path);
        List<Method> allMethod = new ArrayList<>();

        //Create Parser
        ASTParser parsert = ASTParser.newParser(AST.JLS3);
        //Source Code in a String
        parsert.setSource(sourceCode.toString().toCharArray());
        //get AST(CompilationUnit is root)
        CompilationUnit result = (CompilationUnit) parsert.createAST(null);

        //get Types
        List types = result.types();
//        System.out.println(" types = " + types );
        if(!types.isEmpty())
        {
            TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
            //##############获取源代码结构信息#################
            //get Packages Declaration
            PackageDeclaration packetDec = result.getPackage();
            if(packetDec != null)
            {
                //get Class Name
                String className = typeDec.getName().toString();

                //get Method Declaration
                MethodDeclaration methodDec[] = typeDec.getMethods();
                //System.out.println("===================Method:========================");
                for(MethodDeclaration method : methodDec) {
                    Method m = new Method();
                    m.setKlass(className);
                    m.setName(method.getName().toString());

                    List parameterList = method.parameters();
//                    System.out.println(" parameterList = " + parameterList );
                    ArrayList<String> identifiers = new ArrayList<String>();

                    String paraString = "";
                    for (Object obj : parameterList) {
                        SimpleName name = ((SingleVariableDeclaration) obj).getName();
                        identifiers.add(name.toString());
                    }

                    m.setIdentifiers(identifiers);
                    allMethod.add(m);
                }
            }
        }
        return allMethod;
    }
}
