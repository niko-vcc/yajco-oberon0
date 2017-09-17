package yajco.oberon0.model;

public class Variable extends Entity {
    private Type type;

    public Variable(String name) {
        super(name);
    }

    public Type getType() {
        return type;
    }

    void setType(Type type) {
        this.type = type;
    }
}
