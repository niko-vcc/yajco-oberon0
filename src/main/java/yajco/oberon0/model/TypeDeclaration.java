package yajco.oberon0.model;


import yajco.annotation.After;
import yajco.annotation.Before;

public class TypeDeclaration extends Declaration {
    private final Type type;

    @After(";")
    public TypeDeclaration(String name, @Before("=") Type type) {
        super(name);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
