import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import symbolTable.*;

public class CodeGenerator {

    private PrintWriter printWriterFile;
    private final HashMap<String, Symbol> symbolTable;

    public CodeGenerator(SemanticAnalysis semanticAnalysis) {
        this.symbolTable = semanticAnalysis.getSymbolTable();
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

        printWriterFile.println("\t.limit stack 99");//TODO calculate stack and locals, just for ckpt3
        printWriterFile.println("\t.limit locals 99\n");

        generateIndexes(mainNode, symbolMethod);
        generateMethodBody(mainNode, symbolClass, symbolMethod);

        printWriterFile.println("\treturn");
        printWriterFile.println(".end method\n\n");
    }

    private void generateMethod(ASTMethodDeclaration methodNode, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        //Header for method
        generateMethodHeader(methodNode);

        printWriterFile.println("\t.limit stack 99");//TODO calculate stack and locals, just for ckpt3
        printWriterFile.println("\t.limit locals 99\n");

        generateIndexes(methodNode, symbolMethod);
        generateMethodBody(methodNode, symbolClass, symbolMethod);

        printWriterFile.write(".end method\n\n");
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
                    methodArgs.append(generateArgument((ASTArg) child));
                }
            }
            if (child instanceof ASTType) {
                methodType.append(getType((ASTType) child));
            }
        }

        this.printWriterFile.println(".method public " + methodNode.name + "(" + methodArgs + ")" + methodType);
    }

    private String generateArgument(ASTArg argNode) {
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
                indexCounter++;
            }
        }
    }

    private void generateMethodBody(SimpleNode method, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        for (int i = 0; i < method.jjtGetNumChildren(); i++) {

            SimpleNode node = (SimpleNode) method.jjtGetChild(i);
            if (node instanceof ASTEquality) {
                generateEquality((ASTEquality) node, symbolClass, symbolMethod);
            }
            if (node instanceof ASTDotExpression) {
                generateDotExpression(node, symbolClass, symbolMethod);
            }

            //Return
            if (node instanceof ASTReturn) {

                if (node.jjtGetChild(0).jjtGetNumChildren() > 1) {
                    generateRhs((SimpleNode) node.jjtGetChild(0), symbolClass, symbolMethod); //expression
                }
                if (node.jjtGetChild(0) instanceof ASTLiteral) {//expression
                    ASTLiteral literal = (ASTLiteral) node.jjtGetChild(0);
                    loadIntLiteral(literal.val);
                }
                if (node.jjtGetChild(0) instanceof ASTIdentifier) {
                    ASTIdentifier identifier = (ASTIdentifier) node.jjtGetChild(0);
                    loadLocalVariable(identifier, symbolMethod);
                }

                generateMethodReturn(symbolMethod);
                return;
            }
            //TODO -> complete with, dot expressions, if, while...
        }
    }

    private void generateMethodReturn(SymbolMethod symbolMethod) {
        Type type = symbolMethod.getType();

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

        if (lhs.jjtGetNumChildren() != 0 && lhs.jjtGetChild(0) instanceof ASTaccessToArray) {
            ASTaccessToArray arrayAccess = (ASTaccessToArray) lhs.jjtGetChild(0);
            generateAccessToArray(lhs, arrayAccess, symbolMethod);
            generateRhs(rhs, symbolClass, symbolMethod);
            this.printWriterFile.println("\tiastore");
        } else if (rhs.jjtGetNumChildren() != 0 && rhs.jjtGetChild(0) instanceof ASTaccessToArray) {
            ASTaccessToArray arrayAccess = (ASTaccessToArray) rhs.jjtGetChild(0);
            generateAccessToArray((ASTIdentifier) rhs, arrayAccess, symbolMethod);
            this.printWriterFile.println("\tiaload");
            generateLhs(lhs, symbolMethod);
        } else {
            generateRhs(rhs, symbolClass, symbolMethod);
            generateLhs(lhs, symbolMethod);

        }
    }

    private void generateLhs(ASTIdentifier lhs, SymbolMethod symbolMethod) {
        storeLocalVariable(lhs, symbolMethod);

        //TODO -> Global variable
    }

    private void generateRhs(SimpleNode rhs, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        if (rhs != null) {
            if (rhs.jjtGetNumChildren() > 1) {
                generateOperation(rhs, symbolClass, symbolMethod);
            } else if (rhs instanceof ASTIdentifier) {
                ASTIdentifier identifier = (ASTIdentifier) rhs;
                this.loadLocalVariable(identifier, symbolMethod);
            } else if (rhs instanceof ASTLiteral) {
                ASTLiteral literal = (ASTLiteral) rhs;
                loadIntLiteral(literal.val);
            } else if (rhs instanceof ASTNewObject) {
                ASTNewObject object = (ASTNewObject) rhs;
                generateNewObject(object, symbolMethod);
            } else if (rhs instanceof ASTInitializeArray) {
                ASTInitializeArray arrayInit = (ASTInitializeArray) rhs;
                generateArrayInitilization(arrayInit, symbolMethod);
            }
        }
    }

    private void generateOperation(SimpleNode operation, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        SimpleNode lhs = (SimpleNode) operation.jjtGetChild(0);
        SimpleNode rhs = (SimpleNode) operation.jjtGetChild(1);


        if (operation instanceof ASTDotExpression) {
            if (lhs instanceof ASTIdentifier)
                loadLocalVariable((ASTIdentifier) lhs, symbolMethod);
            generateDotExpression(operation, symbolClass, symbolMethod);
        }
        if (operation instanceof ASTNewObject) {
            ASTNewObject object = (ASTNewObject) operation;
            generateNewObject(object, symbolMethod);
        } else {
            if (lhs.jjtGetNumChildren() == 2) {
                generateOperation(lhs, symbolClass, symbolMethod);
            } else {
                if (lhs instanceof ASTIdentifier) {
                    ASTIdentifier identifier = (ASTIdentifier) lhs;
                    this.loadLocalVariable(identifier, symbolMethod);
                } else if (lhs instanceof ASTLiteral) {
                    ASTLiteral literal = (ASTLiteral) lhs;
                    loadIntLiteral(literal.val);
                }
            }

            if (rhs.jjtGetNumChildren() == 2) {
                generateOperation(rhs, symbolClass, symbolMethod);
            } else {
                if (rhs instanceof ASTIdentifier) {
                    ASTIdentifier identifier = (ASTIdentifier) rhs;
                    this.loadLocalVariable(identifier, symbolMethod);
                } else if (rhs instanceof ASTLiteral) {
                    ASTLiteral literal = (ASTLiteral) rhs;
                    loadIntLiteral(literal.val);
                }
            }
        }

        if (operation instanceof ASTSUM)
            this.printWriterFile.println("\tiadd");
        if (operation instanceof ASTSUB)
            this.printWriterFile.println("\tisub");
        if (operation instanceof ASTMUL)
            this.printWriterFile.println("\timul");
        if (operation instanceof ASTDIV)
            this.printWriterFile.println("\tidiv");
        /*  if(operation instanceof ASTLESSTHAN)
            //TODO
        if(operation instanceof ASTAND)
            //TODO*/

    }

    private void loadVariable(ASTIdentifier var, SymbolMethod symbolMethod) {
        if (var.jjtGetNumChildren() != 0) {
            //TODO
            //generateAccessToArray
            this.printWriterFile.println("\tiaload");
        } else {
            //TODO -> global variable
            loadLocalVariable(var, symbolMethod);
        }
    }

    private void storeLocalVariable(ASTIdentifier identifier, SymbolMethod symbolMethod) {
        Type varType = null;

        int index = 0;
        String store;
        String type;

        if (symbolMethod.symbolTable.get(identifier.val) != null) {
            varType = symbolMethod.symbolTable.get(identifier.val).getType();
            index = symbolMethod.symbolTable.get(identifier.val).getIndex();
        }

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

    private void loadLocalVariable(ASTIdentifier identifier, SymbolMethod symbolMethod) {

        Type varType = null;
        String store;
        String type;
        int index = 0;

        if (symbolMethod.symbolTable.get(identifier.val) != null) {
            varType = symbolMethod.symbolTable.get(identifier.val).getType();
            index = symbolMethod.symbolTable.get(identifier.val).getIndex();
        }
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

    private void generateNewObject(ASTNewObject object, SymbolMethod symbolMethod) {
        StringBuilder argsConstructor = new StringBuilder();

        //Default constructor
        if (object.jjtGetNumChildren() == 0) {
            this.printWriterFile.println("\tnew " + object.val + "\n\tdup");
            this.printWriterFile.println("\tinvokespecial " + object.val + "/<init>()V");
        }
        //Constructor w/ arguments
        else {
            for (int i = 0; i < object.jjtGetNumChildren(); i++) {

                if (object.jjtGetChild(i) instanceof ASTLiteral) {
                    ASTLiteral literal = (ASTLiteral) object.jjtGetChild(i);
                    loadIntLiteral(literal.val);
                    argsConstructor.append("I");
                }

                if (object.jjtGetChild(i) instanceof ASTBoolean) {
                    argsConstructor.append("B");
                }

                if (object.jjtGetChild(i) instanceof ASTIdentifier) {
                    ASTIdentifier identifier = (ASTIdentifier) object.jjtGetChild(i);
                    loadLocalVariable(identifier, symbolMethod);

                    if (symbolMethod.symbolTable.get(identifier.val) != null) {
                        Type varType = symbolMethod.symbolTable.get(identifier.val).getType();
                        argsConstructor.append(getSymbolType(varType));
                    }
                }
            }

            this.printWriterFile.println("\tnew " + object.val + "\n\tdup");
            this.printWriterFile.println("\tinvokespecial " + object.val + "/<init>(" + argsConstructor + ")V");
        }

    }

    private void generateArrayInitilization(ASTInitializeArray arrayInit, SymbolMethod symbolMethod) {
        if (arrayInit.jjtGetChild(0) instanceof ASTLiteral) {
            ASTLiteral arg = (ASTLiteral) arrayInit.jjtGetChild(0);
            loadIntLiteral(arg.val);
        } else if (arrayInit.jjtGetChild(0) instanceof ASTIdentifier) {
            ASTIdentifier astIdentifier = (ASTIdentifier) arrayInit.jjtGetChild(0);
            loadLocalVariable(astIdentifier, symbolMethod);
        }

        this.printWriterFile.println("\tnewarray int");
    }

    private void generateAccessToArray(ASTIdentifier node, ASTaccessToArray arrayAccess, SymbolMethod symbolMethod) {
        loadLocalVariable(node, symbolMethod);

        if (arrayAccess.jjtGetChild(0) instanceof ASTLiteral) {
            ASTLiteral arrayPos = (ASTLiteral) arrayAccess.jjtGetChild(0);
            loadIntLiteral(arrayPos.val);
        }
    }

    private void generateDotExpression(SimpleNode node, SymbolClass symbolClass, SymbolMethod symbolMethod) {
        SimpleNode leftPart = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightPart = (SimpleNode) node.jjtGetChild(1);

        if (leftPart instanceof ASTIdentifier && rightPart instanceof ASTIdentifier) {
            ASTIdentifier leftIdentifier = (ASTIdentifier) node.jjtGetChild(0);
            ASTIdentifier rightIdentifier = (ASTIdentifier) node.jjtGetChild(1);

            if (leftIdentifier.val.equals("this")) {
                //TODO
            } else if (rightIdentifier.val.equals("length")) {
                loadVariable(leftIdentifier, symbolMethod);
                this.printWriterFile.println("\tarraylength");
            } else {
                //loadLocalVariable(leftIdentifier, symbolMethod);
                generateCall(symbolClass, symbolMethod, leftIdentifier, rightIdentifier);
            }
        }
    }

    private void generateCall(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier identifier1, ASTIdentifier identifier2) {
        String methodName = "";
        String objectName = "";
        String methodType = "";
        StringBuilder callArgs = new StringBuilder();
        Type type;
        boolean declaredInClass = false;
        ArrayList<Type> callArgsArray;

        //Import
        if (symbolTable.containsKey(identifier1.val)) {
            if (symbolTable.get(identifier1.val) instanceof SymbolClass) {
                SymbolClass sc = (SymbolClass) symbolTable.get(identifier1.val);

                if (identifier2 != null) {
                    //Check for methods
                    if (identifier2.method) {
                        methodName = identifier2.val;
                        if (identifier2.jjtGetNumChildren() > 0)
                            processArgs(identifier2, symbolMethod);
                        if (sc.symbolTableMethods.containsKey(identifier2.val)) {
                            type = checkIfMethodExists(sc.symbolTableMethods.get(identifier2.val), getMethodCallTypes(symbolMethod, identifier2));
                            if (type != null)
                                methodType += getSymbolType(type);
                        }
                        callArgsArray = getMethodCallTypes(symbolMethod, identifier2);
                        if (callArgsArray.size() > 0) {
                            for (Type t : callArgsArray) {
                                if (t != null)
                                    callArgs.append(getSymbolType(t));
                            }
                        }
                        objectName = sc.name;
                    }
                }
            }
        }

        //Verify if first part of dot expression was declared inside the class or method
        else if (symbolMethod.symbolTable.containsKey(identifier1.val) || symbolClass.symbolTableFields.containsKey(identifier1.val)) {
            declaredInClass = true;
            if (symbolMethod.symbolTable.containsKey(identifier1.val)) {
                if (symbolMethod.symbolTable.get(identifier1.val).getType().equals(Type.OBJECT)) {

                    SymbolClass sc = (SymbolClass) symbolTable.get(symbolMethod.symbolTable.get(identifier1.val).getObject_name());

                    if (identifier2 != null) {
                        //Right part of dot expression is a method
                        if (identifier2.method) {
                            methodName = identifier2.val;
                            processArgs(identifier2, symbolMethod);

                            //Get the return type of method
                            if (sc.symbolTableMethods.containsKey(identifier2.val)) {
                                type = checkIfMethodExists(sc.symbolTableMethods.get(identifier2.val), getMethodCallTypes(symbolMethod, identifier2));
                                methodType += getSymbolType(type);
                            }
                            //Get the type of arguments of method
                            callArgsArray = getMethodCallTypes(symbolMethod, identifier2);
                            objectName = sc.name;
                            for (Type t : callArgsArray) {
                                callArgs.append(getSymbolType(t));
                            }
                        }
                    }
                }
            }
        }


        if (declaredInClass)
            this.printWriterFile.println("\t" + "invokevirtual " + objectName + "/" + methodName + "(" + callArgs + ")" + methodType);
        else
            this.printWriterFile.println("\t" + "invokestatic " + objectName + "/" + methodName + "(" + callArgs + ")" + methodType);
    }

    private void processArgs(SimpleNode node, SymbolMethod symbolMethod) {

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if (child instanceof ASTIdentifier) {
                ASTIdentifier arg = (ASTIdentifier) child;
                loadLocalVariable(arg, symbolMethod);
            }
        }
    }

    private ArrayList<Type> getMethodCallTypes(SymbolMethod symbolMethod, ASTIdentifier node2) {
        ArrayList<Type> types = new ArrayList<>();

        for (int i = 0; i < node2.jjtGetNumChildren(); i++) {

            types.add(getNodeType(symbolMethod, (SimpleNode) node2.jjtGetChild(i)));
        }

        return types;
    }

    private Type getNodeType(SymbolMethod symbolMethod, SimpleNode node) {
        if (node instanceof ASTAND || node instanceof ASTLESSTHAN || node instanceof ASTBoolean) {
            return Type.BOOLEAN;
        }
        if (node instanceof ASTLiteral) {
            return Type.INT;
        }
        if (node instanceof ASTNegation) {
            if (node.jjtGetNumChildren() != 1)
                return null;
            return getNodeType(symbolMethod, (SimpleNode) node.jjtGetChild(0));
        }
        if (node instanceof ASTIdentifier) {
            ASTIdentifier identifier = (ASTIdentifier) node;
            return symbolMethod.symbolTable.get(identifier.val).getType();
        }
        if (node instanceof ASTInitializeArray) {
            return Type.INT_ARRAY;
        }
        if (node instanceof ASTNewObject) {
            return Type.OBJECT;
        }
        return null;
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
