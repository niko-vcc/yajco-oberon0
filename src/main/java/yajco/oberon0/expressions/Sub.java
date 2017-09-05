package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Sub extends BinaryOperation {
    @Operator(priority = 2, associativity = Associativity.LEFT)
    public Sub(Expression left, @Before("-") Expression right) {
        super(left, right);
    }
}
