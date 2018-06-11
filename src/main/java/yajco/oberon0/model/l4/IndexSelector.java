package yajco.oberon0.model.l4;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.oberon0.model.Expression;

public class IndexSelector implements Selector {
    private Expression index;

    @Before("[")
    @After("]")
    public IndexSelector(Expression index) {
        this.index = index;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }
}
