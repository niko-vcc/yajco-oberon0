package yajco.oberon0.model.l2;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.oberon0.model.Assignment;
import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Statement;
import yajco.oberon0.model.StatementSequence;

public class ForStatement extends Statement {

    private Assignment assignment;
    private Expression target;
    private StatementSequence body;

    @Before("FOR") @After("END")
    public ForStatement(
            Assignment assignment,
            @Before("TO") Expression target,
            @Before("DO") StatementSequence body) {
        this.assignment = assignment;
        this.target = target;
        this.body = body;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Expression getTarget() {
        return target;
    }

    public StatementSequence getBody() {
        return body;
    }

}
