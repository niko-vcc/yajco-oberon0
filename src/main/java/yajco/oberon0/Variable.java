package yajco.oberon0;

import yajco.annotation.Exclude;

public class Variable extends Entity {
    public Variable(String name) {
        super(name);
    }

    @Exclude
    public Variable(String name, String type) {
        super(name);
    }
}
