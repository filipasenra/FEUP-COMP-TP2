package symbolTable;

public abstract class Symbol {

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        INT, INT_ARRAY, BOOLEAN, STRING, OBJECT, STRING_ARRAY, VOID
    }

    String name; //Name of the variable
    Type type;


    public Symbol(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Symbol(String name) {
        this.name = name;
    }
}