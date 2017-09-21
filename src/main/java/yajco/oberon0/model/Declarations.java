package yajco.oberon0.model;

import java.util.HashMap;
import java.util.Map;

public class Declarations {
    private ConstantDeclarations constants;
    private VariableDeclarations variables;
    private TypeDeclarations types;
    private Map<String, Declaration> symbolTable = new HashMap<>();

    public Declarations(ConstantDeclarations constants,
                        TypeDeclarations types,
                        VariableDeclarations variables) {
        this.constants = constants;
        this.types = types;
        this.variables = variables;
        fillSymbolTable(constants, variables, types);
    }

    public ConstantDeclarations getConstants() {
        return constants;
    }

    public VariableDeclarations getVariables() {
        return variables;
    }

    public TypeDeclarations getTypes() {
        return types;
    }

    public Declaration getDeclaration(String name) {
        return symbolTable.get(name);
    }

    private void fillSymbolTable(ConstantDeclarations constants,
                                 VariableDeclarations variables,
                                 TypeDeclarations types) {
        for (Constant constant: constants) {
            symbolTable.put(constant.getName(), constant);
        }
        for (Variable variable: variables) {
            symbolTable.put(variable.getName(), variable);
        }
        for (TypeDeclaration type: types) {
            symbolTable.put(type.getName(), type);
        }
    }
}
