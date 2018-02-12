package yajco.oberon0.model.l3;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Token;
import yajco.oberon0.model.Declaration;
import yajco.oberon0.model.Declarations;
import yajco.oberon0.model.StatementSequence;
import yajco.oberon0.model.Type;

public class Procedure extends Declaration {
    private final FormalParameters parameters;
    private final Declarations declarations;
    private final StatementSequence statements;

    @Before("PROCEDURE")
    @After(";")
    public Procedure(
            String name,
            @After(";") FormalParameters parameters,
            Declarations declarations,
            @Before("END") @Token("name") String repeatedName) {
        this(name, parameters, declarations, null, repeatedName);
    }

    @Before("PROCEDURE")
    @After(";")
    public Procedure(
            String name,
            @After(";") FormalParameters parameters,
            Declarations declarations,
            @Before("BEGIN") StatementSequence statements,
            @Before("END") @Token("name") String repeatedName) {
        super(name);
        this.parameters = parameters;
        this.statements = statements;
        this.declarations = declarations;
        if (!name.equals(repeatedName)) {
            throw new RuntimeException("Unmatching procedure name at the end.");
        }
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String getRepeatedName() {
        return getName();
    }

    public StatementSequence getStatements() {
        return statements;
    }

    public Declarations getDeclarations() {
        return declarations;
    }

    public FormalParameters getParameters() {
        return parameters;
    }
}
