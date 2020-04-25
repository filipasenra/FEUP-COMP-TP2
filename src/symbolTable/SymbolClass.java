package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolClass extends Symbol {

    public HashMap<String, ArrayList<Symbol>> symbolTable = new HashMap<>();
    public String superClass;

    public SymbolClass(String name) {
        super(name);
    }

    public void addSymbol(String name, Symbol symbol) {
        if(symbolTable.containsKey(name)) {
            ArrayList<Symbol> curr = symbolTable.get(name);
            curr.add(symbol);
        }
        else {
            ArrayList<Symbol> first = new ArrayList<>();
            first.add(symbol);
            this.symbolTable.put(name, first);
        }
    }

    public ArrayList<Symbol> getSymbol(String name) {return this.symbolTable.get(name);}
}