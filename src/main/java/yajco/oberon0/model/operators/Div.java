package yajco.oberon0.model.operators;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;
import yajco.oberon0.model.BinaryOperation;
import yajco.oberon0.model.Expression;

public class Div extends BinaryOperation {
    @Operator(priority = 3, associativity = Associativity.LEFT)
    public Div(Expression left, @Before("DIV") Expression right) {
        super(left, right);
    }
}
