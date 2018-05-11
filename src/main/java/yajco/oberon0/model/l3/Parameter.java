package yajco.oberon0.model.l3;

import yajco.annotation.Exclude;
import yajco.oberon0.model.AbstractDeclaration;
import yajco.oberon0.model.Storage;
import yajco.oberon0.model.Type;

public class Parameter extends AbstractDeclaration implements Storage {
    private boolean variable;
    private Type type;

    public Parameter(String name) {
        super(name);
    }

    @Exclude
    public Parameter(String name, Type type, boolean isVariable) {
        super(name);
        this.type = type;
        this.variable = isVariable;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }
}
