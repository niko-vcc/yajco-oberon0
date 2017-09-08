package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Token;
import yajco.annotation.reference.Identifier;
import yajco.oberon0.expressions.Expression;

public class Variable extends Declaration {
    @Identifier
    private String name;
    private Expression expression;
    private boolean isConstant;

    @Before("VAR") @After(";")
    public Variable(String name, @Before(":") @Token("name") String type) {
        this.name = name;
        isConstant = false;
    }

    @Before("CONST") @After(";")
    public Variable(String name, @Before("=") Expression expression) {
        this.name = name;
        this.expression = expression;
        isConstant = true;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public Expression getExpression() {
        return expression;
    }
}
