import symbolTable.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemanticAnalysis {

    private HashMap<String, Symbol> symbolTable = new HashMap<>();
    private int nErrors = 0;
    private int nWarnings = 0;

    public SemanticAnalysis() { }

    public int getNerros() {
        return nErrors;
    }

    public int getNwarnings() { return nWarnings; }

    public void startAnalysing(SimpleNode node) {

        //Analysing Imports and Class
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {

            if (node.jjtGetChild(i) instanceof ASTImport)
                addImport((ASTImport) node.jjtGetChild(i));
            else if (node.jjtGetChild(i) instanceof ASTClassDeclaration) {

                //Analysis class and signature of methods only (not their core) -> because of overloading
                addClassInfo((ASTClassDeclaration) node.jjtGetChild(i));

                //Analysis core of methods
                startAnalysingClass((ASTClassDeclaration) node.jjtGetChild(i));

            }
        }
    }

    public HashMap<String, Symbol> getSymbolTable() {
        return this.symbolTable;
    }

    private void addImport(ASTImport importNode) {
        SymbolClass symbolClass;

        //Checks if the Class has been imported already
        if (!this.symbolTable.containsKey(importNode.className)) {
            symbolClass = new SymbolClass(importNode.className);
            this.symbolTable.put(importNode.className, symbolClass);
        } else
            symbolClass = (SymbolClass) this.symbolTable.get(importNode.className);

        //If it is an import of a class
        if(importNode.methodName == null)
            return;

        //If it is an import of a method
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
                sm.setType(analysingType(sm, astType));
            }
        }

        //Checks if this import is unique

        if (symbolClass.symbolTableMethods.get(importNode.methodName) != null) {

            //Checks for import methods of the same class
            for (int i = 0; i < symbolClass.symbolTableMethods.get(importNode.methodName).size(); i++) {
                SymbolMethod smCheck = symbolClass.symbolTableMethods.get(importNode.methodName).get(i);

                //If they have the same signature, we don't had
                if (sm.types.equals(smCheck.types)) {
                    return;
                }
            }
        }

        //If it is a unique import, add to class imported
        symbolClass.addSymbolMethod(importNode.methodName, sm);
    }

    //Analysis class and signature of methods only (not their core) -> because of overloading
    private void addClassInfo(ASTClassDeclaration classNode) {

        SymbolClass symbolClass = new SymbolClass(classNode.name);
        this.symbolTable.put(classNode.name, symbolClass);

        for (int i = 0; i < classNode.jjtGetNumChildren(); i++) {
            if (classNode.jjtGetChild(i) instanceof ASTVarDeclaration) {
                //Adds Vars of Class to SymbolClass
                addVarDeclarationInfo(symbolClass, (ASTVarDeclaration) classNode.jjtGetChild(i));

            } else if (classNode.jjtGetChild(i) instanceof ASTMethodDeclaration) {
                //Analysis method signature
                addMethod(symbolClass, (ASTMethodDeclaration) classNode.jjtGetChild(i), i);
            }
        }
    }

    //Adds Vars of Class to SymbolClass
    private void addVarDeclarationInfo(SymbolClass symbolClass, ASTVarDeclaration nodeVarDeclaration) {
        SymbolVar symbolVar = new SymbolVar(nodeVarDeclaration.name);

        if(symbolClass.symbolTableFields.containsKey(nodeVarDeclaration.name)){
            this.errorMessage(nodeVarDeclaration.name + " variable is already defined in the class!", nodeVarDeclaration.getLine());
            return;
        }

        symbolClass.addSymbolField(nodeVarDeclaration.name, symbolVar);
    }

    //Analysis method signature
    private void addMethod(SymbolClass symbolClass, ASTMethodDeclaration methodNode, int num) {
        SymbolMethod symbolMethod = new SymbolMethod(methodNode.name);
        symbolMethod.num = num;

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {

            //Adds arguments to method
            if (methodNode.jjtGetChild(i) instanceof ASTArg) {
                addMethodArg(symbolMethod, (ASTArg) methodNode.jjtGetChild(i));
                continue;
            }

            //Return type
            if (methodNode.jjtGetChild(i) instanceof ASTType) {
                symbolMethod.setType(analysingType(symbolMethod, (ASTType) methodNode.jjtGetChild(i)));
            }
        }

        //Checks if there isn't a method with the same name and signature
        if (symbolClass.symbolTableMethods.containsKey(methodNode.name)) {
            for (int i = 0; i < symbolClass.symbolTableMethods.get(methodNode.name).size(); i++) {

                SymbolMethod sm = symbolClass.symbolTableMethods.get(methodNode.name).get(i);
                if (symbolMethod.types.equals(sm.types)) {
                    this.errorMessage("Method " + methodNode.name + " already exists in class with the specified arguments.", methodNode.getLine());
                    return;
                }
            }
        }

        //Adds method to its class
        symbolClass.addSymbolMethod(methodNode.name, symbolMethod);
    }

    //Adds arguments to method
    private void addMethodArg(SymbolMethod symbolMethod, ASTArg nodeArg) {
        SymbolVar symbolVar = new SymbolVar(nodeArg.val);
        symbolVar.updateInitialized(true);
        symbolMethod.addSymbol(nodeArg.val, symbolVar);

        if (nodeArg.jjtGetNumChildren() != 1)
            return;

        if (nodeArg.jjtGetChild(0) instanceof ASTType) {
            symbolMethod.addType(analysingType(symbolVar, (ASTType) nodeArg.jjtGetChild(0)));
        }
    }

    //Analysis core of methods (signature was already analyzed
    private void startAnalysingClass(ASTClassDeclaration classNode) {

        SymbolClass symbolClass = (SymbolClass) this.symbolTable.get(classNode.name);
        symbolClass.superClass = classNode.ext;

        for (int i = 0; i < classNode.jjtGetNumChildren(); i++) {

            //Adds declared fields to class
            if (classNode.jjtGetChild(i) instanceof ASTVarDeclaration) {

                analysingVarDeclarationClass(symbolClass, (ASTVarDeclaration) classNode.jjtGetChild(i));

            //Analyzes core of generic methods
            } else if (classNode.jjtGetChild(i) instanceof ASTMethodDeclaration) {

                startAnalysingMethod(symbolClass, (ASTMethodDeclaration) classNode.jjtGetChild(i), i);

            //Analyzes core of main method
            } else if (classNode.jjtGetChild(i) instanceof ASTMainDeclaration)
                startAnalysingMainDeclaration(symbolClass, (ASTMainDeclaration) classNode.jjtGetChild(i));

        }

    }

    //Analyzes core of main method
    private void startAnalysingMainDeclaration(SymbolClass symbolClass, ASTMainDeclaration methodNode) {

        if (symbolClass.symbolTableMethods.containsKey("main")) {
            this.errorMessage("Main already exists in class", methodNode.getLine());
            return;
        }

        SymbolMethod symbolMethod = new SymbolMethod("main");

        //adding parameter to main
        SymbolVar symbolVar = new SymbolVar(methodNode.parametherName);
        symbolVar.setType(Type.STRING_ARRAY);
        symbolVar.updateInitialized(true);
        symbolMethod.addSymbol(symbolVar.name, symbolVar);

        symbolClass.addSymbolMethod("main", symbolMethod);

        for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {

            if (methodNode.jjtGetChild(i) instanceof ASTVarDeclaration) {

                //Adds the var declarations to the method
                analysingVarDeclaration(symbolMethod, (ASTVarDeclaration) methodNode.jjtGetChild(i));
                continue;
            }

            //Analysis statements
            analysingStatement(symbolClass, symbolMethod, (SimpleNode) methodNode.jjtGetChild(i));
        }
    }

    //Analyzes core of generic methods
    private void startAnalysingMethod(SymbolClass symbolClass, ASTMethodDeclaration methodNode, int num) {

        for (int j = 0; j < symbolClass.symbolTableMethods.get(methodNode.name).size(); j++) {

            SymbolMethod symbolMethod = symbolClass.symbolTableMethods.get(methodNode.name).get(j);

            //Getting the correct symbolMethod
            if (num == symbolMethod.num) {

                for (int i = 0; i < methodNode.jjtGetNumChildren(); i++) {

                    //Analysis declared return type
                    if (methodNode.jjtGetChild(i) instanceof ASTType) {
                        analysingType(symbolMethod, (ASTType) methodNode.jjtGetChild(i));
                        continue;
                    }

                    //Adds the var declarations to the method
                    if (methodNode.jjtGetChild(i) instanceof ASTVarDeclaration) {
                        analysingVarDeclaration(symbolMethod, (ASTVarDeclaration) methodNode.jjtGetChild(i));
                        continue;
                    }

                    //Analysis the return expression
                    if (methodNode.jjtGetChild(i) instanceof ASTReturn) {
                        analysingReturnExpression(symbolClass, symbolMethod, (SimpleNode) methodNode.jjtGetChild(i));
                        continue;
                    }

                    //Analysis statement
                    analysingStatement(symbolClass, symbolMethod, (SimpleNode) methodNode.jjtGetChild(i));
                }
            }

        }
    }

    //Analysis the return expression
    private void analysingReturnExpression(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node.jjtGetNumChildren() != 1)
            return;

        Type type = this.analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0));

        if(type == null)
            return;

        if (symbolMethod.getType() != type)
            this.errorMessage("Return expression doesn't coincide with return type of function " + symbolMethod.name, node.getLine());


    }

    //Analysis statement
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
            this.errorMessage("Conditional expression of " + type + " must be boolean", node.getLine());
        }

        for (int i = 1; i < node.jjtGetNumChildren(); i++) {
            this.analysingStatement(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(i));
        }

    }

    private void analysingEquality(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTEquality node) {

        if (node.jjtGetNumChildren() != 2) {
            this.errorMessage("Equality can only have 2 arguments!", node.getLine());
            return;
        }

        Type type1 = analysingIdentifier(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0));
        Type type2 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(1));

        setInitialized(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0), false);

        if (type1 == null || type2 == null)
            return;

        if (type1 != type2) {
            this.errorMessage("Assignment between two different types!", node.getLine());
        }

    }

    //Flags that the a variable has been initiated
    private void setInitialized(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node, boolean partially) {
        if (symbolClass.symbolTableFields.containsKey(node.val))
            symbolClass.symbolTableFields.get(node.val).updateInitialized(partially);
        else if (symbolMethod.symbolTable.containsKey(node.val))
            symbolMethod.symbolTable.get(node.val).updateInitialized(partially);
    }

    private void analysingInitializeArray(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTInitializeArray node) {

        if (node.jjtGetNumChildren() != 1)
            return;

        if (analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0)) != Type.INT) {
            this.errorMessage("Array sizes must be integer", ((SimpleNode) node.jjtGetChild(0)).getLine());
        }

    }

    private Type analysingExpression(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node instanceof ASTAND) {

            return analysingBooleanOperation(symbolClass, symbolMethod, node);
        }

        if (node instanceof ASTLESSTHAN) {
            if (analysingOperation(symbolClass, symbolMethod, node) == null)
                return null;

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

            analysingNegation(symbolClass, symbolMethod, (ASTNegation) node);

            return Type.BOOLEAN;
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

    private void analysingNegation(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTNegation node) {

        if (node.jjtGetNumChildren() != 1)
            return;

        if (analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0)) != Type.BOOLEAN){
            this.errorMessage("Negation can only be applied to a boolean", ((SimpleNode) node.jjtGetChild(0)).getLine());
        }
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

    //Analysis expression with dot but without 'this' in the left hand side
    private Type analyseComplexStatement(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {

            //If it is an import
        if (symbolTable.containsKey(node1.val)) {
            return analyseComplexStatementST(symbolClass, symbolMethod, node1, node2);

            //If it is an object declared in the current method
        } else if (symbolMethod.symbolTable.containsKey(node1.val)) {
            return analyseComplexStatementSM(symbolClass, symbolMethod, node1, node2);

            //If it is an object declared in the current class
        } else if (symbolClass.symbolTableFields.containsKey(node1.val)) {
            return analyseComplexStatementSC(symbolClass, symbolMethod, node1, node2);

            //If it none of the above, it is undefined!
        }else {
            this.errorMessage(node1.val + " is undefined!", node1.getLine());
            return null;
        }
    }

    //Return the type of a method with the it's signature and possible candidates
    private Type getMethodType(ArrayList<SymbolMethod> methodArrayList, ArrayList<Type> methodSignature) {

        for (SymbolMethod sm : methodArrayList) {

            //If it has the same signature
            if (methodSignature.size() == sm.types.size()) {
                if (sm.types.equals(methodSignature))
                    return sm.getType();
            }
        }

        return null;
    }

    //Analysis a complex statement present in Import
    private Type analyseComplexStatementST(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {

        if (symbolTable.get(node1.val) instanceof SymbolClass) {
            SymbolClass sc = (SymbolClass) symbolTable.get(node1.val);

            //Check for methods
            if(node2.method) {

                if (sc.symbolTableMethods.containsKey(node2.val)) {

                    Type type = getMethodType(sc.symbolTableMethods.get(node2.val), getMethodCallTypes(symbolMethod, symbolClass, node2));

                    if(type != null)
                        return type;
                }
            }
        }

        this.errorMessage(node2.val + " is undefined!", node2.getLine());
        return null;
    }

    //Analysis a complex statement from an object present in a method
    private Type analyseComplexStatementSM(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {

        //If it is a declared object
        if (symbolMethod.symbolTable.get(node1.val).getType().equals(Type.OBJECT)) {

            //Check if there is a object import with this name
            if (!symbolTable.containsKey(symbolMethod.symbolTable.get(node1.val).getObject_name())) {
                this.errorMessage("Cannot resolve symbol " + symbolMethod.symbolTable.get(node1.val).getObject_name(), node1.getLine());
                return null;
            }

            //Get the object imported
            SymbolClass sc = (SymbolClass) symbolTable.get(symbolMethod.symbolTable.get(node1.val).getObject_name());

            //Check for methods
            if (node2.method) {

                //if import has a method with the same name
                if (sc.symbolTableMethods.containsKey(node2.val)) {

                    //check for a method with the same signature
                    Type type = getMethodType(sc.symbolTableMethods.get(node2.val), getMethodCallTypes(symbolMethod, symbolClass, node2));

                    if(type != null)
                        return type;

                    this.errorMessage(node2.val + " is undefined!", node2.getLine());
                    return null;

                }

                //check if the object has a super class
                if (sc.superClass != null) {

                    SymbolClass ssc = (SymbolClass) symbolTable.get(sc.superClass);

                    //if class has a method with the same name
                    if(ssc.symbolTableMethods.containsKey(node2.val)) {

                        //check for a method with the same signature
                        Type type = getMethodType(ssc.symbolTableMethods.get(node2.val), getMethodCallTypes(symbolMethod, symbolClass, node2));

                        if(type != null)
                            return type;
                    }

                    this.errorMessage(node2.val + " is undefined!", node2.getLine());
                    return null;
                }


                this.errorMessage("Cannnot resolve method " + node2.val + " in the object " + symbolMethod.symbolTable.get(node1.val).getObject_name(), node2.getLine());
                return null;


            }

        } else if (symbolMethod.symbolTable.get(node1.val).getType().equals(Type.INT_ARRAY) || symbolMethod.symbolTable.get(node1.val).getType().equals(Type.INT_ARRAY)) {
            //if it is not an object check if it is an array, if it is, than check for length
            if (node2.val.equals("length"))
                return Type.INT;
        }

        this.errorMessage("Cannot resolve symbol " + node1.val, node1.getLine());
        return null;
    }

    //Analysis a complex statement from an object present in a class
    private Type analyseComplexStatementSC(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node1, ASTIdentifier node2) {


            //check if it is an object
            if (symbolClass.symbolTableFields.get(node1.val).getType().equals(Type.OBJECT)) {

                //get type of object
                String objectName = symbolClass.symbolTableFields.get(node1.val).getObject_name();

                //check if object was imported
                if (!symbolTable.containsKey(objectName)) {
                    this.errorMessage("Cannot resolve symbol " + symbolMethod.symbolTable.get(node1.val).getObject_name(), node2.getLine());
                    return null;
                }

                SymbolClass sc = (SymbolClass) symbolTable.get(symbolMethod.symbolTable.get(node1.val).getObject_name());

                //If it is a call for a method
                if (node2.method) {

                    if (sc.symbolTableMethods.containsKey(node2.val)) {
                        Type type = getMethodType(sc.symbolTableMethods.get(node2.val), getMethodCallTypes(symbolMethod, symbolClass, node2));

                        if (type != null)
                            return type;

                        this.errorMessage(node2.val + " is undefined!", node2.getLine());
                        return null;

                    }

                    if (sc.superClass != null) {

                        SymbolClass ssc = (SymbolClass) symbolTable.get(sc.superClass);

                        //if class has a method with the same name
                        if (ssc.symbolTableMethods.containsKey(node2.val)) {

                            //check for a method with the same signature
                            Type type = getMethodType(ssc.symbolTableMethods.get(node2.val), getMethodCallTypes(symbolMethod, symbolClass, node2));

                            if (type != null)
                                return type;
                        }

                        this.errorMessage(node2.val + " is undefined!", node2.getLine());
                        return null;

                    }

                    this.errorMessage("Cannnot resolve method " + node2.val + " in the object " + symbolMethod.symbolTable.get(node1.val).getObject_name(), node2.getLine());
                    return null;

                }
        } else if (symbolClass.symbolTableFields.get(node1.val).getType().equals(Type.INT_ARRAY)) {
            if (node2.val.equals("length"))
                return Type.INT;
        }

        this.errorMessage("Cannot resolve symbol " + node1.val + "." + node2.val, node1.getLine());
        return null;
    }

    //Analysis a this statement
    private Type analyseThisStatement(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node) {
        Type type = null;

        if (!symbolTable.containsKey(symbolClass.superClass) && !symbolClass.symbolTableMethods.containsKey(node.val) && !symbolClass.symbolTableFields.containsKey(node.val)) {

            this.errorMessage(node.val + " is undefined!", node.getLine());
            return null;
        }


        //if it is a call for variable/field
        if (!node.method) {
            if (symbolClass.symbolTableFields.containsKey(node.val)) {
                return symbolClass.symbolTableFields.get(node.val).getType();
            }
            this.errorMessage(node.val + " is undefined!", node.getLine());
            return null;
        }
        else {

            //Check if current class has any method with the same signature
            if (symbolClass.symbolTableMethods.containsKey(node.val)) {

                type = getMethodType(symbolClass.symbolTableMethods.get(node.val), getMethodCallTypes(symbolMethod, symbolClass, node));

            }
        }

        //check if method is defined in super class, if it is not defined in the current class
        if (symbolTable.containsKey(symbolClass.superClass) && type == null) {

            SymbolClass sc = (SymbolClass) symbolTable.get(symbolClass.superClass);

            Type type1 = getMethodType(sc.symbolTableMethods.get(node.val), getMethodCallTypes(symbolMethod, symbolClass, node));

            if (type1 != null)
                return type1;
        }

        if (type == null)
            this.errorMessage(node.val + " is being called with the wrong number or type of args", node.getLine());

        return type;
    }

    //Returns the method signature
    private ArrayList<Type> getMethodCallTypes(SymbolMethod symbolMethod, SymbolClass symbolClass, ASTIdentifier node2) {
        ArrayList<Type> types = new ArrayList<>();

        for (int i = 0; i < node2.jjtGetNumChildren(); i++) {

            types.add(this.analysingExpression(symbolClass, symbolMethod, (SimpleNode) node2.jjtGetChild(i)));
        }

        return types;
    }

    //Analysis Identifier -> node cannot be the name of a class! be careful
    private Type analysingIdentifier(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node) {

        Type type;

        if (symbolMethod.symbolTable.containsKey(node.val)) {
            type = symbolMethod.symbolTable.get(node.val).getType();

        } else if (symbolClass.symbolTableFields.containsKey(node.val)) {
            type = symbolClass.symbolTableFields.get(node.val).getType();

        } else {
            this.errorMessage(node.val + " is undefined!", node.getLine());
            return null;

        }

        if (node.jjtGetNumChildren() == 1) {
            if (node.jjtGetChild(0) instanceof ASTaccessToArray) {

                if (analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0).jjtGetChild(0)) != Type.INT)
                    this.errorMessage("Array indices must be integer!", node.getLine());

                if (type == Type.INT_ARRAY)
                    return Type.INT;
                else if (type == Type.STRING_ARRAY)
                    return Type.STRING;

                this.errorMessage(node.val + " is not an array!", node.getLine());
                return null;
            }
        }

        return type;

    }

    //Analyses a Boolean Operation
    private Type analysingBooleanOperation(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node.jjtGetNumChildren() != 2) {
            this.errorMessage("Operation can only have 2 arguments!", node.getLine());
            return null;
        }

        Type type1 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0));
        Type type2 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(1));

        if (node.jjtGetChild(0) instanceof ASTIdentifier)
            checkIfInitialized(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0));

        if (node.jjtGetChild(1) instanceof ASTIdentifier)
            checkIfInitialized(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(1));

        if (type1 == null || type2 == null)
            return null;

        if (type1 != Type.BOOLEAN || type2 != Type.BOOLEAN) {
            this.errorMessage("Operation && only accepts booleans!", node.getLine());
            return null;
        }

        return type1;
    }

    //Analyses a Arithmetic Operation
    private Type analysingOperation(SymbolClass symbolClass, SymbolMethod symbolMethod, SimpleNode node) {

        if (node.jjtGetNumChildren() != 2) {
            this.errorMessage("Operation can only have 2 arguments!", node.getLine());
            return null;
        }

        Type type1 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(0));
        Type type2 = analysingExpression(symbolClass, symbolMethod, (SimpleNode) node.jjtGetChild(1));

        if (node.jjtGetChild(0) instanceof ASTIdentifier)
            checkIfInitialized(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(0));

        if (node.jjtGetChild(1) instanceof ASTIdentifier)
            checkIfInitialized(symbolClass, symbolMethod, (ASTIdentifier) node.jjtGetChild(1));

        if (type1 == null || type2 == null)
            return null;

        if(type1 == Type.INT_ARRAY || type1 == Type.STRING_ARRAY || type2 == Type.INT_ARRAY || type2 == Type.STRING_ARRAY) {
            this.errorMessage("It is not possible to use arrays directly in arithmetic operations!", node.getLine());
            return null;
        }

        if (type1 == Type.BOOLEAN || type2 == Type.BOOLEAN) {
            this.errorMessage("Arithmetic Operation doesn't accept booleans!", node.getLine());
            return null;
        }

        if (type1 != type2) {
            this.errorMessage("Operands are not of the same type", node.getLine());
            return null;
        }

        return type1;

    }

    //Checks if a variable has been initialized
    private void checkIfInitialized(SymbolClass symbolClass, SymbolMethod symbolMethod, ASTIdentifier node) {

        if (symbolClass.symbolTableFields.containsKey(node.val)) {

            SymbolVar symbolVar = symbolClass.symbolTableFields.get(node.val);

            if (symbolVar.getType() == Type.INT_ARRAY)
                return;

            if (symbolVar.getInitialized() == Initialized.NOT_INITIALIZED)
                this.warningMessage(node.val + " is not initialized!", node.getLine());
            else if (symbolVar.getInitialized() == Initialized.PARTIALLY_INITIALIZED)
                this.warningMessage(node.val + " may have not been initialized!", node.getLine());

        } else if (symbolMethod.symbolTable.containsKey(node.val)) {

            SymbolVar symbolVar = symbolMethod.symbolTable.get(node.val);

            if (symbolVar.getType() == Type.INT_ARRAY)
                return;

            if (symbolVar.getInitialized() == Initialized.NOT_INITIALIZED)
                this.warningMessage(node.val + " is not initialized!", node.getLine());
            else if (symbolVar.getInitialized() == Initialized.PARTIALLY_INITIALIZED)
                this.warningMessage(node.val + " may have not been initialized!", node.getLine());
        }
    }

    //Adds the var declarations to the method
    private void analysingVarDeclaration(SymbolMethod symbolMethod, ASTVarDeclaration nodeVarDeclaration) {

        if(symbolMethod.symbolTable.containsKey(nodeVarDeclaration.name)){
            this.errorMessage(nodeVarDeclaration.name + " variable is already defined in the method!", nodeVarDeclaration.getLine());
            return;
        }

        SymbolVar symbolVar = new SymbolVar(nodeVarDeclaration.name);
        symbolMethod.addSymbol(nodeVarDeclaration.name, symbolVar);

        if (nodeVarDeclaration.jjtGetNumChildren() != 1)
            return;

        if (nodeVarDeclaration.jjtGetChild(0) instanceof ASTType) {
            Type type = analysingType(symbolVar, (ASTType) nodeVarDeclaration.jjtGetChild(0));

            if (type == Type.OBJECT)
                if (!symbolTable.containsKey(symbolVar.getObject_name())) {
                    this.errorMessage("Class " + symbolVar.getObject_name() + " doesn't exist! ", nodeVarDeclaration.getLine());
                }

        }

    }

    //Adds declared fields to class
    private void analysingVarDeclarationClass(SymbolClass symbolClass, ASTVarDeclaration nodeVarDeclaration) {

        SymbolVar symbolVar = symbolClass.symbolTableFields.get(nodeVarDeclaration.name);

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

    //Analysis declared Type
    private Type analysingType(Symbol parentSymbol, ASTType nodeType) {

        Type type = getType(nodeType);
        parentSymbol.setType(type);

        if(type == Type.OBJECT)
            parentSymbol.setObject_name(nodeType.type);

        return type;

    }

    //Handles an Error
    private void errorMessage(String message, int line) {
        nErrors++;
        System.err.println("Near line " + line + ": " + message);
    }

    //Handles an Warning
    private void warningMessage(String message, int line) {
        nWarnings++;
        System.err.println("Near line " + line + ": " + message);
    }

    //Dumps the symbol table
    public void dump(){

        for(Map.Entry<String, Symbol> entry : this.symbolTable.entrySet()) {

            if(entry.getValue() instanceof SymbolVar)
                ((SymbolVar) entry.getValue()).dump("");
            else if(entry.getValue() instanceof SymbolMethod)
                ((SymbolMethod) entry.getValue()).dump("");
            else if(entry.getValue() instanceof SymbolClass)
                ((SymbolClass) entry.getValue()).dump("");

        }
    }

}
