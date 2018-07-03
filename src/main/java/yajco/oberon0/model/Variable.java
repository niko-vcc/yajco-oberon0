package yajco.oberon0.model;

public class Variable extends Declaration {
    private Type type;

    public Variable(String name) {
        super(name);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
