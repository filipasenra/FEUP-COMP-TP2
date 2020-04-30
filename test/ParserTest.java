import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Method;

import org.junit.Test;

public class ParserTest {

    private static String CLASS_WITH_MAIN = "jmm";

    private void test(String jmmResource, boolean mustFail) {
        // Copy contents of resource to a temporary file
        File tempFolder = CompUtils.getTempFolder("comp_jmm_test");
        File testFile = CompUtils.resourceCopy(jmmResource, tempFolder);

        boolean success = true;

        try {

            // Get class with main
            Class<?> mainClass = Class.forName(CLASS_WITH_MAIN);

            // It is expected that class has a main function
            Method mainMethod = mainClass.getMethod("main", String[].class);

            // Invoke main method with file as argument
            String[] mainArgs = {testFile.getAbsolutePath()};
            Object[] invokeArgs = {mainArgs};
            mainMethod.invoke(null, invokeArgs);

        } catch (Exception e) {
            System.out.println("Test failed: " + e);
            e.printStackTrace();
            success = false;
        } finally {
            // Clean-up
            testFile.delete();
        }

        // Flip result, in case failure is needed
        if (mustFail) {
            success = !success;
        }

        if (!success) {
            if (mustFail) {
                System.out.println("Expected parser to throw exception");
            } else {
                System.out.println("Expected parser to complete successfully");
            }
            fail();
        }
    }

    @Test
    public void testFindMaximum() {
        test("fixtures/public/FindMaximum.jmm", false);
    }

    @Test
    public void testHelloWorld() {
        test("fixtures/public/HelloWorld.jmm", false);
    }

    @Test
    public void testLazysort() {
        test("fixtures/public/Lazysort.jmm", false);
    }

    @Test
    public void testLife() {
        test("fixtures/public/Life.jmm", false);
    }

    @Test
    public void testMonteCarloPi() {
        test("fixtures/public/MonteCarloPi.jmm", false);
    }

    @Test
    public void testQuickSort() {
        test("fixtures/public/QuickSort.jmm", false);
    }

    @Test
    public void testSimple() {
        test("fixtures/public/Simple.jmm", false);
    }

    @Test
    public void testTicTacToe() {
        test("fixtures/public/TicTacToe.jmm", false);
    }

    @Test
    public void testWhileAndIF() {
        test("fixtures/public/WhileAndIF.jmm", false);
    }

    @Test
    public void stringType() {
        test("fixtures/public/fail/semantic/StringType.jmm", true);
    }

    @Test
    public void testarr_index_not_int() {
        test("fixtures/public/fail/semantic/arr_index_not_int.jmm", true);
    }

    @Test
    public void testarr_size_not_int() {
        test("fixtures/public/fail/semantic/arr_size_not_int.jmm", true);
    }

    @Test
    public void testbadArguments() {
        test("fixtures/public/fail/semantic/badArguments.jmm", true);
    }

    @Test
    public void testbinop_incomp() {
        test("fixtures/public/fail/semantic/binop_incomp.jmm", true);
    }

    @Test
    public void testfuncNotFound() {
        test("fixtures/public/fail/semantic/funcNotFound.jmm", true);
    }

    @Test
    public void testsimple_length() {
        test("fixtures/public/fail/semantic/simple_length.jmm", true);
    }

    @Test
    public void testvar_exp_incomp() {
        test("fixtures/public/fail/semantic/var_exp_incomp.jmm", true);
    }

    @Test
    public void testvar_lit_incomp() {
        test("fixtures/public/fail/semantic/var_lit_incomp.jmm", true);
    }

    @Test
    public void testvar_undef() {
        test("fixtures/public/fail/semantic/var_undef.jmm", true);
    }

    @Test
    public void testvarNotInit() {
        test("fixtures/public/varNotInit.jmm", false);
    }

    @Test
    public void testmiss_type() {
        test("fixtures/public/fail/semantic/extra/miss_type.jmm", true);
    }

    //Our tests

    @Test
    public void testAddArrays() {
        test("fixtures/public/fail/semantic/ourTests/addArrays.jmm", true);
    }

    @Test
    public void testSameOperation() {
        test("fixtures/public/fail/semantic/ourTests/sameOperation.jmm", true);
    }

    @Test
    public void testAccessArray() {
        test("fixtures/public/fail/semantic/ourTests/accessArray.jmm", true);
    }


    @Test
    public void testBooleanOper() {
        test("fixtures/public/fail/semantic/ourTests/booleanOper.jmm", true);
    }

    @Test
    public void testCheckIf() {
        test("fixtures/public/fail/semantic/ourTests/checkIf.jmm", true);
    }

    @Test
    public void testAddBooleans() {
        test("fixtures/public/fail/semantic/ourTests/addBooleans.jmm", true);
    }

    @Test
    public void testRepetitiveVars() {
        test("fixtures/public/fail/semantic/ourTests/repetitiveVars.jmm", true);
    }

    @Test
    public void testVarInitWhile() {
        test("fixtures/public/varInitWhile.jmm", false);
    }

    @Test
    public void testStaticContext() {
        test("fixtures/public/fail/semantic/ourTests/staticContext.jmm", true);
    }

    @Test
    public void staticContext() {
        test("fixtures/public/fail/semantic/ourTests/staticImport.jmm", true);
    }

    //end of our tests

    @Test
    public void testBlowUp() {
        test("fixtures/public/fail/syntactical/BlowUp.jmm", true);
    }

    @Test
    public void testCompleteWhileTest() {
        test("fixtures/public/fail/syntactical/CompleteWhileTest.jmm", true);
    }

    @Test
    public void testLengthError() {
        test("fixtures/public/fail/syntactical/LengthError.jmm", true);
    }

    @Test
    public void testMissingRightPar() {
        test("fixtures/public/fail/syntactical/MissingRightPar.jmm", true);
    }

    @Test
    public void testMultipleSequential() {
        test("fixtures/public/fail/syntactical/MultipleSequential.jmm", true);
    }

    @Test
    public void testNestedLoop() {
        test("fixtures/public/fail/syntactical/NestedLoop.jmm", true);
    }
}
