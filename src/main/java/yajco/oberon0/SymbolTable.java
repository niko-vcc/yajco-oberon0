package yajco.oberon0;

import yajco.oberon0.model.Declaration;

public interface SymbolTable {
    boolean containsKey(String name);

    Declaration get(String name);
}
