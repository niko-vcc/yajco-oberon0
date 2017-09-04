package yajco.oberon0;

import yajco.annotation.Before;

public class Add extends BinaryOperation implements Expression {
    public Add(Expression left, @Before("+") Expression right) {
        setLeft(left);
        setRight(right);
    }
}
