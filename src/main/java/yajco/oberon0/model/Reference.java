package yajco.oberon0.model;

import yajco.annotation.Exclude;

public class Reference extends Expression {
    private final String name;
    private Declaration declaration;

    public Reference(String name) {
        this.name = name;
    }

    @Exclude
    public Reference(Declaration declaration) {
        this.name = declaration.getName();
        this.declaration = declaration;
    }

    public String getName() {
        return name;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
    }

    @Override
    public Type getType() {
        return declaration.getType();
    }
}
