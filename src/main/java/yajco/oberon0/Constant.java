package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.oberon0.expressions.Expression;

public class Constant extends Declaration {
    private Expression expression;

    @Before("CONST") @After(";")
    public Constant(String name, @Before("=") Expression expression) {
        setName(name);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
