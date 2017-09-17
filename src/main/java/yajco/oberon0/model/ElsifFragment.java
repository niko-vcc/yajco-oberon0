package yajco.oberon0.model;

import yajco.annotation.Before;

public class ElsifFragment {
    private IfStatement statement;

    @Before("ELSIF")
    public ElsifFragment(Expression condition,
            @Before("THEN") StatementSequence thenBranch) {
        this.statement = new IfStatement(condition, thenBranch);
    }

    @Before("ELSIF")
    public ElsifFragment(Expression condition,
            @Before("THEN") StatementSequence thenBranch,
            @Before("ELSE") StatementSequence elseBranch) {
        this.statement = new IfStatement(condition, thenBranch, elseBranch);
    }

    public Statement getStatement() {
        return statement;
    }

    public Expression getCondition() {
        return statement.getCondition();
    }

    public StatementSequence getThenBranch() {
        return statement.getThenBranch();
    }

    public StatementSequence getElseBranch() {
        return statement.getElseBranch();
    }

    public ElsifFragment getElsif() {
        return statement.getElsif();
    }
}
