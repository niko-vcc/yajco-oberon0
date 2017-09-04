package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Mod extends BinaryOperation implements Expression {
    @Operator(priority = 3, associativity = Associativity.LEFT)
    public Mod(Expression left, @Before("MOD") Expression right) {
        setLeft(left);
        setRight(right);
    }
}
