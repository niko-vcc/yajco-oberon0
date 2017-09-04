package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Add extends BinaryOperation implements Expression {
    @Operator(priority = 2, associativity = Associativity.LEFT)
    public Add(Expression left, @Before("+") Expression right) {
        setLeft(left);
        setRight(right);
    }
}
