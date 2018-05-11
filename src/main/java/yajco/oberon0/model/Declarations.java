package yajco.oberon0.model;

import yajco.oberon0.SymbolTable;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Declarations extends HashMap<String, Declaration>
        implements SymbolTable {

    public Declarations(ConstantDeclarations constants,
                        TypeDeclarations types,
                        VariableDeclarations variables) {
        addDeclarations(constants);
        addDeclarations(types);
        addDeclarations(variables);
    }

    public ConstantDeclarations getConstants() {
        return new ConstantDeclarations(symbolsByType(Constant.class));
    }

    public VariableDeclarations getVariables() {
        return VariableDeclarations.of(symbolsByType(Variable.class));
    }

    public TypeDeclarations getTypes() {
        return new TypeDeclarations(symbolsByType(TypeDeclaration.class));
    }

    protected void addDeclarations(List<? extends Declaration> declarations) {
        for (Declaration declaration: declarations) {
            this.put(declaration.getName(), declaration);
        }
    }

    protected <T> List<T> symbolsByType(Class<T> aClass) {
        return this.values().stream()
                .filter(e -> e.getClass().equals(aClass))
                .map(e -> (T) e)
                .collect(Collectors.toList());
    }

    @Override
    public boolean containsKey(String name) {
        return super.containsKey(name);
    }

    @Override
    public Declaration get(String name) {
        return super.get(name);
    }
}
