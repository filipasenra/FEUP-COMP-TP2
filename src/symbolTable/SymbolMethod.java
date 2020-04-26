package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolMethod extends Symbol {

    public int num;

    public HashMap<String, Symbol> symbolTable = new HashMap<>();

    public Type returnType;

    public ArrayList<Type> types = new ArrayList<>();

    public SymbolMethod(String name) {
        super(name);
    }

    public void addSymbol(String name, Symbol symbol) {
        this.symbolTable.put(name, symbol);
    }

    public Symbol getSymbol(String name) {return this.symbolTable.get(name); }

    public void addType(Type type) { this.types.add(type); }
}
