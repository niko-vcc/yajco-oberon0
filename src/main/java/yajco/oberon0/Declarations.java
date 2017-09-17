package yajco.oberon0;

import java.util.HashMap;
import java.util.Map;

public class Declarations {
    private ConstantsDeclaration constants;
    private VariablesDeclaration variables;
    private TypesDeclaration types;
    private Map<String, Entity> symbolTable = new HashMap<>();

    public Declarations(ConstantsDeclaration constants,
                        VariablesDeclaration variables,
                        TypesDeclaration types) {
        this.constants = constants;
        this.variables = variables;
        this.types = types;
        fillSymbolTable(constants, variables, types);
    }

    public ConstantsDeclaration getConstants() {
        return constants;
    }

    public VariablesDeclaration getVariables() {
        return variables;
    }

    public TypesDeclaration getTypes() {
        return types;
    }

    public Entity getEntity(String name) {
        return symbolTable.get(name);
    }

    private void fillSymbolTable(ConstantsDeclaration constants,
                                 VariablesDeclaration variables,
                                 TypesDeclaration types) {
        for (Constant constant: constants) {
            symbolTable.put(constant.getName(), constant);
        }
        for (Variable variable: variables) {
            symbolTable.put(variable.getName(), variable);
        }
        for (DeclaredType type: types) {
            symbolTable.put(type.getName(), type);
        }
    }
}
