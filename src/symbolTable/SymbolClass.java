package symbolTable;

import java.util.HashMap;

public class SymbolClass extends Symbol {

    public HashMap<String, Symbol> symbolTable = new HashMap<>();

    public SymbolClass(String name) {
        super(name);
    }

    public void addSymbol(String name, Symbol symbol) {
        this.symbolTable.put(name, symbol);
    }

    public Symbol getSymbol(String name) {return this.symbolTable.get(name);}
}