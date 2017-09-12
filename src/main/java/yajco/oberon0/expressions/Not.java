package yajco.oberon0.expressions;

import yajco.annotation.Before;
import yajco.annotation.Operator;

public class Not extends UnaryOperation {
    @Operator(priority = 4)
    public Not(@Before("~") Expression operand) {
        super(operand);
    }
}

