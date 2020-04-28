package symbolTable;

public class SymbolVar extends Symbol {

    boolean isInitialize = false;

    public SymbolVar(String name) {
        super(name);
    }

    public boolean isInitialize() {
        return isInitialize;
    }

    public void setInitialize() {
        isInitialize = true;
    }

    public void dump(String prefix) {

        System.out.println(prefix + "Variable: " + this.name);

        String ifIsObject = "";
        if(this.type == Type.OBJECT)
            ifIsObject = ": " + this.object_name;

        System.out.println(prefix + "  Type: " + this.type + ifIsObject);

    }
}
