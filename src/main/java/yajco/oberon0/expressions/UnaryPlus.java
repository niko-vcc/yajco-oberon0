package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;

public class UnaryPlus extends UnaryOperation {
    @Operator(priority = 2)
    public UnaryPlus(@Before("+") Expression operand) {
        super(operand);
    }
}

