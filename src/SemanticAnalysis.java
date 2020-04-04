import symbolTable.Symbol;
import symbolTable.SymbolClass;
import symbolTable.SymbolMethod;
import symbolTable.SymbolVar;

import java.beans.MethodDescriptor;
import java.util.HashMap;

public class SemanticAnalysis {

    public HashMap<String, Symbol> symbolTable = new HashMap<>();
    int nErrors = 0;

    public SemanticAnalysis() {}

    public void startAnalysing(SimpleNode node) {

        for(int i = 0; i < node.children.length; i++)
        {
            if(node.jjtGetChild(i) instanceof ASTClassDeclaration)
                startAnalysingClass((ASTClassDeclaration) node.jjtGetChild(i));
        }

    }

    private void startAnalysingClass(ASTClassDeclaration classNode) {

        this.symbolTable.put(classNode.name, new SymbolClass(classNode.name));

        for(int i = 0; i < classNode.children.length; i++)
        {
            if(classNode.jjtGetChild(i) instanceof ASTMethodDeclaration)
                startAnalysingMethod((SymbolClass) this.symbolTable.get(classNode.name), (ASTMethodDeclaration) classNode.jjtGetChild(i));

        }

    }

    private void startAnalysingMethod(SymbolClass symbolClass, ASTMethodDeclaration methodNode) {

        if(symbolClass.symbolTable.containsKey(methodNode.name)) {
            nErrors++;
            System.out.println("Method already exists in class");
            return;
        }

        SymbolMethod symbolMethod = new SymbolMethod(methodNode.name);

        symbolClass.addSymbol(methodNode.name, symbolMethod);

        for(int i = 0; i < methodNode.children.length; i++)
        {
            if(methodNode.jjtGetChild(i) instanceof ASTType) {

                analysingType(symbolMethod, (ASTType) methodNode.jjtGetChild(i));
                continue;
            }

            if(methodNode.jjtGetChild(i) instanceof ASTArg) {

                analysingArg(symbolMethod, (ASTArg) methodNode.jjtGetChild(i));
                continue;
            }

            if(methodNode.jjtGetChild(i) instanceof ASTVarDeclaration) {

                analysingVarDeclaration(symbolMethod, (ASTVarDeclaration) methodNode.jjtGetChild(i));
                continue;
            }

            if(methodNode.jjtGetChild(i) instanceof ASTReturn){

            }

        }

    }

    private void analysingVarDeclaration(SymbolMethod symbolMethod, ASTVarDeclaration nodeVarDeclaration) {

        SymbolVar symbolVar = new SymbolVar(nodeVarDeclaration.name);
        symbolMethod.addSymbol(nodeVarDeclaration.name, symbolVar);

        if(nodeVarDeclaration.children.length != 1)
            return;

        if(nodeVarDeclaration.jjtGetChild(0) instanceof ASTType)
            analysingType(symbolVar, (ASTType) nodeVarDeclaration.jjtGetChild(0));

    }

    private void analysingArg(SymbolMethod symbolMethod, ASTArg nodeArg) {

        SymbolVar symbolVar = new SymbolVar(nodeArg.val);
        symbolMethod.addSymbol(nodeArg.val, symbolVar);

        if(nodeArg.children.length != 1)
            return;

        if(nodeArg.jjtGetChild(0) instanceof ASTType)
            analysingType(symbolVar, (ASTType) nodeArg.jjtGetChild(0));

    }

    private void analysingType(Symbol parentSymbol, ASTType nodeType) {
        if (nodeType.isArray) {

            parentSymbol.setType(Symbol.Type.INT_ARRAY);
        }
        else if(nodeType.type.equals("int")) {

            parentSymbol.setType(Symbol.Type.INT);
        }
        else if(nodeType.type.equals("boolean")) {

            parentSymbol.setType(Symbol.Type.BOOLEAN);
        }
        else if(nodeType.type.equals("String")) {

            parentSymbol.setType(Symbol.Type.STRING);
        }
        else if(nodeType.type.equals("void")) {

            parentSymbol.setType(Symbol.Type.VOID);
        }
        else
            parentSymbol.setType(Symbol.Type.OBJECT);
    }

}
