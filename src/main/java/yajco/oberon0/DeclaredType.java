package yajco.oberon0;


import yajco.annotation.After;
import yajco.annotation.Before;

public class DeclaredType extends Entity {
    private final Type type;

    @After(";")
    public DeclaredType(String name, @Before("=") Type type) {
        super(name);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
