package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Separator;
import yajco.annotation.Token;

import java.util.ArrayList;
import java.util.List;

public class VariablesGroup {
    private List<Variable> variables = new ArrayList<>();

    @After(";")
    public VariablesGroup(
            @Separator(",") List<Variable> variables,
            @Before(":") @Token("name") String type) {
        this.variables.addAll(variables);
    }

    public List<Variable> getVariables() {
        return variables;
    }
}
