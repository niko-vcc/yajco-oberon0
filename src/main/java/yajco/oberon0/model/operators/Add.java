package yajco.oberon0.model.operators;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;
import yajco.oberon0.model.BinaryOperation;
import yajco.oberon0.model.Expression;

public class Add extends BinaryOperation {
    @Operator(priority = 2, associativity = Associativity.LEFT)
    public Add(Expression left, @Before("+") Expression right) {
        super(left, right);
    }
}
