
class Test {
    int[] test_arr;

    public int find_maximum(int[] arr) {
        int i;
        int maximum;
        int value;

        i = 1;
        maximum = arr[0];
        while (i < arr.length) {
            value = arr[i];
            if (maximum < value) {
                maximum = value;
            } else {
            }
            i = i + 1;
        }

        return maximum;
    }

    public int build_test_arr() {
        test_arr = new int[5];
        test_arr[0] = 14;
        test_arr[1] = 28;
        test_arr[2] = 0;
        test_arr[3] = 0-5; // No unary minus in Java--
        test_arr[4] = 12;

        return 0;
    }

    public int[] get_array() {
        this.tets();
        return test_arr;
    }

    public int tets() {return 0;}

    public static void main(String[] args) {
        Test fm;
        fm = new Test();

        new Test().build_test_arr();

        print(fm.find_maximum(fm.get_array()));
    }

    public static void print(int i){

    }
}