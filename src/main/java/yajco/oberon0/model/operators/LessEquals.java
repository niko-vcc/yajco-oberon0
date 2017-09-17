package yajco.oberon0.model.operators;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;
import yajco.oberon0.model.BinaryOperation;
import yajco.oberon0.model.Expression;

public class LessEquals extends BinaryOperation {
    @Operator(priority = 1, associativity = Associativity.LEFT)
    public LessEquals(Expression left, @Before("<=") Expression right) {
        super(left, right);
    }
}
