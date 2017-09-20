package yajco.oberon0;

import yajco.annotation.Exclude;
import yajco.oberon0.model.*;
import yajco.oberon0.model.Number;
import yajco.oberon0.model.l2.CaseBranch;
import yajco.oberon0.model.l2.CaseStatement;
import yajco.oberon0.model.l2.ForStatement;
import yajco.oberon0.model.operators.Add;
import yajco.oberon0.model.operators.Equals;
import yajco.oberon0.model.operators.LessEquals;
import yajco.oberon0.model.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

@Exclude
public class L2Transformer extends Visitor<Object> {
    public static void transform(Module module) {
        new L2Transformer().visit(module, null);
    }

    @Override
    protected void visitStatementSequence(StatementSequence statementSequence, Object o) {
        for (int i = 0; i < statementSequence.size(); i++) {
            Statement statement = statementSequence.get(i);
            if (statement instanceof ForStatement) {
                statementSequence.remove(i);
                statementSequence.addAll(i, desugarFor((ForStatement) statement));
            } else if (statement instanceof CaseStatement) {
                statementSequence.set(i, desugarCase((CaseStatement) statement));
            }
        }
        super.visitStatementSequence(statementSequence, o);
    }

    private IfStatement desugarCase(CaseStatement caseStatement) {
        IfStatement ifStatement = null;
        List<CaseBranch> branches = caseStatement.getBranches();
        for (int i = branches.size()-1; i >= 0; i--) {
            CaseBranch branch = branches.get(i);
            ifStatement = new IfStatement(
                    new Equals(caseStatement.getExpression(), branch.getGuard()),
                    StatementSequence.of(branch.getStatement()),
                    StatementSequence.of(ifStatement));
        }
        return ifStatement;
    }

    private List<Statement> desugarFor(ForStatement forStatement) {
        Variable counter = forStatement.getAssignment().getVariable();
        ArrayList<Statement> statements = new ArrayList<>();
        statements.add(forStatement.getAssignment());
        Expression condition = new LessEquals(new Reference(counter), forStatement.getTarget());
        StatementSequence body = forStatement.getBody();
        body.add(new Assignment(counter, new Add(new Reference(counter), new Number(1))));
        statements.add(new WhileStatement(condition, body));
        return statements;
    }
}
