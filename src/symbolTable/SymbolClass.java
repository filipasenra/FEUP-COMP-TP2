package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolClass extends Symbol {

    public HashMap<String, ArrayList<SymbolMethod>> symbolTableMethods = new HashMap<>();

    public HashMap<String, SymbolVar> symbolTableFields = new HashMap<>();

    public String superClass;

    public SymbolClass(String name) {
        super(name);
    }

    public void addSymbolMethod(String name, SymbolMethod symbol) {
        if(symbolTableMethods.containsKey(name)) {
            symbolTableMethods.get(name).add(symbol);
        }
        else {
            ArrayList<SymbolMethod> first = new ArrayList<>();
            first.add(symbol);
            this.symbolTableMethods.put(name, first);
        }
    }

    public void addSymbolField(String name, SymbolVar symbol) {
        if(!symbolTableFields.containsKey(name)) {
            symbolTableFields.put(name, symbol);
        }
    }
}