import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class jmm {

    private static boolean DEBUG = false;

    // When in root folder (comp2020-3a)
    // gradle build
    // or
    // gradle test
    // to run:
        
    // java -jar comp2020-3a.jar test/fixtures/public/Simple.jmm
    // java -jar comp2020-3a.jar test/fixtures/public/fail/syntactical/MultipleSequential.jmm
    //java -jar comp2020-3a.jar test/fixtures/public/fail/semantic/binop_incomp.jmm

    public static void main(String args[]) throws ParseException {
        if (args.length != 1 && args.length != 2) {
            System.err.println("Usage: java Jmm <filename> -debug*>");
            return;
        }

        if(args.length == 2){
            if(!args[1].equals("-debug")){
                System.err.println("Usage: java Jmm <filename> -debug*>");
                return;
            }

            DEBUG = true;
        }


        ParserAST myParser;
        try {
            myParser = new ParserAST(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("file " + args[0] + " not found.");
            return;
        }

        if(DEBUG)
            System.out.println("Starting Parsing\n");

        SimpleNode root = myParser.ParseExpression(); // returns reference to root node
        if (myParser.getNerros() > 0) {
            throw new RuntimeException("Has syntactic errors");
        }

        if(DEBUG) {
            root.dump(""); // prints the tree on the screen
            System.out.println("Finished Parsing");
        }

        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();
        semanticAnalysis.startAnalysing(root);


        if(DEBUG)
            System.out.println("Starting Semantic Analysis\n");

        if (semanticAnalysis.getNerros() > 0) {
            throw new RuntimeException("Has " + semanticAnalysis.getNerros() + " semantic errors");
        }

        if (semanticAnalysis.getNwarnings() > 0) {
            System.err.println("Has " + semanticAnalysis.getNwarnings() + " semantic warnings");
        }

        if(DEBUG) {
            semanticAnalysis.dump();
            System.out.println("Finished Semantic Analysis\n");
        }

        if(DEBUG) {
            System.out.println("Starting creating jasmin code");
        }

        CodeGenerator generator = new CodeGenerator(semanticAnalysis);
        generator.generate(root);


        System.out.println("Jasmin code generated");
    }
}
