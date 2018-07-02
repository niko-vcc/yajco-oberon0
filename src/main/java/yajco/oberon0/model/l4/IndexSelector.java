package yajco.oberon0.model.l4;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Reference;
import yajco.oberon0.model.Type;

public class IndexSelector extends Reference {
    private Reference base;
    private Expression index;


    public IndexSelector(Reference base,
                         @Before("[") @After("]") Expression index) {
        super(base.getName());
        this.base = base;
        this.index = index;
    }

    public Reference getBase() {
        return base;
    }

    public void setBase(Reference base) {
        this.base = base;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

    @Override
    public Type getType() {
        ArrayType arrayType = (ArrayType) base.getType();
        return arrayType.getElementType();
    }
}
