package yajco.oberon0;

public class Declarations {
    private ConstantsDeclaration constants;
    private VariablesDeclaration variables;

    public Declarations(ConstantsDeclaration constants, VariablesDeclaration variables) {
        this.constants = constants;
        this.variables = variables;
    }

    public ConstantsDeclaration getConstants() {
        return constants;
    }

    public VariablesDeclaration getVariables() {
        return variables;
    }
}
