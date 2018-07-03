package yajco.oberon0.model;

import yajco.annotation.After;
import yajco.annotation.Before;

public class Constant extends Declaration {
    private final Expression expression;

    @After(";")
    public Constant(String name, @Before("=") Expression expression) {
        super(name);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Type getType() {
        return expression.getType();
    }
}
