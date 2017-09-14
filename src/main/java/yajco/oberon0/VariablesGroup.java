package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Separator;

import java.util.List;

public class VariablesGroup {
    private List<Variable> variables;
    private Type type;

    @After(";")
    public VariablesGroup(
            @Separator(",") List<Variable> variables,
            @Before(":") Type type) {
        this.variables = variables;
        this.type = type;
        for (Variable variable: variables) {
            variable.setType(type);
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public Type getType() {
        return type;
    }
}
