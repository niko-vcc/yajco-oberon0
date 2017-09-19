package yajco.oberon0.model;

import yajco.annotation.Token;

public class Number extends Expression {
    private int value;

    public Number(@Token("integer") int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return Type.INTEGER;
    }
}
