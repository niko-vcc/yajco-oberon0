package yajco.oberon0.model;

public abstract class Declaration {
    private String name;

    public Declaration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Type getType();
}
