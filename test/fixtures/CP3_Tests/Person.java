class Person {

    String name;
    int age;
    int heigth;
    int weight;

    Person(String name, int age, int height, int weigth) {
        this.name = name;
        this.age = age;
        this.heigth = height;
        this.weight = weigth;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}