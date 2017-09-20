package yajco.oberon0.model;

import yajco.annotation.Range;
import yajco.annotation.Separator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatementSequence extends ArrayList<Statement> {
    public static StatementSequence of(Statement statement) {
        return new StatementSequence(Collections.singletonList(statement));
    }

    public StatementSequence(
            @Separator(";") @Range(minOccurs = 1) List<Statement> statements) {
        addAll(statements);
    }

    public List<Statement> getStatements() {
        return this;
    }
}
