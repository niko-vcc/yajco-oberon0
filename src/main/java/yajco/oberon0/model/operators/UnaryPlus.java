package yajco.oberon0.model.operators;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Type;
import yajco.oberon0.model.UnaryOperation;

public class UnaryPlus extends UnaryOperation {
    @Operator(priority = 2)
    public UnaryPlus(@Before("+") Expression operand) {
        super(operand);
    }

    @Override
    public Type getType() {
        return Type.INTEGER;
    }
}

