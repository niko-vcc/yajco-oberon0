package yajco.oberon0;

public abstract class Declaration {
    private String name;

    public Declaration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
