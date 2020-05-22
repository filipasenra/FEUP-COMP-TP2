

class Test {
    int[] a;
    int c;

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

        //array store
        a = new int[8];

        //array position store
        a[0] = 2;

        //array access
        System.out.println(a);

        //array position load
        System.out.println(a[0]);
    }
}