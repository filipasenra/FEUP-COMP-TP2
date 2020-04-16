import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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
        this.printWriterFile.write(".super java/lang/Object\n");
        
        generateGlobalVariables((SimpleNode)node.jjtGetChild(1)); //como chamar? -> a funcionar so no findmaximum
        System.out.println("Jasmin code generated");
        this.printWriterFile.close();
    }

    private void generateGlobalVariables(SimpleNode node) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if (child instanceof ASTVarDeclaration) {
                generateVar((ASTVarDeclaration) child);
            }
        }
    }

    private void generateVar(ASTVarDeclaration var){
        String vType;
        String finalType = "";

        if(var.jjtGetChild(0) instanceof ASTType){
            ASTType nodeType = (ASTType) var.jjtGetChild(0);
            vType = nodeType.type;
        }
        else vType = "";

        switch (vType) {
        case "int":
            finalType = "I";
            break;
        case "int is an array":
            finalType = "[I";
            break;
        case "String":
            //TODO
            break;
        case "boolean":
            finalType = "B";
            break;
        case "void":
            finalType = "V";
        }

        printWriterFile.println(".field public " + var.name + " " + finalType);
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