package symbolTable;

public class SymbolVar extends Symbol {

    boolean isInitialized = false;

    public SymbolVar(String name) {
        super(name);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized() {
        isInitialized = true;
    }

    public void dump(String prefix) {

        System.out.println(prefix + "Variable: " + this.name);

        String ifIsObject = "";
        if(this.type == Type.OBJECT)
            ifIsObject = ": " + this.object_name;

        System.out.println(prefix + "  Type: " + this.type + ifIsObject);

    }
}
