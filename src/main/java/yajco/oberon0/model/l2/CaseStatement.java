package yajco.oberon0.model.l2;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Range;
import yajco.annotation.Separator;
import yajco.oberon0.model.Expression;
import yajco.oberon0.model.Statement;

import java.util.List;

public class CaseStatement extends Statement {
    private Expression expression;
    private List<CaseBranch> branches;

    @Before("CASE") @After("END")
    public CaseStatement(
            Expression expression,
            @Before("OF") @Separator(";") @Range(minOccurs = 1) List<CaseBranch> branches) {
        this.expression = expression;
        this.branches = branches;
    }

    public Expression getExpression() {
        return expression;
    }

    public List<CaseBranch> getBranches() {
        return branches;
    }
}
