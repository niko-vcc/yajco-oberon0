package yajco.oberon0.model;

public class Reference extends Expression {
    private final String name;
    private Declaration declaration;

    public Reference(String name) {
        this.name = name;
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
}
