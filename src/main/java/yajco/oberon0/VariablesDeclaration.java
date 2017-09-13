package yajco.oberon0;

import yajco.annotation.Before;

import java.util.ArrayList;
import java.util.List;

public class VariablesDeclaration {
    private List<Variable> variables = new ArrayList<>();

    public VariablesDeclaration() {
    }

    @Before("VAR")
    public VariablesDeclaration(List<VariablesGroup> groups) {
        for (VariablesGroup group : groups) {
            variables.addAll(group.getVariables());
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }
}
