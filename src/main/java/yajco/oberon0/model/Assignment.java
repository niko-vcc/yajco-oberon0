package yajco.oberon0.model;

import yajco.annotation.Before;
import yajco.annotation.Exclude;

public class Assignment extends Statement {
    private String name;
    private Storage variable;
    private Expression expression;

    public Assignment(
            String name,
            @Before(":=") Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Exclude
    public Assignment(
            Storage variable,
            Expression expression) {
        this.name = variable.getName();
        this.variable = variable;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Storage getVariable() {
        return variable;
    }

    public void setVariable(Storage variable) {
        this.variable = variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
