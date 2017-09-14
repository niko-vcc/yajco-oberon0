package yajco.oberon0;

public class TypeReference extends Type {
    private String name;

    public TypeReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
