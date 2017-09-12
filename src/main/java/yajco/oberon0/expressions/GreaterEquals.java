package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class GreaterEquals extends BinaryOperation {
    @Operator(priority = 1, associativity = Associativity.LEFT)
    public GreaterEquals(Expression left, @Before(">=") Expression right) {
        super(left, right);
    }
}
