package yajco.oberon0.expressions;

import yajco.annotation.reference.References;
import yajco.oberon0.Variable;

public class Reference extends Expression {
    private Variable variable;

    public Reference(@References(Variable.class) String name) {
    }

    public Variable getVariable() {
        return variable;
    }
}
