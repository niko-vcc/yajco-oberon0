package yajco.oberon0;

import yajco.annotation.Before;

import java.util.ArrayList;
import java.util.List;

public class VariablesDeclaration extends ArrayList<Variable> {

    public VariablesDeclaration() {
    }

    @Before("VAR")
    public VariablesDeclaration(List<VariablesGroup> groups) {
        for (VariablesGroup group : groups) {
            addAll(group.getVariables());
        }
    }
}
