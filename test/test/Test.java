class Test {


    public static void main(String[] args){
        int a;
        int b;
        int c;
        int[] d;
        a = 20;
        b = 10;
        d = new int[10];
        if( a < b){
            c  = a-1;
        }else{
            c = b-1;
        }


        while((0-1) < c){
            d[c] = a-b;
            c= c-1;
            a= a-1;
            b= b-1;
        }
        c=0;
        while(c < d.length){
            c= c+1;
        }
    }

}