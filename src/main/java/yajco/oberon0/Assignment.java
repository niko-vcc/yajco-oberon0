package yajco.oberon0;

import yajco.annotation.Before;
import yajco.annotation.reference.References;
import yajco.oberon0.expressions.Expression;

public class Assignment extends Statement {
    private Variable variable;
    private Expression expression;

    public Assignment(
            @References(value = Variable.class, field = "variable") String name,
            @Before(":=") Expression expression) {
        this.expression = expression;
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
