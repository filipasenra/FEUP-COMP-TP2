import symbolTable.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SemanticAnalysis {

    private HashMap<String, Symbol> symbolTable = new HashMap<>();
    private int nErrors = 0;

    public SemanticAnalysis() {
    }

    public int getNerros() {
        return nErrors;
    }

    public void startAnalysing(SimpleNode node) {
        this.getInfo(node);

        System.out.println("Starting Semantic Analysis");
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i) instanceof ASTClassDeclaration)
                startAnalysingClass((ASTClassDeclaration) node.jjtGetChild(i));
        }
    }

    public HashMap<String, Symbol> getSymbolTable() {
        return this.symbolTable;
    }


    private void getInfo(SimpleNode node) {
        System.out.println("Getting classes, methods and respective info");
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i) instanceof ASTClassDeclaration)
                addClassInfo((ASTClassDeclaration) node.jjtGetChild(i));
            else if (node.jjtGetChild(i) instanceof ASTImport)
                addImport((ASTImport) node.jjtGetChild(i));
        }
    }

    private void addImport(ASTImport importNode) {
        SymbolClass symbolClass;

        if (!this.symbolTable.containsKey(importNode.className)) {
            symbolClass = new SymbolClass(importNode.className);
            this.symbolTable.put(importNode.className, symbolClass);
        }
        else
            symbolClass = (SymbolClass) this.symbolTable.get(importNode.className);

        SymbolMethod sm = new SymbolMethod(importNode.methodName);

        for (int i = 0; i < importNode.jjtGetNumChildren(); i++) {
            if (importNode.jjtGetChild(i) instanceof ASTParamList) {
                ASTParamList astParamList = (ASTParamList) importNode.jjtGetChild(i);

                if (astParamList.children != null) {
                    for (int j = 0; j < astParamList.children.length; j++) {
                        if (astParamList.children[i] instanceof ASTType) {
                            ASTType astType = (ASTType) astParamList.children[i];
                            sm.addType(getType(astType));
                        }
                    }
                }
            } else if (importNode.jjtGetChild(i) instanceof ASTType) {
                ASTType astType = (ASTType) importNode.jjtGetChild(i);
                sm.returnType = analysingType(sm, astType);
            }
        }

        symbolClass.addSymbol(importNode.methodName, sm);
    }

    private void addClassInfo(ASTClassDeclaration classNode) {
        SymbolClass symbolClass = new SymbolClass(classNode.name);
        this.symbolTable.put(classNode.name, symbolClass);

        for (int i = 0; i < classNode.jjtGetNumChildren(); i++) {
            if (classNode.jjtGetChild(i) instanceof ASTVarDeclaration) {
                addVarDeclarationClassInfo(symbolClass, (ASTVarDeclaration) classNode.jjtGetChild(i));
            } else if (classNode.jjtGetChild(i) instanceof ASTMethodDeclaration) {
                addMethod(symbolClass, (ASTMethodDeclaration) classNode.jjtGetChild(i));
            }
        }
    }

    private void addVarDeclarationClassInfo(SymbolClass symbolClass, ASTVarDeclaration nodeVarDeclaration) {
        SymbolVar symbolVar = new SymbolVar(nodeVarDeclaration.name);
        symbolClass.addSymbol(nodeVarDeclaration.name, symbolVar);
    }

    private void addMethod(SymbolClass symbolClass, ASTMethodDeclaration methodNode) {
        SymbolMethod symbolMethod = new SymbolMethod(methodNode.name);

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {
            if (methodNode.jjtGetChild(i) instanceof ASTArg) {
                addMethodArg(symbolMethod, (ASTArg) methodNode.jjtGetChild(i));
            }
            if (methodNode.jjtGetChild(i) instanceof ASTType) {
                symbolMethod.returnType = analysingType(symbolMethod, (ASTType) methodNode.jjtGetChild(i));
            }
        }

        if (symbolClass.symbolTable.containsKey(methodNode.name)) {
            for (int i = 0; i < symbolClass.symbolTable.get(methodNode.name).size(); i++) {
                if (symbolClass.symbolTable.get(methodNode.name).get(i) instanceof SymbolMethod) {
                    SymbolMethod sm = (SymbolMethod) symbolClass.symbolTable.get(methodNode.name).get(i);
                    if (symbolMethod.types.equals(sm.types)) {
                        this.errorMessage("Method " + methodNode.name + " already exists in class with the specified arguments.");
                        return;
                    }
                }
            }
        }

        symbolClass.addSymbol(methodNode.name, symbolMethod);
    }

    private void addMethodArg(SymbolMethod symbolMethod, ASTArg nodeArg) {
        SymbolVar symbolVar = new SymbolVar(nodeArg.val);
        symbolMethod.addSymbol(nodeArg.val, symbolVar);

        if (nodeArg.jjtGetNumChildren() != 1)
            return;

        if (nodeArg.jjtGetChild(0) instanceof ASTType) {
            symbolMethod.addType(analysingType(symbolVar, (ASTType) nodeArg.jjtGetChild(0)));
        }
    }


    private void startAnalysingClass(ASTClassDeclaration classNode) {

        SymbolClass symbolClass = (SymbolClass) this.symbolTable.get(classNode.name);

        for (int i = 0; i < classNode.jjtGetNumChildren(); i++) {

            if (classNode.jjtGetChild(i) instanceof ASTVarDeclaration) {

                analysingVarDeclarationClass(symbolClass, (ASTVarDeclaration) classNode.jjtGetChild(i));

            } else if (classNode.jjtGetChild(i) instanceof ASTMethodDeclaration) {

                startAnalysingMethod(symbolClass, (ASTMethodDeclaration) classNode.jjtGetChild(i));

            } else if (classNode.jjtGetChild(i) instanceof ASTMainDeclaration)
                startAnalysingMainDeclaration(symbolClass, (ASTMainDeclaration) classNode.jjtGetChild(i));

        }

    }

    private void startAnalysingMainDeclaration(SymbolClass symbolClass, ASTMainDeclaration methodNode) {

        if (symbolClass.symbolTable.containsKey("main")) {
            if (symbolClass.symbolTable.get("main").get(0) instanceof SymbolMethod) {
                this.errorMessage("Main already exists in class");
                return;
            }
        }

        SymbolMethod symbolMethod = new SymbolMethod("main");

        symbolClass.addSymbol("main", symbolMethod);

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {

            if (methodNode.jjtGetChild(i) instanceof ASTVarDeclaration) {

                analysingVarDeclaration(symbolMethod, (ASTVarDeclaration) methodNode.jjtGetChild(i));
                continue;
            }

            analysingStatement(symbolClass, symbolMethod, (SimpleNode) methodNode.jjtGetChild(i));

        }

    }

    private void startAnalysingMethod(SymbolClass symbolClass, ASTMethodDeclaration methodNode) {
        for (int j = 0; j < symbolClass.getSymbol(methodNode.name).size(); j++) {
            if (symbolClass.getSymbol(methodNode.name).get(j) instanceof SymbolMethod) {
                SymbolMethod symbolMethod = (SymbolMethod) symbolClass.getSymbol(methodNode.name).get(j);

                for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {
                    if (methodNode.jjtGetChild(i) instanceof ASTType) {
                        analysingType(symbolMethod, (ASTType) methodNode.jjtGetChild(i));
                        continue;
                    }

                    if (methodNode.jjtGetChild(i) instanceof ASTVarDeclaration) {
                        analysingVarDeclaration(symbolMethod, (ASTVarDeclaration) methodNode.jjtGetChild(i));
                        continue;
                    }

                    if (methodNode.jjtGetChild(i) instanceof ASTReturn) {
                        //TODO: maybe verify if return expression matches the function's return type
                        continue;
                    }

                    analysingStatement(symbolClass, symbolMethod, (SimpleNode) methodNode.jjtGetChild(i));
                }
            }
        }
    }

    private void analysingStatement(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node instanceof ASTStatementBlock) {
            //TODO: maybe verify if return expression matches the function's return type
            return;
        }

        if (node instanceof ASTIf) {
            //TODO
            return;
        }

        if (node instanceof ASTWhile) {
            //TODO
            return;
        }

        if (node instanceof ASTEquality) {
            analysingEquality(symbolClass, symbolMethod, (ASTEquality) node);
            return;
        }

        analysingExpression(symbolClass, symbolMethod, node);


    }

    private void analysingEquality(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTEquality node) {

        if (node.jjtGetNumChildren() != 2) {
            this.errorMessage("Equality can only have 2 arguments!");
            return;
        }

        Type type1 = analysingIdentifier(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0));
        Type type2 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(1));

        if (type1 == null || type2 == null)
            return;

        if (type1 != type2) {
            this.errorMessage("Equality between two different types!");
        }


    }

    private void analysingInitializeArray(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTInitializeArray node) {

        if (node.jjtGetNumChildren() != 1)
            return;

        if (analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0)) != Type.INT) {
            this.errorMessage("Array sizes must be integer");
        }

    }

    private Type analysingExpression(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node instanceof ASTAND) {
            return analysingOperation(symbolClass, symbolMethod, node);
        }

        if (node instanceof ASTLESSTHAN) {
            return analysingOperation(symbolClass, symbolMethod, node);
        }

        if (node instanceof ASTSUM) {
            return analysingOperation(symbolClass, symbolMethod, node);
        }

        if (node instanceof ASTSUB) {
            return analysingOperation(symbolClass, symbolMethod, node);
        }

        if (node instanceof ASTMUL) {
            return analysingOperation(symbolClass, symbolMethod, node);
        }

        if (node instanceof ASTDIV) {
            return analysingOperation(symbolClass, symbolMethod, node);
        }

        if (node instanceof ASTLiteral) {
            return Type.INT;
        }

        if (node instanceof ASTBoolean) {
            return Type.BOOLEAN;
        }

        if (node instanceof ASTDotExpression) {
            if (node.jjtGetNumChildren() == 2)
                return analysingDotExpression(symbolClass, symbolMethod, node);

            return null;
        }

        if (node instanceof ASTNegation) {

            if (node.jjtGetNumChildren() != 1)
                return null;

            return analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0));
        }

        if (node instanceof ASTIdentifier) {

            return analysingIdentifier(symbolClass, symbolMethod, (ASTIdentifier) node);
        }

        if (node instanceof ASTInitializeArray) {

            analysingInitializeArray(symbolClass, symbolMethod, (ASTInitializeArray) node);
            return Type.INT_ARRAY;
        }

        if (node instanceof ASTNewObject) {
            return Type.OBJECT;
        }

        return null;
    }

    private Type analysingDotExpression(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {
        if (node.jjtGetNumChildren() != 2)
            return null;

        if (node.jjtGetChild(0) instanceof ASTIdentifier && node.jjtGetChild(1) instanceof ASTIdentifier) {
            ASTIdentifier node1 = (ASTIdentifier) node.jjtGetChild(0);
            ASTIdentifier node2 = (ASTIdentifier) node.jjtGetChild(1);

            if (node1.val.equals("this")) {
                return analyseThisStatement(symbolClass, symbolMethod, node2);
            } else
                return null;
        } else
            return null;
    }

    private ArrayList<Type> getMethodCallTypes(SymbolMethod symbolMethod, SymbolClass symbolClass, ASTIdentifier node2) {
        ArrayList<Type> types = new ArrayList<>();
        for (int i = 0; i < node2.jjtGetNumChildren(); i++) {
            if (node2.jjtGetChild(i) instanceof ASTIdentifier) {
                ASTIdentifier identifier = (ASTIdentifier) node2.jjtGetChild(i);

                if (symbolMethod.symbolTable.containsKey(identifier.val)) {
                    types.add(symbolMethod.symbolTable.get(identifier.val).getType());
                } else if (symbolClass.symbolTable.containsKey(identifier.val)) {
                    types.add(symbolClass.symbolTable.get(identifier.val).get(0).getType());
                } else {
                    types.add(null);
                }
            } else if (node2.jjtGetChild(i) instanceof ASTLiteral) {
                types.add(Type.INT);
            } else if (node2.jjtGetChild(i) instanceof ASTBoolean) {
                types.add(Type.BOOLEAN);
            } else if (node2.jjtGetChild(i) instanceof ASTDotExpression) {
                types.add(analysingDotExpression(symbolClass, symbolMethod, (SimpleNode) node2.jjtGetChild(i)));
            }
        }

        return types;
    }

    private Type analyseThisStatement(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node2) {
        Type type = null;

        if (symbolClass.symbolTable.containsKey(node2.val)) {
            // Analysing this statement for variables
            if (!node2.method) {
                for (int j = 0; j < symbolClass.symbolTable.get(node2.val).size(); j++) {
                    if (symbolClass.symbolTable.get(node2.val).get(j) instanceof SymbolVar) {
                        SymbolVar sv = (SymbolVar) symbolClass.symbolTable.get(node2.val).get(j);
                        return sv.getType();
                    }

                    if (j == symbolClass.symbolTable.get(node2.val).size() - 1) {
                        this.errorMessage(node2.val + " is undefined!");
                        return null;
                    }
                }
            }
            // Analysing this statement for methods
            else {
                ArrayList<Type> curr_types = getMethodCallTypes(symbolMethod, symbolClass, node2);
                for (int j = 0; j < symbolClass.symbolTable.get(node2.val).size(); j++) {
                    if (symbolClass.symbolTable.get(node2.val).get(j) instanceof SymbolMethod) {
                        SymbolMethod sm = (SymbolMethod) symbolClass.symbolTable.get(node2.val).get(j);
                        if (node2.jjtGetNumChildren() == sm.types.size()) {
                            if (sm.types.equals(curr_types))
                                type = sm.returnType;
                        }
                    }
                }
            }
        } else {
            this.errorMessage(node2.val + " is undefined!");
            return null;
        }

        if (type == null)
            this.errorMessage(node2.val + " is being called with the wrong number or type of args");

        return type;
    }

    private Type analysingIdentifier(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node) {

        Type type;

        if (symbolClass.symbolTable.containsKey(node.val))
            type = symbolClass.symbolTable.get(node.val).get(0).getType();
        else if (symbolMethod.symbolTable.containsKey(node.val))
            type = symbolMethod.symbolTable.get(node.val).getType();
        else {
            this.errorMessage(node.val + " is undefined!");
            return null;
        }

        if (node.jjtGetNumChildren() == 1) {
            if (node.jjtGetChild(0) instanceof ASTaccessToArray) {

                if (analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0).jjtGetChild(0)) != Type.INT)
                    this.errorMessage("Access to array is not int!");

                if (type == Type.INT_ARRAY)
                    return Type.INT;
                else if (type == Type.STRING_ARRAY)
                    return Type.STRING;

                this.errorMessage(node.val + "is not an array");
                return null;
            }
        }

        return type;

    }

    private Type analysingOperation(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node.jjtGetNumChildren() != 2) {
            this.errorMessage("Operation can only have 2 arguments!");
            return null;
        }

        Type type1 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0));
        Type type2 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(1));

        if (type1 == null || type2 == null)
            return null;

        if (type1 != type2) {
            this.errorMessage("Operands are not of the same type");
            return null;
        }

        return type1;

    }

    private void analysingVarDeclaration(SymbolMethod symbolMethod, ASTVarDeclaration nodeVarDeclaration) {

        SymbolVar symbolVar = new SymbolVar(nodeVarDeclaration.name);
        symbolMethod.addSymbol(nodeVarDeclaration.name, symbolVar);

        if (nodeVarDeclaration.jjtGetNumChildren() != 1)
            return;

        if (nodeVarDeclaration.jjtGetChild(0) instanceof ASTType)
            analysingType(symbolVar, (ASTType) nodeVarDeclaration.jjtGetChild(0));

    }

    private void analysingVarDeclarationClass(SymbolClass symbolClass, ASTVarDeclaration nodeVarDeclaration) {

        SymbolVar symbolVar = (SymbolVar) symbolClass.getSymbol(nodeVarDeclaration.name).get(0);

        if (nodeVarDeclaration.jjtGetNumChildren() != 1)
            return;

        if (nodeVarDeclaration.jjtGetChild(0) instanceof ASTType)
            analysingType(symbolVar, (ASTType) nodeVarDeclaration.jjtGetChild(0));

    }

    private Type getType(ASTType nodeType) {
        if (nodeType.isArray) {
            return Type.INT_ARRAY;
        } else if (nodeType.type.equals("int")) {
            return Type.INT;
        } else if (nodeType.type.equals("boolean")) {
            return Type.BOOLEAN;
        } else if (nodeType.type.equals("String")) {
            return Type.STRING;
        } else if (nodeType.type.equals("void")) {
            return Type.VOID;
        } else {
            return Type.OBJECT;
        }
    }

    private Type analysingType(Symbol parentSymbol, ASTType nodeType) {
        if (nodeType.isArray) {
            parentSymbol.setType(Type.INT_ARRAY);
            return Type.INT_ARRAY;
        } else if (nodeType.type.equals("int")) {
            parentSymbol.setType(Type.INT);
            return Type.INT;
        } else if (nodeType.type.equals("boolean")) {
            parentSymbol.setType(Type.BOOLEAN);
            return Type.BOOLEAN;
        } else if (nodeType.type.equals("String")) {
            parentSymbol.setType(Type.STRING);
            return Type.STRING;
        } else if (nodeType.type.equals("void")) {
            parentSymbol.setType(Type.VOID);
            return Type.VOID;
        } else {
            parentSymbol.setType(Type.OBJECT);
            return Type.OBJECT;
        }

    }

    private void errorMessage(String message) {
        nErrors++;
        System.err.println(message);
    }

}
