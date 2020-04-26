import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

        SymbolClass symbolClass = (SymbolClass) this.symbolTable.get(classNode.name);

        generateClassVariables(classNode);
        generateMethods(classNode, symbolClass);
    }


    private void generateClassVariables(SimpleNode node) {
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

        //TODO: make sure this is correct
        return "L" + nodeType.type+";";
    }

     private void generateMethods(SimpleNode node, SymbolClass symbolClass) {

        //Should it be here? i don't think so? check this out: http://www.cs.sjsu.edu/faculty/pearce/modules/lectures/co/jvm/jasmin/instructions.html
        //There is no declaration of a constructor so there is no jasmin code for a constructor made
         generateConstructor();

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);

            if (child instanceof ASTMainDeclaration){
                SymbolMethod symbolMethod = getSymbolMethod(symbolClass.getSymbol("main"), i);
                generateMainMethod(child, symbolClass, symbolMethod);
            }
            if(child instanceof ASTMethodDeclaration){
                ASTMethodDeclaration methodDeclaration = (ASTMethodDeclaration) child;
                SymbolMethod symbolMethod = getSymbolMethod(symbolClass.getSymbol(methodDeclaration.name), i);

                if(symbolMethod == null) {
                    System.err.println("ERROR generating code for method " + methodDeclaration.name);
                    System.exit(0);
                }

                generateMethod(methodDeclaration, symbolClass, symbolMethod);
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

    private void generateMainMethod(SimpleNode mainNode, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        this.printWriterFile.println(".method public static main([Ljava/lang/String;)V");

        generateRegisters(mainNode, symbolMethod);
        generateMethodBody(mainNode, symbolClass, symbolMethod);

        printWriterFile.write(".endMethod\n\n");
    }

    private void generateMethod(ASTMethodDeclaration methodNode, SymbolClass symbolClass, SymbolMethod symbolMethod){
        //Header for method
        generateMethodHeader(methodNode);

        printWriterFile.println("\t.limit stack 99");//TODO calculate stack and locals, just for ckpt3
        printWriterFile.println("\t.limit locals 99\n");

        generateRegisters(methodNode, symbolMethod);
        generateMethodBody(methodNode, symbolClass, symbolMethod);

        printWriterFile.write(".endMethod\n\n");
    }


    private SymbolMethod getSymbolMethod(ArrayList<Symbol> listSymbolMethod, int num) {

        if(listSymbolMethod.size() == 1)
            return (SymbolMethod) listSymbolMethod.get(0);

        for(int i = 0; i < listSymbolMethod.size(); i++) {

            if (listSymbolMethod.get(i) instanceof SymbolMethod) {

                SymbolMethod symbolMethod = (SymbolMethod) listSymbolMethod.get(i);

                System.out.println(symbolMethod.num + " : " + num);

                if (symbolMethod.num == num) {
                    return symbolMethod;
                }
            }
        }

        return null;

    }


    private SymbolMethod getMethod(ASTMethodDeclaration methodNode, SymbolClass symbolClass) {
        ArrayList<Symbol> possibleMethods = symbolClass.getSymbol(methodNode.name);

        if(possibleMethods.size() == 1)
            return (SymbolMethod) possibleMethods.get(0);

        return null;
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

        if(argNode.jjtGetChild(0) instanceof ASTType)
           return getType((ASTType) argNode.jjtGetChild(0));

        return "";
    }

    private void generateRegisters(SimpleNode methodNode, SymbolMethod symbolMethod) {

        int registerCounter = 1;

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++){

            if (methodNode.jjtGetChild(i) instanceof ASTArg){
                symbolMethod.symbolTable.get(((ASTArg) methodNode.jjtGetChild(i)).val).setRegister(registerCounter);
                registerCounter++;
                continue;
            }

            if(methodNode.jjtGetChild(i) instanceof ASTVarDeclaration){
                ASTVarDeclaration varDeclaration = (ASTVarDeclaration) methodNode.jjtGetChild(i);
                symbolMethod.symbolTable.get(varDeclaration.name).setRegister(registerCounter);
                registerCounter++;
                continue;
            }
        }
    }

    private void generateMethodBody(SimpleNode method, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        for (int i = 0; i < method.jjtGetNumChildren(); i++) {

            SimpleNode node = (SimpleNode) method.jjtGetChild(i);
            int j=1;
            if(node instanceof ASTEquality){
                System.out.println("equality: " + j);
                i++;
                //System.out.println("equality");
                //System.out.println("nr filhos equality: " + node.jjtGetNumChildren());
                generateEquality((ASTEquality) node, symbolClass, symbolMethod);
            }

            //TODO -> complete with return, dought expressions, if, while...
        }

    }

    private void generateEquality(ASTEquality node, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        ASTIdentifier lhs = (ASTIdentifier) node.jjtGetChild(0);  //left identifier
        SimpleNode rhs = (SimpleNode) node.jjtGetChild(1);  //right side

        //System.out.println("Nr filhos direita: " + rhs.jjtGetNumChildren());

        generateLhs(lhs);
        generateRhs(rhs);

        //TODO -> parse left side of expression
    }

    private void generateLhs(ASTIdentifier lhs){

        String varName = lhs.val;

        //storeLocalVariable(lhs, varName);
        //System.out.println("varname: " + varName);

        if(lhs.jjtGetNumChildren() != 0 && lhs.jjtGetChild(0) instanceof ASTaccessToArray)
        {
            //access to array
        } else {

            //not access to array
        }

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

/*
    private void storeLocalVariable(SimpleNode node, String varName){
		int index = node.getSymbolIndex(varName);
		String store = "";
		
		if(index<=3)
			store="store_";
		else store="store ";
		
		System.out.println("i"+store+varIndex);	
    }
*/
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