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
}
