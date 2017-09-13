package yajco.oberon0.expressions;

import yajco.oberon0.Memory;

public class Reference extends Expression {
    private final String name;
    private Memory variable;

    public Reference(String name) {
        this.name = name;
    }

    public Memory getVariable() {
        return variable;
    }
}
