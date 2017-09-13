package yajco.oberon0;

import yajco.annotation.After;
import yajco.annotation.Before;
import yajco.annotation.Token;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private List<Declaration> declarations = new ArrayList<>();
    private StatementSequence statements;

    @Before("MODULE")
    public Module(
            @After(";") String name,
            ConstantsDeclaration constants,
            VariablesDeclaration variables,
            @Token("name") @Before("END") @After(".") String nameRepeated) {
        if (!name.equals(nameRepeated)) {
            throw new RuntimeException("Unmatching module name at the end.");
        }
        this.name = name;
        this.declarations.addAll(constants.getDeclarations());
        this.declarations.addAll(variables.getVariables());
    }

    @Before("MODULE")
    public Module(
            @After(";") String name,
            ConstantsDeclaration constants,
            VariablesDeclaration variables,
            @Before("BEGIN") StatementSequence statements,
            @Token("name") @Before("END") @After(".") String nameRepeated) {
        this(name, constants, variables, nameRepeated);
        this.statements = statements;
    }

    public String getName() {
        return name;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public StatementSequence getStatements() {
        return statements;
    }

    public void setStatements(StatementSequence statements) {
        this.statements = statements;
    }
}
