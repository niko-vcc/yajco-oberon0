package yajco.oberon0.model.l4;

import yajco.annotation.Before;
import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Type;

public class ArrayType extends Type {
    private Expression size;
    private Type elementType;

    @Before("ARRAY")
    public ArrayType(Expression size, @Before("OF") Type elementType) {
        this.size = size;
        this.elementType = elementType;
    }

    public Expression getSize() {
        return size;
    }

    public void setSize(Expression size) {
        this.size = size;
    }

    public Type getElementType() {
        return elementType;
    }

    public void setElementType(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public boolean matches(Type that) {
        return false;
    }
}
