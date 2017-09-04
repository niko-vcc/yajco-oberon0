package yajco.oberon0;

import yajco.annotation.Token;

public class Number implements Expression {
    private int value;

    public Number(@Token("integer") int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
