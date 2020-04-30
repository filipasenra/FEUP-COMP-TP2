package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolClass extends Symbol {

    public HashMap<String, ArrayList<SymbolMethod>> symbolTableMethods = new HashMap<>();

    public HashMap<String, SymbolVar> symbolTableFields = new HashMap<>();

    public String superClass;

    public boolean imported;

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

    public void setImported(){
        this.imported = true;
    }

    public void addSymbolField(String name, SymbolVar symbol) {
        if(!symbolTableFields.containsKey(name)) {
            symbolTableFields.put(name, symbol);
        }
    }

    public void dump(String prefix) {

        String superClassString = "";

        if(this.superClass != null)
            superClassString = " extends " + this.superClass;

        System.out.print(imported ? "IMPORTED ": "");
        System.out.println("Class: " + this.name + superClassString);

        if(this.symbolTableFields.size() > 0)
            System.out.println(prefix + "  Fields: ");

        for(Map.Entry<String, SymbolVar> entry : this.symbolTableFields.entrySet()) {

            entry.getValue().dump(prefix + "   ");
        }

        if(this.symbolTableMethods.size() > 0)
            System.out.println(prefix + "  Methods: ");
        else
            System.out.println("");

        for(Map.Entry<String, ArrayList<SymbolMethod>> entry : this.symbolTableMethods.entrySet()) {

            for (int j = 0; j < entry.getValue().size(); j++) {
                entry.getValue().get(j).dump(prefix + "   ");
                System.out.println();
            }
        }

    }
}