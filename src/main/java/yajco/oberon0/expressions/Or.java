package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Or extends BinaryOperation {
    @Operator(priority = 2, associativity = Associativity.LEFT)
    public Or(Expression left, @Before("OR") Expression right) {
        super(left, right);
    }
}
