package yajco.oberon0.model;

public class Reference extends Expression {
    private final String name;
    private Entity entity;

    public Reference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
