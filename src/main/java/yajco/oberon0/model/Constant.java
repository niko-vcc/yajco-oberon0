package yajco.oberon0.model;

import yajco.annotation.After;
import yajco.annotation.Before;

public class Constant extends Entity {
    private final Expression expression;

    @After(";")
    public Constant(String name, @Before("=") Expression expression) {
        super(name);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
