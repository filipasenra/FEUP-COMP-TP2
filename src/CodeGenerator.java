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
        System.out.println("N children: " + node.jjtGetNumChildren());
        for(int i = 0; i < node.jjtGetNumChildren(); i++){
            if(node.jjtGetChild(i) instanceof ASTClassDeclaration){
                System.out.println("SIM");
                startWritingJasmin((ASTClassDeclaration) node.jjtGetChild(i));
            }
        }
    }
    

    private void startWritingJasmin(ASTClassDeclaration classNode) {
       // System.out.println("class name: " + classNode.name);
        PrintWriter file = getFile(classNode.name);
        // System.out.println(file.getClass());
      //  PrintWriter file = new PrintWriter(System.out);
        file.flush();
        file.println(".class public " + classNode.name);
        file.write(".super java/lang/Object\n");
        //PAUSING..
        file.close();
        
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
