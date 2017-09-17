package yajco.oberon0.model;

import java.util.Collections;

import yajco.annotation.After;
import yajco.annotation.Before;

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

    @Before("IF") @After("END")
    public IfStatement(
            Expression condition,
            @Before("THEN") StatementSequence thenBranch,
            ElsifFragment elsif) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = new StatementSequence(Collections.singletonList(elsif.getStatement()));
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

    public ElsifFragment getElsif() {
        return null;
    }
}
