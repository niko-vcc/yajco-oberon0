package yajco.oberon0;

import yajco.annotation.Range;
import yajco.annotation.Separator;

import java.util.List;

public class StatementSequence {
    private List<Statement> statements;

    public StatementSequence(
            @Separator(";") @Range(minOccurs = 1) List<Statement> statements) {
        this.statements = statements;
    }

    public int size() {
        return statements.size();
    }

    public Statement get(int i) {
        return statements.get(i);
    }
}
