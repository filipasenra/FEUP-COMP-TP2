public class jmm {



    // When in root folder (comp2020-3a)
    // gradle build
    // or
    // gradle test
    // to run:
    // java -jar comp2020-3a.jar test/fixtures/public/FindMaximum.jmm
    // java -jar comp2020-3a.jar test/fixtures/public/fail/syntactical/MultipleSequential.jmm
    //java -jar comp2020-3a.jar test/fixtures/public/fail/semantic/binop_incomp.jmm

    public static void main(String args[]) throws ParseException {
        if (args.length != 1) {
            System.err.println("Usage: java Jmm <filename>");
            return;
        }

        ParserAST myParser;
        try {
            myParser = new ParserAST(new java.io.FileInputStream(args[0]));
        } catch (java.io.FileNotFoundException e) {
            System.out.println("file " + args[0] + " not found.");
            return;
        }

        SimpleNode root = myParser.ParseExpression(); // returns reference to root node
        if (myParser.getNerros() > 0) {
            throw new RuntimeException("Has syntactic errors");
        }
        root.dump(""); // prints the tree on the screen
        System.out.println("Finished Parsing");

        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();
        semanticAnalysis.startAnalysing(root);

        /*if (semanticAnalysis.getNerros() > 0) {
            throw new RuntimeException("Has semantic errors");
        }*/

        System.out.println("ERRORS: " + semanticAnalysis.getNerros());

    }
}
