package yajco.oberon0;

import yajco.annotation.Before;
import yajco.oberon0.expressions.Expression;

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
