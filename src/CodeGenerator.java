import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import symbolTable.Symbol;
import symbolTable.SymbolClass;


public class CodeGenerator {

    private HashMap<String, Symbol> symbolTable = new HashMap<>();

    public CodeGenerator(){}

	public void generate(SimpleNode node) {

        System.out.println("Starting creating jasmin code");

        for(int i = 0; i < node.jjtGetNumChildren(); i++){
            if(node.jjtGetChild(i) instanceof ASTClassDeclaration){
                startWritingJasmin((ASTClassDeclaration) node.jjtGetChild(i));
            }
        }
    }
    

    private void startWritingJasmin(ASTClassDeclaration classNode) {

        PrintWriter file = getFile(classNode.name);
        file.println(".class public " + classNode.name );
        file.println(".super java/lang/Object\n");
        
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
