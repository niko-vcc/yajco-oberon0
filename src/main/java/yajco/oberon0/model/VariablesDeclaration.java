package yajco.oberon0.model;

import yajco.annotation.Before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VariablesDeclaration extends ArrayList<Variable> {

    private final List<VariablesGroup> groups;

    public VariablesDeclaration() {
        this.groups = Collections.emptyList();
    }

    @Before("VAR")
    public VariablesDeclaration(List<VariablesGroup> groups) {
        this.groups = groups;
        for (VariablesGroup group : groups) {
            addAll(group.getVariables());
        }
    }

    public List<Variable> getDeclarations() {
        return this;
    }

    public List<VariablesGroup> getGroups() {
        return groups;
    }
}
