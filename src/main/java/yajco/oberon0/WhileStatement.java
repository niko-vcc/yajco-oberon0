package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.oberon0.expressions.Expression;

public class WhileStatement extends Statement {
    private Expression condition;
    private StatementSequence body;

    @Before("WHILE") @After("END")
    public WhileStatement(
            Expression condition,
            @Before("DO") StatementSequence body) {
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public StatementSequence getBody() {
        return body;
    }
}
