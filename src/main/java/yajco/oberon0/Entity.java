package yajco.oberon0;

public abstract class Entity {
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
