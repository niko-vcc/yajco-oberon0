package yajco.oberon0.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Declarations {
    private Map<String, Declaration> symbolTable = new HashMap<>();

    public Declarations(ConstantDeclarations constants,
                        TypeDeclarations types,
                        VariableDeclarations variables) {
        addToSymbolTable(constants);
        addToSymbolTable(types);
        addToSymbolTable(variables);
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

    public Declaration getDeclaration(String name) {
        return symbolTable.get(name);
    }

    private void addToSymbolTable(List<? extends Declaration> declarations) {
        for (Declaration declaration: declarations) {
            symbolTable.put(declaration.getName(), declaration);
        }
    }

    private <T> List<T> symbolsByType(Class<T> aClass) {
        return symbolTable.values().stream()
                .filter(e -> e.getClass().equals(aClass))
                .map(e -> (T) e)
                .collect(Collectors.toList());
    }
}
