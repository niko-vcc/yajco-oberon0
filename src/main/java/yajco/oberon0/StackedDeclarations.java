package yajco.oberon0;

import yajco.oberon0.model.Declaration;

public class StackedDeclarations implements SymbolTable {
    SymbolTable table;
    SymbolTable parent;

    public StackedDeclarations(SymbolTable table, SymbolTable parent) {
        this.table = table;
        this.parent = parent;
    }

    @Override
    public boolean containsKey(String name) {
        return table.containsKey(name) || parent.containsKey(name);
    }

    @Override
    public Declaration get(String name) {
        if (table.containsKey(name))
            return table.get(name);
        else
            return parent.get(name);
    }
}
