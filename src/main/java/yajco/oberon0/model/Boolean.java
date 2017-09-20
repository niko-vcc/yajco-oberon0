package yajco.oberon0.model;

import yajco.annotation.Exclude;

@Exclude
public class Boolean extends Expression{
    private boolean value;

    public static Boolean TRUE = new Boolean(true);
    public static Boolean FALSE = new Boolean(false);

    private Boolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return PrimitiveType.BOOLEAN;
    }
}
