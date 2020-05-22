import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import symbolTable.*;

public class CodeGenerator {

    private PrintWriter printWriterFile;
    private final HashMap<String, Symbol> symbolTable;
    private int loopCounter;
    private int localVars = 0;
    private int nParams = 0;
    private int maxStack = 0;
    private int totalStack = 0;

    public CodeGenerator(SemanticAnalysis semanticAnalysis) {
        symbolTable = semanticAnalysis.getSymbolTable();
        this.loopCounter = 0;
    }

    public void generate(SimpleNode node) {

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i) instanceof ASTClassDeclaration) {
                ASTClassDeclaration classNode = (ASTClassDeclaration) node.jjtGetChild(i);

                this.printWriterFile = createOutputFile(classNode.name);
                this.generateClass(classNode);
                this.printWriterFile.close();

            }
        }
    }

    private void generateClass(ASTClassDeclaration classNode) {
        this.localVars = 0;
        this.printWriterFile.println(".class public " + classNode.name);

        if (classNode.ext != null)
            this.printWriterFile.println(".super " + classNode.ext);
        else
            this.printWriterFile.println(".super java/lang/Object");

        SymbolClass symbolClass = (SymbolClass) this.symbolTable.get(classNode.name);

        generateClassVariables(classNode);
        generateExtend(classNode);
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

    private void generateGlobalVar(ASTVarDeclaration var) {

        if (var.jjtGetChild(0) instanceof ASTType) {
            ASTType nodeType = (ASTType) var.jjtGetChild(0);
            printWriterFile.println(".field public " + var.name + " " + getType(nodeType));
        }
    }

    private String getType(ASTType nodeType) {

        if (nodeType.isArray)
            return "[I";

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
        return "L" + nodeType.type + ";";
    }

    private String getSymbolType(Type symbolType) {

        if (symbolType == Type.INT_ARRAY)
            return "[I";

        switch (symbolType) {
            case INT:
                return "I";
            case STRING:
                return "Ljava/lang/String";
            case BOOLEAN:
                return "B";
            case VOID:
                return "V";
        }
        return "";
    }

    private void generateExtend(ASTClassDeclaration node) {
        if (node.ext != null)
            this.printWriterFile.println("\n.method public <init>()V\n\taload_0\n\tinvokenonvirtual "
                    + node.ext + "/<init>()V\n\treturn\n.end method\n");
        else
            generateConstructor();
    }

    private void generateConstructor() {
        printWriterFile.println("\n.method public <init>()V");
        printWriterFile.println("\taload_0");
        printWriterFile.println("\tinvokenonvirtual java/lang/Object/<init>()V");
        printWriterFile.println("\treturn");
        printWriterFile.println(".end method\n");
    }

    private void generateMethods(SimpleNode node, SymbolClass symbolClass) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);

            if (child instanceof ASTMainDeclaration) {
                SymbolMethod symbolMethod = getSymbolMethod(symbolClass.symbolTableMethods.get("main"), i);
                generateMainMethod(child, symbolClass, symbolMethod);
            }
            if (child instanceof ASTMethodDeclaration) {
                ASTMethodDeclaration methodDeclaration = (ASTMethodDeclaration) child;
                SymbolMethod symbolMethod = getSymbolMethod(symbolClass.symbolTableMethods.get(methodDeclaration.name), i);

                if (symbolMethod == null) {
                    System.err.println("ERROR generating code for method " + methodDeclaration.name);
                    System.exit(0);
                }
                generateMethod(methodDeclaration, symbolClass, symbolMethod);
            }
        }

    }

    private void generateMainMethod(SimpleNode mainNode, SymbolClass symbolClass, SymbolMethod symbolMethod) {        
        this.printWriterFile.println(".method public static main([Ljava/lang/String;)V");

        generateIndexes(mainNode, symbolMethod);
        
        int localLimits = this.localVars+1; //nParams = 1

        printWriterFile.println("\t.limit stack 99");
        printWriterFile.println("\t.limit locals " + localLimits + "\n");

        generateMethodBody(mainNode, symbolClass, symbolMethod);

        printWriterFile.println("\treturn");
        printWriterFile.println(".end method\n\n");
        this.localVars = 0;
    }

    private void generateMethod(ASTMethodDeclaration methodNode, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        //Header for method
        generateMethodHeader(methodNode);
        generateIndexes(methodNode, symbolMethod);

        int localLimits = this.localVars + this.nParams;

        if(!symbolMethod.isStatic())
            localLimits+=1;

        printWriterFile.println("\t.limit stack 99");
        printWriterFile.println("\t.limit locals " + localLimits + "\n");
        generateMethodBody(methodNode, symbolClass, symbolMethod);

        printWriterFile.write(".end method\n\n");
        this.localVars = 0;
        this.nParams = 0;
    }

    private SymbolMethod getSymbolMethod(ArrayList<SymbolMethod> listSymbolMethod, int num) {
        if (listSymbolMethod.size() == 1)
            return listSymbolMethod.get(0);

        for (SymbolMethod symbolMethod : listSymbolMethod) {
            if (symbolMethod.num == num) {
                return symbolMethod;
            }
        }
        return null;
    }

    private void generateMethodHeader(ASTMethodDeclaration methodNode) {

        StringBuilder methodArgs = new StringBuilder();
        StringBuilder methodType = new StringBuilder();

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) methodNode.jjtGetChild(i);
            if (child instanceof ASTArg) {
                if (child.jjtGetChild(0) instanceof ASTType) {
                    methodArgs.append(generateMethodArgument((ASTArg) child));
                    this.nParams++;
                }
            }
            if (child instanceof ASTType) {
                methodType.append(getType((ASTType) child));
            }
        }

        this.printWriterFile.println(".method public " + methodNode.name + "(" + methodArgs + ")" + methodType);
    }

    private String generateMethodArgument(ASTArg argNode) {
        if (argNode.jjtGetChild(0) instanceof ASTType)
            return getType((ASTType) argNode.jjtGetChild(0));

        return "";
    }

    private void generateIndexes(SimpleNode methodNode, SymbolMethod symbolMethod) {

        int indexCounter = 1;

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {

            if (methodNode.jjtGetChild(i) instanceof ASTArg) {
                symbolMethod.symbolTable.get(((ASTArg) methodNode.jjtGetChild(i)).val).setIndex(indexCounter);
                indexCounter++;
                continue;
            }

            if (methodNode.jjtGetChild(i) instanceof ASTVarDeclaration) {
                ASTVarDeclaration varDeclaration = (ASTVarDeclaration) methodNode.jjtGetChild(i);
                symbolMethod.symbolTable.get(varDeclaration.name).setIndex(indexCounter);
                this.localVars++;
                indexCounter++;
                continue;
            }
        }
    }

    private void generateMethodBody(SimpleNode method, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        for (int i = 0; i < method.jjtGetNumChildren(); i++) {

            SimpleNode node = (SimpleNode) method.jjtGetChild(i);

            //Already processed
            if (node instanceof ASTArg || node instanceof ASTVarDeclaration) {
                continue;
            }

            if (node instanceof ASTReturn) {

                Type type = generateExpression((SimpleNode) node.jjtGetChild(0), symbolClass, symbolMethod); //expression
                generateMethodReturn(type);
                return;
            }

            //If is not any of the others, it is a statement
            generateStatement(node, symbolClass, symbolMethod);

        }
    }

    private void generateStatement(SimpleNode node, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if (node instanceof ASTEquality) {
            generateEquality((ASTEquality) node, symbolClass, symbolMethod);

        } else if (node instanceof ASTIf) {
            generateIfExpression(node, symbolClass, symbolMethod);

        } else if (node instanceof ASTWhile) {

            generateWhileExpression(node, symbolClass, symbolMethod);

        } else if (node instanceof ASTStatementBlock) {
            for (int i = 0; i < node.jjtGetNumChildren(); i++)
                generateStatement((SimpleNode) node.jjtGetChild(i), symbolClass, symbolMethod);
        }

        //If it is not any of the others it is an expression
        generateExpression(node, symbolClass, symbolMethod);


    }


    private void generateWhileExpression(SimpleNode node, SymbolClass symbolClass, SymbolMethod symbolMethod){
        this.loopCounter++;
        int thisCounter = this.loopCounter;
        SimpleNode testExpression = (SimpleNode) node.jjtGetChild(0);
        SimpleNode statement = (SimpleNode) node.jjtGetChild(1);

        this.printWriterFile.println("while_" + thisCounter + "_begin:");

        //evaluate expression
        if (!generateConditional(testExpression, symbolClass, symbolMethod, thisCounter, "while_", "_end"))
            return;

        generateStatement(statement, symbolClass, symbolMethod);
        this.printWriterFile.println("\tgoto while_" + thisCounter + "_begin");
        this.printWriterFile.println("while_" + thisCounter + "_end:");
    }

    private void generateIfExpression(SimpleNode node, SymbolClass symbolClass, SymbolMethod symbolMethod){
        this.loopCounter++;
        int thisCounter = this.loopCounter;

        SimpleNode expression = (SimpleNode) node.jjtGetChild(0);
        SimpleNode ifBlock = (SimpleNode) node.jjtGetChild(1);
        SimpleNode elseBlock = (SimpleNode) node.jjtGetChild(2);

        //**********Expression Test************
        if (!generateConditional(expression, symbolClass, symbolMethod, thisCounter, "if_", "_else"))
            return;

        //**************************************

        // *********IF BLOCK*********************
        generateStatement(ifBlock, symbolClass, symbolMethod);
        this.printWriterFile.println("\tgoto if_" + thisCounter + "_end");
        //************************** */

        //*******ELSE BLOCK ***********/
        this.printWriterFile.println("if_" + thisCounter + "_else:");
        generateStatement(elseBlock, symbolClass, symbolMethod);
        this.printWriterFile.println("if_" + thisCounter + "_end:");
        //******************************** */
    }

    private boolean generateConditional(SimpleNode expression, SymbolClass symbolClass, SymbolMethod symbolMethod, int thisCounter, String firstPartTag, String secondPartTag){

        if (expression instanceof ASTBoolean) {
            generateBoolean((ASTBoolean) expression);
            this.printWriterFile.println("\tifeq " + firstPartTag + thisCounter + secondPartTag);

            return true;

        }

        if (expression instanceof ASTLESSTHAN) {

            if(expression.jjtGetNumChildren() != 2)
                return false;

            generateExpression((SimpleNode) expression.jjtGetChild(0), symbolClass, symbolMethod);
            generateExpression((SimpleNode) expression.jjtGetChild(1), symbolClass, symbolMethod);

            this.printWriterFile.println("\tif_icmpge " + firstPartTag + thisCounter + secondPartTag);

            return true;

        }

        if (expression instanceof ASTAND) {

            if(expression.jjtGetNumChildren() != 2)
                return false;

            // Code for first child
            generateExpression((SimpleNode) expression.jjtGetChild(0), symbolClass, symbolMethod);
            this.printWriterFile.println("\tif_eq " + firstPartTag + thisCounter + secondPartTag);

            //Code for second child
            generateExpression((SimpleNode) expression.jjtGetChild(1), symbolClass, symbolMethod);
            this.printWriterFile.println("\tif_eq " + firstPartTag + thisCounter + secondPartTag);

            return true;

        }

        if(expression instanceof ASTNegation) {

            if(expression.jjtGetNumChildren() != 1)
                return false;

            generateExpression((SimpleNode) expression.jjtGetChild(0), symbolClass, symbolMethod);

            this.printWriterFile.println("\tifne if_" + thisCounter + "_else");

        }

        return false;
    }



    private void generateMethodReturn(Type type) {

        if (type != null) {
            switch (type) {
                case BOOLEAN:
                case INT:
                    this.printWriterFile.println("\tireturn");
                    break;
                case VOID:
                    this.printWriterFile.println("\treturn");
                    break;
                default:
                    this.printWriterFile.println("\tareturn");
                    break;
            }
        }
    }

    private void generateEquality(ASTEquality node, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        ASTIdentifier lhs = (ASTIdentifier) node.jjtGetChild(0);  //left identifier
        SimpleNode rhs = (SimpleNode) node.jjtGetChild(1);  //right side

        //Special Case for element of array
        if (lhs.jjtGetNumChildren() != 0 && lhs.jjtGetChild(0) instanceof ASTaccessToArray) {
            ASTaccessToArray arrayAccess = (ASTaccessToArray) lhs.jjtGetChild(0);
            generateAccessToArray(lhs, arrayAccess, symbolClass, symbolMethod);
            generateExpression(rhs, symbolClass, symbolMethod);
            this.printWriterFile.println("\tiastore");

        } else {
            generateExpression(rhs, symbolClass, symbolMethod);
            storeVariable(lhs, symbolClass, symbolMethod);

        }

        this.printWriterFile.println();
    }

    private Type generateExpression(SimpleNode node, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        if (node != null) {
            if (node instanceof ASTAND) {
                generateAnd((ASTAND) node, symbolClass, symbolMethod);
                return Type.BOOLEAN;

            } else if (node instanceof ASTLESSTHAN) {

                generateLessThan((ASTLESSTHAN) node, symbolClass, symbolMethod);
                return Type.BOOLEAN;

            } else if (node instanceof ASTSUM) {
                generateOperation(node, symbolClass, symbolMethod);
                this.printWriterFile.println("\tiadd");
                return Type.INT;


            } else if (node instanceof ASTSUB) {
                generateOperation(node, symbolClass, symbolMethod);
                this.printWriterFile.println("\tisub");

            } else if (node instanceof ASTMUL) {
                generateOperation(node, symbolClass, symbolMethod);
                this.printWriterFile.println("\timul");
                return Type.INT;

            } else if (node instanceof ASTDIV) {
                generateOperation(node, symbolClass, symbolMethod);
                this.printWriterFile.println("\tidiv");
                return Type.INT;

            } else if (node instanceof ASTIdentifier) {

                return generateIdentifier((ASTIdentifier) node, symbolClass, symbolMethod);

            } else if (node instanceof ASTLiteral) {
                loadIntLiteral(((ASTLiteral) node).val);
                return Type.INT;

            } else if (node instanceof ASTNewObject) {
                ASTNewObject object = (ASTNewObject) node;
                generateNewObject(object, symbolClass, symbolMethod);
                return Type.OBJECT;

            } else if (node instanceof ASTInitializeArray) {
                generateArrayInitialization((ASTInitializeArray) node, symbolClass, symbolMethod);
                return Type.INT_ARRAY;

            } else if (node instanceof ASTNegation) {
                generateNegation((ASTNegation) node, symbolClass, symbolMethod);
                return Type.BOOLEAN;

            } else if (node instanceof ASTBoolean) {
                this.generateBoolean((ASTBoolean) node);
                return Type.BOOLEAN;

            } else if (node instanceof ASTDotExpression){
                return this.generateDotExpression(node, symbolClass, symbolMethod);
            }
        }

        return null;
    }

    private Type generateIdentifier(ASTIdentifier node, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if( node.jjtGetNumChildren() == 1) {

            Type returnType = null;
            if (node.jjtGetChild(0) instanceof ASTaccessToArray){

                returnType = generateAccessToArray(node, (ASTaccessToArray) node.jjtGetChild(0), symbolClass, symbolMethod);
                this.printWriterFile.println("\tiaload");
            }

            return returnType;
        }

        return loadVariable(node.val, symbolClass, symbolMethod);

    }

    private void generateAnd(ASTAND node, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if(node.jjtGetNumChildren() != 2)
            return;

        this.loopCounter++;
        int thisCounter = this.loopCounter;

        // Code for first child
        generateExpression((SimpleNode) node.jjtGetChild(0), symbolClass, symbolMethod);
        this.printWriterFile.println("\tif_eq AND_" + thisCounter);

        //Code for second child
        generateExpression((SimpleNode) node.jjtGetChild(1), symbolClass, symbolMethod);
        this.printWriterFile.println("\tif_eq AND_" + thisCounter);

        //If both are true
        // *********IN CASE EXPRESSION IS TRUE *********************
        this.printWriterFile.println("\ticonst_1");
        this.printWriterFile.println("\tgoto AND_" + thisCounter + "_end");
        //************************** */

        //******* IN CASE EXPRESSION IS FALSE ***********/
        this.printWriterFile.println("AND_" + thisCounter + ":");
        this.printWriterFile.println("\ticonst_0");
        this.printWriterFile.println("AND_" + thisCounter + "_end:");
        //******************************** */

    }

    private void generateLessThan(ASTLESSTHAN node, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if(node.jjtGetNumChildren() != 2)
            return;

        this.loopCounter++;
        int thisCounter = this.loopCounter;


        generateExpression((SimpleNode) node.jjtGetChild(0), symbolClass, symbolMethod);
        generateExpression((SimpleNode) node.jjtGetChild(1), symbolClass, symbolMethod);

        this.printWriterFile.println("\tif_icmpge lessThan_" + thisCounter);

        // *********IN CASE EXPRESSION IS TRUE *********************
        this.printWriterFile.println("\ticonst_1");
        this.printWriterFile.println("\tgoto lessThan_" + thisCounter + "_end");
        // **************************

        // ******* IN CASE EXPRESSION IS FALSE
        this.printWriterFile.println("lessThan_" + thisCounter + ":");
        this.printWriterFile.println("\ticonst_0");
        this.printWriterFile.println("lessThan_" + thisCounter + "_end:");
        // ********************************

    }


    private void generateNegation(ASTNegation node, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if(node.jjtGetNumChildren() != 1)
            return;

        this.loopCounter++;
        int thisCounter = this.loopCounter;

        generateExpression((SimpleNode) node.jjtGetChild(0), symbolClass, symbolMethod);

        this.printWriterFile.println("\tifne negation_" + thisCounter);

        // *********IN CASE EXPRESSION IS TRUE *********************
        this.printWriterFile.println("\ticonst_1");
        this.printWriterFile.println("\tgoto negation_" + thisCounter + "_end");
        //************************** */

        //******* IN CASE EXPRESSION IS FALSE ***********/
        this.printWriterFile.println("negation_" + thisCounter + ":");
        this.printWriterFile.println("\ticonst_0");
        this.printWriterFile.println("negation_" + thisCounter + "_end:");
        //******************************** */

    }

    private void generateBoolean(ASTBoolean node) {

        this.printWriterFile.println("\ticonst_" + ((node.val) ? "1" : "0") );

    }

    private void generateOperation(SimpleNode operation, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if(operation.jjtGetNumChildren() != 2)
            return;

        generateExpression((SimpleNode) operation.jjtGetChild(0), symbolClass, symbolMethod);
        generateExpression((SimpleNode) operation.jjtGetChild(1), symbolClass, symbolMethod);
    }

    private void storeVariable(ASTIdentifier identifier, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if (symbolMethod.symbolTable.get(identifier.val) != null) {
            Type varType = symbolMethod.symbolTable.get(identifier.val).getType();
            int index = symbolMethod.symbolTable.get(identifier.val).getIndex();

            String type = (varType == Type.INT || varType == Type.BOOLEAN) ? "i" : "a";

            String store = (index <= 3) ? "store_" : "store ";

            this.printWriterFile.println("\t" + type + store + index);

        } else if(symbolClass.symbolTableFields.get(identifier.val) != null) {

            //TODO: check if correct

            Type varType = symbolClass.symbolTableFields.get(identifier.val).getType();
            this.printWriterFile.println("\taload_0");
            this.printWriterFile.println("\tputfield " + identifier.val + ":" + getSymbolType(varType) );

        }
    }

    private Type loadVariable(String val, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        if(val.equals("this")) {
            this.printWriterFile.println("\taload_0");
            return Type.OBJECT;
        }

        if (symbolMethod.symbolTable.get(val) != null) {
            Type varType = symbolMethod.symbolTable.get(val).getType();
            int index = symbolMethod.symbolTable.get(val).getIndex();

            String type = (varType == Type.INT || varType == Type.BOOLEAN) ? "i" : "a";
            String store = (index <= 3) ? "load_" : "load ";

            this.printWriterFile.println("\t" + type + store + index);

            return varType;

        }

        if (symbolClass.symbolTableFields.get(val) != null){

            //TODO: check if correct

            Type varType = symbolClass.symbolTableFields.get(val).getType();
            this.printWriterFile.println("\t aload_0");
            this.printWriterFile.println("\tgetfield " + val + ":" + getSymbolType(varType) );

            return varType;

        }

        return null;
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

    private void generateNewObject(ASTNewObject object, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        //TODO: missing new object with parameters

        Type varType = null;

        if (symbolMethod.symbolTable.get(object.val) != null)
            varType = symbolMethod.symbolTable.get(object.val).getType();


        this.printWriterFile.println("\tnew " + object.val + "\n\tdup");
        this.printWriterFile.println("\tinvokespecial " + object.val + "/<init>()V");

    }

    private void generateArrayInitialization(ASTInitializeArray arrayInit, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        if (arrayInit.jjtGetChild(0) instanceof ASTLiteral) {
            ASTLiteral arg = (ASTLiteral) arrayInit.jjtGetChild(0);
            loadIntLiteral(arg.val);
        }
        else if (arrayInit.jjtGetChild(0) instanceof ASTIdentifier) {

            ASTIdentifier arg = (ASTIdentifier) arrayInit.jjtGetChild(0);
            loadVariable(arg.val, symbolClass, symbolMethod);
        }

        this.printWriterFile.println("\tnewarray int");
    }

    private Type generateAccessToArray(ASTIdentifier node, ASTaccessToArray arrayAccess, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        Type returnType = loadVariable(node.val, symbolClass, symbolMethod);

        if (arrayAccess.jjtGetChild(0) instanceof ASTLiteral) {
            ASTLiteral arrayPos = (ASTLiteral) arrayAccess.jjtGetChild(0);
            loadIntLiteral(arrayPos.val);
        }

        return returnType;
    }

    private Type generateDotExpression(SimpleNode node, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        SimpleNode leftPart = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightPart = (SimpleNode) node.jjtGetChild(1);

        if(rightPart instanceof ASTIdentifier) {

            ASTIdentifier rightIdentifier = (ASTIdentifier) node.jjtGetChild(1);

            if (leftPart instanceof ASTIdentifier) {
                ASTIdentifier leftIdentifier = (ASTIdentifier) node.jjtGetChild(0);

                if (leftIdentifier.val.equals("this")) {
                    return generateThisStatement(leftIdentifier, rightIdentifier, symbolClass, symbolMethod);
                }

                if (rightIdentifier.val.equals("length")) {

                    this.generateExpression(leftIdentifier, symbolClass, symbolMethod);
                    this.printWriterFile.println("\tarraylength");
                    return Type.INT;

                }

                return generateCall(leftIdentifier, rightIdentifier, symbolClass, symbolMethod);

            }

            if (leftPart instanceof ASTNewObject) {

                ASTNewObject leftIdentifier = (ASTNewObject) node.jjtGetChild(0);

                return generateCallObject(leftIdentifier, rightIdentifier, symbolClass, symbolMethod);

            }
        }

        return null;
    }


    private Type generateCallObject(ASTNewObject node1, ASTIdentifier node2, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        this.generateExpression(node1, symbolClass, symbolMethod);

        if (symbolTable.containsKey(node1.val)) {

            if (symbolTable.get(node1.val) instanceof SymbolClass) {

                SymbolClass sc = (SymbolClass) symbolTable.get(node1.val);
                return generateCallForMethod(node2, sc, symbolClass, symbolMethod, true);
            }
        }

        return null;

    }

    private Type generateThisStatement( ASTIdentifier identifier1, ASTIdentifier identifier2, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        this.generateExpression(identifier1, symbolClass, symbolMethod);

        //if it is a call for variable/field
        if (!identifier2.method) {
            if (symbolClass.symbolTableFields.containsKey(identifier2.val)) {
                return symbolClass.symbolTableFields.get(identifier2.val).getType();
            }

            return null;

        }

        //Check if current class has any method with the same signature
        if (symbolClass.symbolTableMethods.containsKey(identifier2.val)) {

            return generateCallForMethod(identifier2, symbolClass, symbolClass, symbolMethod, true);
        }

        //check if method is defined in super class, if it is not defined in the current class
        if (symbolTable.containsKey(symbolClass.superClass)) {

            SymbolClass sc = (SymbolClass) symbolTable.get(symbolClass.superClass);
            return generateCallForMethod(identifier2, sc, symbolClass, symbolMethod, true);
        }

        return null;
    }

    private Type generateCall(ASTIdentifier identifier1, ASTIdentifier identifier2, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        //Import
        if (symbolTable.containsKey(identifier1.val)) {
            if (symbolTable.get(identifier1.val) instanceof SymbolClass) {
                SymbolClass sc = (SymbolClass) symbolTable.get(identifier1.val);

                return generateCallForMethod(identifier2, sc, symbolClass, symbolMethod, false);
            }

            return null;
        }

        //Verify if first part of dot expression was declared inside the class or method
        if (symbolMethod.symbolTable.containsKey(identifier1.val) || symbolClass.symbolTableFields.containsKey(identifier1.val)) {
            if (symbolMethod.symbolTable.containsKey(identifier1.val)) {

                this.generateExpression(identifier1, symbolClass, symbolMethod);

                if (symbolMethod.symbolTable.get(identifier1.val).getType().equals(Type.OBJECT)) {

                    SymbolClass sc = (SymbolClass) symbolTable.get(symbolMethod.symbolTable.get(identifier1.val).getObject_name());
                    return generateCallForMethod(identifier2, sc, symbolClass, symbolMethod, true);
                }
            }

            return null;
        }

        return null;
    }

    private Type generateCallForMethod(ASTIdentifier identifier2, SymbolClass classOfMethod, SymbolClass symbolClass, SymbolMethod symbolMethod, boolean virtual) {

        //Check for methods
        if (identifier2.method) {

            ArrayList<Type> methodCallTypes = processArgs(identifier2, symbolClass, symbolMethod);

            //Get return type of method
            Type returnType = getReturnTypeMethod(classOfMethod, methodCallTypes, identifier2);

            StringBuilder callArgs = new StringBuilder();
            //Get list of arguments type
            if (methodCallTypes.size() > 0) {
                for (Type t : methodCallTypes) {
                    if (t != null) {
                        callArgs.append(getSymbolType(t));
                    }
                }
            }


            String methodName = identifier2.val;
            String methodType = ((returnType != null) ? getSymbolType(returnType) : "");
            String objectName = classOfMethod.name;

            this.printWriterFile.println("\t" + ((virtual) ? "invokevirtual " : "invokestatic ") + objectName + "/" + methodName + "(" + callArgs + ")" + methodType);
            this.printWriterFile.println();

            return returnType;
        }

        return null;
    }

    private Type getReturnTypeMethod( SymbolClass symbolClass, ArrayList<Type> methodCallTypes, ASTIdentifier identifier){

        if (symbolClass.symbolTableMethods.containsKey(identifier.val)) {
            return checkIfMethodExists(symbolClass.symbolTableMethods.get(identifier.val), methodCallTypes);
        }

        return null;
    }

    private ArrayList<Type> processArgs(SimpleNode node, SymbolClass symbolClass, SymbolMethod symbolMethod) {

        ArrayList<Type> methodSignature = new ArrayList<>();

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            methodSignature.add(this.generateExpression((SimpleNode) node.jjtGetChild(i), symbolClass, symbolMethod));
        }

        return methodSignature;
    }

    private Type checkIfMethodExists(ArrayList<SymbolMethod> methodArrayList, ArrayList<Type> methodSignature) {
        for (SymbolMethod sm : methodArrayList) {
            //If it has the same signature
            if (methodSignature.size() == sm.types.size()) {
                if (sm.types.equals(methodSignature))
                    return sm.getType();
            }
        }
        return null;
    }

    private void incrementStack(){
        this.totalStack++;
        if(this.totalStack>this.maxStack){
            this.maxStack = this.totalStack;
        }
    }

    private void reduceStack(int decrement){
        this.totalStack -= decrement;
    }


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
