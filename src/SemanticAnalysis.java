import symbolTable.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SemanticAnalysis {

    private HashMap<String, Symbol> symbolTable = new HashMap<>();
    private int nErrors = 0;
    private int nWarnings = 0;

    public SemanticAnalysis() {
    }

    public int getNerros() {
        return nErrors;
    }

    public int getNwarnings() { return nWarnings; }

    public void startAnalysing(SimpleNode node) {

        System.out.println("Starting Semantic Analysis");
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i) instanceof ASTImport)
                addImport((ASTImport) node.jjtGetChild(i));
            else if (node.jjtGetChild(i) instanceof ASTClassDeclaration) {
                addClassInfo((ASTClassDeclaration) node.jjtGetChild(i));
                startAnalysingClass((ASTClassDeclaration) node.jjtGetChild(i));
            }
        }
    }

    public HashMap<String, Symbol> getSymbolTable() {
        return this.symbolTable;
    }

    private void addImport(ASTImport importNode) {
        SymbolClass symbolClass;

        if (!this.symbolTable.containsKey(importNode.className)) {
            symbolClass = new SymbolClass(importNode.className);
            this.symbolTable.put(importNode.className, symbolClass);
        } else
            symbolClass = (SymbolClass) this.symbolTable.get(importNode.className);

        SymbolMethod sm = new SymbolMethod(importNode.methodName);

        for (int i = 0; i < importNode.jjtGetNumChildren(); i++) {
            if (importNode.jjtGetChild(i) instanceof ASTParamList) {
                ASTParamList astParamList = (ASTParamList) importNode.jjtGetChild(i);

                if (astParamList.children != null) {
                    for (int j = 0; j < astParamList.children.length; j++) {
                        if (astParamList.children[j] instanceof ASTType) {
                            ASTType astType = (ASTType) astParamList.children[j];
                            sm.addType(getType(astType));
                        }
                    }
                }
            } else if (importNode.jjtGetChild(i) instanceof ASTType) {
                ASTType astType = (ASTType) importNode.jjtGetChild(i);
                sm.returnType = analysingType(sm, astType);
            }
        }

        if (symbolClass.symbolTable.get(importNode.methodName) != null) {
            for (int i = 0; i < symbolClass.symbolTable.get(importNode.methodName).size(); i++) {
                SymbolMethod smCheck = (SymbolMethod) symbolClass.symbolTable.get(importNode.methodName).get(i);

                if (sm.types.equals(smCheck.types)) {
                    return;
                }
            }
        }

        symbolClass.addSymbol(importNode.methodName, sm);
    }

    private void addClassInfo(ASTClassDeclaration classNode) {
        SymbolClass symbolClass = new SymbolClass(classNode.name);
        this.symbolTable.put(classNode.name, symbolClass);

        for (int i = 0; i < classNode.jjtGetNumChildren(); i++) {
            if (classNode.jjtGetChild(i) instanceof ASTVarDeclaration) {
                addVarDeclarationInfo(symbolClass, (ASTVarDeclaration) classNode.jjtGetChild(i));
            } else if (classNode.jjtGetChild(i) instanceof ASTMethodDeclaration) {
                addMethod(symbolClass, (ASTMethodDeclaration) classNode.jjtGetChild(i), i);
            }
        }
    }

    private void addVarDeclarationInfo(SymbolClass symbolClass, ASTVarDeclaration nodeVarDeclaration) {
        SymbolVar symbolVar = new SymbolVar(nodeVarDeclaration.name);
        symbolClass.addSymbol(nodeVarDeclaration.name, symbolVar);
    }

    private void addMethod(SymbolClass symbolClass, ASTMethodDeclaration methodNode, int num) {
        SymbolMethod symbolMethod = new SymbolMethod(methodNode.name);
        symbolMethod.num = num;

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
        symbolVar.setInitialize();
        symbolMethod.addSymbol(nodeArg.val, symbolVar);

        if (nodeArg.jjtGetNumChildren() != 1)
            return;

        if (nodeArg.jjtGetChild(0) instanceof ASTType) {
            symbolMethod.addType(analysingType(symbolVar, (ASTType) nodeArg.jjtGetChild(0)));
        }
    }


    private void startAnalysingClass(ASTClassDeclaration classNode) {

        SymbolClass symbolClass = (SymbolClass) this.symbolTable.get(classNode.name);
        symbolClass.superClass = classNode.ext;

        for (int i = 0; i < classNode.jjtGetNumChildren(); i++) {

            if (classNode.jjtGetChild(i) instanceof ASTVarDeclaration) {

                analysingVarDeclarationClass(symbolClass, (ASTVarDeclaration) classNode.jjtGetChild(i));

            } else if (classNode.jjtGetChild(i) instanceof ASTMethodDeclaration) {

                startAnalysingMethod(symbolClass, (ASTMethodDeclaration) classNode.jjtGetChild(i), i);

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

    private void startAnalysingMethod(SymbolClass symbolClass, ASTMethodDeclaration methodNode, int num) {

        for (int j = 0; j < symbolClass.symbolTable.get(methodNode.name).size(); j++) {

            if (symbolClass.symbolTable.get(methodNode.name).get(j) instanceof SymbolMethod) {
                SymbolMethod symbolMethod = (SymbolMethod) symbolClass.symbolTable.get(methodNode.name).get(j);

                if (num == symbolMethod.num) {

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
                            analysingReturnExpression(symbolClass, symbolMethod, (SimpleNode) methodNode.jjtGetChild(i));
                            continue;
                        }

                        analysingStatement(symbolClass, symbolMethod, (SimpleNode) methodNode.jjtGetChild(i));
                    }
                }

            }
        }
    }

    private void analysingReturnExpression(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node.jjtGetNumChildren() != 1)
            return;

        if (symbolMethod.returnType != this.analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0)))
            this.errorMessage("Return expression doesn't coincide with return type of function " + symbolMethod.name);


    }

    private void analysingStatement(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node instanceof ASTStatementBlock) {

            for (int i = 0; i < node.jjtGetNumChildren(); i++)
                analysingStatement(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(i));
        }

        if (node instanceof ASTIf) {
            analysingConditional(symbolClass, symbolMethod, node, "if");
            return;
        }

        if (node instanceof ASTWhile) {
            analysingConditional(symbolClass, symbolMethod, node, "while");
            return;
        }

        if (node instanceof ASTEquality) {
            analysingEquality(symbolClass, symbolMethod, (ASTEquality) node);
            return;
        }

        analysingExpression(symbolClass, symbolMethod, node);


    }

    private void analysingConditional(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node, String type) {

        if (node.jjtGetNumChildren() < 2)
            return;

        if (analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0)) != Type.BOOLEAN) {
            this.errorMessage("Conditional expression of " + type + " must be boolean");
        }

        for (int i = 1; i < node.jjtGetNumChildren(); i++) {
            this.analysingStatement(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(i));
        }

    }

    private void analysingEquality(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTEquality node) {

        if (node.jjtGetNumChildren() != 2) {
            this.errorMessage("Equality can only have 2 arguments!");
            return;
        }

        Type type1 = analysingIdentifier(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0));
        Type type2 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(1));

        setInitialize(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0));

        if (type1 == null || type2 == null)
            return;

        if (type1 != type2) {
            this.errorMessage("Equality between two different types!");
        }

    }

    private void setInitialize(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node) {

        if (symbolClass.symbolTable.containsKey(node.val)) {

            ArrayList<Symbol> symbolArrayList = symbolClass.symbolTable.get(node.val);

            for (Symbol symbol : symbolArrayList) {

                if (symbol instanceof SymbolVar) {
                    ((SymbolVar) symbol).setInitialize();
                    return;
                }
            }
        }

        if (symbolMethod.symbolTable.containsKey(node.val)) {

            if (symbolMethod.symbolTable.get(node.val) instanceof SymbolVar)
                ((SymbolVar) symbolMethod.symbolTable.get(node.val)).setInitialize();
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
            analysingOperation(symbolClass, symbolMethod, node);
            return Type.BOOLEAN;
        }

        if (node instanceof ASTLESSTHAN) {
            analysingOperation(symbolClass, symbolMethod, node);
            return Type.BOOLEAN;
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
            } else {
                return analyseComplexStatement(symbolClass, symbolMethod, node1, node2);
            }
        } else
            return null;
    }

    private Type analyseComplexStatement(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {
        if (symbolTable.containsKey(node1.val))
            return analyseComplexStatementST(symbolClass, symbolMethod, node1, node2);
        else if (symbolMethod.symbolTable.containsKey(node1.val))
            return analyseComplexStatementSM(symbolClass, symbolMethod, node1, node2);
        else if (symbolClass.symbolTable.containsKey(node1.val))
            return analyseComplexStatementSC(symbolClass, symbolMethod, node1, node2);
        else {
            this.errorMessage(node1.val + " is undefined!");
            return null;
        }
    }

    private Type analyseComplexStatementST(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {
        if (symbolTable.get(node1.val) instanceof SymbolClass) {
            SymbolClass sc = (SymbolClass) symbolTable.get(node1.val);

            for (int i = 0; i < sc.symbolTable.get(node2.val).size(); i++) {
                SymbolMethod sm = (SymbolMethod) sc.symbolTable.get(node2.val).get(i);
                ArrayList<Type> curr_types = getMethodCallTypes(symbolMethod, symbolClass, node2);

                if (node2.jjtGetNumChildren() == sm.types.size()) {
                    if (sm.types.equals(curr_types))
                        return sm.returnType;
                }
            }
        }

        this.errorMessage(node2.val + " is undefined!");
        return null;
    }

    private Type analyseComplexStatementSM(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {
        if (symbolMethod.symbolTable.get(node1.val).getType().equals(Type.OBJECT)) {
            if (symbolTable.containsKey(symbolMethod.symbolTable.get(node1.val).getObject_name())) {
                SymbolClass sc = (SymbolClass) symbolTable.get(symbolMethod.symbolTable.get(node1.val).getObject_name());

                if (sc.symbolTable.containsKey(node2.val)) {
                    for (int i = 0; i < sc.symbolTable.get(node2.val).size(); i++) {
                        SymbolMethod sm = (SymbolMethod) sc.symbolTable.get(node2.val).get(i);
                        ArrayList<Type> curr_types = getMethodCallTypes(symbolMethod, symbolClass, node2);

                        if (node2.jjtGetNumChildren() == sm.types.size()) {
                            if (sm.types.equals(curr_types))
                                return sm.returnType;
                        }
                    }

                    this.errorMessage(node2.val + " is undefined!");
                    return null;
                } else if (sc.superClass != null) {
                    SymbolClass ssc = (SymbolClass) symbolTable.get(sc.superClass);
                    for (int i = 0; i < ssc.symbolTable.get(node2.val).size(); i++) {
                        SymbolMethod sm = (SymbolMethod) ssc.symbolTable.get(node2.val).get(i);
                        ArrayList<Type> curr_types = getMethodCallTypes(symbolMethod, symbolClass, node2);

                        if (node2.jjtGetNumChildren() == sm.types.size()) {
                            if (sm.types.equals(curr_types))
                                return sm.returnType;
                        }
                    }

                    this.errorMessage(node2.val + " is undefined!");
                    return null;
                } else {
                    this.errorMessage("Cannnot resolve method " + node2.val + " in the object " + symbolMethod.symbolTable.get(node1.val).getObject_name());
                    return null;
                }
            } else {
                this.errorMessage("Cannnot resolve symbol " + symbolMethod.symbolTable.get(node1.val).getObject_name());
                return null;
            }
        } else if (symbolMethod.symbolTable.get(node1.val).getType().equals(Type.INT_ARRAY)) {
            if (node2.val.equals("length"))
                return Type.INT;
        }

        this.errorMessage("Cannnot resolve symbol " + node1.val);
        return null;
    }

    private Type analyseComplexStatementSC(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {
        for (int j = 0; j < symbolClass.symbolTable.get(node1.val).size(); j++) {
            if (symbolClass.symbolTable.get(node1.val).get(j).getType().equals(Type.OBJECT)) {
                if (symbolTable.containsKey(symbolClass.symbolTable.get(node1.val).get(j).getObject_name())) {
                    SymbolClass sc = (SymbolClass) symbolTable.get(symbolMethod.symbolTable.get(node1.val).getObject_name());

                    if (sc.symbolTable.containsKey(node2.val)) {
                        for (int i = 0; i < sc.symbolTable.get(node2.val).size(); i++) {
                            SymbolMethod sm = (SymbolMethod) sc.symbolTable.get(node2.val).get(i);
                            ArrayList<Type> curr_types = getMethodCallTypes(symbolMethod, symbolClass, node2);

                            if (node2.jjtGetNumChildren() == sm.types.size()) {
                                if (sm.types.equals(curr_types))
                                    return sm.returnType;
                            }
                        }

                        this.errorMessage(node2.val + " is undefined!");
                        return null;
                    } else if (sc.superClass != null) {
                        SymbolClass ssc = (SymbolClass) symbolTable.get(sc.superClass);
                        for (int i = 0; i < ssc.symbolTable.get(node2.val).size(); i++) {
                            SymbolMethod sm = (SymbolMethod) ssc.symbolTable.get(node2.val).get(i);
                            ArrayList<Type> curr_types = getMethodCallTypes(symbolMethod, symbolClass, node2);

                            if (node2.jjtGetNumChildren() == sm.types.size()) {
                                if (sm.types.equals(curr_types))
                                    return sm.returnType;
                            }
                        }

                        this.errorMessage(node2.val + " is undefined!");
                        return null;
                    } else {
                        this.errorMessage("Cannnot resolve method " + node2.val + " in the object " + symbolMethod.symbolTable.get(node1.val).getObject_name());
                        return null;
                    }
                } else {
                    this.errorMessage("Cannnot resolve symbol " + symbolMethod.symbolTable.get(node1.val).getObject_name());
                    return null;
                }
            } else if (symbolClass.symbolTable.get(node1.val).get(j).getType().equals(Type.INT_ARRAY)) {
                if (node2.val.equals("length"))
                    return Type.INT;
            }
        }

        this.errorMessage("Cannnot resolve symbol " + node1.val);
        return null;
    }

    private Type analyseThisStatement(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node2) {
        Type type = null;

        if (symbolTable.containsKey(symbolClass.superClass) || symbolClass.symbolTable.containsKey(node2.val)) {
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
            }
            if (symbolTable.containsKey(symbolClass.superClass) && type == null) {
                SymbolClass sc = (SymbolClass) symbolTable.get(symbolClass.superClass);

                ArrayList<Type> curr_types = getMethodCallTypes(symbolMethod, symbolClass, node2);
                for (int j = 0; j < sc.symbolTable.get(node2.val).size(); j++) {
                    if (sc.symbolTable.get(node2.val).get(j) instanceof SymbolMethod) {
                        SymbolMethod sm = (SymbolMethod) sc.symbolTable.get(node2.val).get(j);
                        if (node2.jjtGetNumChildren() == sm.types.size()) {
                            if (sm.types.equals(curr_types)) {
                                type = sm.returnType;
                            }
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

    private ArrayList<Type> getMethodCallTypes(SymbolMethod symbolMethod, SymbolClass symbolClass, ASTIdentifier node2) {
        ArrayList<Type> types = new ArrayList<>();

        for (int i = 0; i < node2.jjtGetNumChildren(); i++) {

            types.add(this.analysingExpression(symbolClass, symbolMethod, (SimpleNode) node2.jjtGetChild(i)));
        }

        return types;
    }

    //Functions cannot come through here! it will go wrong!
    private Type analysingIdentifier(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node) {

        Type type = null;
        boolean found = false;

        if (symbolMethod.symbolTable.containsKey(node.val)) {

            type = symbolMethod.symbolTable.get(node.val).getType();
            found = true;

        } else if (symbolClass.symbolTable.containsKey(node.val)) {
            ArrayList<Symbol> symbolArrayList = symbolClass.symbolTable.get(node.val);

            for (Symbol symbol : symbolArrayList) {

                //It means it is a variable
                if (symbol instanceof SymbolVar) {
                    type = symbolClass.symbolTable.get(node.val).get(0).getType();
                    found = true;
                }
            }
        }

        if (!found) {
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

        if (node.jjtGetChild(0) instanceof ASTIdentifier)
            checkIfInitialize(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0));

        if (node.jjtGetChild(1) instanceof ASTIdentifier)
            checkIfInitialize(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(1));

        if (type1 == null || type2 == null)
            return null;

        if(type1 == Type.INT_ARRAY || type1 == Type.STRING_ARRAY || type2 == Type.INT_ARRAY || type2 == Type.STRING_ARRAY) {
            this.errorMessage("It is not possible to use arrays directly for arithmetic operations!");
            return null;
        }

        if (type1 != type2) {
            this.errorMessage("Operands are not of the same type");
            return null;
        }

        return type1;

    }

    private void checkIfInitialize(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node) {

        if (symbolClass.symbolTable.containsKey(node.val)) {

            ArrayList<Symbol> symbolArrayList = symbolClass.symbolTable.get(node.val);

            for (Symbol symbol : symbolArrayList) {

                if (symbol instanceof SymbolVar) {
                    SymbolVar symbolVar = (SymbolVar) symbol;

                    if (symbolVar.getType() == Type.INT_ARRAY)
                        return;

                    if (!symbolVar.isInitialize())
                        this.warningMessage(node.val + " is not initialize!");

                    return;
                }
            }
        }

        if (symbolMethod.symbolTable.containsKey(node.val)) {

            if (!(symbolMethod.symbolTable.get(node.val) instanceof SymbolVar))
                return;

            SymbolVar symbolVar = (SymbolVar) symbolMethod.symbolTable.get(node.val);

            if (symbolVar.getType() == Type.INT_ARRAY)
                return;

            if (!symbolVar.isInitialize())
                this.warningMessage(node.val + " is not initialize!");
        }
    }


    private void analysingVarDeclaration(SymbolMethod symbolMethod, ASTVarDeclaration nodeVarDeclaration) {
        SymbolVar symbolVar = new SymbolVar(nodeVarDeclaration.name);
        symbolMethod.addSymbol(nodeVarDeclaration.name, symbolVar);

        if (nodeVarDeclaration.jjtGetNumChildren() != 1)
            return;

        if (nodeVarDeclaration.jjtGetChild(0) instanceof ASTType) {
            Type type = analysingType(symbolVar, (ASTType) nodeVarDeclaration.jjtGetChild(0));

            if (type == Type.OBJECT)
                if (!symbolTable.containsKey(symbolVar.getObject_name())) {
                    this.errorMessage("Missing type");
                }

        }

    }

    private void analysingVarDeclarationClass(SymbolClass symbolClass, ASTVarDeclaration nodeVarDeclaration) {

        SymbolVar symbolVar = (SymbolVar) symbolClass.symbolTable.get(nodeVarDeclaration.name).get(0);

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
            parentSymbol.setObject_name(nodeType.type);
            parentSymbol.setType(Type.OBJECT);
            return Type.OBJECT;
        }

    }

    private void errorMessage(String message) {
        nErrors++;
        System.err.println(message);
    }

    private void warningMessage(String message) {
        nWarnings++;
        System.err.println(message);
    }

}
