package yajco.oberon0;

import java.util.HashMap;
import java.util.Map;

public class Declarations {
    private ConstantsDeclaration constants;
    private VariablesDeclaration variables;
    private Map<String, Declaration> symbolTable = new HashMap<>();

    public Declarations(ConstantsDeclaration constants, VariablesDeclaration variables) {
        this.constants = constants;
        this.variables = variables;
        for (Constant constant: constants) {
            symbolTable.put(constant.getName(), constant);
        }
        for (Variable variable: variables) {
            symbolTable.put(variable.getName(), variable);
        }
    }

    public ConstantsDeclaration getConstants() {
        return constants;
    }

    public VariablesDeclaration getVariables() {
        return variables;
    }

    public Declaration getDeclaration(String name) {
        return symbolTable.get(name);
    }
}
