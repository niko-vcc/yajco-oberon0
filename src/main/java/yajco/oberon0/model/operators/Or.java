package yajco.oberon0.model.operators;

import yajco.annotation.Before;
import yajco.annotation.Operator;
import yajco.model.pattern.impl.Associativity;
import yajco.oberon0.model.BinaryOperation;
import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Type;

public class Or extends BinaryOperation {
    @Operator(priority = 2, associativity = Associativity.LEFT)
    public Or(Expression left, @Before("OR") Expression right) {
        super(left, right);
    }

    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }
}
