import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import symbolTable.*;

public class CodeGenerator {

    private PrintWriter printWriterFile;
    private final HashMap<String, Symbol> symbolTable;

    public CodeGenerator(SemanticAnalysis semanticAnalysis ){
        symbolTable = semanticAnalysis.getSymbolTable();
    }


	public void generate(SimpleNode node) {

        System.out.println("Starting creating jasmin code");
        System.out.println(this.symbolTable.toString());

        ASTClassDeclaration classNode=null;

        for(int i=0;i<node.jjtGetNumChildren();i++){
            if(node.jjtGetChild(i) instanceof ASTClassDeclaration){
                classNode = (ASTClassDeclaration) node.jjtGetChild(i);
            }
        }

        this.printWriterFile = createOutputFile(classNode.name);
        this.generateClass(classNode);
        System.out.println("Jasmin code generated");
        this.printWriterFile.close();
    }

    private void generateClass(ASTClassDeclaration classNode) {

        this.printWriterFile.println(".class public " + classNode.name);

        if(classNode.ext != null)
            this.printWriterFile.println(".super " + classNode.ext);
        else
            this.printWriterFile.println(".super java/lang/Object");

        generateGlobalVariables(classNode);
        generateMethods(classNode);
    }


    private void generateGlobalVariables(SimpleNode node) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if (child instanceof ASTVarDeclaration) {
                generateGlobalVar((ASTVarDeclaration) child);
            }
        }
    }

    private void generateGlobalVar(ASTVarDeclaration var){

        if(var.jjtGetChild(0) instanceof ASTType){
            ASTType nodeType = (ASTType) var.jjtGetChild(0);
            printWriterFile.println(".field public " + var.name + " " + getType(nodeType));
        }
    }

    private String getType(ASTType nodeType) {

        if (nodeType.isArray) {
            return "[I";
        }

        switch (nodeType.type) {
            case "int":
                return "I";
            case "String":
                return "Ljava/lang/String";
            case "boolean":
                return "B";
            case "void":
                return "V";
        }

        return "";
    }

     private void generateMethods(SimpleNode node) {

        //Should it be here? i don't think so? check this out: http://www.cs.sjsu.edu/faculty/pearce/modules/lectures/co/jvm/jasmin/instructions.html
        //There is no declaration of a constructor so there is no jasmin code for a constructor made
         generateConstructor();

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);

            if (child instanceof ASTMainDeclaration){
                generateMainMethod(child);
            }
            if(child instanceof ASTMethodDeclaration){
                generateMethod(child);
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

    private void generateMainMethod(SimpleNode mainNode) {
        this.printWriterFile.println(".method public static main([Ljava/lang/String;)V");
        generateMethodStatments(mainNode);
        printWriterFile.write(".endMethod\n\n");
    }
        
    private void generateMethod(SimpleNode methodNode){
        generateMethodHeader((ASTMethodDeclaration) methodNode);//parametros de entrada colocados em varsTable
        printWriterFile.println("\t.limit stack 99");//TO-DO calculate stack and locals, just for ckpt3
        printWriterFile.println("\t.limit locals 99\n");
        generateMethodStatments(methodNode);
        printWriterFile.write(".endMethod\n\n");
    }
    
    private void generateMethodHeader(ASTMethodDeclaration methodNode) {

        String methodArgs = "";
        String methodType = "";

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

        this.printWriterFile.println(".method public " + methodNode.name + "(" + methodArgs + ")" + methodType);
    }

    private String generateMethodArgument(ASTArg argNode) {
        String argType="";

        if(argNode.jjtGetChild(0) instanceof ASTType)
           argType = getType((ASTType) argNode.jjtGetChild(0));

        return argType;
    }

    private void generateMethodStatments(SimpleNode methodNode) {

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++){
            
            if(methodNode.jjtGetChild(i) instanceof ASTVarDeclaration){
                ASTVarDeclaration varDeclaration = (ASTVarDeclaration) methodNode.jjtGetChild(i);
               // generateVar(varDeclaration);
            }
        }
    }

    private void generateMethodBody(SimpleNode method) {
        for (int i = 0; i < method.jjtGetNumChildren(); i++) {
            SimpleNode method_child = (SimpleNode) method.jjtGetChild(i);
            generateBody(method_child);
        }
    }

    private void generateBody(SimpleNode node) {
        if(node instanceof ASTEquality){
            //System.out.println("equality");
            //System.out.println("nr filhos equality: " + node.jjtGetNumChildren());
            generateEquality(node);
        }

        //TODO -> complete with return, dought expressions, if, while...
    }

    private void generateEquality(SimpleNode node) {
        ASTIdentifier lhs = (ASTIdentifier) node.jjtGetChild(0);  //left identifier
        SimpleNode rhs = (SimpleNode) node.jjtGetChild(1);  //right side 

//        System.out.println("lhs: " + lhs.toString());
//        System.out.println("Nr filhos direita: " + rhs.jjtGetNumChildren());

        generateRhs(rhs);

        //TODO -> parse left side of expression
    }


    private void generateRhs(SimpleNode rhs) {
        if (rhs.jjtGetNumChildren() == 2) {
            generateOperation(rhs);
        }

        //TODO -> nrChildren != 2 
    }

    private void generateOperation(SimpleNode operation) {

        //System.out.println("filhos operation: " + operation.jjtGetNumChildren());
        //System.out.println("operation: " + operation.toString());
        
        SimpleNode lhs = (SimpleNode) operation.jjtGetChild(0);
        SimpleNode rhs = (SimpleNode) operation.jjtGetChild(1);


        if(operation instanceof ASTSUM)
            this.printWriterFile.println("\tiadd");
        if(operation instanceof ASTSUB)
            this.printWriterFile.println("\tisub");
        if(operation instanceof ASTMUL)
            this.printWriterFile.println("\timul");
        if(operation instanceof ASTDIV)
            this.printWriterFile.println("\tidiv");
        /*  if(operation instanceof ASTLESSTHAN)
            //TODO
        if(operation instanceof ASTAND)
            //TODO*/
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


    private PrintWriter createOutputFile(String className) {
        try {
            File dir = new File("src/jasmin/");
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir + "/" + className + ".j");
            if (!file.exists())
                file.createNewFile();

            return new PrintWriter(file);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}