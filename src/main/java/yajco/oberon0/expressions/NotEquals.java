package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class NotEquals extends BinaryOperation {
    @Operator(priority = 1, associativity = Associativity.LEFT)
    public NotEquals(Expression left, @Before("#") Expression right) {
        super(left, right);
    }
}
