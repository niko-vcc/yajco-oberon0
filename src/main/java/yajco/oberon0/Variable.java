package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.annotation.reference.Identifier;

public class Variable extends Declaration implements Memory {
    public Variable(String name) {
        super(name);
    }

    @Exclude
    public Variable(String name, String type) {
        super(name);
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
