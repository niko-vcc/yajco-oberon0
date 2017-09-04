package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;

public class Div extends BinaryOperation implements Expression {
    @Operator(priority = 3, associativity = Associativity.LEFT)
    public Div(Expression left, @Before("DIV") Expression right) {
        setLeft(left);
        setRight(right);
    }
}
