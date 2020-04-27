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
        //System.out.println(this.symbolTable.toString());

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
                SymbolMethod symbolMethod = getSymbolMethod(symbolClass.symbolTableMethods.get("main"), i);
                generateMainMethod(child, symbolClass, symbolMethod);
            }
            if(child instanceof ASTMethodDeclaration){
                ASTMethodDeclaration methodDeclaration = (ASTMethodDeclaration) child;
                SymbolMethod symbolMethod = getSymbolMethod(symbolClass.symbolTableMethods.get(methodDeclaration.name), i);

                if(symbolMethod == null) {
                    System.err.println("ERROR generating code for method " + methodDeclaration.name);
                    System.exit(0);
                }

                generateMethod(methodDeclaration, symbolClass, symbolMethod);
            }
        }

    }

    private void generateConstructor() {
        printWriterFile.println("\n.method public<init>()V");
        printWriterFile.println("\taload_0");
        printWriterFile.println("\tinvokenonvirtual java/lang/Object<init>()V");//TO-DO if the class extends, we need to change "Object" with extended class name
        printWriterFile.println("\treturn");
        printWriterFile.println(".end method\n");
    }

    private void generateMainMethod(SimpleNode mainNode, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        this.printWriterFile.println(".method public static main([Ljava/lang/String;)V");

        generateIndexes(mainNode, symbolMethod);
        generateMethodBody(mainNode, symbolClass, symbolMethod);

        printWriterFile.write(".endMethod\n\n");
    }

    private void generateMethod(ASTMethodDeclaration methodNode, SymbolClass symbolClass, SymbolMethod symbolMethod){
        //Header for method
        generateMethodHeader(methodNode);

        printWriterFile.println("\t.limit stack 99");//TODO calculate stack and locals, just for ckpt3
        printWriterFile.println("\t.limit locals 99\n");

        generateIndexes(methodNode, symbolMethod);
        generateMethodBody(methodNode, symbolClass, symbolMethod);

        printWriterFile.write(".endMethod\n\n");
    }


    private SymbolMethod getSymbolMethod(ArrayList<SymbolMethod> listSymbolMethod, int num) {

        if(listSymbolMethod.size() == 1)
            return (SymbolMethod) listSymbolMethod.get(0);

        for(int i = 0; i < listSymbolMethod.size(); i++) {

            SymbolMethod symbolMethod = listSymbolMethod.get(i);

            System.out.println(symbolMethod.num + " : " + num);

            if (symbolMethod.num == num) {
                return symbolMethod;
            }
        }

        return null;

    }


    private SymbolMethod getMethod(ASTMethodDeclaration methodNode, SymbolClass symbolClass) {
        ArrayList<SymbolMethod> possibleMethods = symbolClass.symbolTableMethods.get(methodNode.name);

        if(possibleMethods.size() == 1)
            return possibleMethods.get(0);

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

    private void generateIndexes(SimpleNode methodNode, SymbolMethod symbolMethod) {

        int indexCounter = 1;

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++){

            if (methodNode.jjtGetChild(i) instanceof ASTArg){
                symbolMethod.symbolTable.get(((ASTArg) methodNode.jjtGetChild(i)).val).setIndex(indexCounter);
                indexCounter++;
                continue;
            }

            if(methodNode.jjtGetChild(i) instanceof ASTVarDeclaration){
                ASTVarDeclaration varDeclaration = (ASTVarDeclaration) methodNode.jjtGetChild(i);
                symbolMethod.symbolTable.get(varDeclaration.name).setIndex(indexCounter);
                indexCounter++;
                continue;
            }
        }
    }

    private void generateMethodBody(SimpleNode method, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        for (int i = 0; i < method.jjtGetNumChildren(); i++) {

            SimpleNode node = (SimpleNode) method.jjtGetChild(i);
            if(node instanceof ASTEquality){
                generateEquality((ASTEquality) node, symbolClass, symbolMethod);
            }            
            //Return
            if(node instanceof ASTReturn){
                if(node.jjtGetNumChildren() != 1){//expression

                }
                else{
                    if(symbolMethod.getType() == Type.INT){
                    printWriterFile.println("\tireturn");
                    return;
                    }
                    else printWriterFile.println("\treturn");//void
                }
            }
            //TODO -> complete with, dot expressions, if, while...

        }

    }

    private void generateEquality(ASTEquality node, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        ASTIdentifier lhs = (ASTIdentifier) node.jjtGetChild(0);  //left identifier
        SimpleNode rhs = (SimpleNode) node.jjtGetChild(1);  //right side


        System.out.println("lhs: " + lhs.val + "\n\tNr filhos direita: " + rhs.jjtGetNumChildren());
        if(lhs.jjtGetNumChildren() != 0 && lhs.jjtGetChild(0) instanceof ASTaccessToArray)
        {
            //TODO -> access to array
            generateRhs(rhs, symbolMethod);
            this.printWriterFile.println("\tiastore");
        } else {
            generateLhs(lhs, symbolMethod);
            generateRhs(rhs, symbolMethod);
        }
    }

    private void generateLhs(ASTIdentifier lhs, SymbolMethod symbolMethod){
        storeLocalVariable(lhs, symbolMethod);

        //TODO -> Global variable
    }

    private void generateRhs(SimpleNode rhs, SymbolMethod symbolMethod) {
        if (rhs != null) {
            if (rhs.jjtGetNumChildren() > 1) {            
                generateOperation(rhs, symbolMethod);
            }
            else if (rhs instanceof ASTIdentifier) {
                ASTIdentifier identifier = (ASTIdentifier) rhs;
                this.loadLocalVariable(identifier, symbolMethod);
            }
            else if(rhs instanceof ASTLiteral){
                ASTLiteral literal = (ASTLiteral) rhs;
                loadIntLiteral(literal.val);
            }  
            else if (rhs instanceof ASTNewObject) {
                ASTNewObject object = (ASTNewObject) rhs;
                //System.out.println("object: " + object.val);
                generateNewObject(object, symbolMethod);
            }
            else if(rhs instanceof ASTDotExpression){
                System.out.println("dot expression");
            }
        }
    }

    private void generateOperation(SimpleNode operation, SymbolMethod symbolMethod) {        
        SimpleNode lhs = (SimpleNode) operation.jjtGetChild(0);
        SimpleNode rhs = (SimpleNode) operation.jjtGetChild(1);

        if(operation instanceof ASTDotExpression) {
            System.out.println("dot expression operation");
            generateDotExpression(operation, symbolMethod);
        }

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

    private void storeLocalVariable(ASTIdentifier identifier, SymbolMethod symbolMethod){
        int index = symbolMethod.symbolTable.get(identifier.val).getIndex();
		String store = "";
        String type="";
		
		Type varType = symbolMethod.symbolTable.get(identifier.val).getType();

        if (varType == Type.INT || varType == Type.BOOLEAN)
            type = "i";
        else
            type = "a";

        if (index <= 3)
            store = "store_";
        else
            store = "store ";

        this.printWriterFile.println("\t" + type + store + index);
    }

    private void loadLocalVariable(ASTIdentifier identifier, SymbolMethod symbolMethod){
		String store = "";
        String type="";
        
        int index = symbolMethod.symbolTable.get(identifier.val).getIndex();
		Type varType = symbolMethod.symbolTable.get(identifier.val).getType();

        if (varType == Type.INT || varType == Type.BOOLEAN)
            type = "i";
        else
            type = "a";

        if (index <= 3)
            store = "load_";
        else
            store = "load ";

        this.printWriterFile.println("\t" + type + store + index);
    }


    private void loadIntLiteral(String val) {
        String output = "";
        int value = Integer.parseInt(val);

        if ((value >= 0) && (value <= 5)) {
            output += "\ticonst_" + value;
        } else if (value == -1) {
            output += "\ticonst_m1";
        } else if (value > -129 && value < 128) {
            output += "\tbipush " + value;
        } else if (value > -32769 && value < 32768) {
            output += "\tsipush " + value;
        } else {
            output += "\tldc " + value;
        }
        this.printWriterFile.println(output);
    }

    private void generateNewObject(ASTNewObject object, SymbolMethod symbolMethod){
		Type varType = null;
        if(symbolMethod.symbolTable.get(object.val)!=null) 
            varType = symbolMethod.symbolTable.get(object.val).getType();

        if (varType == Type.INT_ARRAY) {
            generateArrayInitilization(object);
        } else {
            this.printWriterFile.println("\tnew " + object.val + "\n\tdup");
            this.printWriterFile.println("\tinvokespecial " + object.val + "/<init>()V");
        }
    }

    private void generateArrayInitilization(ASTNewObject object) {

        //TODO
        this.printWriterFile.println("\tnewarray int");
    }


    private void generateDotExpression(SimpleNode node, SymbolMethod symbolMethod){
        SimpleNode leftPart = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightPart = (SimpleNode) node.jjtGetChild(1);

        if (leftPart instanceof ASTIdentifier && rightPart instanceof ASTIdentifier) {
            ASTIdentifier leftIdentifier = (ASTIdentifier) node.jjtGetChild(0);
            ASTIdentifier rightIdentifier = (ASTIdentifier) node.jjtGetChild(1);

            System.out.println("Left part dot expression: " + leftIdentifier.val);
            System.out.println("Right part dot expression: " + rightIdentifier.val);

            if (leftIdentifier.val.equals("this")) {
                //TODO
            }
            else if(rightIdentifier.val.equals("length")){
                //TODO
            } else {
                generateCall(symbolMethod, leftIdentifier, rightIdentifier);
            }
        }
    }

    private void generateCall(SymbolMethod symbolMethod, ASTIdentifier identifier1, ASTIdentifier identifier2) {
        //Doing this
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