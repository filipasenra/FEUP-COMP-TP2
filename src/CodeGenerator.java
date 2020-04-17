import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import symbolTable.Symbol;
import symbolTable.SymbolClass;


public class CodeGenerator {

    private HashMap<String, Symbol> symbolTable = new HashMap<>();
    private PrintWriter printWriterFile;

    public CodeGenerator(){}


	public void generate(SimpleNode node) {

        System.out.println("Starting creating jasmin code");

        ASTClassDeclaration classNode = null;

        for(int i=0;i<node.jjtGetNumChildren();i++){
            if(node.jjtGetChild(i) instanceof ASTClassDeclaration){
                classNode = (ASTClassDeclaration) node.jjtGetChild(i);
            }
        }

        this.printWriterFile = getFile(classNode.name);
        this.printWriterFile.println(".class public " + classNode.name);
        this.printWriterFile.println(".super java/lang/Object\n");
        
        generateGlobalVariables(classNode);
        generateMethods(classNode);
        System.out.println("Jasmin code generated");
        this.printWriterFile.close();
    }

    private void generateGlobalVariables(SimpleNode node) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if (child instanceof ASTVarDeclaration) {
                generateVar((ASTVarDeclaration) child);
            }
        }
    }

    private void generateVar(ASTVarDeclaration var){
        ASTType nodeType=null;

        if(var.jjtGetChild(0) instanceof ASTType)
            nodeType = (ASTType) var.jjtGetChild(0);

        String finalType = getType(nodeType);

        printWriterFile.println(".field public " + var.name + " " + finalType + "\n");
    }

    private String getType(ASTType nodeType){
        String vType = nodeType.type;
        String finalType="";

        if(nodeType.isArray)
            finalType="[I";

        else{
            switch (vType) {
                case "int":
                    finalType = "I";
                    break;
                case "String":
                    //TODO
                    break;
                case "boolean":
                    finalType = "B";
                    break;
                case "void":
                    finalType = "V";
                    break;
                default:
                    finalType="";
                    break;
            }
        }
        return finalType;
    }

     private void generateMethods(SimpleNode node) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);

            if (child instanceof ASTMainDeclaration)
                generateMainMethod(child);
            if(child instanceof ASTMethodDeclaration)
                generateMethod(child);
        }
    }

    private void generateMainMethod(SimpleNode mainNode) {
        this.printWriterFile.println(".method public static main([Ljava/lang/String;)V\n");
        //TODO
    }
        
    private void generateMethod(SimpleNode methodNode){
        generateMethodHeader((ASTMethodDeclaration) methodNode);
        //TODO
    }

    private void generateMethodHeader(ASTMethodDeclaration methodNode) {
        String methodArgs="";
        String methodType="";

        if (methodNode.jjtGetNumChildren() == 0)
            this.printWriterFile.println("()");

        else {
            for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {
                SimpleNode child = (SimpleNode) methodNode.jjtGetChild(i);
                if (child instanceof ASTArg){
                    if(child.jjtGetChild(0) instanceof ASTType){
                        methodArgs+=generateMethodArgument((ASTArg)child);
                    }
                }
                if (child instanceof ASTType) {
                    methodType+=getType((ASTType) child);
                }
            }
        }
        this.printWriterFile.println(".method public " + methodNode.name + "(" + methodArgs + ")" + methodType + "\n");
    }

    private String generateMethodArgument(ASTArg argNode){
        String argType="";
        if(argNode.jjtGetChild(0) instanceof ASTType)
           argType = getType((ASTType) argNode.jjtGetChild(0));

        return argType;
    }

    private PrintWriter getFile(String className) {
        try {
            File dir = new File("src/jasmin/");
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir + "/" + className + ".j");
            if (!file.exists())
                file.createNewFile();

            PrintWriter writer = new PrintWriter(file);
            return writer;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}