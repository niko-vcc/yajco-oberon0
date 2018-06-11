package yajco.oberon0.model.l4;

import yajco.annotation.Before;
import yajco.annotation.Separator;
import yajco.oberon0.model.Type;
import yajco.oberon0.model.Variable;

import java.util.List;

public class FieldList {
    private List<Variable> variables;
    private Type type;

    public FieldList(
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
