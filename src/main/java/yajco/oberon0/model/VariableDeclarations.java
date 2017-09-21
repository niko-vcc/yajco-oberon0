package yajco.oberon0.model;

import yajco.annotation.Before;
import yajco.annotation.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class VariableDeclarations extends ArrayList<Variable> {
    public VariableDeclarations() {}

    @Before("VAR")
    public VariableDeclarations(@Range(minOccurs = 1) List<VariablesGroup> groups) {
        for (VariablesGroup group : groups) {
            addAll(group.getVariables());
        }
    }

    public List<VariablesGroup> getGroups() {
        return groupVariables(this);
    }

    public static List<VariablesGroup> groupVariables(List<Variable> vars) {
        return vars.stream()
                .map(v -> new VariablesGroup(singletonList(v), v.getType()))
                .collect(Collectors.toList());
    }

    public static VariableDeclarations of(List<Variable> vars) {
        return new VariableDeclarations(groupVariables(vars));
    }
}
