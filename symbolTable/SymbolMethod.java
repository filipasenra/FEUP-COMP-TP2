import java.util.ArrayList;

public class SymbolMethod extends Symbol {

    ArrayList<String> argTypes;

    public SymbolMethod(String name, Type type,  ArrayList<String> argTypes) {
        super(name, type);
        this.argTypes = argTypes;
    }
}
