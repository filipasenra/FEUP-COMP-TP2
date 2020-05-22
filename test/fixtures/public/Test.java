

class Test {

    public String[] teste(String[] args) {

        return args;
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