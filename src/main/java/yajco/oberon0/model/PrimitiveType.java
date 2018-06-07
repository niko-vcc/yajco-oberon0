package yajco.oberon0.model;

import yajco.annotation.Exclude;

@Exclude
public class PrimitiveType extends Type {
    public static PrimitiveType INTEGER = new PrimitiveType("INTEGER");
    public static PrimitiveType BOOLEAN = new PrimitiveType("BOOLEAN");

    private String name;

    private PrimitiveType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean matches(Type that) {
        while (that instanceof TypeReference) {
            that = ((TypeReference) that).getReferencedType();
        }
        return this == that;
    }
}
