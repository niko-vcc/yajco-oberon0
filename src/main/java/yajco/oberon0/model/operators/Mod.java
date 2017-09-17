package yajco.oberon0.model.operators;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;
import yajco.oberon0.model.BinaryOperation;
import yajco.oberon0.model.Expression;

public class Mod extends BinaryOperation {
    @Operator(priority = 3, associativity = Associativity.LEFT)
    public Mod(Expression left, @Before("MOD") Expression right) {
        super(left, right);
    }
}
