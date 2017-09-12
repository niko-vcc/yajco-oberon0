package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class LessEquals extends BinaryOperation {
    @Operator(priority = 1, associativity = Associativity.LEFT)
    public LessEquals(Expression left, @Before("<=") Expression right) {
        super(left, right);
    }
}
