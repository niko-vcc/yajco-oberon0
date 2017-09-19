package yajco.oberon0.model;

public class Type {
    public static Type INTEGER = new Type("INTEGER");
    public static Type BOOLEAN = new Type("BOOLEAN");

    private String name;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        return name != null ? name.equals(type.name) : type.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
