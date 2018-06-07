package yajco.oberon0.model;

public class TypeReference extends Type {
    private String name;
    private Type referencedType;

    public TypeReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean matches(Type that) {
        return this.referencedType.matches(that);
    }

    public void setReferencedType(Type referencedType) {
        this.referencedType = referencedType;
    }

    public Type getReferencedType() {
        return referencedType;
    }

    @Override
    public String toString() {
        return getName();
    }
}
