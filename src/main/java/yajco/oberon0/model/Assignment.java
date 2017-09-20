package yajco.oberon0.model;

import yajco.annotation.Before;
import yajco.annotation.Exclude;

public class Assignment extends Statement {
    private String name;
    private Variable variable;
    private Expression expression;

    public Assignment(
            String name,
            @Before(":=") Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Exclude
    public Assignment(
            Variable variable,
            @Before(":=") Expression expression) {
        this.name = variable.getName();
        this.variable = variable;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
