package yajco.oberon0;

import org.junit.Before;
import org.junit.Test;
import yajco.oberon0.model.*;
import yajco.oberon0.model.Number;
import yajco.oberon0.model.l2.CaseBranch;
import yajco.oberon0.model.l2.CaseStatement;
import yajco.oberon0.model.l2.ForStatement;
import yajco.oberon0.model.operators.Equals;
import yajco.oberon0.model.operators.LessEquals;
import yajco.oberon0.model.parser.LALRModuleParser;
import yajco.oberon0.model.parser.ParseException;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class L2Test {
    private LALRModuleParser parser;

    @Before
    public void createParser() {
        parser = new LALRModuleParser();
    }

    @Test
    public void forStatement() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x, y: INTEGER; BEGIN\n"
              + "  FOR x := 1 TO 10 DO y := x * 3 END\n"
              + "END Test.");
        NamesResolver.resolve(module);
        assertThat(module.getStatements().get(0), instanceOf(ForStatement.class));
        ForStatement forStatement = (ForStatement) module.getStatements().get(0);
        assertThat(forStatement.getAssignment().getVariable().getName(), is("x"));
        assertThat(forStatement.getTarget(), instanceOf(Number.class));
        assertThat(((Number) forStatement.getTarget()).getValue(), is(10));
        assertThat(forStatement.getBody().get(0), instanceOf(Assignment.class));
    }

    @Test
    public void caseStatement() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x, y: INTEGER; BEGIN\n"
              + "  CASE x OF\n"
              + "    1: y := 1;\n"
              + "    2: y := 5;\n"
              + "    3: y := 20\n"
              + "  END\n"
              + "END Test.");
        NamesResolver.resolve(module);
        assertThat(module.getStatements().get(0), instanceOf(CaseStatement.class));
        CaseStatement caseStatement = (CaseStatement) module.getStatements().get(0);
        assertThat(caseStatement.getExpression(), instanceOf(Reference.class));
        Reference reference = (Reference) caseStatement.getExpression();
        assertThat(reference.getDeclaration().getName(), is("x"));

        assertThat(caseStatement.getBranches(), hasSize(3));
        CaseBranch firstBranch = caseStatement.getBranches().get(0);
        assertThat(firstBranch.getGuard(), instanceOf(Number.class));
        assertThat(firstBranch.getStatement(), instanceOf(Assignment.class));
    }

    @Test
    public void forTypeChecking() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x, y: INTEGER; BEGIN\n"
              + "  FOR x := 1 TO 10 DO y := x * 3 END\n"
              + "END Test.");
        NamesResolver.resolve(module);
        List<ParserError> errors = L2TypeChecker.check(module);
        assertThat(errors, hasSize(0));
    }

    @Test
    public void forAssignmentTypeError() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x: BOOLEAN; y: INTEGER; BEGIN\n"
              + "  FOR x := 1 TO 10 DO y := 3 END\n"
              + "END Test.");
        NamesResolver.resolve(module);
        List<ParserError> errors = L2TypeChecker.check(module);
        assertThat(errors, hasSize(1));
    }

    @Test
    public void forTargetTypeError() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x, y: INTEGER; BEGIN\n"
              + "  FOR x := 1 TO TRUE DO y := 3 END\n"
              + "END Test.");
        NamesResolver.resolve(module);
        List<ParserError> errors = L2TypeChecker.check(module);
        assertThat(errors, hasSize(1));
    }

    @Test
    public void forDesugaring() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x, y: INTEGER; BEGIN\n"
              + "  FOR x := 1 TO 10 DO y := x * 3 END\n"
              + "END Test.");
        NamesResolver.resolve(module);
        ForStatement forStmt = (ForStatement) module.getStatements().get(0);
        Variable counter = forStmt.getAssignment().getVariable();

        L2Transformer.transform(module);
        assertThat(module.getStatements().get(0), is(forStmt.getAssignment()));
        assertThat(module.getStatements().get(1), instanceOf(WhileStatement.class));
        WhileStatement whileStmt = (WhileStatement) module.getStatements().get(1);
        LessEquals condition = (LessEquals) whileStmt.getCondition();
        assertThat(((Reference) condition.getLeft()).getDeclaration(), is(counter));
        assertThat(whileStmt.getBody(), hasSize(2));
        Assignment increment = (Assignment) whileStmt.getBody().get(1);
        assertThat(increment.getVariable(), is(counter));
    }

    @Test
    public void caseDesugaring() throws ParseException {
        Module module = parser.parse(
                "MODULE Test; VAR x, y: INTEGER; BEGIN\n"
              + "  CASE x OF\n"
              + "    1: y := 1;\n"
              + "    2: y := 5;\n"
              + "    3: y := 20\n"
              + "  END\n"
              + "END Test.");
        NamesResolver.resolve(module);
        CaseStatement caseStmt = (CaseStatement) module.getStatements().get(0);
        Expression key = caseStmt.getExpression();

        L2Transformer.transform(module);
        assertThat(module.getStatements().get(0), instanceOf(IfStatement.class));
        IfStatement ifStmt = (IfStatement) module.getStatements().get(0);
        assertThat(((Equals) ifStmt.getCondition()).getLeft(), is(key));
        assertThat(((Equals) ifStmt.getCondition()).getRight(), is(caseStmt.getBranches().get(0).getGuard()));
        assertThat(ifStmt.getThenBranch().get(0), is(caseStmt.getBranches().get(0).getStatement()));
        assertThat(ifStmt.getElseBranch().getStatements().get(0), instanceOf(IfStatement.class));
    }
}
