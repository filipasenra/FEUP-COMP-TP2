package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolMethod extends Symbol {

    public int num;

    public HashMap<String, SymbolVar> symbolTable = new HashMap<>();

    public ArrayList<Type> types = new ArrayList<>();

    public SymbolMethod(String name) {
        super(name);
    }

    public void addSymbol(String name, SymbolVar symbol) {
        this.symbolTable.put(name, symbol);
    }

    public SymbolVar getSymbol(String name) {return this.symbolTable.get(name); }

    public void addType(Type type) { this.types.add(type); }
}
