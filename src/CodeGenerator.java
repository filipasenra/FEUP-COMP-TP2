import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import symbolTable.SymbolClass;

/*
public class CodeGenerator {


    public CodeGenerator(){}

	public void generate(SimpleNode node) {

        PrintWriter file = getFile();
        if (node!= null && node instanceof ASTMethodDeclaration){ //[COMPILERS ASSIGNMENT] ->6. Generate JVM code accepted by jasmin corresponding to the invocation of functions in Java--;
            for (int i = 0; i < node.jjtGetNumChildren(); i++) {

                if (node.jjtGetChild(i) instanceof ASTMethodDeclaration) {
                    ASTMethodDeclaration function = (ASTMethodDeclaration) node.jjtGetChild(i);
                    functionToJvm(file, function);
                }
            }

            file.close();
/*
            for (int i = 0; i < module.jjtGetNumChildren(); i++) {

                if (module.jjtGetChild(i) instanceof ASTFunction) {
                    ASTFunction function = (ASTFunction) module.jjtGetChild(i);
                    SymbolTable functionTable = this.symbolTables.get(function.name);
                    writeStackNumber(functionTable, function.name);
                }
            } 
*/
/*

        }


    }
*/
////AQUIQQ
   /* private void functionToJvm(PrintWriter file, ASTMethodDeclaration function) {

        SymbolClass functionTable = this.symbolTable.get(function.name); //Estou aqui!! não estou a ver como utilizar a synbol table..

        functionTable.setRegisters(function.name);
*/
        /*
        //function header

        ASTArgumentList arguments = null;

        for(int i = 0; i < function.jjtGetNumChildren(); i++){
            if(function.jjtGetChild(i) instanceof ASTArgumentList)
                arguments = (ASTArgumentList) function.jjtGetChild(i);
        }

        file.print("\n.method public static ");
        if (function.name.equals("main")) {
            file.print("main([Ljava/lang/String;)V\n");
        } else {
            file.print(functionHeader(function.name)+"\n");
        }

        //function limits

        int nrParameters = (new ArrayList(functionTable.getParameters().keySet())).size();
        int nrVariables = (new ArrayList(functionTable.getVariables().keySet())).size();
        int nrReturn = functionTable.getReturnSymbol() != null ? 1 : 0;

        /*int nrLocals = nrParameters + nrVariables + nrReturn;
        if(function.name.equals("main")) nrLocals++;
        int nrStack = 6;*/

    //    file.println("locals_" + function.name);
    //    file.println("stack_" + function.name);

/*
        //function statements
        for (int i = 0; i < function.jjtGetNumChildren(); i++) {
           
            if(!(function.jjtGetChild(i) instanceof ASTElement || function.jjtGetChild(i) instanceof ASTVarlist)){
                //file.print("\n");
                statementToJvm(file, functionTable, function.jjtGetChild(i));
                //file.print("\n");
            }
        }

        //function return
        
        if (functionTable.getReturnSymbol() != null) {
            printVariableLoad(file, functionTable,functionTable.getReturnSymbol().getName(), "ID");
            if (functionTable.getReturnSymbol().getType() == "int") {
                file.println("  ireturn");

            } else { //array
                file.println("  areturn");

            }
        } else { //void
            file.println("  return");
        }
        file.println(".end method\n");
*/
   // }

/*
    private PrintWriter getFile() {
        try {
            File dir = new File("jasmin");
            if (!dir.exists())
                dir.mkdirs();

            File file = new File("jasmin/bytcodes.j"); //é criado só 1 ficheiro??????
            if (!file.exists())
                file.createNewFile();

            PrintWriter writer = new PrintWriter(file);
            return writer;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}*/
