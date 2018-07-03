package yajco.oberon0.model;

public abstract class Declaration {
    private String name;

    public Declaration(String name) {
        this.name = name;
    }

    public abstract Type getType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
