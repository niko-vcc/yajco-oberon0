package yajco.oberon0.model;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Token;

public class Module {
    private String name;
    private Declarations declarations;
    private StatementSequence statements;

    @Before("MODULE")
    public Module(
            @After(";") String name,
            Declarations declarations,
            @Token("name") @Before("END") @After(".") String nameRepeated) {
        if (!name.equals(nameRepeated)) {
            throw new RuntimeException("Unmatching module name at the end.");
        }
        this.name = name;
        this.declarations = declarations;
    }

    @Before("MODULE")
    public Module(
            @After(";") String name,
            Declarations declarations,
            @Before("BEGIN") StatementSequence statements,
            @Token("name") @Before("END") @After(".") String nameRepeated) {
        this(name, declarations, nameRepeated);
        this.statements = statements;
    }

    public String getName() {
        return name;
    }

    public Declarations getDeclarations() {
        return declarations;
    }

    public StatementSequence getStatements() {
        return statements;
    }
}
