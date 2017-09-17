package yajco.oberon0.model;

public abstract class Entity {
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
