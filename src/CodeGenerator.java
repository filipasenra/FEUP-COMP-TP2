import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

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
        this.printWriterFile.println(".super java/lang/Object\n"); //falta a possibilidadae de ser implements!!!!!
        
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {//Global Variables
                SimpleNode child = (SimpleNode) node.jjtGetChild(i);
                if (child instanceof ASTVarDeclaration) {
                    generateGlobalVar((ASTVarDeclaration) child);
                }
        }

        generateMethods(classNode);

        System.out.println("Jasmin code generated");
        this.printWriterFile.close();
    }

    private void generateGlobalVar(ASTVarDeclaration var){
        ASTType nodeType=null;

        if(var.jjtGetChild(0) instanceof ASTType)
            nodeType = (ASTType) var.jjtGetChild(0);

        String finalType = getType(nodeType);

        printWriterFile.println(".field public " + var.name + " " + finalType + "\n");
    }

    private void generateVar(ASTVarDeclaration var, HashMap<String, String> varTable){
        ASTType nodeType=null;

         
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
                    finalType = "Ljava/lang/String";
                    break;
                case "boolean":
                    finalType = "B";
                    break;
                case "void":
                    finalType = "V";
                    break;
                case "double":
                    finalType = "D";
                case "byte":
                    finalType = "B";
                case "short":
                    finalType = "S"; 
                default:
                    finalType="";
                    break;
            }
        }
        return finalType;
    }

     private void generateMethods(SimpleNode node) {
        generateConstructor();
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            HashMap<String, String> varsTable = new HashMap<String, String>();
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);

            if (child instanceof ASTMainDeclaration){
                generateMainMethod(child, varsTable);
                printWriterFile.write(".endMethod\n\n");
            }
            if(child instanceof ASTMethodDeclaration){
                generateMethod(child, varsTable);
                printWriterFile.write(".endMethod\n\n");
            }   
        }
    }

    private void generateConstructor() {
        
        printWriterFile.println(".method public<init>()V");
        printWriterFile.println("\taload_0");
        printWriterFile.println("\tinvokenonvirtual java/lang/Object<init>()V");//TO-DO if the class extends, we need to change "Object" with extended class name
        printWriterFile.println("\treturn");
        printWriterFile.println(".end method\n");

    }

    private void generateMainMethod(SimpleNode mainNode, HashMap<String, String> varsTable) {
        this.printWriterFile.println(".method public static main([Ljava/lang/String;)V");
        generateMethodStatments(mainNode, varsTable);
    }
        
    private void generateMethod(SimpleNode methodNode, HashMap<String, String> varsTable){
        generateMethodHeader((ASTMethodDeclaration) methodNode, varsTable);//parametros de entrada colocados em varsTable
        printWriterFile.println(varsTable.toString());
        printWriterFile.println("\t.limit stack 99");//TO-DO calculate stack and locals, just for ckpt3
        printWriterFile.println("\t.limit locals 99\n");
        generateMethodStatments((ASTMethodDeclaration) methodNode, varsTable);
    } 
    
    private void generateMethodHeader(ASTMethodDeclaration methodNode, HashMap<String, String> varsTable) {
        String methodArgs="";
        String arg = "";
        String methodType="";
        String type = "";

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) methodNode.jjtGetChild(i);
            if (child instanceof ASTArg){
                ASTArg argument = (ASTArg) methodNode.jjtGetChild(i);
                arg = argument.val;
                if(child.jjtGetChild(0) instanceof ASTType){
                    methodArgs+=generateMethodArgument((ASTArg)child);
                    type = generateMethodArgument((ASTArg)child);
                }
                varsTable.put(arg, type);
            }
            if (child instanceof ASTType) {
                methodType+=getType((ASTType) child);
                ASTType returnType = (ASTType) methodNode.jjtGetChild(0);
                varsTable.put("return", returnType.type); 
            }
            
        }
        this.printWriterFile.println(".method public " + methodNode.name + "(" + methodArgs + ")" + methodType);
    }

    private void generateMethodStatments(SimpleNode methodNode, HashMap<String, String> varsTable) {

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++){
            
            if(methodNode.jjtGetChild(i) instanceof ASTVarDeclaration){
                ASTVarDeclaration varDeclaration = (ASTVarDeclaration) methodNode.jjtGetChild(i);
                generateVar(varDeclaration, varsTable);
            }
        }
    }

    // private void generateBlock(ASTStatementBlock block) {
    //     for (int i = 0; i < block.jjtGetNumChildren(); i++) {
    //         System.out.println("blockchildren");
    //        if(block.jjtGetChild(i) instanceof ASTVarDeclaration){
    //            ASTVarDeclaration declaration = (ASTVarDeclaration) block.jjtGetChild(i);
    //            System.out.println(declaration.name);

    //        }
            
    //     }
    // }

    private String generateMethodArgument(ASTArg argNode) {
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