package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.oberon0.expressions.Expression;

public class IfStatement extends Statement {
    private Expression condition;
    private StatementSequence thenBranch, elseBranch;

    @Before("IF") @After("END")
    public IfStatement(
            Expression condition,
            @Before("THEN") StatementSequence thenBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
    }

    @Before("IF") @After("END")
    public IfStatement(
            Expression condition,
            @Before("THEN") StatementSequence thenBranch,
            @Before("ELSE") StatementSequence elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public Expression getCondition() {
        return condition;
    }

    public StatementSequence getThenBranch() {
        return thenBranch;
    }

    public StatementSequence getElseBranch() {
        return elseBranch;
    }
}
