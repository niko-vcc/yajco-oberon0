package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;

public class UnaryMinus extends UnaryOperation {
    @Operator(priority = 2)
    public UnaryMinus(@Before("-") Expression operand) {
        super(operand);
    }
}

