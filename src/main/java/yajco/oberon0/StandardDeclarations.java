package yajco.oberon0;

import yajco.oberon0.model.Declaration;
import yajco.oberon0.model.PrimitiveType;
import yajco.oberon0.model.l3.Parameter;
import yajco.oberon0.model.l3.Procedure;

import java.util.*;

public class StandardDeclarations implements SymbolTable {
    static Map<String, Declaration> declarations = new HashMap<>();

    public StandardDeclarations() {
        declarations.put("Read", new Procedure("Read", Collections.singletonList(
                        new Parameter("x", PrimitiveType.INTEGER, true))));
        declarations.put("Write", new Procedure("Write", Collections.singletonList(
                        new Parameter("x", PrimitiveType.INTEGER, false))));
        declarations.put("WriteHex", new Procedure("WriteHex", Collections.singletonList(
                        new Parameter("x", PrimitiveType.INTEGER, false))));
        declarations.put("WriteLn", new Procedure("WriteLn", Collections.emptyList()));
    }

    @Override
    public boolean containsKey(String name) {
        return declarations.containsKey(name);
    }

    @Override
    public Declaration get(String name) {
        return declarations.get(name);
    }
}
