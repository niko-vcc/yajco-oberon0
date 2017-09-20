package yajco.oberon0.model.l2;

import yajco.annotation.Before;
import yajco.oberon0.model.Number;
import yajco.oberon0.model.Statement;

public class CaseBranch {
    private Number guard;
    private Statement statement;

    public CaseBranch(Number guard,
                      @Before(":") Statement statement) {
        this.guard = guard;
        this.statement = statement;
    }

    public Number getGuard() {
        return guard;
    }

    public Statement getStatement() {
        return statement;
    }
}
