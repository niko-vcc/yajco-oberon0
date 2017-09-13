package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.reference.Identifier;
import yajco.oberon0.expressions.Expression;

public class Constant extends Declaration implements Memory {
    @Identifier
    private final String name;
    private final Expression expression;

    @After(";")
    public Constant(String name, @Before("=") Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    public Expression getExpression() {
        return expression;
    }
}
