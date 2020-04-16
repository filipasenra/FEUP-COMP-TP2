package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolMethod extends Symbol {

    public HashMap<String, Symbol> symbolTable = new HashMap<>();

    public ArrayList<Type> types = new ArrayList<>();

    public SymbolMethod(String name) {
        super(name);
    }

    public void addSymbol(String name, Symbol symbol) {
        this.symbolTable.put(name, symbol);
    }

    public void addType(Type type) { this.types.add(type); }
}
