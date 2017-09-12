package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Greater extends BinaryOperation {
    @Operator(priority = 1, associativity = Associativity.LEFT)
    public Greater(Expression left, @Before(">") Expression right) {
        super(left, right);
    }
}
