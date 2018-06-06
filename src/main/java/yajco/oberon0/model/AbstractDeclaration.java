package yajco.oberon0.model;

public abstract class AbstractDeclaration implements Declaration {
    private String name;

    public AbstractDeclaration(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
