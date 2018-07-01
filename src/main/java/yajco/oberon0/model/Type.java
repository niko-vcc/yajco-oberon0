package yajco.oberon0.model;

public abstract class Type {
    public abstract boolean matches(Type that);

    public Type getRealType() {
        return this;
    }
}
