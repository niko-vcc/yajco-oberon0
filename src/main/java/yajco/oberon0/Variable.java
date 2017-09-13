package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.annotation.reference.Identifier;

public class Variable extends Declaration implements Memory {
    @Identifier
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Exclude
    public Variable(String name, String type) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
