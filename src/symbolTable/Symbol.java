package symbolTable;

public abstract class Symbol {
    String name; //Name of the variable
    Type type;
    String object_name;
    int register;


    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getObject_name() {
        return object_name;
    }

    public Symbol(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Symbol(String name) {
        this.name = name;
    }


    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setRegister(int register) {
        this.register = register;
    }
}