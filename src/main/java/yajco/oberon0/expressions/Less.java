package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Less extends BinaryOperation {
    @Operator(priority = 1, associativity = Associativity.LEFT)
    public Less(Expression left, @Before("<") Expression right) {
        super(left, right);
    }
}
