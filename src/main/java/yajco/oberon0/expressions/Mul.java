package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Mul extends BinaryOperation {
    @Operator(priority = 3, associativity = Associativity.LEFT)
    public Mul(Expression left, @Before("*") Expression right) {
        super(left, right);
    }
}
