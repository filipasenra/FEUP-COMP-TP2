class Person {

    int id;
    int age;

    public static void main(String[] args) {
    }

    Person(int id, int age) {
        this.id = id;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void printInfo() {
        System.out.println("A pessoa com o id " + this.id + " tem " + this.age + " anos.");
    }
}