class Test {
    int[] a;
    int c;

    int movesmade;

    public Test(int a ){

    }

    public int test() {

        return c;
    }

    public void test(String[] args){

        c = 1;
        //array store
        a = new int[8];

        //array position store
        a[0] = 2;

        //array access
        System.out.println(a);

        //array position load
        System.out.println(a[0]);
    }

    public static void main(String[] args){
        int[] a;
        Test Test;

        //array store
        a = new int[8];

        //array position store
        a[0] = 2;

        //array access
        System.out.println(a);

        //array position load
        System.out.println(a[0]);

        Test = new Test(1);
    }

    public int winner() {
        int[] array;
        int winner;
        winner = 0-1;
        array = new int[3];
        // Check for three X's or O's in a row.

        if (winner < 1 && !(movesmade < 9)&& !(9 < movesmade))
            winner = 0;
        else{}
        return winner;
    }
}