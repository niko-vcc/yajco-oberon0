package yajco.oberon0;

import yajco.annotation.Before;
import yajco.oberon0.expressions.Expression;

public class Assignment extends Statement {
    private String variable;
    private Expression expression;

    public Assignment(String name, @Before(":=") Expression expression) {
        this.variable = name;
        this.expression = expression;
    }
}
